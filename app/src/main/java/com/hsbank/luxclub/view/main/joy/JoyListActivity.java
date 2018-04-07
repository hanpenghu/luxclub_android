package com.hsbank.luxclub.view.main.joy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.hsbank.luxclub.R;
import com.hsbank.luxclub.adapter.common.CommonAdapter;
import com.hsbank.luxclub.model.CityBean;
import com.hsbank.luxclub.mywidget.swipe_refresh_loadmore.ListviewScrollListener;
import com.hsbank.luxclub.provider.ProviderFactory;
import com.hsbank.luxclub.provider.apis.CityApis;
import com.hsbank.luxclub.provider.http.APISubscriber;
import com.hsbank.luxclub.provider.http.RetrofitHelper;
import com.hsbank.luxclub.provider.http.RxUtil;
import com.hsbank.luxclub.util.ProjectApi;
import com.hsbank.luxclub.util.constants.Constants;
import com.hsbank.luxclub.util.popupwindow.CommonPopupWindow;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.luxclub.view.main.joy.event.JoyEvent;
import com.hsbank.luxclub.view.main.my.event.MyEvent;
import com.hsbank.util.android.util.AlertUtil;
import com.hsbank.util.android.util.JsonUtil;
import com.hsbank.util.android.util.SharedPreferencesUtil;
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
public class JoyListActivity extends ProjectBaseActivity implements OnRefreshListener, OnLoadMoreListener, View.OnClickListener {

    public static final int PAGE_SIZE = 10;
    private int pageNumber = 1;
    private boolean isRefresh = true; // 默认当前是下拉刷新状态
    private boolean hasMore; // 默认没有更多数据
    private SwipeToLoadLayout swipeToLoadLayout;
    private List<Map<String, Object>> mDatas;
    private CommonAdapter mJoyListAdapter;
    // 默认城市Id
    private String mCityId = "0";
    // 默认城市名称
    private String mCityName = "全部";
    // 场所类型
    private int mSiteType = -1;
    // 页面标题
    public static final String KEY_TITLE = "key_title";
    // 城市列表缓存
    private List<CityBean> mCityList;

    @Override
    public void onEventMainThread(BaseProjectEvent event) {
        if (event.getCommand().equals(JoyEvent.COMMAND_JOY_LIST)) {
            String obj = (String) event.getMessage();
            mDatas = JsonUtil.toList(obj);
            updateUi();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_joy_list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBundleData();
        viewHandler();
        onRefresh();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        initBundleData();
    }

    protected void viewHandler() {
        setToolbarTitle(getIntent().getStringExtra(KEY_TITLE));
        mCityName = SharedPreferencesUtil.getInstance().getString(Constants.CITY_NAME, mCityName);
        TextView txt_menu_item = mViewHelper.getView(R.id.txt_menu_item);
        txt_menu_item.setText(mCityName);
        txt_menu_item.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_location, 0);
        txt_menu_item.setOnClickListener(this);

        swipeToLoadLayout = mViewHelper.getView(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        // 初始化listview适配器
        mJoyListAdapter = new CommonAdapter<Map<String, Object>>(this, R.layout.item_list_joy) {
            @Override
            public void convert(ViewHolder holder, Map<String, Object> map, int position) {
                holder.setText(R.id.txt_item_title, MapUtil.getString(map, "siteName"));
                String siteImageUrl = MapUtil.getString(map, "siteImageUrl");
                holder.setTag(R.id.img_item_cover, siteImageUrl);
                ImageView imgCover = holder.getView(R.id.img_item_cover);
                if (imgCover.getTag() != null && imgCover.getTag().equals(siteImageUrl)) {
                    ImageLoader.getInstance().displayImage(siteImageUrl, imgCover, Constants.networkOptions);
                }
            }
        };
        ListView listView = mViewHelper.getView(R.id.swipe_target);
        listView.setAdapter(mJoyListAdapter);
        listView.setOnScrollListener(new ListviewScrollListener()); // 滑到底部自动加载更多
        listView.setOnItemClickListener(listItemClickListener);
    }

    private void updateUi() {
        swipeToLoadLayout.setRefreshing(false);
        swipeToLoadLayout.setLoadingMore(false);
        ViewAnimator vam_ticket = mViewHelper.getView(R.id.vam_joy_list);

        if (mDatas == null || mDatas.size() == 0) {
            if (isRefresh) {
                vam_ticket.setDisplayedChild(1);
            } else {
                AlertUtil.getInstance().alertMessage(mContext, getString(R.string.txt_no_data));
                swipeToLoadLayout.setLoadingMore(false);
            }
            return;
        }
        vam_ticket.setDisplayedChild(0);
        if (isRefresh) { // 下拉刷新状态
            mJoyListAdapter.setDatas(mDatas);
        } else { // 加载更多状态
            mJoyListAdapter.addDatas(mDatas, false);
        }
        hasMore = mDatas.size() == PAGE_SIZE;
    }

    @Override
    public void onRefresh() {
        pageNumber = 1;
        isRefresh = true;
        ProviderFactory.getInstance().luxclub_siteInfoPageList(mContext, mCityId, mSiteType, PAGE_SIZE, pageNumber, joyListCallback);
    }

    @Override
    public void onLoadMore() {
        if (!hasMore) {
            AlertUtil.getInstance().alertMessage(mContext, getString(R.string.txt_no_data));
            swipeToLoadLayout.setLoadingMore(false);
            return;
        }
        isRefresh = false;
        ProviderFactory.getInstance().luxclub_siteInfoPageList(mContext, mCityId, mSiteType, PAGE_SIZE, ++pageNumber, joyListCallback);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.txt_menu_item) {
            if (mCityList != null) {
                popupCityList(mCityList);
            } else {
                RetrofitHelper.getInstance()
                        .create(CityApis.class, false)
                        .getList(ProjectApi.getInstance().getClient())
                        .compose(RxUtil.<List<CityBean>>compose(this))
                        .subscribe(new APISubscriber<List<CityBean>>() {
                            @Override
                            public void onNext(List<CityBean> cityList) {
                                CityBean all = new CityBean("0", "全部", false);
                                cityList.add(0, all);
                                mCityList = cityList;
                                popupCityList(cityList);
                            }
                        });
            }
        }
    }

