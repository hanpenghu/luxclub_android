package com.hsbank.luxclub.view.main.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.provider.ProviderFactory;
import com.hsbank.luxclub.util.AlertFail;
import com.hsbank.luxclub.util.AlertSuccess;
import com.hsbank.luxclub.util.ProjectConstant;
import com.hsbank.luxclub.util.popupwindow.MyPopupWindow;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.luxclub.view.main.MainActivity;
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
 * 绑定会员卡/登陆页面
 * name：zhuzhenghua
 * create time:2016.3.23
 */
public class BindCardActivity extends ProjectBaseActivity implements View.OnClickListener, TextWatcher {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_card;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewHandler();
    }

    protected void viewHandler() {
        mViewHelper.getView(R.id.btn_confirm).setOnClickListener(this);
        mViewHelper.getView(R.id.img_tip).setOnClickListener(this);
        EditText edt_account_number = mViewHelper.getView(R.id.edt_account_number);
        edt_account_number.addTextChangedListener(this);
        EditText edt_password = mViewHelper.getView(R.id.edt_password);
        edt_password.addTextChangedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_forget_password, menu);
        return super.onCreateOptionsMenu(menu);
    }

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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        String edt_password = mViewHelper.getText(R.id.edt_password);
        String edt_account_number = mViewHelper.getText(R.id.edt_account_number);
        Button btn_confirm = mViewHelper.getView(R.id.btn_confirm);
        if (edt_password.length() >= 6 && edt_account_number.length() >= 8) {
            btn_confirm.setEnabled(true);
        } else {
            btn_confirm.setEnabled(false);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                String edt_account_number = mViewHelper.getText(R.id.edt_account_number).trim();
                String edt_password = mViewHelper.getText(R.id.edt_password).trim();
                String error = "";
                if (TextUtils.isEmpty(edt_account_number)) {
                    error = getString(R.string.txt_input_card_number);
                    AlertUtil.getInstance().alertMessage(this, error);
                    return;
                }
//                if (edt_card_number_str.length() < 8) {
//                    error = getString(R.string.txt_input_card_number_limit);
//                    AlertUtil.getInstance().alertMessage(this, error);
//                    return;
//                }
                if (TextUtils.isEmpty(edt_password)) {
                    error = getString(R.string.txt_input_card_password);
                    AlertUtil.getInstance().alertMessage(this, error);
                    return;
                }
//                if (mEdtCardPassword.getText().length() < 6) {
//                    error = getString(R.string.txt_input_card_password_limit);
//                    AlertUtil.getInstance().alertMessage(this, error);
//                    return;
//                }
                ProviderFactory.getInstance().luxclub_bindMemberCard(mContext, edt_account_number,
                        edt_password, bindMemberCardCallback);
                break;
            case R.id.img_tip:
                View parentView = mViewHelper.getView(R.id.parentView);
                MyPopupWindow.showRechargeDiscount(mContext, parentView, getString(R.string.txt_card_password), getString(R.string.txt_card_password_instruction));
                break;
        }
    }

    private ApiCallback bindMemberCardCallback = new ApiCallback() {
        @Override
        public void onError(Request request, Exception e) {
        }

        @Override
        public void onResponse(final ApiResponseBean response) {
            if (response.getCode() == ApiCodeConstant.SUCCESS_CODE) {
                String object = response.getData();
                Map<String, Object> data = JsonUtil.toMap(object);
                String edt_account_number = mViewHelper.getText(R.id.edt_account_number).trim();
                String edt_password = mViewHelper.getText(R.id.edt_password).trim();
                SharedPreferencesUtil.getInstance().setString(SharedPreferencesUtil.TOKEN, MapUtil.getString(data, "token"));
                SharedPreferencesUtil.getInstance().setString(ProjectConstant.CARDNO, edt_account_number);
                SharedPreferencesUtil.getInstance().setString(ProjectConstant.PASSWORD, edt_password);
                final AlertSuccess dialog = new AlertSuccess.Builder(mContext, getString(R.string.txt_bind_card_success)).create();
                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.cancel();
                        MainActivity.show(mContext, MainActivity.MENU_INDEX_04);
                        finish();
                    }
                }, 1500);
            } else {
                final AlertFail dialog = new AlertFail.Builder(mContext, getString(R.string.txt_bind_card_fail)).create();
                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.cancel();
                        AlertUtil.getInstance().alertMessage(BindCardActivity.this, response.getText());
                    }
                }, 1500);
            }
        }
    };

    public static void show(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, BindCardActivity.class);
        context.startActivity(intent);
    }
}
