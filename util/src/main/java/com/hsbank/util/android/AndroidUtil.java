package com.hsbank.util.android;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.hsbank.util.android.util.bean.NetworkState;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AndroidUtil {
    /**
     * 设置网络状态
     * @param context       上下文对象
     */
    public static void setNetworkState(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //设置网络的连接情况
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isAvailable()) {
            if (ni.getType() == ConnectivityManager.TYPE_WIFI){
                //WIFI
                NetworkState.getInstance().setCurrentState(NetworkState.TYPE_WIFI);
            } else if (ni.getType() == ConnectivityManager.TYPE_MOBILE) {
                //手机网络
                NetworkState.getInstance().setCurrentState(NetworkState.TYPE_MOBILE);
            } else {
                //离线模式
                NetworkState.getInstance().setCurrentState(NetworkState.TYPE_NONE);
            }
            Log.d(AndroidInit.class.getName(), "Current network：type = " + ni.getType());
            Log.d(AndroidInit.class.getName(), "Current network：typeName = " + ni.getTypeName());
        } else {
            Log.d(AndroidInit.class.getName(), "No available network");
            //离线模式
            NetworkState.getInstance().setCurrentState(NetworkState.TYPE_NONE);
        }
    }

    /**
     * 得到指定名称的元数据的值
     * @param context       上下文对象
     * @param name          元数据名称
     * @return              元数据的值
     */
    public static String getMetaDataValue(Context context, String name) {
        String resultValue = "";
        ApplicationInfo applicationInfo = null;
        if (context != null) {
            try {
                applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null && applicationInfo.metaData.get(name) != null) {
                        resultValue = String.valueOf(applicationInfo.metaData.get(name));
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(AndroidUtil.class.getName(), e.getMessage());
            }
        }
        return resultValue;
    }

    /**
     * 当前应用是否在前端运行
     * @return          ture or false
     */
    public static boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {return false;}
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName) && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

	/**
	 * 是否有可用的SD卡
	 * @return          ture or false
	 */
	public static boolean hasSdCard(){
		//得到外部存储的状态
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)){
        	//SD卡正常挂载
            return true;
        } else {
        	return false;
        }
	}

    /**
     * 得到手机内已安装的所有应用的列表（包含系统应用和自己安装的应用）
     * @param context       上下文对象
     * @return              手机内已安装的所有应用的列表
     */
    public static List<PackageInfo> getAppList(Context context) {
        PackageManager pManager = context.getPackageManager();
        return pManager.getInstalledPackages(0);
    }

    /**
     * 得到手机内已安装的所有系统应用的列表
     * @param context       上下文对象
     * @return              手机内已安装的所有系统应用的列表
     */
    public static List<PackageInfo> getSystemAppList(Context context) {
        List<PackageInfo> resultValue = new ArrayList<PackageInfo>();
        List<PackageInfo> appList = getAppList(context);
        for (int i = 0; i < appList.size(); i++) {
            PackageInfo pi = (PackageInfo) appList.get(i);
            //判断是否为非系统预装的应用程序
            if ((pi.applicationInfo.flags & pi.applicationInfo.FLAG_SYSTEM) <= 0) {
                //非系统应用
            } else {
                //系统应用
                resultValue.add(pi);
            }
        }
        return resultValue;
    }

    /**
     * 得到手机内已安装的所有非系统预装的应用的列表
     * @param context       上下文对象
     * @return              手机内已安装的所有非系统预装的应用的列表
     */
    public static List<PackageInfo> getCustomAppList(Context context) {
        List<PackageInfo> resultValue = new ArrayList<PackageInfo>();
        List<PackageInfo> appList = getAppList(context);
        for (int i = 0; i < appList.size(); i++) {
            PackageInfo pi = (PackageInfo) appList.get(i);
            //判断是否为非系统预装的应用
            if ((pi.applicationInfo.flags & pi.applicationInfo.FLAG_SYSTEM) <= 0) {
                //非系统应用
                resultValue.add(pi);
            }
        }
        return resultValue;
    }

    /**
     * 得到手机内已安装的所有支持分享的应用的列表
     * @param context       上下文对象
     * @return              手机内已安装的所有支持分享的应用的列表
     */
    public static List<ResolveInfo> getShareAppList(Context context){
        Intent intent = new Intent(Intent.ACTION_SEND,null);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setType("text/plain");
        PackageManager pm = context.getPackageManager();
        return pm.queryIntentActivities(intent, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
    }

    /**
     * 安装指定路径的Apk
     * @param context       上下文对象
     * @param filePathName  指定路径名称
     */
    public static void installApk(Context context, String filePathName) {
        if (context != null && filePathName != null) {
            File file = new File(filePathName);
            if (file.exists()) {
                Uri uri = Uri.fromFile(file);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 新开任务栈存放安装页面
                context.startActivity(intent);
            }
        }
    }

    /**
     * 卸载指定包名的Apk
     * @param context       上下文对象
     * @param packageName   指定包名
     */
    public static void uninstallApk(Context context, String packageName) {
        if (context != null && packageName != null) {
            Uri uri = Uri.parse("package:" + packageName);
            Intent intent = new Intent(Intent.ACTION_DELETE, uri);
            context.startActivity(intent);
        }
    }

    /**
     * 是否已安装指定包名的Apk
     * @param context       上下文对象
     * @param packageName   指定包名
     * @return              ture or false
     */
    public static boolean hasInstallApk(Context context, String packageName) {
        //TODO
        /*
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
        } catch (PackageManager.NameNotFoundException e) {
            // e.printStackTrace();
        }
        return (applicationInfo != null);
        */
        return false;
    }

    /**
     * 打电话
     * @param context       上下文对象
     * @param phone         电话号码
     */
    public static void callPhone(Context context, String phone) {

        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        // 任何环境均可启动
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 发短信
     * @param context       上下文对象
     * @param mobile        手机号码
     * @param content       短信内容
     */
    public static void sendSms(Context context, String mobile, String content) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + mobile));
        intent.putExtra("sms_body", content);
        ((Activity) context).startActivity(intent);
    }

    /**
     * 设置网络
     * @param context       上下文对象
     */
    public static void setNetwork(final Context context){
        //提示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("设置网络").setMessage("网络连接不可用，是否设置网络？").setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = null;
                //判断手机系统的版本  即API大于10 就是3.0或以上版本
                if(android.os.Build.VERSION.SDK_INT>10){
                    intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                } else {
                    intent = new Intent();
                    ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
                    intent.setComponent(component);
                    intent.setAction("android.intent.action.VIEW");
                }
                context.startActivity(intent);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    /**
     * 打开链接
     * @param context       上下文对象
     * @param url           链接
     */
    public static void openUrl(Context context, String url) {
        Log.d(AndroidUtil.class.getSimpleName(), url);
        Uri uri = Uri.parse(url);
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(it);
    }
}
