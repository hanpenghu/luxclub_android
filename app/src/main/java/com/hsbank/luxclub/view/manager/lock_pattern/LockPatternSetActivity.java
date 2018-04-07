package com.hsbank.luxclub.view.manager.lock_pattern;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.mywidget.lock_view.LockPatternView;
import com.hsbank.luxclub.mywidget.lock_view.ProjectLockPatternUtil;
import com.hsbank.luxclub.util.AlertSuccess;
import com.hsbank.luxclub.util.ProjectConstant;
import com.hsbank.luxclub.view.main.MainActivity;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.util.android.AndroidAppInfo;
import com.hsbank.util.android.util.SharedPreferencesUtil;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;


/**
 * 设置手势密码
 * 2016-01-14
 */
public class LockPatternSetActivity extends ProjectBaseActivity {
    public static final String MESSAGE_DIGEST_ALGORITHM_MD5 = "MD5";
    private static final int ID_EMPTY_MESSAGE = -1;
    private static final String KEY_UI_STAGE = "uiStage";
    private static final String KEY_PATTERN_CHOICE = "chosenPattern";
    private LockPatternView mLockPatternView;
    private Button mFooterRightButton;
//    private RelativeLayout mFooterLeftButton;
    protected TextView mHeaderText;
    protected List<LockPatternView.Cell> mChosenPattern = null;
    private Stage mUiStage = Stage.Introduction;
    private View mPreviewViews[][] = new View[3][3];
    /**
     * The patten used during the help screen to show how to draw a pattern.
     */
    private final List<LockPatternView.Cell> cellList = new ArrayList<LockPatternView.Cell>();

    /**
     * The states of the left footer button.
     */
    enum LeftButtonMode {
        Cancel(android.R.string.cancel, true), CancelDisabled(
                android.R.string.cancel, false), Retry(
                R.string.util_lockpattern_retry_button_text, true), RetryDisabled(
                R.string.util_lockpattern_retry_button_text, false), Gone(
                ID_EMPTY_MESSAGE, false);

        /**
         * @param text    The displayed text for this mode.
         * @param enabled Whether the button should be enabled.
         */
        LeftButtonMode(int text, boolean enabled) {
            this.text = text;
            this.enabled = enabled;
        }

        final int text;
        final boolean enabled;
    }

    /**
     * The states of the right button.
     */
    enum RightButtonMode {
        Continue(R.string.util_lockpattern_continue_button_text, true), ContinueDisabled(
                R.string.util_lockpattern_continue_button_text, false), Confirm(
                R.string.util_lockpattern_confirm_button_text, true), ConfirmDisabled(
                R.string.util_lockpattern_confirm_button_text, false), Ok(
                android.R.string.ok, true);

        /**
         * @param text    The displayed text for this mode.
         * @param enabled Whether the button should be enabled.
         */
        RightButtonMode(int text, boolean enabled) {
            this.text = text;
            this.enabled = enabled;
        }

        final int text;
        final boolean enabled;
    }

    /**
     * Keep track internally of where the user is in choosing a pattern.
     */
    protected enum Stage {

        Introduction(com.hsbank.luxclub.R.string.txt_input_gesture,
                LeftButtonMode.Cancel, RightButtonMode.ContinueDisabled,
                ID_EMPTY_MESSAGE, true), HelpScreen(
                R.string.util_lockpattern_settings_help_how_to_record,
                LeftButtonMode.Gone, RightButtonMode.Ok, ID_EMPTY_MESSAGE,
                false), ChoiceTooShort(
                R.string.util_lockpattern_recording_incorrect_too_short,
                LeftButtonMode.Retry, RightButtonMode.ContinueDisabled,
                ID_EMPTY_MESSAGE, true), FirstChoiceValid(
                R.string.util_lockpattern_pattern_entered_header,
                LeftButtonMode.Retry, RightButtonMode.Continue,
                ID_EMPTY_MESSAGE, false), NeedToConfirm(
                com.hsbank.luxclub.R.string.lockpattern_need_to_confirm, LeftButtonMode.Cancel,
                RightButtonMode.ConfirmDisabled, ID_EMPTY_MESSAGE, true), ConfirmWrong(
                R.string.util_lockpattern_need_to_unlock_wrong,
                LeftButtonMode.Cancel, RightButtonMode.ConfirmDisabled,
                ID_EMPTY_MESSAGE, true), ChoiceConfirmed(
                R.string.util_lockpattern_pattern_confirmed_header,
                LeftButtonMode.Cancel, RightButtonMode.Confirm,
                ID_EMPTY_MESSAGE, false);

