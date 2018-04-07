package com.hsbank.luxclub.view.manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.provider.ProviderFactory;
import com.hsbank.luxclub.util.AlertFail;
import com.hsbank.luxclub.util.AlertSuccess;
import com.hsbank.luxclub.util.ProjectConstant;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.luxclub.view.manager.order.event.OrderEvent;
import com.hsbank.util.android.util.AlertUtil;
import com.hsbank.util.android.util.JsonUtil;
import com.hsbank.util.android.util.SharedPreferencesUtil;
import com.hsbank.util.android.util.http.api.ApiCodeConstant;
import com.hsbank.util.android.util.http.api.ApiResponseBean;
import com.hsbank.util.android.util.http.okhttp.callback.ApiCallback;
import com.hsbank.util.java.collection.MapUtil;
import com.hsbank.util.project.util.BaseProjectEvent;
import com.hsbank.xgpush.PushUtil;
import com.squareup.okhttp.Request;

import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * 经理-登录页面
 * name：zhuzhenghua
 * create time:2016.4.5
 */
public class LoginActivity extends ProjectBaseActivity {
    /**用户名*/
    private EditText edt_name;
    /**用户密码*/
    private EditText edt_password;

    /**
     * 得到布局文件Id
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_manager_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewHandler();
    }

    /**
     * 控件初始化
     */
    protected void viewHandler() {
        mViewHelper.getView(R.id.btn_confirm).setOnClickListener(clickListener);
        ((EditText)mViewHelper.getView(R.id.edt_name)).addTextChangedListener(changedListenerName);
        ((EditText)mViewHelper.getView(R.id.edt_password)).addTextChangedListener(changedListenerPassword);
        mViewHelper.getView(R.id.img_delete_username).setOnClickListener(clickListener);
        mViewHelper.getView(R.id.img_delete_password).setOnClickListener(clickListener);
    }

    /**
     * EventBus消息处理器
     * @param event
     */
    @Override
    public void onEventMainThread(BaseProjectEvent event) {
        super.onEventMainThread(event);
        if(event.getCommand().equals(OrderEvent.COMMAND_MANAGER_lOGIN)){
            ManagerMainActivity.show(mContext);
        }
    }

    /**
     * 界面所有控件的点击事件
     */
    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_confirm:
                    String error = "";
                    edt_name = mViewHelper.getView(R.id.edt_name);
                    String edt_name_str = edt_name.getText().toString();
                    edt_password = mViewHelper.getView(R.id.edt_password);
                    String edt_password_str = edt_password.getText().toString();
                    if(edt_name_str.isEmpty()|| edt_name_str.equals("")){
                        error = getString(R.string.txt_manager_please_input_name);
                        AlertUtil.getInstance().alertMessage(LoginActivity.this,error);
                        return;
                    }
                    if(edt_name_str.length()<4){
                        error = getString(R.string.txt_manager_please_input_name_limit);
                        AlertUtil.getInstance().alertMessage(LoginActivity.this,error);
                        return;
                    }
                    if(edt_password_str.isEmpty()|| edt_password_str.equals("")){
                        error = getString(R.string.txt_manager_please_input_password);
                        AlertUtil.getInstance().alertMessage(LoginActivity.this,error);
                        return;
                    }
                    if(edt_password_str.length()<6){
                        error = getString(R.string.txt_manager_please_input_password_limit);
                        AlertUtil.getInstance().alertMessage(LoginActivity.this,error);
                        return;
                    }
                    if(error.isEmpty()||error.equals("")){
                        ProviderFactory.getInstance().manager_login(mContext,edt_name_str,
                                edt_password_str,loginCallback);
                    }else{
                        AlertUtil.getInstance().alertMessage(LoginActivity.this,error);
                    }
                    break;
                case R.id.img_delete_username:
                    ((EditText)mViewHelper.getView(R.id.edt_name)).setText("");
                    break;
                case R.id.img_delete_password:
                    ((EditText)mViewHelper.getView(R.id.edt_password)).setText("");
                    break;
            }
        }
    };

    /**
     * 用户名输入框监听
     */
    private TextWatcher changedListenerName = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            isOk();
            String edt_name_str = ((EditText)mViewHelper.getView(R.id.edt_name)).getText().toString();
            if(edt_name_str!=null&&!edt_name_str.equals("")){
                mViewHelper.getView(R.id.img_delete_username).setVisibility(View.VISIBLE);
            }else{
                mViewHelper.getView(R.id.img_delete_username).setVisibility(View.GONE);
            }
        }
    };


    /**
     * 满足条件确认按钮变黄色
     */
    private void isOk(){
        Button btn_confirm = mViewHelper.getView(R.id.btn_confirm);
        String edt_name = ((EditText)mViewHelper.getView(R.id.edt_name)).getText().toString();
        String edt_password = ((EditText)mViewHelper.getView(R.id.edt_password)).getText().toString();
        if(edt_name.length()>=4&&edt_name.length()<=18&&edt_password.length()==6){
            btn_confirm.setBackgroundResource(R.drawable.shape_btn_normal_dow);
        }else{
            btn_confirm.setBackgroundResource(R.drawable.shape_btn_normal_up);
        }
    }

    /**
     * 用户密码输入框监听
     */
    private TextWatcher changedListenerPassword = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            isOk();
            String edt_password_str = ((EditText)mViewHelper.getView(R.id.edt_password)).getText().toString();
            if(edt_password_str!=null&&!edt_password_str.equals("")){
                mViewHelper.getView(R.id.img_delete_password).setVisibility(View.VISIBLE);
            }else{
                mViewHelper.getView(R.id.img_delete_password).setVisibility(View.GONE);
            }
        }
    };

    private ApiCallback loginCallback = new ApiCallback() {
        @Override
        public void onError(Request request, Exception e) {
        }

        @Override
        public void onResponse(final ApiResponseBean response) {
            if (response.getCode() == ApiCodeConstant.SUCCESS_CODE) {
                String object = response.getData();
                Map<String, Object> data = JsonUtil.toMap(object);
                SharedPreferencesUtil.getInstance().setString(ProjectConstant.MANAGER_TOKEN, MapUtil.getString(data, "token"));
                SharedPreferencesUtil.getInstance().setString(ProjectConstant.MANAGER_USER_ID, MapUtil.getString(data, "userId"));
                SharedPreferencesUtil.getInstance().setString(ProjectConstant.USER_TYPE, MapUtil.getString(data, "userType"));
                // 注册信鸽推送
                boolean isPushMessage = SharedPreferencesUtil.getInstance().getBoolean(ProjectConstant.IS_PUSH_MESSAGE, true);
                if (isPushMessage) {
//                    PushUtil.INSTANCE.registerMyPush(LoginActivity.this, MapUtil.getString(data, "userId"));
                    PushUtil.INSTANCE.registerMyPush(LoginActivity.this);
                }
                final AlertSuccess dialog = new AlertSuccess.Builder(mContext,getString(R.string.txt_login_success)).create();
                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.cancel();
                        EventBus.getDefault().post(new OrderEvent(OrderEvent.COMMAND_MANAGER_lOGIN));
                        finish();
                    }
                }, 1500);
            } else {
                final AlertFail dialog = new AlertFail.Builder(mContext,getString(R.string.txt_login_fail)).create();
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
        intent.setClass(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
