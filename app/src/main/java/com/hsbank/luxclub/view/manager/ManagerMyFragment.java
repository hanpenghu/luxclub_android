package com.hsbank.luxclub.view.manager;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.provider.ProviderFactory;
import com.hsbank.luxclub.util.AlertFail;
import com.hsbank.luxclub.util.AlertSuccess;
import com.hsbank.luxclub.util.ProjectConstant;
import com.hsbank.luxclub.util.ProjectUtil;
import com.hsbank.luxclub.view.main.MainActivity;
import com.hsbank.luxclub.view.main.my.AboutActivity;
import com.hsbank.luxclub.view.base.ProjectBaseFragment;
import com.hsbank.luxclub.view.manager.my.MMessageActivity;
import com.hsbank.luxclub.view.manager.my.MOpenCardActivity;
import com.hsbank.luxclub.view.manager.my.UpdatePasswordActivity;
import com.hsbank.luxclub.view.manager.my.event.MyEvent;
import com.hsbank.util.android.util.AlertUtil;
import com.hsbank.util.android.util.SharedPreferencesUtil;
import com.hsbank.util.android.util.http.api.ApiCodeConstant;
import com.hsbank.util.android.util.http.api.ApiResponseBean;
import com.hsbank.util.android.util.http.okhttp.callback.ApiCallback;
import com.hsbank.util.project.util.BaseProjectEvent;
import com.squareup.okhttp.Request;

import de.greenrobot.event.EventBus;

/**
 * 经理端我的界面
 * Created by pengwei on 2016/4/5.
 */
public class ManagerMyFragment extends ProjectBaseFragment implements View.OnClickListener {
    private FragmentActivity mActivity;

    @Override
    public void onEventMainThread(BaseProjectEvent event) {
        super.onEventMainThread(event);
        int dataCount;
        if (event.getCommand().equals(MyEvent.COMMAND_MANAGER_MESSAGE_UNREADCOUNT)) {
            String obj = (String) event.getMessage();
            dataCount = Integer.parseInt(obj);
            if (dataCount == 0) {
                mViewHelper.getView(R.id.text_imageView).setVisibility(View.GONE);
            } else {
                mViewHelper.getView(R.id.text_imageView).setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_manager_my;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewHandler();
        String managerUserId = ProjectUtil.getManagerUserId();
        ProviderFactory.getInstance().message_unreadCount(mContext, ProjectUtil.getManagerToken(), managerUserId, unReadCountCallback);
    }

    private void viewHandler() {
        mViewHelper.getView(R.id.btn_exit).setOnClickListener(this);
        mViewHelper.getView(R.id.change_item_password).setOnClickListener(this);
        mViewHelper.getView(R.id.personal_item_message).setOnClickListener(this);
        mViewHelper.getView(R.id.set_item_about_my).setOnClickListener(this);
        if (!ProjectUtil.isServantType()) {
            mViewHelper.setVisible(R.id.setting_recommend_card, true);
            mViewHelper.getView(R.id.setting_recommend_card).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.change_item_password:
                UpdatePasswordActivity.show(mActivity);
                break;
            case R.id.personal_item_message:
                MMessageActivity.show(mActivity);
                break;
            case R.id.set_item_about_my:
                AboutActivity.show(mActivity);
                break;
            case R.id.setting_recommend_card:
                MOpenCardActivity.show(mContext);
                break;
            case R.id.btn_exit:
                ProviderFactory.getInstance().manager_logout(mContext, ProjectUtil.getManagerToken(), exitCallback);
                break;
        }
    }

    // 未读消息数量的请求回调
    private ApiCallback unReadCountCallback = new ApiCallback() {
        @Override
        public void onError(Request request, Exception e) {
            e.printStackTrace();
        }

        @Override
        public void onResponse(ApiResponseBean response) {
            if (response.getCode() == ApiCodeConstant.SUCCESS_CODE) {
                EventBus.getDefault().post(new MyEvent(MyEvent.COMMAND_MANAGER_MESSAGE_UNREADCOUNT, response.getData()));
            } else {
                AlertUtil.getInstance().alertMessage(mActivity, response.getText());
            }
        }
    };

    private ApiCallback exitCallback = new ApiCallback() {
        @Override
        public void onError(Request request, Exception e) {
            e.printStackTrace();
        }

        @Override
        public void onResponse(final ApiResponseBean response) {
            if (response.getCode() == ApiCodeConstant.SUCCESS_CODE) {
                new AlertSuccess.Builder(mActivity, getString(R.string.txt_exit_succeed)).create().show();
            } else {
                new AlertFail.Builder(mActivity, getString(R.string.txt_exit_fail)).create().show();
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mActivity.finish();
                    MainActivity.show(mContext, 0);
                    SharedPreferencesUtil.getInstance().setString(ProjectConstant.MANAGER_TOKEN, "");
                    SharedPreferencesUtil.getInstance().setString(ProjectConstant.MANAGER_USER_ID, "");
                }
            }, 1500);
        }
    };
}
