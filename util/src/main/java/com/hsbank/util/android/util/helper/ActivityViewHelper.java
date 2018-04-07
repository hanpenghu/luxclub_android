package com.hsbank.util.android.util.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.hsbank.util.android.util.ResourceUtil;
import com.hsbank.util.java.type.StringUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Activity视图辅助类
 * @author Arthur.Xie
 * 2015-01-30
 */
public class ActivityViewHelper {
	/**当前布局的视图集合*/
	private final SparseArray<View> mViewMap;
	/**当前上下文对象*/
	private Context mContext;
	/**根视图*/
	private View mRootView;

	/**
	 * 构造函数
	 * @param context				上下文
	 * @param layoutId				布局Id
	 */
	public ActivityViewHelper(Context context, int layoutId) {
		this.mContext = context;
		((FragmentActivity)this.mContext).setContentView(layoutId);
		this.mRootView = ((FragmentActivity)this.mContext).getWindow().getDecorView().getRootView();
		this.mViewMap = new SparseArray<View>();
	}

	/**
	 * 构造函数
	 * @param context				上下文
	 * @param layoutId				布局Id
	 * @param setContentViewFlag	设置标识
	 */
	public ActivityViewHelper(Context context, int layoutId, boolean setContentViewFlag) {
		this.mContext = context;
		if (setContentViewFlag) {
			((FragmentActivity)this.mContext).setContentView(layoutId);
			this.mRootView = ((FragmentActivity)this.mContext).getWindow().getDecorView().getRootView();
		} else {
			LayoutInflater inflater = ((FragmentActivity)this.mContext).getLayoutInflater();
			this.mRootView = inflater.inflate(layoutId, null);
		}
		this.mViewMap = new SparseArray<>();
	}

	/**得到当前布局的根视图*/
	public View getRootView() {
		return mRootView;
	}

	/**
	 * 通过控件Id得到相应的的控件，如果没有则加入_viewMap
	 * @param viewId			控件Id
	 * @return					控件
	 */
	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int viewId) {
		View view = mViewMap.get(viewId);
		if (view == null) {
			view = mRootView.findViewById(viewId);
			mViewMap.put(viewId, view);
		}
		return (T)view;
	}

	/**
	 * 得到TextView的值
	 * @param viewId
	 * @return
	 */
	public String getText(int viewId) {
		TextView view = getView(viewId);
		return view == null ? "" : StringUtil.dealString(view.getText().toString());
	}

	/**
	 * 设置TextView的值
	 * @param viewId
	 * @param text
	 */
	public void setText(int viewId, String text) {
		TextView view = getView(viewId);
		view.setText(text);
	}

	/**
	 * 设置TextView的值
	 * @param viewId
	 * @param stringId
	 */
	public void setText(int viewId, int stringId) {
		TextView view = getView(viewId);
		view.setText(ResourceUtil.getInstance().getString(stringId));
	}

	/**
	 * 设置图片
	 * @param viewId
	 * @param drawableId
	 */
	public void setImageResource(int viewId, int drawableId) {
		ImageView view = getView(viewId);
		view.setImageResource(drawableId);
	}

	/**
	 * 设置图片
	 * @param viewId
	 * @param bm
	 */
	public void setImageBitmap(int viewId, Bitmap bm) {
		ImageView view = getView(viewId);
		view.setImageBitmap(bm);
	}

	/**
	 * 设置图片
	 * @param viewId
	 * @param url
	 */
	public void setImageByUrl(int viewId, String url) {
		ImageLoader.getInstance().displayImage(url, (ImageView)getView(viewId));
	}

	/**
	 * 设置图片
	 * @param viewId
	 * @param url
	 * @param options
	 * @param listener
	 */
	public void setImageByUrl(int viewId, String url, DisplayImageOptions options, ImageLoadingListener listener) {
		ImageLoader.getInstance().displayImage(url, (ImageView) getView(viewId), options, listener);
	}

	/**
	 * 设置背景
	 * @param viewId
	 * @param resourceId
	 * @return
	 */
	public void setBackgroundResource(int viewId, int resourceId) {
		ImageView view = getView(viewId);
		view.setBackgroundResource(resourceId);
	}

	/**
	 * 设置视频
	 * @param viewId
	 * @param url
	 */
	public void setVideoByUrl(int viewId, String url) {
		((VideoView)getView(viewId)).setVideoURI(Uri.parse(url));
	}
}
