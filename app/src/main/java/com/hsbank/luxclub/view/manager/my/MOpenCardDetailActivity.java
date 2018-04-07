package com.hsbank.luxclub.view.manager.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.model.CardDetailBean;
import com.hsbank.luxclub.mywidget.flowlayout.FlowLayout;
import com.hsbank.luxclub.mywidget.flowlayout.TagAdapter;
import com.hsbank.luxclub.mywidget.flowlayout.TagFlowLayout;
import com.hsbank.luxclub.provider.apis.ActivateCardApis;
import com.hsbank.luxclub.provider.http.APISubscriber;
import com.hsbank.luxclub.provider.http.RetrofitHelper;
import com.hsbank.luxclub.provider.http.RxUtil;
import com.hsbank.luxclub.util.ProjectUtil;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;

/**
 * Author:      chenliuchun
 * Date:        17/5/19
 * Description: 推荐开卡详情
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */
public class MOpenCardDetailActivity extends ProjectBaseActivity{

    private static final String KEY_RECORD_ID = "key_record_id";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_m_open_card_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(getString(R.string.txt_open_card_detail));

        int recordId = getIntent().getIntExtra(KEY_RECORD_ID, -1);
        if (recordId != -1){
            RetrofitHelper.getInstance()
                    .create(ActivateCardApis.class, true)
                    .detail(ProjectUtil.getManagerToken(), recordId)
                    .compose(RxUtil.<CardDetailBean>compose(this))
                    .subscribe(new APISubscriber<CardDetailBean>() {
                        @Override
                        public void onNext(CardDetailBean cardDetailBean) {
                            updateUi(cardDetailBean);
                        }
                    });
        }
    }

    private void updateUi(CardDetailBean cardDetailBean) {
        mViewHelper.setText(R.id.txt_card_number, cardDetailBean.getCardNo());
        mViewHelper.setText(R.id.txt_rule_name, cardDetailBean.getRuleName());
        mViewHelper.setText(R.id.txt_card_amount, cardDetailBean.getAmount());
        mViewHelper.setText(R.id.txt_actual_amount, cardDetailBean.getActualAmount());
        mViewHelper.setText(R.id.txt_open_date, cardDetailBean.getIssuingDate());
        mViewHelper.setText(R.id.txt_member_name, cardDetailBean.getMemberName());
        mViewHelper.setText(R.id.txt_phone_num, cardDetailBean.getMemberMobile());
        mViewHelper.setText(R.id.txt_steward, cardDetailBean.getStewardName());
        mViewHelper.setText(R.id.txt_birthday, cardDetailBean.getMemberBirthday());
        mViewHelper.setText(R.id.txt_status, cardDetailBean.getStatusLabel());
        mViewHelper.setText(R.id.txt_remark, cardDetailBean.getRemarks());

        // 更新兴趣选择项
        final TagFlowLayout tagflow_interest = mViewHelper.getView(R.id.tagflow_interest);
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        tagflow_interest.setAdapter(new TagAdapter<String>(cardDetailBean.getInterests()) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView textView = (TextView) inflater.inflate(R.layout.item_flow_textview_narrow, tagflow_interest, false);
                textView.setText(s);
                return textView;
            }
        });
    }

    public static void show(Context context, int recordId) {
        Intent intent = new Intent();
        intent.putExtra(KEY_RECORD_ID, recordId);
        intent.setClass(context, MOpenCardDetailActivity.class);
        context.startActivity(intent);
    }
}
