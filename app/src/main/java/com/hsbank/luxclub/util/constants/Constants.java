package com.hsbank.luxclub.util.constants;

import android.graphics.Bitmap;

import com.hsbank.luxclub.MyApp;
import com.hsbank.luxclub.R;
import com.hsbank.util.android.listener.AnimateFirstDisplayListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class Constants {
    public static final ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    public static final ImageLoader imageLoader = ImageLoader.getInstance();

    public static final String CITY_ID = "city_id";
    public static final String CITY_NAME = "city_name";
    public static final String SITE_ID = "site_id";
    public static final String SITE_TYPE = "site_type";
    public static final String SITE_NAME = "site_name";

    //web页面加载方式
    public enum WebType {
        WEB_TYPE_URL_NORMAL, WEB_TYPE_URL_POST, WEB_TYPE_HTML_CODE
    }

    public static final DisplayImageOptions displayImageHeader = new DisplayImageOptions.Builder()
            .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
            .showImageOnLoading(null)
            .showImageForEmptyUri(null)
            .showImageOnFail(null)
                    // .delayBeforeLoading(0 * 1000)
            .resetViewBeforeLoading(false).cacheInMemory(true)
            .cacheOnDisc(false).considerExifParams(true)
            .displayer(new RoundedBitmapDisplayer(3600))
            .bitmapConfig(Bitmap.Config.RGB_565).build();

    public static final DisplayImageOptions displayImageQR = new DisplayImageOptions.Builder()
            .imageScaleType(ImageScaleType.EXACTLY).showImageOnLoading(null)
            .showImageForEmptyUri(null).showImageOnFail(null)
            .resetViewBeforeLoading(false).cacheInMemory(false)
            .cacheOnDisc(true).considerExifParams(true)
            .bitmapConfig(Bitmap.Config.RGB_565).build();

    public static final DisplayImageOptions displayImageAD = new DisplayImageOptions.Builder()
            .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).showImageOnLoading(R.drawable.back_my_user_header)
            .showImageForEmptyUri(R.drawable.back_my_user_header).showImageOnFail(R.drawable.back_my_user_header)
            .resetViewBeforeLoading(false).cacheInMemory(false)
            .cacheOnDisc(true).considerExifParams(false)
            .bitmapConfig(Bitmap.Config.RGB_565).build();

    /**
     * 加载本地图片选项
     */
    public static final DisplayImageOptions localOptions = new DisplayImageOptions.Builder()
            .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
            .showImageOnLoading(null)
            .showImageForEmptyUri(null)
            .showImageOnFail(null)
            .resetViewBeforeLoading(false)
            .cacheInMemory(true)//设置下载的图片是否缓存在内存中
            .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
            .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
            .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
            .build();

    public static final DisplayImageOptions localNoCache = new DisplayImageOptions.Builder()
            .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
            .showImageOnLoading(null)
            .showImageForEmptyUri(null)
            .showImageOnFail(null)
            .resetViewBeforeLoading(false)
            .cacheInMemory(false)//设置下载的图片是否缓存在内存中
            .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
            .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
            .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
            .build();

    /**
     * 加载网络图片选项
     */
    public static final DisplayImageOptions networkOptions = new DisplayImageOptions.Builder()
            .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
            .showImageOnLoading(null)
            .showImageForEmptyUri(MyApp.getContext().getResources().getDrawable(R.drawable.img_logo_gray_square))
            .showImageOnFail(MyApp.getContext().getResources().getDrawable(R.drawable.img_logo_gray_square))
            .resetViewBeforeLoading(false)
            .cacheInMemory(true)//设置下载的图片是否缓存在内存中
            .cacheOnDisk(true)
            .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
            .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
//            .displayer(new FadeInBitmapDisplayer(300))//是否图片加载好后渐入的动画时间
            .build();

    public static final DisplayImageOptions displayImageNormal = new DisplayImageOptions.Builder()
            .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).showImageOnLoading(null)
            .showImageForEmptyUri(null).showImageOnFail(null)
            .resetViewBeforeLoading(false).cacheInMemory(false)
            .cacheOnDisc(true).considerExifParams(false)
            .bitmapConfig(Bitmap.Config.RGB_565).build();

}