        /**
         * @param headerMessage  The message displayed at the top.
         * @param leftMode       The mode of the left button.
         * @param rightMode      The mode of the right button.
         * @param footerMessage  The footer message.
         * @param patternEnabled Whether the pattern widget is enabled.
         */
        Stage(int headerMessage, LeftButtonMode leftMode,
              RightButtonMode rightMode, int footerMessage,
              boolean patternEnabled) {
            this.headerMessage = headerMessage;
            this.leftMode = leftMode;
            this.rightMode = rightMode;
            this.footerMessage = footerMessage;
            this.patternEnabled = patternEnabled;
        }

        final int headerMessage;
        final LeftButtonMode leftMode;
        final RightButtonMode rightMode;
        final int footerMessage;
        final boolean patternEnabled;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewHelper.setText(R.id.toolbar_title,getString(R.string.txt_set_gesture));
        mLockPatternView =mViewHelper.getView (R.id.lockview);
        mHeaderText = mViewHelper.getView(R.id.txtGesture);
        mLockPatternView.setOnPatternListener(mChooseNewLockPatternListener);
        mLockPatternView.setTactileFeedbackEnabled(true);
        mFooterRightButton = mViewHelper.getView(R.id.right);
//        mFooterLeftButton = mViewHelper.getView(R.id.reset);
        mFooterRightButton.setOnClickListener(clickListener);
//        mFooterLeftButton.setOnClickListener(clickListener);
        initPreviewViews();
        if (savedInstanceState == null) {
            updateStage(Stage.Introduction);
        } else {
            final String patternString = savedInstanceState.getString(KEY_PATTERN_CHOICE);
            if (patternString != null) {
                mChosenPattern = ProjectLockPatternUtil
                        .stringToPattern(patternString);
            }
            updateStage(Stage.values()[savedInstanceState.getInt(KEY_UI_STAGE)]);
        }
        // 数据初始化
        cellList.add(LockPatternView.Cell.of(0, 0));
        cellList.add(LockPatternView.Cell.of(0, 1));
        cellList.add(LockPatternView.Cell.of(1, 1));
        cellList.add(LockPatternView.Cell.of(2, 1));
        cellList.add(LockPatternView.Cell.of(2, 2));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_lock_pattern_set;
    }

    private void initPreviewViews() {
        mPreviewViews = new View[3][3];
        mPreviewViews[0][0] = findViewById(R.id.gesturepwd_setting_preview_0);
        mPreviewViews[0][1] = findViewById(R.id.gesturepwd_setting_preview_1);
        mPreviewViews[0][2] = findViewById(R.id.gesturepwd_setting_preview_2);
        mPreviewViews[1][0] = findViewById(R.id.gesturepwd_setting_preview_3);
        mPreviewViews[1][1] = findViewById(R.id.gesturepwd_setting_preview_4);
        mPreviewViews[1][2] = findViewById(R.id.gesturepwd_setting_preview_5);
        mPreviewViews[2][0] = findViewById(R.id.gesturepwd_setting_preview_6);
        mPreviewViews[2][1] = findViewById(R.id.gesturepwd_setting_preview_7);
        mPreviewViews[2][2] = findViewById(R.id.gesturepwd_setting_preview_8);
    }

    private void updatePreviewViews() {
        if (mChosenPattern == null)
            return;
        Log.i("way", "result = " + mChosenPattern.toString());
        for (LockPatternView.Cell cell : mChosenPattern) {
            Log.i("way", "cell.getRow() = " + cell.getRow()
                    + ", cell.getColumn() = " + cell.getColumn());
            mPreviewViews[cell.getRow()][cell.getColumn()]
                    .setBackgroundResource(R.drawable.shape_circle_solid_lock_pattern_set);

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_UI_STAGE, mUiStage.ordinal());
        if (mChosenPattern != null) {
            outState.putString(KEY_PATTERN_CHOICE,
                    ProjectLockPatternUtil.patternToString(mChosenPattern));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (mUiStage == Stage.HelpScreen) {
                updateStage(Stage.Introduction);
                return true;
            }
        }
        if (keyCode == KeyEvent.KEYCODE_MENU && mUiStage == Stage.Introduction) {
            updateStage(Stage.HelpScreen);
            return true;
        }
        return false;
    }

