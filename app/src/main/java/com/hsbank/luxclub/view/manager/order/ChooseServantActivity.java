package com.hsbank.luxclub.view.manager.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ViewAnimator;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.hsbank.luxclub.R;
import com.hsbank.luxclub.adapter.common.CommonAdapter;
import com.hsbank.luxclub.model.ServantItemBean;
import com.hsbank.luxclub.provider.apis.ServantApis;
import com.hsbank.luxclub.provider.http.APISubscriber;
import com.hsbank.luxclub.provider.http.RetrofitHelper;
import com.hsbank.luxclub.provider.http.RxUtil;
import com.hsbank.luxclub.util.ProjectUtil;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by chenliuchun on 2018/1/10.
 * 管家选择列表页
 */

public class ChooseServantActivity extends ProjectBaseActivity implements AdapterView.OnItemClickListener, OnRefreshListener {

    private SwipeToLoadLayout swipeToLoadLayout;
    private CommonAdapter<ServantItemBean> servantInfoAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_servant;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewHandler();
    }

    @Override
    public void onStart() {
        super.onStart();
        swipeToLoadLayout.setRefreshing(true);
    }

    private void viewHandler() {
        setToolbarTitle("选择移动管家");
        swipeToLoadLayout = mViewHelper.getView(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        // 初始化listview适配器
        servantInfoAdapter = new CommonAdapter<ServantItemBean>(mContext, R.layout.item_servant_info) {
            @Override
            public void convert(ViewHolder holder, ServantItemBean item, int position) {
                holder.setText(R.id.txt_name, item.getStewardName());
            }
        };
        ListView listView = mViewHelper.getView(R.id.swipe_target);
        listView.setAdapter(servantInfoAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onRefresh() {
        RetrofitHelper.getInstance()
                .create(ServantApis.class, true)
                .list(ProjectUtil.getManagerToken())
                .compose(RxUtil.<List<ServantItemBean>>compose(this))
                .subscribe(new APISubscriber<List<ServantItemBean>>() {
                    @Override
                    public void onNext(List<ServantItemBean> list) {
                        swipeToLoadLayout.setRefreshing(false);
                        ViewAnimator vam_ticket = mViewHelper.getView(R.id.vam_list_data);
                        if (list == null || list.size() == 0) {
                            vam_ticket.setDisplayedChild(1);
                            return;
                        }
                        vam_ticket.setDisplayedChild(0);
                        ServantItemBean bean = new ServantItemBean("", "请选择移动管家");
                        list.add(0, bean);
                        servantInfoAdapter.setDatas(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        swipeToLoadLayout.setRefreshing(false);
                        ViewAnimator vam_ticket = mViewHelper.getView(R.id.vam_list_data);
                        vam_ticket.setDisplayedChild(1);
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ServantItemBean item = servantInfoAdapter.getItem(position);
        Intent intent = new Intent();
        intent.putExtra("StewardId", item.getStewardId());
        intent.putExtra("StewardName", item.getStewardName());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

}
