package com.hsbank.util.android;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.hsbank.util.android.util.ResourceUtil;
import com.hsbank.util.android.util.SharedPreferencesUtil;
import com.hsbank.util.android.util.UniversalImageLoaderUtil;
import com.hsbank.util.android.util.X509CertificateUtil;
import com.hsbank.util.android.util.http.okhttp.OkHttpUtil;
import com.hsbank.util.android.util.http.okhttp.https.OkHttpsUtil;
import com.hsbank.util.project.provider.util.ClientBean;
import com.hsbank.util.project.util.BaseProjectConstant;
import com.squareup.okhttp.OkHttpClient;

import java.security.cert.X509Certificate;

public class AndroidInit {
    /**单例*/
    private static AndroidInit instance = null;
    //---------------------------------------------------
    /**私有构造函数*/
    protected AndroidInit() {
        Log.d(this.getClass().getName(), "................" + this.getClass().getName() + "..................");
    }

    /**
     * 设置单例
     * @return  单例
     */
    public static synchronized AndroidInit getInstance() {
        return instance == null ? instance = new AndroidInit() : instance;
    }

    /**
     * 初始化Android系统信息
     * @param context
     */
    public void init(Context context) {
        //1.设置公共对象的上下文对象
        SharedPreferencesUtil.getInstance().setContext(context);
        ResourceUtil.getInstance().setContext(context);
        //2.设置当前App的名称、包名、版本、图标、权限、签名
        setAppParameters(context);
        //3.设置ApiLevel
        setApiLevel(context);
        //4.设置移动设备的Mac地址
        setMobileMacAddress(context);
        //5.设置移动设备国际识别码、国际移动用户识别码、手机号码
        setTelephonyInfo(context);
        //6.设置网络状态
        AndroidUtil.setNetworkState(context);
        //7.设置屏幕的尺寸、宽度、高度、密度等参数
        setScreenSize(context);
        //8.设置当前应用的签名证书的md5指纹
        setMd5Fingerprint(context);
        //9.设置当前应用的发布渠道
        setChannel(context);
        //10.初始化图片加载器
        UniversalImageLoaderUtil.initImageLoader(context);
        //11.注册OkHttpClient
        OkHttpClient client = OkHttpUtil.getInstance().getOkHttpClient();
        //12.设置https证书
        OkHttpsUtil.trustAllHosts(client);
        //13.系统通用对象日志
        Log.d(this.getClass().getName(), AndroidSystemInfo.getInstance().toString());
        Log.d(this.getClass().getName(), AndroidAppInfo.getInstance().toString());
        Log.d(this.getClass().getName(), ClientBean.getInstance().toString());
    }

