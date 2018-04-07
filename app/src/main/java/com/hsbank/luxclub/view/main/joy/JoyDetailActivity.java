package com.hsbank.luxclub.view.main.joy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.hsbank.luxclub.R;
import com.hsbank.luxclub.adapter.common.CommonAdapter;
import com.hsbank.luxclub.model.SiteAlbumItem;
import com.hsbank.luxclub.mywidget.MeansureGridView;
import com.hsbank.luxclub.mywidget.poll_to_zoom_view.PullToZoomScrollViewEx;
import com.hsbank.luxclub.provider.ProviderFactory;
import com.hsbank.luxclub.util.constants.Constants;
import com.hsbank.luxclub.util.popupwindow.MyPopupWindow;
import com.hsbank.luxclub.util.popupwindow.PopupWindowFunction;
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

import static com.hsbank.luxclub.R.id.gridview;

/**
 * 场所详情页面
 * Created by chen_liuchun on 2016/3/15.
 */
public class JoyDetailActivity extends ProjectBaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Map<String, Object> mDatas;
    // zoom 动画content布局内控件
    private TextView txt_name;
    //    private TextView txt_phone_num;
    private TextView txt_address;
    private ImageView img_zoom;
    // 店家图集
    private List<SiteAlbumItem> mAlbumUrlList;
    private CommonAdapter<SiteAlbumItem> mAlbumAdapter;
    private WebView webview;

    @Override
    public void onEventMainThread(BaseProjectEvent event) {
        if (event.getCommand().equals(JoyEvent.COMMAND_JOY_DETAIL)) {
            String obj = (String) event.getMessage();
            mDatas = JsonUtil.toMap(obj);
            updateUi();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_joy_detail;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String mSiteId = getIntent().getStringExtra(Constants.SITE_ID);
        viewHandler();
        ProviderFactory.getInstance().luxclub_siteInfoDetails(mContext, mSiteId, joyDetailCallback);
    }

    protected void viewHandler() {
        // 初始化zoom_scroll_view
        PullToZoomScrollViewEx scrollView = mViewHelper.getView(R.id.zoomsrol_detail);
        LayoutInflater inflater = LayoutInflater.from(this);
        View zoomView = inflater.inflate(R.layout.zoom_image_ktv, null, false);
        View contentView = inflater.inflate(R.layout.zoom_content_ktv, null, false);
        scrollView.setZoomView(zoomView);
        scrollView.setScrollContentView(contentView);
        // 设置zoom动画
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int mScreenHeight = displayMetrics.heightPixels;
        int mScreenWidth = displayMetrics.widthPixels;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mScreenWidth, (int) (9.0F * (mScreenWidth / 16.0F)));
        scrollView.setHeaderLayoutParams(layoutParams);
        // 初始化content页面
        mViewHelper.getView(R.id.txt_appoiment).setOnClickListener(this);
        mViewHelper.getView(R.id.txt_appoiment_flow).setOnClickListener(this);
        txt_name = (TextView) scrollView.getPullRootView().findViewById(R.id.txt_name);
        txt_address = (TextView) scrollView.getPullRootView().findViewById(R.id.txt_address);

        MeansureGridView gridView = mViewHelper.getView(gridview);
        gridView.setOnItemClickListener(this);

//        txt_intro = (TextView) scrollView.getPullRootView().findViewById(R.id.txt_intro);
        img_zoom = (ImageView) scrollView.getZoomView();

        FrameLayout fly_web_container = mViewHelper.getView(R.id.fly_web_container);
        webview = new WebView(getApplicationContext());
        webview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        webview.setBackgroundColor(Color.TRANSPARENT);
        // 设置不缩放，防止图片过大。默认是按照手机屏幕密度比缩放的
//        webview.setInitialScale(200);
//        webview.getSettings().setTextZoom((int) (Resources.getSystem().getDisplayMetrics().density * 100));
        fly_web_container.addView(webview);


        // 初始化店家图集
        mAlbumAdapter = new CommonAdapter<SiteAlbumItem>(this, R.layout.item_gridview_img_title) {
            @Override
            public void convert(ViewHolder holder, SiteAlbumItem item, int position) {
                holder.setText(R.id.txt_album_title, item.getAlbumTitle());
                ImageView img_round = holder.getView(R.id.img_round);
                ImageLoader.getInstance().displayImage(item.getAlbumImageUrl(), img_round, Constants.networkOptions);
            }
        };
        gridView.setAdapter(mAlbumAdapter);
    }

    private void updateUi() {
        if (mDatas.size() == 0) {
            AlertUtil.getInstance().alertMessage(this, "该店家ID无效，无法打开");
        }
        // 是否可预约(0:否，1:是)
        if (MapUtil.getString(mDatas, "isOpen").equals("0")) {
            ((TextView) mViewHelper.getView(R.id.txt_appoiment)).setText(R.string.txt_no_subscribe_now);
        } else if (MapUtil.getString(mDatas, "isOpen").equals("1")) {
            ((TextView) mViewHelper.getView(R.id.txt_appoiment)).setText(getString(R.string.txt_subscribe_now));
        }
        txt_name.setText(MapUtil.getString(mDatas, "siteName"));
        txt_address.setText(MapUtil.getString(mDatas, "siteAddr"));
//        txt_intro.setText(MapUtil.getString(mDatas, "siteDescription"));
        String description = MapUtil.getString(mDatas, "siteDescription");
        // 这么怪异的用法是解决乱码，loadDataWithBaseURL()无此问题
        webview.loadData(wrapContent(description), "text/html; charset=UTF-8", null);

        // vip特有属性
//        txt_phone_num.setText(MapUtil.getString(mDatas, "contactPhone"));
        String siteImageUrl = MapUtil.getString(mDatas, "siteImageUrl");
        ImageLoader.getInstance().displayImage(siteImageUrl, img_zoom, Constants.networkOptions);
        String siteAlbum = MapUtil.getString(mDatas, "siteAlbum");
        if (!TextUtils.isEmpty(siteAlbum)) {
            mAlbumUrlList = JSON.parseArray(siteAlbum, SiteAlbumItem.class);
            // 显示店家缩略图GridView
            mAlbumAdapter.setDatas(mAlbumUrlList);
        }
    }

    private String wrapContent(String description) {
        String head = getString(R.string.txt_theme_head);
        String foot = getString(R.string.txt_theme_foot);
        StringBuilder builder = new StringBuilder();
        StringBuilder append = builder.append(head).append(description).append(foot);
        return append.toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearWebViewResource();
    }

    /**
     * release the memory of web view, otherwise it's resource will not be recycle.
     */
    public void clearWebViewResource() {
        if (webview != null) {
            webview.removeAllViews();
            // in android 5.1(sdk:21) we should invoke this to avoid memory leak
            // see (https://coolpers.github.io/webview/memory/leak/2015/07/16/
            // android-5.1-webview-memory-leak.html)
            ((ViewGroup) webview.getParent()).removeView(webview);
            webview.setTag(null);
            webview.clearHistory();
            webview.destroy();
            webview = null;
        }
    }
    
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RoomAlbumActivity.show(this, mAlbumUrlList.get(position).getAlbumId());
    }

    /**
     * 详情请求回调
     */
    private ApiCallback joyDetailCallback = new ApiCallback() {
        @Override
        public void onError(Request request, Exception e) {
            e.printStackTrace();
        }

        @Override
        public void onResponse(ApiResponseBean response) {
            if (response.getCode() == ApiCodeConstant.SUCCESS_CODE) {
                EventBus.getDefault().post(new MyEvent(JoyEvent.COMMAND_JOY_DETAIL, response.getData()));
            } else {
                AlertUtil.getInstance().alertMessage(JoyDetailActivity.this, response.getText());
            }
        }
    };

    // item点击事件监听器
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_appoiment:
                TextView txt_appoiment = mViewHelper.getView(R.id.txt_appoiment);
                if (txt_appoiment.getText().toString().equals(getString(R.string.txt_no_subscribe_now))) {
                    AlertUtil.getInstance().alertMessage(JoyDetailActivity.this, getString(R.string.txt_no_reserve));
                } else if (txt_appoiment.getText().toString().equals(getString(R.string.txt_subscribe_now))) {
                    int siteId = MapUtil.getInt(mDatas, "siteId");
                    String siteName = MapUtil.getString(mDatas, "siteName");
                    String areaName = MapUtil.getString(mDatas, "areaName");
                    JoySubscribeActivity.show(mContext, siteId, siteName, areaName);
                }
                break;
            case R.id.txt_appoiment_flow:
                SubscribeFlowActivity.show(this);
                break;
            case R.id.imgbt_call:
                MyPopupWindow.showConfirmWin(mContext, mViewHelper.getContentView(), "",
                        MapUtil.getString(mDatas, "contactPhone"), "呼叫", new PopupWindowFunction() {
                            @Override
                            public void popupWinFunction(Object o) {
                                AndroidUtil.callPhone(mContext, MapUtil.getString(mDatas, "contactPhone"));
                            }
                        });
                break;
        }
    }

    /**
     * 打开此场所详情页面
     *
     * @param context
     * @param siteId
     */
    public static void show(Context context, String siteId) {
        Intent intent = new Intent();
        intent.putExtra(Constants.SITE_ID, siteId);
        intent.setClass(context, JoyDetailActivity.class);
        context.startActivity(intent);
    }
}
