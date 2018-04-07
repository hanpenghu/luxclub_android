package com.hsbank.luxclub.view.manager.lock_pattern;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.mywidget.lock_view.LockPatternView;
import com.hsbank.luxclub.mywidget.lock_view.ProjectLockPatternUtil;
import com.hsbank.luxclub.util.AlertSuccess;
import com.hsbank.luxclub.util.ProjectConstant;
import com.hsbank.luxclub.util.ProjectUtil;
import com.hsbank.luxclub.view.main.my.VerifyPasswordActivity;
import com.hsbank.luxclub.view.main.my.event.MyEvent;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.util.android.AndroidAppInfo;
import com.hsbank.util.android.util.SharedPreferencesUtil;
import com.hsbank.util.project.util.BaseProjectEvent;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 解锁手势密码
 * 2014-01-14
 */
public class LockPatternUnlockActivity extends ProjectBaseActivity {
    private int mFailedPatternAttemptsSinceLastTimeout = 0;
    private CountDownTimer mCountdownTimer = null;
    private Animation mShakeAnim;
    private Toast mToast;
    private LockPatternView mLockPatternView = null;
    private TextView mHeadTextView;
    /**进去解锁解锁手势页面有三种情况：1.为了关闭手势而验证2.为了修改手势验证3.从后台进入前台超过10秒等待时间*/
    private String unLockType = "";

    private void showToast(CharSequence message) {
        if (null == mToast) {
            mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast.setText(message);
        }

        mToast.show();
    }

    private static CheckSucc checkSucc = null;

