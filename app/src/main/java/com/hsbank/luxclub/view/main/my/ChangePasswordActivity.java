package com.hsbank.luxclub.view.main.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.mywidget.setting_item.item.SwitchView;
import com.hsbank.luxclub.provider.ProviderFactory;
import com.hsbank.luxclub.util.AlertFail;
import com.hsbank.luxclub.util.AlertSuccess;
import com.hsbank.luxclub.util.ProjectConstant;
import com.hsbank.luxclub.util.ProjectUtil;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.util.android.util.AlertUtil;
import com.hsbank.util.android.util.JsonUtil;
import com.hsbank.util.android.util.SharedPreferencesUtil;
import com.hsbank.util.android.util.http.api.ApiCodeConstant;
import com.hsbank.util.android.util.http.api.ApiResponseBean;
import com.hsbank.util.android.util.http.okhttp.callback.ApiCallback;
import com.hsbank.util.java.collection.MapUtil;
import com.squareup.okhttp.Request;

import java.util.Map;

/**
 * 修改卡片密码
 * name：zhuzhenghua
 * create time:2016.3.14
 */
public class ChangePasswordActivity extends ProjectBaseActivity {
    /**
     * 新密码
     */
    private EditText mEdtNewPassword;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewHandler();
    }

    protected void viewHandler() {
        mViewHelper.setText(R.id.toolbar_title, getString(R.string.txt_modify_password));
        mViewHelper.getView(R.id.mBtnDetermine).setOnClickListener(clickListener);
        mViewHelper.getView(R.id.mImgDeleteOld).setOnClickListener(clickListener);
        mViewHelper.getView(R.id.mImgDeleteNew).setOnClickListener(clickListener);
        mViewHelper.getView(R.id.mImgDeleteOkNew).setOnClickListener(clickListener);
        /*显示密码开关*/
        SwitchView set_item_switch = (SwitchView) findViewById(R.id.set_item_switch);
        ((EditText) mViewHelper.getView(R.id.edt_old_password)).addTextChangedListener(changedListenerOld);
        ((EditText) mViewHelper.getView(R.id.edt_new_password)).addTextChangedListener(changedListenerNew);
        ((EditText) mViewHelper.getView(R.id.edt_confirm_password)).addTextChangedListener(changedListenerNewOk);
        set_item_switch.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(View view) {
                ((EditText) mViewHelper.getView(R.id.edt_old_password)).setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                ((EditText) mViewHelper.getView(R.id.edt_new_password)).setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                ((EditText) mViewHelper.getView(R.id.edt_confirm_password)).setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }

            @Override
            public void toggleToOff(View view) {
                ((EditText) mViewHelper.getView(R.id.edt_old_password)).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                ((EditText) mViewHelper.getView(R.id.edt_new_password)).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                ((EditText) mViewHelper.getView(R.id.edt_confirm_password)).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_forget_password, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 编辑按钮的点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_forget_password:
                ResetPasswordActivity.show(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 旧密码输入框监听
     */
    private TextWatcher changedListenerOld = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            isOk();
            String mEdtOldPassword = ((EditText) mViewHelper.getView(R.id.edt_old_password)).getText().toString();
            if (mEdtOldPassword != null && !mEdtOldPassword.equals("")) {
                mViewHelper.getView(R.id.mImgDeleteOld).setVisibility(View.VISIBLE);
            } else {
                mViewHelper.getView(R.id.mImgDeleteOld).setVisibility(View.GONE);
            }
        }
    };

    /**
     * 新密码输入框监听
     */
    private TextWatcher changedListenerNew = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            isOk();
            String mEdtNewPassword = ((EditText) mViewHelper.getView(R.id.edt_new_password)).getText().toString();
            if (mEdtNewPassword != null && !mEdtNewPassword.equals("")) {
                mViewHelper.getView(R.id.mImgDeleteNew).setVisibility(View.VISIBLE);
            } else {
                mViewHelper.getView(R.id.mImgDeleteNew).setVisibility(View.GONE);
            }
        }
    };

    /**
     * 再次输入密码输入框监听
     */
    private TextWatcher changedListenerNewOk = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            isOk();
            String mEdtNewPasswordOk = ((EditText) mViewHelper.getView(R.id.edt_confirm_password)).getText().toString();
            if (mEdtNewPasswordOk != null && !mEdtNewPasswordOk.equals("")) {
                mViewHelper.getView(R.id.mImgDeleteOkNew).setVisibility(View.VISIBLE);
            } else {
                mViewHelper.getView(R.id.mImgDeleteOkNew).setVisibility(View.GONE);
            }
        }
    };

    /**
     * 满足条件确认按钮变黄色
     */
    private void isOk() {
        Button mBtnDetermine = mViewHelper.getView(R.id.mBtnDetermine);
        String mEdtOldPassword = ((EditText) mViewHelper.getView(R.id.edt_old_password)).getText().toString();
        String mEdtNewPassword = ((EditText) mViewHelper.getView(R.id.edt_new_password)).getText().toString();
        String mEdtNewPasswordOk = ((EditText) mViewHelper.getView(R.id.edt_confirm_password)).getText().toString();
        if (mEdtOldPassword.length() >= 6 && mEdtNewPassword.length() >= 6 && mEdtNewPasswordOk.length() >= 6) {
            mBtnDetermine.setBackgroundResource(R.drawable.shape_btn_normal_dow);
        } else {
            mBtnDetermine.setBackgroundResource(R.drawable.shape_btn_normal_up);
        }
    }

    /**
     * 界面所有控件的点击事件
     */
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.mBtnDetermine:
                    String error = "";
                    EditText mEdtOldPassword = mViewHelper.getView(R.id.edt_old_password);
                    mEdtNewPassword = mViewHelper.getView(R.id.edt_new_password);
                    EditText mEdtNewPasswordOk = mViewHelper.getView(R.id.edt_confirm_password);
                    if (mEdtOldPassword.getText().toString().isEmpty() || mEdtOldPassword.getText().toString().equals("")) {
                        error = getString(R.string.txt_old_password_input);
                        AlertUtil.getInstance().alertMessage(ChangePasswordActivity.this, error);
                        return;
                    }
                    if (mEdtOldPassword.getText().toString().length() < 6) {
                        error = getString(R.string.txt_old_password_input_limit);
                        AlertUtil.getInstance().alertMessage(ChangePasswordActivity.this, error);
                        return;
                    }
                    if (mEdtNewPassword.getText().toString().isEmpty() || mEdtNewPassword.getText().toString().equals("")) {
                        error = getString(R.string.txt_new_password_input);
                        AlertUtil.getInstance().alertMessage(ChangePasswordActivity.this, error);
                        return;
                    }
                    if (mEdtNewPassword.getText().toString().length() < 6) {
                        error = getString(R.string.txt_new_password_input_limit);
                        AlertUtil.getInstance().alertMessage(ChangePasswordActivity.this, error);
                        return;
                    }
                    if (mEdtNewPasswordOk.getText().toString().isEmpty() || mEdtNewPasswordOk.getText().toString().equals("")) {
                        error = getString(R.string.txt_ok_new_password_input);
                        AlertUtil.getInstance().alertMessage(ChangePasswordActivity.this, error);
                        return;
                    }
                    if (mEdtNewPasswordOk.getText().toString().length() < 6) {
                        error = getString(R.string.txt_ok_new_password_input_limit);
                        AlertUtil.getInstance().alertMessage(ChangePasswordActivity.this, error);
                        return;
                    }
                    if (!mEdtNewPassword.getText().toString().equals(mEdtNewPasswordOk.getText().toString())) {
                        error = getString(R.string.txt_ok_new_password_different);
                        AlertUtil.getInstance().alertMessage(ChangePasswordActivity.this, error);
                        return;
                    }
                    if (error.isEmpty() || error.equals("")) {
                        if (!TextUtils.isEmpty(SharedPreferencesUtil.getInstance().getToken())) {
                            ProviderFactory.getInstance().luxclub_updateCardPassword(mContext, SharedPreferencesUtil.getInstance().getToken(), ProjectUtil.getCardNo(),
                                    mEdtOldPassword.getText().toString(), mEdtNewPassword.getText().toString(), updateCardPasswordCallback);
                        }
                    } else {
                        AlertUtil.getInstance().alertMessage(ChangePasswordActivity.this, error);
                    }
                    break;
                case R.id.mImgDeleteOld:
                    ((EditText) mViewHelper.getView(R.id.edt_old_password)).setText("");
                    break;
                case R.id.mImgDeleteNew:
                    ((EditText) mViewHelper.getView(R.id.edt_new_password)).setText("");
                    break;
                case R.id.mImgDeleteOkNew:
                    ((EditText) mViewHelper.getView(R.id.edt_confirm_password)).setText("");
                    break;
            }
        }
    };

    private ApiCallback updateCardPasswordCallback = new ApiCallback() {
        @Override
        public void onError(Request request, Exception e) {
        }

        @Override
        public void onResponse(final ApiResponseBean response) {
            if (response.getCode() == ApiCodeConstant.SUCCESS_CODE) {
                String object = response.getData();
                Map<String, Object> data = JsonUtil.toMap(object);
                SharedPreferencesUtil.getInstance().setString(SharedPreferencesUtil.TOKEN, MapUtil.getString(data, "token"));
                SharedPreferencesUtil.getInstance().setString(ProjectConstant.PASSWORD, mEdtNewPassword.getText().toString());
                final AlertSuccess dialog = new AlertSuccess.Builder(mContext, getString(R.string.txt_update_success)).create();
                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.cancel();
                        AlertUtil.getInstance().alertMessage(ChangePasswordActivity.this, response.getText());
                        finish();
                    }
                }, 1500);
            } else {
                final AlertFail dialog = new AlertFail.Builder(mContext, getString(R.string.txt_update_fail)).create();
                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.cancel();
                        AlertUtil.getInstance().alertMessage(ChangePasswordActivity.this, response.getText());
                    }
                }, 1500);
            }
        }
    };

    public static void show(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ChangePasswordActivity.class);
        context.startActivity(intent);
    }
}
