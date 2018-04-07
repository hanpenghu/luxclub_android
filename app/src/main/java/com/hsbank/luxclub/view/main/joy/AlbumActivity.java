package com.hsbank.luxclub.view.main.joy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.WindowManager;
import android.widget.TextView;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.mywidget.HackyViewPager;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;

import java.util.ArrayList;

/**
 *  店家相册查看页面
 *  Created by chen_liuchun on 2016/3/26.
 */
public class AlbumActivity extends ProjectBaseActivity {
    public static final String KEY_IMAGE_URLS = "image_urls";
    public static final String KEY_IMAGE_POSITION = "key_image_position";
    // 相册的viewpager
	private HackyViewPager mViewPager;
	private TextView txt_index_photo;

	@Override
	protected int getLayoutId() {
        return R.layout.activity_album;
    }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// 隐藏状态栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
        ArrayList<String> imageUrls = getIntent().getStringArrayListExtra(KEY_IMAGE_URLS);
        // 初始化viewpager
		mViewPager = mViewHelper.getView(R.id.pager);
		ImagePagerAdapter mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), imageUrls);
		mViewPager.setAdapter(mAdapter);

		// 初始化相册下标
        int position = getIntent().getIntExtra(KEY_IMAGE_POSITION, 0);
        mViewPager.setCurrentItem(position);

        txt_index_photo = mViewHelper.getView(R.id.indicator);
        CharSequence text = getString(R.string.viewpager_indicator, 1 + position, mViewPager.getAdapter().getCount());
        txt_index_photo.setText(text);
		// 相册更新下标
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

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

    public static void show(Context context, ArrayList<String> arrayList, int position) {
        Intent intent = new Intent();
        intent.putExtra(KEY_IMAGE_URLS, arrayList);
        intent.putExtra(KEY_IMAGE_POSITION, position);
        intent.setClass(context, AlbumActivity.class);
        context.startActivity(intent);
    }
}
