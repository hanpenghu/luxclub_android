package com.hsbank.luxclub.view.main.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.mywidget.password_view.GridPasswordView;
import com.hsbank.luxclub.mywidget.password_view.PasswordType;
import com.hsbank.luxclub.util.AlertFail;
import com.hsbank.luxclub.util.AlertSuccess;
import com.hsbank.luxclub.util.ProjectConstant;
import com.hsbank.luxclub.util.ProjectUtil;
import com.hsbank.luxclub.util.popupwindow.MyPopupWindow;
import com.hsbank.luxclub.view.main.my.event.MyEvent;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.luxclub.view.manager.lock_pattern.LockPatternSetActivity;
import com.hsbank.luxclub.view.manager.lock_pattern.LockPatternUnlockActivity;
import com.hsbank.util.android.util.AlertUtil;
import com.hsbank.util.android.util.SharedPreferencesUtil;
import com.hsbank.util.project.util.BaseProjectEvent;

/**
 * 验证卡片密码
 * name：zhuzhenghua
 * create time:
 */
public class VerifyPasswordActivity extends ProjectBaseActivity {
    /**判断是设置手势验证还是忘记手势密码验证*/
    private String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getStringExtra("type");
        if (type.equals("cardPassword")) {
            mViewHelper.setText(R.id.txt_title_gesture,getString(R.string.txt_for_input_gesture));
            mViewHelper.setText(R.id.toolbar_title,getString(R.string.txt_open_gesture_password));
        }else if(type.equals("forgetPassword")||type.equals("")){
            mViewHelper.setText(R.id.txt_title_gesture,getString(R.string.txt_for_input_gesture));
            mViewHelper.setText(R.id.toolbar_title,getString(R.string.txt_gesture_verification));
        }else if(type.equals("toOffPassword")){
            mViewHelper.setText(R.id.txt_title_gesture,getString(R.string.txt_for_input_gesture));
            mViewHelper.setText(R.id.toolbar_title,getString(R.string.txt_close_gesture_password));
        }else if(type.equals("updatePassword")){
            mViewHelper.setText(R.id.txt_title_gesture,getString(R.string.txt_for_input_gesture));
            mViewHelper.setText(R.id.toolbar_title,getString(R.string.txt_update_gesture_password));
        }
        viewHandler();
    }

    /**
     * 如果是派单状态或者提交状态编辑按钮显示，其他情况隐藏
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_forget_password, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 编辑按钮的点击事件
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_forget_password:
                View parentView = mViewHelper.getView(R.id.parentView);
                MyPopupWindow.showRechargeDiscount(mContext,parentView,getString(R.string.txt_forget_password_),
                        String.format(getString(R.string.txt_forget_password_content), getString(R.string.txt_service_number)));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 得到布局文件Id
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_verify_password;
    }

    /**
     * EventBus消息处理器
     * @param event
     */
    @Override
    public void onEventMainThread(BaseProjectEvent event) {
        super.onEventMainThread(event);
        if(event.getCommand().equals(MyEvent.COMMAND_MY_VERIFY_PASSWORD)){
        }
    }

    /**
     * 视图元素处理器
     */
    protected void viewHandler() {
        final GridPasswordView passwordView = mViewHelper.getView(R.id.verifyPasswordView);
        passwordView.setPasswordType(PasswordType.TEXT);
        passwordView.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {
                if (psw.length() == 6) {
                    if (psw.equals(ProjectUtil.getPassword())) {
                        final AlertSuccess dialog = new AlertSuccess.Builder(mContext,getString(R.string.txt_verification_success)).create();
                        dialog.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.cancel();
                                if (type.equals("cardPassword")) {
                                    LockPatternSetActivity.show(mContext,"false");
                                    LockPatternUnlockActivity.close();
                                    finish();
                                } else if (type.equals("forgetPassword")) {
                                    finish();
                                    LockPatternUnlockActivity.close();
                                    SharedPreferencesUtil.getInstance().setString(ProjectConstant.MD_5_STRING, "");
                                }else{
                                    LockPatternUnlockActivity.close();
                                    finish();
                                }
                            }
                        }, 1500);
                    } else {
                        final AlertFail dialog = new AlertFail.Builder(mContext,getString(R.string.txt_verification_fail)).create();
                        dialog.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.cancel();
                                AlertUtil.getInstance().alertMessage(VerifyPasswordActivity.this, getString(R.string.txt_card_password_bug));
                            }
                        }, 1500);
                    }
                }
            }

            @Override
            public void onInputFinish(String psw) {
            }
        });
    }

    public static void show(Context context,String type) {
        Intent intent = new Intent();
        intent.putExtra("type",type);
        intent.setClass(context, VerifyPasswordActivity.class);
        context.startActivity(intent);
    }
}
