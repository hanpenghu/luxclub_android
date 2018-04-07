package com.hsbank.luxclub.view.main.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.util.ProjectConstant;
import com.hsbank.luxclub.util.ProjectUtil;
import com.hsbank.luxclub.util.tool.ToastUtil;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.util.android.util.SharedPreferencesUtil;

/**
 * 设置页面
 * name：zhuzhenghua
 * create time:2015.11.19
 */
public class SettingsActivity extends ProjectBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewHandler();
    }

    /**
     * 视图元素处理器
     */
    protected void viewHandler() {
        mViewHelper.getView(R.id.set_item_safe).setOnClickListener(clickListener);
        mViewHelper.getView(R.id.set_item_modify_password).setOnClickListener(clickListener);
        TextView text_set_safe = mViewHelper.getView(R.id.text_set_safe);
        if(!SharedPreferencesUtil.getInstance().getString(ProjectConstant.MD_5_STRING).isEmpty()&&SharedPreferencesUtil.getInstance().getString(ProjectConstant.MD_5_STRING)!=null){
            mViewHelper.setText(R.id.text_set_safe, getString(R.string.txt_set_already));
            text_set_safe.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_lock_gold, 0, 0, 0);
        }else{
            mViewHelper.setText(R.id.text_set_safe, getString(R.string.txt_set_no));
            text_set_safe.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_lock_red, 0, 0, 0);
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.set_item_safe:
                    SetSecurityActivity.show(SettingsActivity.this);
                    break;
                case R.id.set_item_modify_password:
                    if (ProjectUtil.isLogin()) {
                        ChangePasswordActivity.show(SettingsActivity.this);
                    } else {
                        ToastUtil.show(getString(R.string.txt_no_bind_card));
                    }
                    break;
            }
        }
    };

    public static void show(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SettingsActivity.class);
        context.startActivity(intent);
    }

    /**
     * 得到布局文件Id
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_set;
    }
}