    /**
     * 设置当前App的名称、包名、版本、图标、权限、签名
     * 当前App的版本信息在manifest.xml文件中设置，如下所示：
     * <?xml version="1.0" encoding="utf-8"?>
     * <manifest xmlns:android="http://schemas.android.com/apk/res/android" package="com.example.package.name"
     *      android:versionCode="2"
     *      android:versionName="1.1">
     *  <application>
     *  ...
     *  </application>
     * </manifest>
     * versionCode：对消费者不可见，仅用于应用市场、程序内部识别版本，判断新旧等用途。
     * versionName：展示给消费者，消费者会通过它认知自己安装的版本。一般我们说的版本号就是这个。
     * @param context
     */
    private void setAppParameters(Context context) {
        if (AndroidAppInfo.getInstance().getPackageName() == null) {
            PackageManager pm = context.getPackageManager();
            try {
                PackageInfo pi01 = pm.getPackageInfo(context.getPackageName(), 0);
                if (pi01 != null) {
                    //包名
                    AndroidAppInfo.getInstance().setPackageName(pi01.packageName);
                    //版本名称
                    AndroidAppInfo.getInstance().setVersionName(pi01.versionName);
                    //版本号
                    AndroidAppInfo.getInstance().setVersionCode(pi01.versionCode);

                }
                PackageInfo pi02 = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
                if (pi02 != null) {
                    //权限
                    AndroidAppInfo.getInstance().setAppPermissions(pi01.requestedPermissions);
                }
                PackageInfo pi03 = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
                if (pi03 != null) {
                    //签名
                    AndroidAppInfo.getInstance().setAppSignature(pi03.signatures[0].toCharsString());
                }
                ApplicationInfo ai = pm.getApplicationInfo(context.getPackageName(), 0);
                if (ai != null) {
                    //图标
                    AndroidAppInfo.getInstance().setAppIcon(ai.loadIcon(pm));
                    //名称
                    AndroidAppInfo.getInstance().setAppName(ai.loadLabel(pm).toString());
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(AndroidInit.this.getClass().getName(), e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * 设置ApiLevel
     * @param context
     */
    private void setApiLevel(Context context) {
        if (AndroidSystemInfo.getInstance().getApiLevel() == 0) {
            AndroidSystemInfo.getInstance().setApiLevel(Build.VERSION.SDK_INT);
        }
    }

    /**
     * 设置手机的Mac地址
     * @param context
     */
    private void setMobileMacAddress(Context context){
        if (AndroidSystemInfo.getInstance().getDeviceMacAddress() == null) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            AndroidSystemInfo.getInstance().setDeviceMacAddress(wifiInfo.getMacAddress());
        }
    }

    /**
     * 设置移动设备国际识别码、国际移动用户识别码、手机号码
     * @param context
     */
    private void setTelephonyInfo(Context context) {
        if (AndroidSystemInfo.getInstance().getImei() == null) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            AndroidSystemInfo.getInstance().setImei(tm.getDeviceId());
            AndroidSystemInfo.getInstance().setImsi(tm.getSubscriberId());
            AndroidSystemInfo.getInstance().setDeviceNumber(tm.getLine1Number());
        }
    }

    /**
     * 设置屏幕的尺寸、宽度、高度、密度等参数
     * @param context
     */
    private void setScreenSize(Context context){
        if (AndroidSystemInfo.getInstance().getScreenWidth() <= 0) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            DisplayMetrics dm = new DisplayMetrics();
            display.getMetrics(dm);
            //屏幕尺寸
            double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
            double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
            double screenSize = Math.round(Math.sqrt(x + y) * 10) * 0.1;
            AndroidSystemInfo.getInstance().setScreenSize(screenSize);
            //屏幕的宽度、高度
            Point size = new Point();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                display.getRealSize(size);
            } else {
                display.getSize(size);
            }
            AndroidSystemInfo.getInstance().setScreenWidth(size.x);
            AndroidSystemInfo.getInstance().setScreenHeight(size.y);
            //屏幕密度
            AndroidSystemInfo.getInstance().setScreenDensity(dm.density);
            //屏幕像素密度
            AndroidSystemInfo.getInstance().setScreenDensityDpi(dm.densityDpi);
            //字体缩放比例
            AndroidSystemInfo.getInstance().setScaledDensity(dm.scaledDensity);
        }
    }

    /**
     * 设置当前应用的签名证书的md5指纹
     * @param context
     */
    private void setMd5Fingerprint(Context context) {
        if (AndroidAppInfo.getInstance().getMd5() == null) {
            X509Certificate[] certArray = X509CertificateUtil.getSignatureInfo(context);
            if (certArray == null || certArray.length <= 0) {
                AndroidAppInfo.getInstance().setMd5("");
            } else {
                String md5 = X509CertificateUtil.getFingerprint(certArray[0], X509CertificateUtil.MESSAGE_DIGEST_ALGORITHM_MD5);
                AndroidAppInfo.getInstance().setMd5(md5);
            }
        }
    }

    /**
     * 设置当前应用的发布渠道
     * @param context
     */
    private void setChannel(Context context) {
        if (AndroidAppInfo.getInstance().getChannel() == null) {
            String channel = AndroidUtil.getMetaDataValue(context, BaseProjectConstant.META_DATA_APP_CHANNEL);
            AndroidAppInfo.getInstance().setChannel(channel);
        }
    }
}