    private Runnable mClearPatternRunnable = new Runnable() {
        public void run() {
            mLockPatternView.clearPattern();
        }
    };

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
            // Log.i("way", "result = " + pattern.toString());
            if (mUiStage == Stage.NeedToConfirm
                    || mUiStage == Stage.ConfirmWrong) {
                if (mChosenPattern == null)
                    throw new IllegalStateException(
                            "null chosen pattern in stage 'need to confirm");
                if (mChosenPattern.equals(pattern)) {
                    updateStage(Stage.ChoiceConfirmed);
                } else {
                    updateStage(Stage.ConfirmWrong);
                }
            } else if (mUiStage == Stage.Introduction
                    || mUiStage == Stage.ChoiceTooShort) {
                if (pattern.size() < ProjectLockPatternUtil.MIN_LOCK_PATTERN_SIZE) {
                    updateStage(Stage.ChoiceTooShort);
                } else {
                    mChosenPattern = new ArrayList<LockPatternView.Cell>(
                            pattern);
                    updateStage(Stage.FirstChoiceValid);
                }
            } else {
                throw new IllegalStateException("Unexpected stage " + mUiStage
                        + " when " + "entering the pattern.");
            }
            if (mUiStage.rightMode == RightButtonMode.Continue) {
                if (mUiStage != Stage.FirstChoiceValid) {
                    throw new IllegalStateException("expected ui stage "
                            + Stage.FirstChoiceValid + " when button is "
                            + RightButtonMode.Continue);
                }
                updateStage(Stage.NeedToConfirm);
            } else if (mUiStage.rightMode == RightButtonMode.Confirm) {
                if (mUiStage != Stage.ChoiceConfirmed) {
                    throw new IllegalStateException("expected ui stage "
                            + Stage.ChoiceConfirmed + " when button is "
                            + RightButtonMode.Confirm);
                }
                saveChosenPatternAndFinish();
            } else if (mUiStage.rightMode == RightButtonMode.Ok) {
                if (mUiStage != Stage.HelpScreen) {
                    throw new IllegalStateException(
                            "Help screen is only mode with ok button, but "
                                    + "stage is " + mUiStage);
                }
                mLockPatternView.clearPattern();
                mLockPatternView.setDisplayMode(LockPatternView.DisplayMode.Correct);
                updateStage(Stage.Introduction);
            }
        }

        public void onPatternCellAdded(List<LockPatternView.Cell> pattern) {

        }

        private void patternInProgress() {
            mHeaderText.setTextColor(getResources().getColor(R.color.gray));
            mHeaderText.setText(R.string.util_lockpattern_recording_inprogress);
//            mFooterLeftButton.setEnabled(false);
            mFooterRightButton.setEnabled(false);
        }
    };

    private void updateStage(Stage stage) {
        mUiStage = stage;
        if (stage == Stage.ChoiceTooShort) {
            mHeaderText.setText(getResources().getString(stage.headerMessage,
                    ProjectLockPatternUtil.MIN_LOCK_PATTERN_SIZE));
            mHeaderText.setTextColor(getResources().getColor(R.color.gray));
        } else {
            mHeaderText.setText(stage.headerMessage);
            if(stage.equals(Stage.ConfirmWrong)){
                mHeaderText.setTextColor(getResources().getColor(R.color.red));
            }
        }

        if (stage.leftMode == LeftButtonMode.Gone) {
//            mFooterLeftButton.setVisibility(View.GONE);
        } else {
//            mFooterLeftButton.setVisibility(View.VISIBLE);
//            mFooterLeftButton.setText(stage.leftMode.text);
//            mFooterLeftButton.setEnabled(stage.leftMode.enabled);
        }

        mFooterRightButton.setText(stage.rightMode.text);
        mFooterRightButton.setEnabled(stage.rightMode.enabled);

        // same for whether the patten is enabled
        if (stage.patternEnabled) {
            mLockPatternView.enableInput();
        } else {
            mLockPatternView.disableInput();
        }

        mLockPatternView.setDisplayMode(LockPatternView.DisplayMode.Correct);

        switch (mUiStage) {
            case Introduction:
                mLockPatternView.clearPattern();
                break;
            case ChoiceTooShort:
                mLockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
                postClearPatternRunnable();
                break;
            case FirstChoiceValid:
                break;
            case NeedToConfirm:
                mLockPatternView.clearPattern();
                updatePreviewViews();
                break;
            case ConfirmWrong:
                mLockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
                postClearPatternRunnable();
                break;
            case ChoiceConfirmed:
                break;
        }

    }

    // clear the wrong pattern unless they have started a new one
    // already
    private void postClearPatternRunnable() {
        mLockPatternView.removeCallbacks(mClearPatternRunnable);
        mLockPatternView.postDelayed(mClearPatternRunnable, 2000);
    }

    private void saveChosenPatternAndFinish() {
        ProjectLockPatternUtil lockPatternUtils = new ProjectLockPatternUtil(this);
        lockPatternUtils.saveLockPattern(mChosenPattern);
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < mChosenPattern.size(); i++) {
            int Column = mChosenPattern.get(i).getColumn();
            int Row = mChosenPattern.get(i).getRow();
            stringBuffer.append(Column);
            stringBuffer.append(Row);
        }
        final String stringMd5 = stringToMD5(stringBuffer.toString());
        SharedPreferencesUtil.getInstance().setString(ProjectConstant.MD_5_STRING, stringMd5);
        final AlertSuccess dialog = new AlertSuccess.Builder(mContext,getString(R.string.txt_set_success)).create();
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String isFirst = getIntent().getStringExtra("isFirst");
                if(isFirst.equals("true")){
                    AndroidAppInfo.getInstance().setNeedAuthGesture(false);
                    gotoMain();
                }
                dialog.cancel();
                finish();
            }
        }, 1500);
    }

    /**跳转到主页*/
    private void gotoMain() {
        Intent intent = new Intent(LockPatternSetActivity.this, MainActivity.class);
        /**从启动页进入主页如果设置了手势密码就会显示解锁手势密码页面*/
        intent.putExtra("entrance","");
        LockPatternSetActivity.this.startActivity(intent);
        LockPatternSetActivity.this.finish();
    }

    public static String stringToMD5(String string) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] stringByte = string.getBytes();
            MessageDigest messageDigest = MessageDigest.getInstance(MESSAGE_DIGEST_ALGORITHM_MD5);
            messageDigest.update(stringByte);
            byte[] md5Byte = messageDigest.digest();
            int j = md5Byte.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byteValue = md5Byte[i];
                str[k++] = hexDigits[byteValue >>> 4 & 0xf];
                str[k++] = hexDigits[byteValue & 0xf];
            }
            return (new String(str));
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 界面所有控件的点击事件
     */
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
//                case R.id.reset:
//                    if (mUiStage.leftMode == LeftButtonMode.Retry) {
//                        mChosenPattern = null;
//                        mLockPatternView.clearPattern();
//                        updateStage(Stage.Introduction);
//                    } else if (mUiStage.leftMode == LeftButtonMode.Cancel) {
//                        // They are canceling the entire wizard
//                        finish();
//                    } else {
//                        throw new IllegalStateException(
//                                "left footer button pressed, but stage of " + mUiStage
//                                        + " doesn't make sense");
//                    }
//                    break;
                case R.id.right:
                    if (mUiStage.rightMode == RightButtonMode.Continue) {
                        if (mUiStage != Stage.FirstChoiceValid) {
                            throw new IllegalStateException("expected ui stage "
                                    + Stage.FirstChoiceValid + " when button is "
                                    + RightButtonMode.Continue);
                        }
                        updateStage(Stage.NeedToConfirm);
                    } else if (mUiStage.rightMode == RightButtonMode.Confirm) {
                        if (mUiStage != Stage.ChoiceConfirmed) {
                            throw new IllegalStateException("expected ui stage "
                                    + Stage.ChoiceConfirmed + " when button is "
                                    + RightButtonMode.Confirm);
                        }
                        saveChosenPatternAndFinish();
                    } else if (mUiStage.rightMode == RightButtonMode.Ok) {
                        if (mUiStage != Stage.HelpScreen) {
                            throw new IllegalStateException(
                                    "Help screen is only mode with ok button, but "
                                            + "stage is " + mUiStage);
                        }
                        mLockPatternView.clearPattern();
                        mLockPatternView.setDisplayMode(LockPatternView.DisplayMode.Correct);
                        updateStage(Stage.Introduction);
                    }
                    break;
            }
        }
    };

    /**
     * 显示
     * @param context
     * @param isFirst 是否是第一次安装时设置手势密码
     */
    public static void show(Context context,String isFirst) {
        Intent intent = new Intent(context, LockPatternSetActivity.class);
        intent.putExtra("isFirst",isFirst);
        context.startActivity(intent);
    }
}
