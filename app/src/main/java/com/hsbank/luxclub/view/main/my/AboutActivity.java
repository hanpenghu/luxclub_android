package com.hsbank.luxclub.view.main.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.util.popupwindow.MyPopupWindow;
import com.hsbank.luxclub.util.popupwindow.PopupWindowFunction;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.util.android.AndroidAppInfo;
import com.hsbank.util.android.AndroidUtil;

/**
 * 关于我们
 * name：zhuzhenghua
 * create time:2016.3.10
 */
public class AboutActivity extends ProjectBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewHandler();
    }

    /**
     * 视图元素处理器
     */
    protected void viewHandler() {
        mViewHelper.getView(R.id.setitem_call_customer).setOnClickListener(clickListener);
        mViewHelper.getView(R.id.setitem_service_agreement).setOnClickListener(clickListener);
        mViewHelper.getView(R.id.setitem_company).setOnClickListener(clickListener);
        mViewHelper.setText(R.id.txt_app_version, String.format(getString(R.string.txt_app_version), AndroidAppInfo.getInstance().getVersionName()));
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.setitem_call_customer:
                    MyPopupWindow.showUnbindCard(AboutActivity.this, mViewHelper.getView(R.id.parentView),
                            getString(R.string.txt_service_hot_phone) + getString(R.string.txt_service_number), getString(R.string.txt_app_call), popwinCallback);
                    break;
                case R.id.setitem_company:
                    IntroduceActivity.show(mContext);
                    break;
                case R.id.setitem_service_agreement:
                    break;
            }
        }
    };

    private PopupWindowFunction popwinCallback = new PopupWindowFunction() {
        @Override
        public void popupWinFunction(Object o) {
            AndroidUtil.callPhone(AboutActivity.this, getString(R.string.txt_service_hot_phone)+ getString(R.string.txt_service_number));
        }
    };

    public static void show(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, AboutActivity.class);
        context.startActivity(intent);
    }

    /**
     * 得到布局文件Id
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_about;
    }
}
