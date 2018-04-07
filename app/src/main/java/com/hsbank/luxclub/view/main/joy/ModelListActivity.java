package com.hsbank.luxclub.view.main.joy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ViewAnimator;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.hsbank.luxclub.R;
import com.hsbank.luxclub.adapter.common.CommonAdapter;
import com.hsbank.luxclub.mywidget.swipe_refresh_loadmore.GridviewScrollListener;
import com.hsbank.luxclub.provider.ProviderFactory;
import com.hsbank.luxclub.util.constants.Constants;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.luxclub.view.main.joy.event.JoyEvent;
import com.hsbank.luxclub.view.main.my.event.MyEvent;
import com.hsbank.util.android.AndroidUtil;
import com.hsbank.util.android.util.AlertUtil;
import com.hsbank.util.android.util.JsonUtil;
import com.hsbank.util.android.util.http.api.ApiCodeConstant;
import com.hsbank.util.android.util.http.api.ApiResponseBean;
import com.hsbank.util.android.util.http.okhttp.callback.ApiCallback;
import com.hsbank.util.java.collection.MapUtil;
import com.hsbank.util.project.util.BaseProjectEvent;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.okhttp.Request;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * 场所信息分页列表
 * Created by chen_liuchun on 2016/3/15.
 */
public class ModelListActivity extends ProjectBaseActivity implements OnRefreshListener, OnLoadMoreListener, View.OnClickListener{

    public static final String KEY_TITLE = "key_title";
    public static final int PAGE_SIZE = 10;
    private int pageNumber = 1;
    private boolean isRefresh = true; // 默认当前是下拉刷新状态
    private boolean hasMore; // 默认没有更多数据
    private SwipeToLoadLayout swipeToLoadLayout;
    private List<Map<String, Object>> mDatas;
    private CommonAdapter mModelListAdapter;

    @Override
    public void onEventMainThread(BaseProjectEvent event) {
        if (event.getCommand().equals(JoyEvent.COMMAND_MODEL_LIST)) {
            String obj = (String) event.getMessage();
            mDatas = JsonUtil.toList(obj);
            updateUi();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_model_list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(getIntent().getStringExtra(KEY_TITLE));
        viewHandler();
        swipeToLoadLayout.setRefreshing(true);
    }

    protected void viewHandler() {
        mViewHelper.getView(R.id.fly_appoiment).setOnClickListener(this);

        swipeToLoadLayout = mViewHelper.getView(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        // 初始化listview适配器
        mModelListAdapter = new CommonAdapter<Map<String, Object>>(this, R.layout.item_grid_model){
            @Override
            public void convert(ViewHolder holder, Map<String, Object> map, int position) {
                holder.setText(R.id.txt_name, MapUtil.getString(map, "nickname"));
                holder.setText(R.id.txt_height_weight, MapUtil.getString(map, "height")+ "cm/" + MapUtil.getString(map, "weight") + "kg");
                holder.setText(R.id.txt_country, MapUtil.getString(map, "country"));

                String modelHead = MapUtil.getString(map, "modelHead");
                holder.setTag(R.id.img_cover, modelHead);
                ImageView imgCover = holder.getView(R.id.img_cover);
                if (imgCover.getTag() != null && imgCover.getTag().equals(modelHead)) {
                    ImageLoader.getInstance().displayImage(modelHead, imgCover, Constants.networkOptions);
                }
            }
        };
        GridView gridView = mViewHelper.getView(R.id.swipe_target);
        gridView.setAdapter(mModelListAdapter);
        gridView.setOnScrollListener(new GridviewScrollListener()); // 滑到底部自动加载更多
        gridView.setOnItemClickListener(listItemClickListener);
    }

    private void updateUi() {
        swipeToLoadLayout.setRefreshing(false);
        swipeToLoadLayout.setLoadingMore(false);
        ViewAnimator vam_ticket = mViewHelper.getView(R.id.vam_joy_list);
        if (mDatas == null || mDatas.size() == 0) {
            if (isRefresh){
                vam_ticket.setDisplayedChild(1);
            } else {
                AlertUtil.getInstance().alertMessage(mContext, getString(R.string.txt_no_data));
                swipeToLoadLayout.setLoadingMore(false);
            }
            return;
        }
        vam_ticket.setDisplayedChild(0);
        if (isRefresh) { // 下拉刷新状态
            mModelListAdapter.setDatas(mDatas);
        } else { // 加载更多状态
            mModelListAdapter.addDatas(mDatas, false);
        }
        hasMore = mDatas.size() == PAGE_SIZE;
    }

    @Override
    public void onRefresh() {
        pageNumber = 1;
        isRefresh = true;

        ProviderFactory.getInstance().luxclub_modelPageList(mContext, PAGE_SIZE, pageNumber, modelListCallback);
    }

    @Override
    public void onLoadMore() {
        if (!hasMore){
            AlertUtil.getInstance().alertMessage(mContext, getString(R.string.txt_no_data));
            swipeToLoadLayout.setLoadingMore(false);
            return;
        }
        isRefresh = false;
        ProviderFactory.getInstance().luxclub_modelPageList(mContext, PAGE_SIZE, ++pageNumber, modelListCallback);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public void onClick(View v) {
        AndroidUtil.callPhone(this, getString(R.string.txt_service_number));
    }

    // listview item 监听器
    private AdapterView.OnItemClickListener listItemClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> adapterView, View view, int positionId, long rowId) {
            Map<String, Object> map = (Map<String, Object>) mModelListAdapter.getItem(positionId);
            String modelId = MapUtil.getString(map, "modelId");
            ModelDetailActivity.show(mContext, modelId);
        }
    };

    // 列表请求回调
    private ApiCallback modelListCallback = new ApiCallback() {
        @Override
        public void onError(Request request, Exception e) {
            e.printStackTrace();
        }

        @Override
        public void onResponse(ApiResponseBean response) {
            if (response.getCode() == ApiCodeConstant.SUCCESS_CODE) {
                EventBus.getDefault().post(new MyEvent(JoyEvent.COMMAND_MODEL_LIST, response.getData()));
            } else {
                AlertUtil.getInstance().alertMessage(ModelListActivity.this, response.getText());
            }
        }
    };

    public static void show(Context context, String title) {
        Intent intent = new Intent();
        intent.putExtra(KEY_TITLE, title);
        intent.setClass(context, ModelListActivity.class);
        context.startActivity(intent);
    }

}
