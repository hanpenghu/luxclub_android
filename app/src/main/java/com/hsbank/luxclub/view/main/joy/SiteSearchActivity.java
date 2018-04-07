package com.hsbank.luxclub.view.main.joy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.mywidget.CustomEditText;
import com.hsbank.luxclub.provider.ProviderFactory;
import com.hsbank.luxclub.util.constants.Constants;
import com.hsbank.luxclub.view.main.joy.event.JoyEvent;
import com.hsbank.luxclub.view.main.my.event.MyEvent;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.util.android.util.AlertUtil;
import com.hsbank.util.android.util.JsonUtil;
import com.hsbank.util.android.util.http.api.ApiCodeConstant;
import com.hsbank.util.android.util.http.api.ApiResponseBean;
import com.hsbank.util.android.util.http.okhttp.callback.ApiCallback;
import com.hsbank.util.project.util.BaseProjectEvent;
import com.squareup.okhttp.Request;

import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/** 场所(热门)搜索页面
 * Created by chen_liuchun on 2016/3/22.
 */
public class SiteSearchActivity extends ProjectBaseActivity {

    // 请求返回搜索场所集合
    private List<Map<String, Object>> mSearchDatas;
    // 请求返回热门场所集合
    private List<Map<String, Object>> mHotDatas ;
    private ListView m_list_site_search;
    private GridView m_grid_site_hot;
    // 场所类型
    private int mSiteType = -1;

    @Override
    public void onEventMainThread(BaseProjectEvent event) {
        if(event.getCommand().equals(JoyEvent.COMMAND_SITE_HOT)){
            String hotSiteString = (String) event.getMessage();
            mHotDatas = JsonUtil.toList(hotSiteString);
            updateUiHot();
        }else if (event.getCommand().equals(JoyEvent.COMMAND_SITE_SEARCH)){
            String hotSearchString = (String) event.getMessage();
            mSearchDatas = JsonUtil.toList(hotSearchString);
            if (mSearchDatas!=null && mSearchDatas.size()>0){
                updateUiSearch();
                mViewHelper.getView(R.id.NoDataIsShow).setVisibility(View.GONE);
            }else{
                mViewHelper.getView(R.id.NoDataIsShow).setVisibility(View.VISIBLE);
                mViewHelper.setText(R.id.txt_no_data, getString(R.string.txt_no_data5));
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_site_search;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mSiteType = getIntent().getIntExtra(Constants.SITE_TYPE, -1);
        super.onCreate(savedInstanceState);
        viewHandler();
        ProviderFactory.getInstance().luxclub_hotSearchSiteList(mContext,"1", siteHotCallback);
    }

    @Override
    protected void initToolbar() {
        // 此页面无toolbar
    }

    private void viewHandler(){
        m_list_site_search = mViewHelper.getView(R.id.list_site_search);
        m_grid_site_hot = mViewHelper.getView(R.id.grid_site_hot);
        mViewHelper.getView(R.id.imgbt_back).setOnClickListener(clickListener);
        CustomEditText edt_site = mViewHelper.getView(R.id.edt_site);
        edt_site.addTextChangedListener(textWacher);
    }

    private void updateUiHot() {
        // 给gridView设置adapter
        SimpleAdapter simpleAdapter = new SimpleAdapter(mContext, mHotDatas, R.layout.item_grid_hot_city,
                new String[]{"siteName"}, new int[]{R.id.txt_city_hot});
        mViewHelper.getView(R.id.lly_site_search).setVisibility(View.GONE);
        mViewHelper.getView(R.id.lly_site_hot).setVisibility(View.VISIBLE);
        m_grid_site_hot.setAdapter(simpleAdapter);
        m_grid_site_hot.setOnItemClickListener(hotItemClickListener);
    }

    private void updateUiSearch() {
        // 给listView设置adapter
        SimpleAdapter simpleAdapter = new SimpleAdapter(mContext, mSearchDatas, R.layout.item_list_hot_site,
                new String[]{"siteName"}, new int[]{R.id.txt_site_hot});
        mViewHelper.getView(R.id.lly_site_hot).setVisibility(View.GONE);
        mViewHelper.getView(R.id.lly_site_search).setVisibility(View.VISIBLE);
        m_list_site_search.setAdapter(simpleAdapter);
        m_list_site_search.setOnItemClickListener(listItemClickListener);
    }

    public static void show(Context context, int siteType) {
        Intent intent = new Intent();
        intent.putExtra(Constants.SITE_TYPE, siteType);
        intent.setClass(context, SiteSearchActivity.class);
        context.startActivity(intent);
    }

    // 热门场所请求回调
    private ApiCallback siteHotCallback = new ApiCallback() {
        @Override
        public void onError(Request request, Exception e) {
            e.printStackTrace();
        }

        @Override
        public void onResponse(ApiResponseBean response) {
            if (response.getCode() == ApiCodeConstant.SUCCESS_CODE) {
                EventBus.getDefault().post(new MyEvent(JoyEvent.COMMAND_SITE_HOT, response.getData()));
            } else {
                AlertUtil.getInstance().alertMessage(SiteSearchActivity.this, response.getText());
            }
        }
    };

    // 搜索场所请求回调
    private ApiCallback siteSearchCallback = new ApiCallback() {
        @Override
        public void onError(Request request, Exception e) {
            e.printStackTrace();
        }

        @Override
        public void onResponse(ApiResponseBean response) {
            if (response.getCode() == ApiCodeConstant.SUCCESS_CODE) {
                EventBus.getDefault().post(new MyEvent(JoyEvent.COMMAND_SITE_SEARCH, response.getData()));
            } else {
                AlertUtil.getInstance().alertMessage(SiteSearchActivity.this, response.getText());
            }
        }
    };

    // 搜索场所点击事件监听器
    private AdapterView.OnItemClickListener listItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int siteId = (int)mHotDatas.get((int) id).get("siteId");
            JoyDetailActivity.show(mContext, siteId+"");
        }
    };

    // 热门场所点击事件监听器
    private AdapterView.OnItemClickListener hotItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int siteId = (int)mHotDatas.get((int) id).get("siteId");
            JoyDetailActivity.show(mContext, siteId+"");
        }
    };

    // 场所名称输入监听器
    private TextWatcher textWacher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String input = s.toString();
            if (!TextUtils.isEmpty(input)) {
                ProviderFactory.getInstance().luxclub_searchListBySiteName(mContext,input, String.valueOf(mSiteType), siteSearchCallback);
                mViewHelper.getView(R.id.lly_site_hot).setVisibility(View.GONE);
            }else{
                updateUiHot();
                mViewHelper.getView(R.id.lly_site_hot).setVisibility(View.VISIBLE);
                mViewHelper.getView(R.id.NoDataIsShow).setVisibility(View.GONE);
            }
        }
    };

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imgbt_back:
                    finish();
                    break;
            }
        }
    };
}

