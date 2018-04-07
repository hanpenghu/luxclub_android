package com.hsbank.luxclub.view.main.my;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.provider.apis.MemberApis;
import com.hsbank.luxclub.provider.http.APISubscriber;
import com.hsbank.luxclub.provider.http.RetrofitHelper;
import com.hsbank.luxclub.provider.http.RxUtil;
import com.hsbank.luxclub.util.tool.ToastUtil;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.util.java.tool.MobileUtil;

import org.apache.commons.lang3.math.NumberUtils;

public class ResetPasswordActivity extends ProjectBaseActivity implements View.OnClickListener{

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_password;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle("重置密码");

        mViewHelper.setOnClickListener(R.id.img_delete, this);
        mViewHelper.setOnClickListener(R.id.btn_ok, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_delete:
                mViewHelper.setText(R.id.edt_mobile, "");
                break;
            case R.id.btn_ok:
                String mobile = mViewHelper.getText(R.id.edt_mobile).trim();
                if (verifyMobile(mobile)){
                    final ProgressDialog progressDialog = ProgressDialog.show(this, null, "加载中...", false, true);
                    RetrofitHelper.getInstance()
                            .create(MemberApis.class, true)
                            .forgotCardPassword(mobile)
                            .compose(RxUtil.compose(this))
                            .subscribe(new APISubscriber<Object>() {
                                @Override
                                public void onNext(Object o) {
                                    progressDialog.dismiss();
                                    ToastUtil.show("重置密码成功，密码已经发送到您的手机号，请注意查收");
                                    finish();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    super.onError(e);
                                    progressDialog.dismiss();
                                }
                            });
                }
                break;
        }

    }

    private boolean verifyMobile(String mobile) {
        if (TextUtils.isEmpty(mobile)) {
            ToastUtil.show("请填写手机号");
            return false;
        }
        if (!NumberUtils.isDigits(mobile)){
            ToastUtil.show("请填写正确的手机号");
            return false;
        }
        return MobileUtil.isMobile(mobile);
    }

    public static void show(Context context){
        Intent intent = new Intent();
        intent.setClass(context, ResetPasswordActivity.class);
        context.startActivity(intent);
    }
}
