package com.hsbank.luxclub.view.main.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.mywidget.password_view.GridPasswordView;
import com.hsbank.luxclub.mywidget.password_view.PasswordType;
import com.hsbank.luxclub.provider.ProviderFactory;
import com.hsbank.luxclub.util.AlertFail;
import com.hsbank.luxclub.util.AlertSuccess;
import com.hsbank.luxclub.util.ProjectConstant;
import com.hsbank.luxclub.util.ProjectUtil;
import com.hsbank.luxclub.util.popupwindow.MyPopupWindow;
import com.hsbank.luxclub.view.main.MainActivity;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.util.android.util.SharedPreferencesUtil;
import com.hsbank.util.android.util.http.api.ApiCodeConstant;
import com.hsbank.util.android.util.http.api.ApiResponseBean;
import com.hsbank.util.android.util.http.okhttp.callback.ApiCallback;
import com.squareup.okhttp.Request;

/**
 * 解绑卡片页面
 * name：zhuzhenghua
 * create time:
 */
public class UnbindCardActivity extends ProjectBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
     * 控件初始化
     */
    protected void viewHandler() {
        final GridPasswordView passwordView = mViewHelper.getView(R.id.passwordView);
        passwordView.setPasswordType(PasswordType.NUMBER);
        passwordView.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {
                if (psw.length() == 6){
                    ProviderFactory.getInstance().luxclub_unbindMemberCard(mContext,SharedPreferencesUtil.getInstance().getToken(),
                            ProjectUtil.getCardNo(),psw,unBindMemberCardCallback);
                }
            }

            @Override
            public void onInputFinish(String psw) {
            }
        });
    }

    private ApiCallback unBindMemberCardCallback = new ApiCallback() {
        @Override
        public void onError(Request request, Exception e) {
            Toast.makeText(UnbindCardActivity.this,"Exception"+e.toString(),Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(final ApiResponseBean response) {
            if (response.getCode() == ApiCodeConstant.SUCCESS_CODE) {
                SharedPreferencesUtil.getInstance().setString(SharedPreferencesUtil.TOKEN,"");
                SharedPreferencesUtil.getInstance().setString(ProjectConstant.CARDNO, "");
                SharedPreferencesUtil.getInstance().setString(ProjectConstant.MD_5_STRING, "");
                final AlertSuccess dialog = new AlertSuccess.Builder(mContext,getString(R.string.txt_unbind_success)).create();
                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.cancel();
                        finish();
                        MainActivity.show(mContext, 0);
                    }
                }, 1500);
            } else {
                final AlertFail dialog = new AlertFail.Builder(mContext,getString(R.string.txt_unbind_fail)).create();
                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.cancel();
                    }
                }, 1500);
            }
        }
    };

    public static void show(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, UnbindCardActivity.class);
        context.startActivity(intent);
    }

    /**
     * 得到布局文件Id
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_unbind_card;
    }
}
