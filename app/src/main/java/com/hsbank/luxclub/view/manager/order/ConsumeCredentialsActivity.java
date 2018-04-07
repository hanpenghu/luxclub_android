package com.hsbank.luxclub.view.manager.order;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.mywidget.HackyViewPager;
import com.hsbank.luxclub.view.main.joy.SiteAlbumFragment;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.luxclub.view.manager.order.event.OrderEvent;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * 消费凭证图片
 * name：zhuzhenghua
 * create time:2016.4.8
 */
public class ConsumeCredentialsActivity extends ProjectBaseActivity {
    public static final String EXTRA_IMAGE_URLS = "image_urls";
    private HackyViewPager mViewPager;
    private TextView txt_index_photo;
    private int index = -1;
    private String urlType;
    private ArrayList<String> imageUrls;
    private String orederId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_consumer_credentials;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index = getIntent().getIntExtra("index",-1);
        urlType = getIntent().getStringExtra("urlType");
        orederId = getIntent().getStringExtra("orderId");
        if(!urlType.equals("local")){
            mViewHelper.getView(R.id.delete).setVisibility(View.GONE);
        }
        mViewHelper.getView(R.id.delete).setOnClickListener(clickListener);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        imageUrls = getIntent().getStringArrayListExtra(EXTRA_IMAGE_URLS);
        mViewPager = mViewHelper.getView(R.id.pager);
        ImagePagerAdapter mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), imageUrls);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(index);
        // 初始化相册下标
        txt_index_photo = mViewHelper.getView(R.id.indicator);
        CharSequence text = getString(R.string.viewpager_indicator, index+1, mViewPager.getAdapter().getCount());
        txt_index_photo.setText(text);
        // 相册更新下标
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int arg0) {
                CharSequence text = getString(R.string.viewpager_indicator, arg0 + 1, mViewPager.getAdapter().getCount());
                txt_index_photo.setText(text);
            }
        });
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mViewHelper.getView(R.id.toolbar).setBackgroundColor(Color.BLACK);
    }

    private class ImagePagerAdapter extends FragmentStatePagerAdapter {

        public ArrayList<String> fileList;

        public ImagePagerAdapter(FragmentManager fm, ArrayList<String> fileList) {
            super(fm);
            this.fileList = fileList;
        }

        @Override
        public int getCount() {
            return fileList == null ? 0 : fileList.size();
        }

        @Override
        public Fragment getItem(int position) {
            String url = fileList.get(position);
            return SiteAlbumFragment.newInstance(url);
        }
    }

    public View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.delete:
                    int i = mViewPager.getCurrentItem();
                    mViewPager.setCurrentItem(i-1);
                    imageUrls.remove(i);
                    if(imageUrls!=null&&imageUrls.size()!=0){
                        ImagePagerAdapter mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), imageUrls);
                        mViewPager.setAdapter(mAdapter);
                        CharSequence text = getString(R.string.viewpager_indicator, 1, mViewPager.getAdapter().getCount());
                        txt_index_photo.setText(text);
                    }else{
                        MOrderDetailActivity.show(mContext,orederId);
                        finish();
                    }
                    EventBus.getDefault().post(new OrderEvent(OrderEvent.COMMAND_MANAGER_DELETE,imageUrls));
                    break;
            }
        }
    };

    /**
     * @param context
     * @param arrayList 图片url
     * @param index 默认显示几张图片
     * @param urlType 是本地图片还是网络图片
     * @param orderId 订单详情id
     */
    public static void show(Context context, ArrayList<String> arrayList,int index,String urlType,String orderId) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_IMAGE_URLS, arrayList);
        intent.putExtra("index",index);
        intent.putExtra("urlType",urlType);
        intent.putExtra("orderId",orderId);
        intent.setClass(context, ConsumeCredentialsActivity.class);
        context.startActivity(intent);
    }
}
