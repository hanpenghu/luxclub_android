package com.hsbank.util.android.util.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hsbank.util.R;
import com.hsbank.util.android.util.AnimType;
import com.hsbank.util.android.util.helper.ActivityViewHelper;
import com.hsbank.util.java.type.StringUtil;

/**
 * 加载等待对话框_公共类
 * 2016-01-15
 */
public class LoadingDialogUtil {
	/**
	 * 得到一个自定义的加载等待动画对话框
	 * @param context					上下文对象
	 * @param layoutId					加载等待对话框布局Id
	 * @param styleId					加载等待对话框样式Id
	 * @param imageDrawableId			加载图片资源Id
	 * @param tip						加载提示语
	 * @param type						加载动画类型
	 * @param animResourceId			加载动画资源Id
	 * @param canceledOnTouchOutside	触摸对话框外部区域时，对话框是否关闭
	 */
	public static Dialog getDialog(Context context, int layoutId, int styleId, int imageDrawableId, String tip, AnimType type, int animResourceId, boolean canceledOnTouchOutside) {
		layoutId = layoutId > 0 ? layoutId : R.layout.util_custom_loading_dialog;
		ActivityViewHelper viewHelper = new ActivityViewHelper(context, layoutId, false);
		// 设置加载图片
		if (imageDrawableId > 0) {
			viewHelper.setImageResource(R.id.loading_image_view, imageDrawableId);
		}
		// 设置加载提示语
		tip = StringUtil.dealString(tip);
		if (!"".equals(tip)) {
			viewHelper.setText(R.id.loading_text_view, tip);
		}
		// 加载动画
		type = type == null ? AnimType.TWEEN : type;
		switch (type){
			case TWEEN:
				animResourceId = animResourceId > 0 ? animResourceId : R.anim.util_loading;
				Animation animation = AnimationUtils.loadAnimation(context, animResourceId);
				// 使用ImageView显示动画
				viewHelper.getView(R.id.loading_image_view).startAnimation(animation);
				break;
			case FRAME:
				ImageView imageView = viewHelper.getView(R.id.loading_image_view);
				// 通过ImageView对象拿到背景显示的AnimationDrawable
				final AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
				// 为了防止在onCreate方法中只显示第一帧
				imageView.post(new Runnable() {
					@Override
					public void run() {
						animationDrawable.start();
					}
				});
				break;
		}
		// 生成对话框
		Dialog resultValue = new Dialog(context, styleId > 0 ? styleId : R.style.util_custom_loading_dialog);
		// 触摸对话框外部区域时，对话框是否关闭
		resultValue.setCancelable(canceledOnTouchOutside);
		// 设置布局
		resultValue.setContentView(viewHelper.getRootView(),new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
		return resultValue;
	}

	/**
	 * 得到一个自定义的加载等待动画对话框
	 * @param context					上下文对象
	 * @param styleId					加载等待对话框样式Id
	 * @param imageDrawableId			加载图片资源Id
	 * @param tip						加载提示语
	 * @param type						加载动画类型
	 * @param animResourceId			加载动画资源Id
	 * @param canceledOnTouchOutside	触摸对话框外部区域时，对话框是否关闭
	 */
	public static Dialog getDialog(Context context, int styleId, int imageDrawableId, String tip, AnimType type, int animResourceId, boolean canceledOnTouchOutside) {
		return getDialog(context, -1, styleId, imageDrawableId, tip, type, animResourceId, canceledOnTouchOutside);
	}

	/**
	 * 得到一个自定义的加载等待动画对话框
	 * @param context					上下文对象
	 * @param imageDrawableId			加载图片资源Id
	 * @param tip						加载提示语
	 * @param type						加载动画类型
	 * @param animResourceId			加载动画资源Id
	 * @param canceledOnTouchOutside	触摸对话框外部区域时，对话框是否关闭
	 */
	public static Dialog getDialog(Context context, int imageDrawableId, String tip, AnimType type, int animResourceId, boolean canceledOnTouchOutside) {
		return getDialog(context, -1, imageDrawableId, tip, type, animResourceId, canceledOnTouchOutside);
	}

	/**
	 * 得到一个自定义的加载等待动画对话框
	 * @param context					上下文对象
	 * @param tip						加载提示语
	 * @param type						加载动画类型
	 * @param animResourceId			加载动画资源Id
	 * @param canceledOnTouchOutside	触摸对话框外部区域时，对话框是否关闭
	 */
	public static Dialog getDialog(Context context, String tip, AnimType type, int animResourceId, boolean canceledOnTouchOutside) {
		return getDialog(context, -1, tip, type, animResourceId, canceledOnTouchOutside);
	}

	/**
	 * 得到一个自定义的加载等待动画对话框
	 * @param context					上下文对象
	 * @param imageDrawableId			加载图片资源Id
	 * @param type						加载动画类型
	 * @param animResourceId			加载动画资源Id
	 * @param canceledOnTouchOutside	触摸对话框外部区域时，对话框是否关闭
	 */
	public static Dialog getDialog(Context context, AnimType type, int imageDrawableId, int animResourceId, boolean canceledOnTouchOutside) {
		return getDialog(context, imageDrawableId, "", type, animResourceId, canceledOnTouchOutside);
	}

	/**
	 * 得到一个自定义的加载等待动画对话框
	 * @param context					上下文对象
	 * @param canceledOnTouchOutside	触摸对话框外部区域时，对话框是否关闭
	 */
	public static Dialog getDialog(Context context, boolean canceledOnTouchOutside) {
		return getDialog(context, -1, "", null, -1, canceledOnTouchOutside);
	}

	/**
	 * 得到一个自定义的加载等待动画对话框
	 * @param context					上下文对象
	 * @param imageDrawableId			加载图片资源Id
	 */
	public static Dialog getDialog(Context context, int imageDrawableId) {
		return getDialog(context, imageDrawableId, "", null, -1, false);
	}

	/**
	 * 得到一个自定义的加载等待动画对话框：默认是橙色圈圈
	 * @param context					上下文对象
	 */
	public static Dialog getDialog(Context context) {
		return getDialog(context, false);
	}

	/**
	 * 得到一个自定义的加载等待动画对话框：橙色圈圈
	 * @param context					上下文对象
	 */
	public static Dialog getDialog_orange(Context context) {
		return getDialog(context, R.drawable.util_loading_orange);
	}

	/**
	 * 得到一个自定义的加载等待动画对话框：绿色圈圈
	 * @param context					上下文对象
	 */
	public static Dialog getDialog_green(Context context) {
		return getDialog(context, R.drawable.util_loading_green);
	}
}
