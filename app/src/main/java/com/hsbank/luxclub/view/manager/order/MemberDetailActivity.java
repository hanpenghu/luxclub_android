package com.hsbank.luxclub.view.manager.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.model.ContentBean;
import com.hsbank.luxclub.provider.apis.MemberApis;
import com.hsbank.luxclub.provider.http.APISubscriber;
import com.hsbank.luxclub.provider.http.RetrofitHelper;
import com.hsbank.luxclub.provider.http.RxUtil;
import com.hsbank.luxclub.util.ProjectUtil;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;

/**
 * 会员信息
 * name：zhuzhenghua
 * create time:2016.4.13
 */
public class MemberDetailActivity extends ProjectBaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle("会员信息");
        String cardNo = getIntent().getStringExtra("cardNo");

        RetrofitHelper.getInstance()
                .create(MemberApis.class, true)
                .memberDetails(ProjectUtil.getManagerToken(), cardNo)
                .compose(RxUtil.<ContentBean>compose(this))
                .subscribe(new APISubscriber<ContentBean>() {
                    @Override
                    public void onNext(ContentBean contentBean) {
                        mViewHelper.setText(R.id.content, Html.fromHtml(contentBean.getContent()));
                    }
                });
    }

    public static void show(Context context, String cardNo) {
        Intent intent = new Intent();
        intent.putExtra("cardNo", cardNo);
        intent.setClass(context, MemberDetailActivity.class);
        context.startActivity(intent);
    }
}
