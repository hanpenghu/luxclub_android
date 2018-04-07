package com.hsbank.util.android.util;

/**
 * 各种显示单位的转换
 * <p>
 * px:          Pixel，像素
 *
 * dip:         Device-independent pixel，设备独立像素,
 *              同dp，可作长度单位，不同设备有不同的显示效果, 这个和设备硬件有关.
 *              一般我们为了支持WVGA、HVGA和QVGA 推荐使用这个，不依赖像素。
 *              dip和具体像素值的对应公式是：dip = density / 160 * pixel
 *              可以看出在dpi（像素密度）为160dpi的设备上1px=1dip
 *
 * density:     指每平方英寸中的像素数。密度
 *
 * dpi:         dots-per-inch,即每英寸的点数。像素密度
 *
 * sp:          ScaledPixels，字体缩放比例。
 *              主要用于字体显示（best for textsize）。
 *              根据 google 的建议，TextView 的字号最好使用 sp 做单位，
 *              而且查看TextView的源码可知 Android 默认使用 sp 作为字号单位。
 * <p>
 * 2015-12-22
 */
public class DisplayUtil {
    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     * @param px
     * @param density
     * @return
     */
    public static int px2dip(float px, float density) {
        return (int) (px / density + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     * @param dip
     * @param density
     * @return
     */
    public static int dip2px(float dip, float density) {
        return (int) (dip * density + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     * @param px
     * @param scaledDensity
     * @return
     */
    public static int px2sp(float px, float scaledDensity) {
        return (int) (px / scaledDensity + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     * @param sp
     * @param scaledDensity
     * @return
     */
    public static int sp2px(float sp, float scaledDensity) {
        return (int) (sp * scaledDensity + 0.5f);
    }
}