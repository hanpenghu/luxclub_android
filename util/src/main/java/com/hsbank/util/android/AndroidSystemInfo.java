package com.hsbank.util.android;

import android.os.Build;
import android.util.Log;

import com.hsbank.util.java.tool.CarrierUtil;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Locale;

/**
 * Android系统信息
 * Created by Administrator on 2015/12/13.
 */
public class AndroidSystemInfo {
    /**单例*/
    private static AndroidSystemInfo instance = null;
    //---------------------------------------------------
    /**移动设备型号*/
    private String deviceModel = null;
    /**移动设备品牌*/
    private String deviceBand = null;
    //移动设备号码
    private String deviceNumber = null;
    /**移动设备Mac地址*/
    private String deviceMacAddress = null;
    /**
     * IMEI（International Mobile Equipment Identity，移动设备国际识别码，又称为国际移动设备标识
     * 是手机的唯一识别号码。
     * 拨打#06#即可查询移动设备国际识别码。
     * 从这个缩写的全称中来分析它的含义：
     * “移动设备”就是手机，不包括便携式电脑。
     * “国际”这个字眼也表明了它可辨识的范围是全球，即全球范围内IMEI不会重复。
     * “识别”表明了它的作用，是辨识不同的手机。
     * “码”字又说明它是一串编号，常称为手机的“串号”、“电子串号”。
     */
    private String imei = null;
    /**
     * IMSI：International Mobile Subscriber IdentificationNumber, 国际移动用户识别码）
     * 是国际上为唯一识别一个移动用户所分配的号码，储存在SIM卡中。
     * IMSI共有15位，其结构如右：MCC + MNC + MIN
     * MCC：Mobile Country Code，移动用户所属国家代号，共3位，中国为460;
     * MNC：Mobile Network Code，移动网络码，共2位，用于识别移动用户所归属的移动通信网;
     * MIN：是移动用户识别码，用以识别某一移动通信网中的移动用户。共有10位，其结构如右：09 + M0M1M2M3 + ABCD
     *      其中的M0M1M2M3和MDN号码中的H0H1H2H3可存在对应关系，ABCD四位为自由分配。
     * IMSI在MIN号码前加了MCC，可以区别出每个用户的来自的国家，因此可以实现国际漫游。在同一个国家内，如果有多个运营商，可以通过MNC来进行区别.
     * 从技术上讲，IMSI可以彻底解决国际漫游问题。但是由于北美目前仍有大量的AMPS系统使用MIN号码，
     * 且北美的MDN和MIN采用相同的编号，系统已经无法更改，所以目前国际漫游暂时还是以MIN为主。
     * 其中以O和1打头的MIN资源称为IRM(International Roaming MIN)，
     * 由IFAST (International Forum on ANSI-41 Standards Technology)统一管理。
     * 可以看出，随着用户的增长，用于国际漫游的MIN资源将很快耗尽，全球统一采用IMSI标识用户势在必行.
     */
    private String imsi = null;
    /**sdk版本*/
    private String sdkVersion = null;
    /**操作系统版本*/
    private String version = null;
    /**ApiLevel*/
    private int apiLevel = 0;
    /**操作系统语言*/
    private String language = null;
    /**移动运营商*/
    private String carrier = null;
    /**屏幕尺寸：屏幕对角线的长度，单位是英寸
     * 简单起见，Android把所有的屏幕大小分为四种尺寸：
     * 小、普通、大、超大(分别对应：small、normal、large、extra large).
     * 应用程序可以为这四种尺寸分别提供不同的自定义屏幕布局，
     * 平台将根据屏幕实际尺寸选择对应布局进行渲染，
     * 这种选择对于程序侧是透明的。*/
    private double screenSize = 0.0;
    /**屏幕宽度：
     * 注意：在没有设定多分辨率支持的情况下，Android系统会将240x320的低密度（120）尺寸转换为中等密度（160）对应的尺寸，
     * 这样的话代码得到的屏幕宽高就会不准确。所以，需要在工程的AndroidManifest.xml文件中，加入supports-screens节点，
     * 内容如下：
     * <supports-screens
     * android:smallScreens="true"
     * android:normalScreens="true"
     * android:largeScreens="true"
     * android:resizeable="true"
     * android:anyDensity="true" />
     */
    private int screenWidth = 0;
    /**屏幕高度：
     * 注意：在没有设定多分辨率支持的情况下，Android系统会将240x320的低密度（120）尺寸转换为中等密度（160）对应的尺寸，
     * 这样的话代码得到的屏幕宽高就会不准确。所以，需要在工程的AndroidManifest.xml文件中，加入supports-screens节点，
     * 内容如下：
     * <supports-screens
     * android:smallScreens="true"
     * android:normalScreens="true"
     * android:largeScreens="true"
     * android:resizeable="true"
     * android:anyDensity="true" />
     */
    private int screenHeight = 0;
    /**屏幕密度：指每平方英寸中的像素数。
     * DENSITY_LOW = 120
     * DENSITY_MEDIUM = 160  //默认值
     * DENSITY_TV = 213      //TV专用
     * DENSITY_HIGH = 240
     * DENSITY_XHIGH = 320
     * DENSITY_400 = 400
     * DENSITY_XXHIGH = 480
     * DENSITY_XXXHIGH = 640
     */
    private float screenDensity = 0;
    /**屏幕像素密度：
     * DPI：dots-per-inch,即每英寸的点数
     * 如160dpi指手机水平或垂直方向上每英寸距离有160个像素点。
     * 假定设备分辨率为320*240，屏幕长2英寸宽1.5英寸，dpi=320/2=240/1.5=160
     */
    private int screenDensityDpi = 0;
    /**字体缩放比例*/
    private float scaledDensity = 0;