    /**Handler*/
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
            }
            super.handleMessage(message);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewHandler();
        unLockType = getIntent().getStringExtra("unLockType");
        if(unLockType.equals("updatePassword")||unLockType.equals("toOffPassword")){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }else{
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        if(unLockType.equals("updatePassword")){
           mViewHelper.setText(R.id.toolbar_title,getString(R.string.txt_update_gesture));
            mHeadTextView.setText(R.string.txt_please_gesture_password);
        }else if(unLockType.equals("toOffPassword")){
            mViewHelper.setText(R.id.toolbar_title,getString(R.string.txt_to_off_password));
            mHeadTextView.setText(R.string.txt_please_gesture_password);
        }else{
            mViewHelper.setText(R.id.toolbar_title, getString(R.string.txt_gesture_verification));
            mHeadTextView.setText(R.string.txt_please_input_gesture_password);
        }
    }

    protected void viewHandler() {
        mLockPatternView = mViewHelper.getView(R.id.lockview);
        mLockPatternView.setOnPatternListener(mChooseNewLockPatternListener);
        mLockPatternView.setTactileFeedbackEnabled(true);
        mHeadTextView =mViewHelper.getView(R.id.txtGesture);
        mShakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake_x);
        mViewHelper.getView(R.id.forgetPassword).setOnClickListener(clickListener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_lock_pattern_unlock;
    }

    /**
     * EventBus消息处理器
     * @param event
     */
    public void onEventMainThread(BaseProjectEvent event) {
        if (event.getCommand().equals(MyEvent.COMMAND_FINISH)) {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ProjectLockPatternUtil lockPatternUtils = new ProjectLockPatternUtil(this);
        if (!lockPatternUtils.savedPatternExists()) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountdownTimer != null)
            mCountdownTimer.cancel();
    }

    private Runnable mClearPatternRunnable = new Runnable() {
        public void run() {
            mLockPatternView.clearPattern();
        }
    };

    /**
     * 界面所有控件的点击事件
     */
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.forgetPassword:
                    /**参数：代表忘记手势密码手势*/
                    if(unLockType.equals("forgetPassword")){
                        VerifyPasswordActivity.show(LockPatternUnlockActivity.this, "forgetPassword");
                    }else if(unLockType.equals("cardPassword")){
                        VerifyPasswordActivity.show(LockPatternUnlockActivity.this, "cardPassword");
                    }else if(unLockType.equals("updatePassword")){
                        VerifyPasswordActivity.show(LockPatternUnlockActivity.this, "updatePassword");
                    }else if(unLockType.equals("toOffPassword")){
                        VerifyPasswordActivity.show(LockPatternUnlockActivity.this, "toOffPassword");
                    }else{
                        VerifyPasswordActivity.show(LockPatternUnlockActivity.this, "");
                    }
                    break;
            }
        }
    };

    /**
     * 显示界面，需要传一些特定参数
     *
     * @param context
     */
    public static void show(Context context,String unLockType) {
        String md5PointValues = SharedPreferencesUtil.getInstance().getString(ProjectConstant.MD_5_STRING, "");
        if (ProjectUtil.isLogin() && !md5PointValues.equals("")) {
            Intent intent = new Intent();
            intent.putExtra("unLockType",unLockType);
            intent.setClass(context, LockPatternUnlockActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    /**
     * 显示界面，需要传一些特定参数
     *
     * @param context
     * @param checkSuccFun
     */
    public static void show(Context context, CheckSucc checkSuccFun) {
        checkSucc = checkSuccFun;
        String md5PointValues = SharedPreferencesUtil.getInstance().getString(ProjectConstant.MD_5_STRING, "");
        if (!SharedPreferencesUtil.getInstance().getToken().isEmpty() && !md5PointValues.equals("") && AndroidAppInfo.getInstance().isNeedAuthGesture()) {
            Intent intent = new Intent();
            intent.setClass(context, LockPatternUnlockActivity.class);
            context.startActivity(intent);
        } else {
            if (checkSucc != null) {
                checkSucc.checkSucc();
                checkSucc = null;
            }
        }
    }

    /**
     * 关闭界面
     */
    public static void close() {
        EventBus.getDefault().post(new MyEvent(MyEvent.COMMAND_FINISH));
    }

    protected LockPatternView.OnPatternListener mChooseNewLockPatternListener = new LockPatternView.OnPatternListener() {

        public void onPatternStart() {
            mLockPatternView.removeCallbacks(mClearPatternRunnable);
            patternInProgress();
        }

        public void onPatternCleared() {
            mLockPatternView.removeCallbacks(mClearPatternRunnable);
        }

        public void onPatternDetected(List<LockPatternView.Cell> pattern) {
            if (pattern == null)
                return;
            ProjectLockPatternUtil lockPatternUtils = new ProjectLockPatternUtil(getApplicationContext());
            if (lockPatternUtils.checkPattern(pattern)) {
                mLockPatternView
                        .setDisplayMode(LockPatternView.DisplayMode.Correct);
                AndroidAppInfo.getInstance().setNeedAuthGesture(false);
                if (checkSucc != null) {
                    checkSucc.checkSucc();
                    checkSucc = null;
                }
                if(unLockType.equals("toOffPassword")){
                    SharedPreferencesUtil.getInstance().setString(ProjectConstant.MD_5_STRING, "");
                    final AlertSuccess dialog = new AlertSuccess.Builder(mContext,getString(R.string.txt_verification_success)).create();
                    dialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.cancel();
                            finish();
                        }
                    }, 1500);
                }else if(unLockType.equals("updatePassword")){
                    final AlertSuccess dialog = new AlertSuccess.Builder(mContext,getString(R.string.txt_verification_success)).create();
                    dialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.cancel();
                            LockPatternSetActivity.show(mContext,"false");
                            finish();
                        }
                    }, 1500);
                }else{
                    final AlertSuccess dialog = new AlertSuccess.Builder(mContext,getString(R.string.txt_verification_success)).create();
                    dialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.cancel();
                            finish();
                        }
                    }, 1500);
                }
            } else {
                mLockPatternView
                        .setDisplayMode(LockPatternView.DisplayMode.Wrong);
                if (pattern.size() >= ProjectLockPatternUtil.MIN_PATTERN_REGISTER_FAIL) {
                    mFailedPatternAttemptsSinceLastTimeout++;
                    int retry = ProjectLockPatternUtil.FAILED_ATTEMPTS_BEFORE_TIMEOUT
                            - mFailedPatternAttemptsSinceLastTimeout;
                    if (retry >= 0) {
                        if (retry == 0)
                            showToast(getString(R.string.util_lockpattern_confirm_restart));
                        mHeadTextView.setText(getString(R.string.util_lockpattern_confirm_password_error)
                                + retry + getString(R.string.util_lockpattern_confirm_forget_num));
                        mHeadTextView.setTextColor(Color.RED);
                        mHeadTextView.startAnimation(mShakeAnim);
                    }

                } else {
                    showToast(getString(R.string.util_lockpattern_confirm_forget_lenght_error));
                }

                if (mFailedPatternAttemptsSinceLastTimeout >= ProjectLockPatternUtil.FAILED_ATTEMPTS_BEFORE_TIMEOUT) {
                    mHandler.postDelayed(attemptLockout, 2000);
                } else {
                    mLockPatternView.postDelayed(mClearPatternRunnable, 2000);
                }
            }
        }

        public void onPatternCellAdded(List<LockPatternView.Cell> pattern) {
        }

        private void patternInProgress() {
        }
    };
    Runnable attemptLockout = new Runnable() {

        @Override
        public void run() {
            mLockPatternView.clearPattern();
            mLockPatternView.setEnabled(false);
            mCountdownTimer = new CountDownTimer(
                    ProjectLockPatternUtil.FAILED_ATTEMPT_TIMEOUT_MS + 1, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    int secondsRemaining = (int) (millisUntilFinished / 1000) - 1;
                    if (secondsRemaining > 0) {
                        mHeadTextView.setText(secondsRemaining + getString(R.string.util_lockpattern_continue_time));
                    } else {
                        mHeadTextView.setText(getString(R.string.util_lockpattern_continue_gesture));
                        mHeadTextView.setTextColor(Color.WHITE);
                    }

                }

                @Override
                public void onFinish() {
                    mLockPatternView.setEnabled(true);
                    mFailedPatternAttemptsSinceLastTimeout = 0;
                }
            }.start();
        }
    };

    public interface CheckSucc {
        void checkSucc();
    }
}