    /**
     * 弹出城市列表
     *
     * @param cityList
     */
    private void popupCityList(final List<CityBean> cityList) {
        final String cityId = SharedPreferencesUtil.getInstance().getString(Constants.CITY_ID, mCityId);
        final int index = getCityPosition(cityList, cityId);
        cityList.get(index).setSelected(true);

        CommonAdapter<CityBean> cityAdapter = new CommonAdapter<CityBean>(this, R.layout.item_grid_city, cityList) {
            @Override
            public void convert(ViewHolder holder, CityBean cityBean, int position) {
                RadioButton convertView = (RadioButton) holder.getConvertView();
                convertView.setChecked(cityBean.isSelected());
                holder.setText(R.id.txt_city, cityBean.getCityName());
            }
        };

        // 创建popupWindow对象
        View view = LayoutInflater.from(this).inflate(R.layout.popup_city_grid, null);
        CommonPopupWindow.Builder builder = new CommonPopupWindow.Builder(this)
                .setView(view)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setBackgroundLevel(0.5f);
        final CommonPopupWindow popupWindow = builder.create();

        GridView grid_city = (GridView) view.findViewById(R.id.grid_city);
        grid_city.setAdapter(cityAdapter);
        grid_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != index) {
                    CityBean itemOld = (CityBean) parent.getAdapter().getItem(index);
                    itemOld.setSelected(false);

                    CityBean itemNew = (CityBean) parent.getAdapter().getItem(position);
                    itemNew.setSelected(true);
                    SharedPreferencesUtil.getInstance().setString(Constants.CITY_ID, itemNew.getCityId());
                    SharedPreferencesUtil.getInstance().setString(Constants.CITY_NAME, itemNew.getCityName());
                    mViewHelper.setText(R.id.txt_menu_item, itemNew.getCityName());
                    mCityId = itemNew.getCityId();
                    onRefresh();
                    ((CommonAdapter) parent.getAdapter()).notifyDataSetChanged();
                }
                popupWindow.dismiss();
            }
        });

        popupWindow.showAsDropDown(findViewById(R.id.toolbar));
    }

    private int getCityPosition(List<CityBean> cityList, String cityId) {
        for (int i = 0; i < cityList.size(); i++) {
            if (cityList.get(i).getCityId().equals(cityId)) {
                return i;
            }
        }
        return 0;
    }

    /**
     * 接受界面传值
     */
    private void initBundleData() {
        mSiteType = getIntent().getIntExtra(Constants.SITE_TYPE, -1);
    }

    // listView item 监听器
    private AdapterView.OnItemClickListener listItemClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> adapterView, View view, int positionId, long rowId) {
            Map<String, Object> map = (Map<String, Object>) mJoyListAdapter.getItem(positionId);
            int siteId = Integer.parseInt(MapUtil.getString(map, "siteId"));
            JoyDetailActivity.show(mContext, siteId + "");
        }
    };

    // KTV 列表请求回调
    private ApiCallback joyListCallback = new ApiCallback() {
        @Override
        public void onError(Request request, Exception e) {
            e.printStackTrace();
        }

        @Override
        public void onResponse(ApiResponseBean response) {
            if (response.getCode() == ApiCodeConstant.SUCCESS_CODE) {
                EventBus.getDefault().post(new MyEvent(JoyEvent.COMMAND_JOY_LIST, response.getData()));
            } else {
                AlertUtil.getInstance().alertMessage(JoyListActivity.this, response.getText());
            }
        }
    };

    public static void show(Context context, int siteType, String title) {
        Intent intent = new Intent();
        intent.putExtra(Constants.SITE_TYPE, siteType);
        intent.putExtra(KEY_TITLE, title);
        intent.setClass(context, JoyListActivity.class);
        context.startActivity(intent);
    }

}