    /**私有构造函数*/
    protected AndroidSystemInfo() {
        Log.d(this.getClass().getName(), "................" + this.getClass().getName() + "..................");
    }

    /**
     * 得到单例
     * @return  单例
     */
    public static synchronized AndroidSystemInfo getInstance() {
        return instance == null ? instance = new AndroidSystemInfo() : instance;
    }

    public String getDeviceModel() {
        if (deviceModel == null) {
            deviceModel = android.os.Build.MODEL;
        }
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDeviceBand() {
        if (deviceBand == null) {
            deviceBand = android.os.Build.BRAND;
        }
        return deviceBand;
    }

    public void setDeviceBand(String deviceBand) {
        this.deviceBand = deviceBand;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public String getDeviceMacAddress() {
        return deviceMacAddress;
    }

    public void setDeviceMacAddress(String deviceMacAddress) {
        this.deviceMacAddress = deviceMacAddress;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getSdkVersion() {
        if (sdkVersion == null) {
            sdkVersion = String.valueOf(Build.VERSION.SDK_INT);
        }
        return sdkVersion;
    }

    public void setSdkVersion(String sdkVersion) {
        this.sdkVersion = sdkVersion;
    }

    public String getVersion() {
        if (version == null) {
            version = String.valueOf(Build.VERSION.RELEASE);
        }
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getApiLevel() {
        return apiLevel;
    }

    public void setApiLevel(int apiLevel) {
        this.apiLevel = apiLevel;
    }

    public String getLanguage() {
        if (language == null) {
            language = Locale.getDefault().getLanguage();
        }
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCarrier() {
        if (carrier == null) {
            carrier = CarrierUtil.getNameByImsi(imsi);
        }
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public double getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(double screenSize) {
        this.screenSize = screenSize;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public float getScreenDensity() {
        return screenDensity;
    }

    public void setScreenDensity(float screenDensity) {
        this.screenDensity = screenDensity;
    }

    public int getScreenDensityDpi() {
        return screenDensityDpi;
    }

    public void setScreenDensityDpi(int screenDensityDpi) {
        this.screenDensityDpi = screenDensityDpi;
    }

    public float getScaledDensity() {
        return scaledDensity;
    }

    public void setScaledDensity(float scaledDensity) {
        this.scaledDensity = scaledDensity;
    }

    public String toString(){
        return ReflectionToStringBuilder.toString(this);
    }
}
