package com.hsbank.luxclub.view.servant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.adapter.common.CommonAdapter;
import com.hsbank.luxclub.model.ViewSummaryBean;
import com.hsbank.luxclub.provider.apis.ServantApis;
import com.hsbank.luxclub.provider.http.APISubscriber;
import com.hsbank.luxclub.provider.http.RetrofitHelper;
import com.hsbank.luxclub.provider.http.RxUtil;
import com.hsbank.luxclub.util.ProjectUtil;
import com.hsbank.luxclub.util.constants.Constants;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.luxclub.view.manager.order.ConsumeCredentialsActivity;
import com.hsbank.util.java.collection.ListUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 上传服务总结 补充总结
 * Created by chenliuchun on 2018/1/16.
 */

public class ViewSummaryActivity extends ProjectBaseActivity {

    private static final String ORDER_ID = "order_id";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_view_summary;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle("服务总结");

        String orderId = getIntent().getStringExtra(ORDER_ID);
        RetrofitHelper.getInstance()
                .create(ServantApis.class, true)
                .viewSummary(ProjectUtil.getManagerToken(), orderId)
                .compose(RxUtil.<ViewSummaryBean>compose(this))
                .subscribe(new APISubscriber<ViewSummaryBean>() {
                    @Override
                    public void onNext(ViewSummaryBean summaryBean) {
                        updateUI(summaryBean);
                    }
                });

    }

    private void updateUI(final ViewSummaryBean summaryBean) {
        final String orderId = getIntent().getStringExtra(ORDER_ID);
        mViewHelper.setText(R.id.txt_service_summary, summaryBean.getContent());

        final List<String> imagesUrl = summaryBean.getImagesUrl();
        if (ListUtil.isEmpty(imagesUrl)) {
            return;
        }
        GridView gridview_images = mViewHelper.getView(R.id.gridview_images);
        // 客户端消费凭证adapter
        CommonAdapter<String> commonAdapter = new CommonAdapter<String>(
                this, R.layout.item_grid_pic, imagesUrl) {
            @Override
            public void convert(ViewHolder viewHolder, String item, int position) {
                ImageView img = viewHolder.getView(R.id.img);
                ImageLoader.getInstance().displayImage(item, img, Constants.networkOptions);
            }
        };
        gridview_images.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int positionId, long rowId) {
                ConsumeCredentialsActivity.show(mContext, new ArrayList<>(imagesUrl),
                        positionId, "network", orderId);
            }
        });
        gridview_images.setAdapter(commonAdapter);

    }


    public static void show(Context context, String orderId) {
        Intent intent = new Intent();
        intent.putExtra(ORDER_ID, orderId);
        intent.setClass(context, ViewSummaryActivity.class);
        context.startActivity(intent);
    }
}
