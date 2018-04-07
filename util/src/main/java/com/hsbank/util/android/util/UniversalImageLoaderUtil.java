package com.hsbank.util.android.util;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.hsbank.util.java.type.StringUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * UniversalImageLoader公共类
 * Created by Administrator on 2016/1/28.
 */
public class UniversalImageLoaderUtil {
    /**
     * 初始化图片加载器
     * @param context
     */
    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPoolSize(5);                                           //设置线程池的容量
        config.threadPriority(Thread.NORM_PRIORITY - 2);                    //设置线程的优先级
        config.denyCacheImageMultipleSizesInMemory();                       //当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。默认会缓存多个不同的大小的相同图片
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());      //设置缓存文件的名字生成器
        config.diskCacheSize(50 * 1024 * 1024);                             //设置缓存大小为50M
        config.memoryCacheSize(2 * 1024 * 1024);                            //设置内存缓存大小为2M
        config.tasksProcessingOrder(QueueProcessingType.LIFO);              //设置图片下载和显示的处理队列的工作方式为LIFO(Last in, First out，后进先出)
        config.writeDebugLogs();                                            // 打印日志，发布时可以去掉这个配置
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    /**
     * 显示图片
     * @param mResName
     * @param imageView
     */
    public static void displayImage(String mResName, ImageView imageView) {
        mResName = StringUtil.dealString(mResName);
        if (mResName.startsWith("http://")) {
            ImageLoader.getInstance().displayImage(mResName, imageView);
        } else if (mResName.startsWith("https://")) {
            ImageLoader.getInstance().displayImage(mResName, imageView);
        } else if(mResName.startsWith("assets://")) {
            ImageLoader.getInstance().displayImage(mResName, imageView);
        } else if(mResName.startsWith("file:///mnt")) {
            ImageLoader.getInstance().displayImage(mResName, imageView);
        } else if(mResName.startsWith("content://")) {
            ImageLoader.getInstance().displayImage(mResName, imageView);
        } else if(mResName.startsWith("drawable://")) {
            ImageLoader.getInstance().displayImage(mResName, imageView);
        } else {
            Uri uri = Uri.parse(mResName);
            imageView.setImageURI(uri);
        }
    }
}
