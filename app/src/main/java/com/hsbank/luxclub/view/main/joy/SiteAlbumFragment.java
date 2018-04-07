package com.hsbank.luxclub.view.main.joy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.util.constants.Constants;
import com.hsbank.luxclub.view.base.ProjectBaseFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import uk.co.senab.photoview.PhotoViewAttacher;
import uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;

/**
 *  单张图片显示的Fragment
 *   Created by chen_liuchun on 2016/3/26.
 */
public class SiteAlbumFragment extends ProjectBaseFragment {
	private String mImageUrl;
	private ImageView mImageView;
	private ProgressBar progressBar;
	// 图片缩放控件
	private PhotoViewAttacher mAttacher;

	public static SiteAlbumFragment newInstance(String imageUrl) {
		final SiteAlbumFragment f = new SiteAlbumFragment();
		final Bundle args = new Bundle();
		args.putString("url", imageUrl);
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mImageUrl = getArguments() != null ? getArguments().getString("url") : null;
	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_site_album;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		mImageView = mViewHelper.getView(R.id.image);
		progressBar = mViewHelper.getView(R.id.loading);
		mAttacher = new PhotoViewAttacher(mImageView);
		// 缩放图片监听器
		mAttacher.setOnPhotoTapListener(new OnPhotoTapListener() {

			@Override
			public void onPhotoTap(View arg0, float arg1, float arg2) {
				getActivity().onBackPressed();
			}

			/**
			 * A simple callback where out of photo happened;
			 */
			@Override
			public void onOutsidePhotoTap() {

			}
		});
		return mViewHelper.getContentView();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if(mImageUrl.startsWith("/")){
//			ImageLoader.getInstance().displayImage("file://"+mImageUrl, mImageView, Constants.localOptions);
			Bitmap bitmap0 = getLoacalBitmap(mImageUrl);
			mImageView.setImageBitmap(bitmap0);
		}else{
			ImageLoader.getInstance().displayImage(mImageUrl, mImageView, Constants.networkOptions, new SimpleImageLoadingListener() {

				@Override
				public void onLoadingStarted(String imageUri, View view) {
					progressBar.setVisibility(View.VISIBLE);
				}

				@Override
				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
					String message = null;
					switch (failReason.getType()) {
						case IO_ERROR:
							message = "下载错误";
							break;
						case DECODING_ERROR:
							message = "图片无法显示";
							break;
						case NETWORK_DENIED:
							message = "网络有问题，无法下载";
							break;
						case OUT_OF_MEMORY:
							message = "图片太大无法显示";
							break;
						case UNKNOWN:
							message = "未知的错误";
							break;
					}
					Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
					progressBar.setVisibility(View.GONE);
				}

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					progressBar.setVisibility(View.GONE);
					// photoview加载图片
					mAttacher.update();
				}
			});
		}
	}

	/**
	 * 加载本地图片
	 * @param url
	 * @return
	 */
	public static Bitmap getLoacalBitmap(String url) {
		try {
			FileInputStream fis = new FileInputStream(url);
			return BitmapFactory.decodeStream(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
