package com.hsbank.luxclub.util.check_update;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.hsbank.luxclub.R;
import com.hsbank.luxclub.provider.ProviderFactory;
import com.hsbank.luxclub.util.LogUtil;
import com.hsbank.luxclub.util.ProjectConstant;
import com.hsbank.luxclub.util.dialog.CustomDialogUtil;
import com.hsbank.luxclub.util.dialog.DialogFragmentUtil;
import com.hsbank.luxclub.util.dialog.dialog_fragment.UpdateDialogFragment;
import com.hsbank.luxclub.util.dialog.listener.DialogFragmentListener;
import com.hsbank.util.android.AndroidUtil;
import com.hsbank.util.android.util.SharedPreferencesUtil;
import com.hsbank.util.android.util.http.api.ApiCodeConstant;
import com.hsbank.util.android.util.http.api.ApiResponseBean;
import com.hsbank.util.android.util.http.okhttp.OkHttpUtil;
import com.hsbank.util.android.util.http.okhttp.callback.ApiCallback;
import com.hsbank.util.android.util.http.okhttp.callback.FileCallBack;
import com.hsbank.util.java.tool.FileUtil;
import com.hsbank.util.project.util.BaseProjectCache;
import com.squareup.okhttp.Request;

import java.io.File;

/**
 * Author:      chen_liuchun
 * Date:        2016/6/17
 * Description: 应用更新类, 单例
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public class CheckUpdateHelper {

    public CheckUpdateHelper(Context context) {
        mContext = context;
    }

    private Context mContext;

    // 下载信息实体
    private CheckUpdateEntity mCheckUpdateEntity;
    // 下载路径
    private String mAPKPath = BaseProjectCache.FILE_PATH_UPDATE;
    // 下载文件名
    private String mAPKName = BaseProjectCache.getUpdateFileName();
    // 下载后是否自动安装
    private boolean mIsAuto;
    // 下载通知
    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;

    private static final int UPDATE_START = 0x11;
    private static final int UPDATE_PROGRESS = 0x12;
    private static final int UPDATE_COMPLETE = 0x13;
    private static final int DOWNLOAD_FAIL = 0x14;
    private static final int DOWNLOAD_CANCEL = 0x15;
    private static final int NOTIFICATION_ID = 0x1001;

    private float mAppSize; // app文件大小（byte）
    private float mProgress; // 文件已下载大小（byte）
    // 是否需要更新
    public boolean mNeedUpdate;
    /**
     * 对话框类型 {@link UpdateDialogFragment}
     */
    private int mType;
    // 是否立刻弹出更新
    private boolean mIsNowUpdate;

    // 对话框弹出回调接口
    public interface CheckDialogListener {
        void dialogShow(int which);
    }

    private CheckUpdateCallback mCheckUpdateCallback;
    /**
     * 检查更新的回调
     */
    public interface CheckUpdateCallback{
        void success();
        void fail();
        void noUpdate();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_START:
                    if (mIsNowUpdate){
                        showUpdateDialog(mContext, true, null);
                    }
                    break;
                case UPDATE_PROGRESS:
                    showProgressNotification(msg.arg1);
                    // 间隔1s更新进度
                    Message message = handler.obtainMessage(UPDATE_PROGRESS, (int) (mProgress / mAppSize * 100), -1);
                    handler.sendMessageDelayed(message, 1000);
                    break;
                case UPDATE_COMPLETE:
                    handler.removeCallbacksAndMessages(null);
                    notificationManager.cancel(NOTIFICATION_ID);
                    if (mIsAuto) {
                        AndroidUtil.installApk(mContext, mAPKPath + mAPKName);
                        //安装完成后，重新显示引导页
                        SharedPreferencesUtil.getInstance().setBoolean(ProjectConstant.IS_FIRST_ENTER, false);
                    } else {
                        showCompleteNotification();
                    }
                    break;
                case DOWNLOAD_FAIL:
                    // 弹出下载失败的对话框
                    DialogFragmentUtil.standard(mContext,
                            R.string.str_download_fail, R.string.str_tip_download_fail,
                            R.string.str_download_browser, R.string.str_retry, new DialogFragmentListener() {
                                @Override
                                public void onDialogClick(DialogInterface dialog, int which) {
                                    LogUtil.i3("转浏览器下载: " + dialog.toString());
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE: // 转浏览器下载
                                            Intent browserIntent = getBrowserIntent(mCheckUpdateEntity.getUrl());
                                            if (browserIntent == null) return;
                                            mContext.startActivity(browserIntent);
                                            break;
                                        case DialogInterface.BUTTON_NEGATIVE: // 重试下载
                                            downLoadAPK(mCheckUpdateEntity.getUrl(), mAPKPath, mAPKName);
                                            break;
                                    }
                                }
                            });
                    break;
                case DOWNLOAD_CANCEL:
                    handler.removeCallbacksAndMessages(null);
                    if (notificationManager != null) {
                        notificationManager.cancel(NOTIFICATION_ID);
                    }
                    break;
            }
        }
    };

    /**
     * 检查应用更新, 通过eventbus通知activity
     *
     * @param isNowUpdate 检查完接口后是否立刻弹出更新窗口
     */
    public void check(boolean isNowUpdate, CheckUpdateCallback checkUpdateCallback) {
        mIsNowUpdate = isNowUpdate;
        mCheckUpdateCallback = checkUpdateCallback;
        ProviderFactory.getInstance().checkUpdate(null, updateCallback);
    }

    /**
     * 显示更新对话框
     *
     * @param isAuto 下载完后是否自动安装
     */
    public void showUpdateDialog(Context context, boolean isAuto, final CheckDialogListener checkUpdateListener) {
        if (!mNeedUpdate) {
            LogUtil.i3("应用程序不需要更新！");
            return;
        }
        mContext = context;
        mIsAuto = isAuto;
        FileUtil.createDir(BaseProjectCache.FILE_PATH_UPDATE); // 创建下载目录
        initNotification();
        String versionInfo = mCheckUpdateEntity.getVersionInfo();
        String androidAppSize = mCheckUpdateEntity.getAndroidAppSize();
        mAppSize = Float.parseFloat(androidAppSize);
        CustomDialogUtil.showUpdateDialog(mContext, mType, versionInfo, new DialogFragmentListener() {

            @Override
            public void onDialogClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        downLoadAPK(mCheckUpdateEntity.getUrl(), mAPKPath, mAPKName);
                        // 间隔1s更新进度
                        Message message = handler.obtainMessage(UPDATE_PROGRESS, (int) (mProgress / mAppSize * 100), -1);
                        handler.sendMessageDelayed(message, 1000);
                        if (checkUpdateListener != null) {
                            checkUpdateListener.dialogShow(DialogInterface.BUTTON_POSITIVE);
                        }
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        if (checkUpdateListener != null) {
                            checkUpdateListener.dialogShow(DialogInterface.BUTTON_NEGATIVE);
                        }
                        break;
                }
            }
        });
    }

    // 初始化任务栏通知
    private void initNotification() {
        if (builder == null) {
            builder = new NotificationCompat.Builder(mContext);
            builder.setSmallIcon(mContext.getApplicationInfo().icon)
                    .setContentTitle(mContext.getApplicationInfo().name)
                    .setWhen(System.currentTimeMillis()) //通知产生的时间，会在通知信息里显示
//				    .setAutoCancel(true) //设置这个标志当用户单击面板就可以让通知将自动取消
                    .setOngoing(true); // 设置他为一个正在进行的通知
//                    .setDefaults(Notification.DEFAULT_LIGHTS); //向通知添加声音、闪灯和振动效果, 慎用！
            if (Build.VERSION.SDK_INT >= 16) {
                builder.setPriority(Notification.PRIORITY_HIGH); // 设置该通知优先级
            }
        }
        if (notificationManager == null) {
            notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        }
    }

    /**
     * 显示下载的通知
     *
     * @param progress 通知进度
     */
    private void showProgressNotification(int progress) {
        initNotification();
        String contentStr = String.valueOf(progress) + "%";
        PendingIntent intent = PendingIntent.getActivity(mContext,
                0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setTicker(mContext.getString(R.string.str_begin_start_download) + mContext.getString(R.string.app_name))
                .setContentTitle(mContext.getString(R.string.app_name) + mContext.getString(R.string.str_downloading))
                .setWhen(System.currentTimeMillis())
                .setContentText(contentStr)
                .setContentIntent(intent)
                .setProgress(100, progress, false);
//        Notification notification = builder.build();
//        notification.flags = Notification.FLAG_ONGOING_EVENT; // 常驻通知栏
//        notification.when=System.currentTimeMillis();
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    // 下载完成的通知, 点击 即可安装APK
    private void showCompleteNotification() {
        initNotification();
        Intent installIntent = getInstallIntent(mAPKPath + mAPKName);
        if (installIntent == null) return;

        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, installIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setTicker(mContext.getString(R.string.app_name) + mContext.getString(R.string.str_download_finish))
                .setContentText(mContext.getString(R.string.str_download_finish) + mContext.getString(R.string.str_click_install))
                .setContentIntent(pendingIntent);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    /**
     * 获取安装APK的意图
     *
     * @return intent
     */
    private Intent getInstallIntent(String filePathName) {
        if (TextUtils.isEmpty(filePathName)) return null;

        File file = new File(filePathName);
        if (!file.exists()) return null;

        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        //新开任务栈存放安装页面
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    /**
     * 获取打开浏览器的意图
     *
     * @return intent
     */
    private Intent getBrowserIntent(String uriString) {
        if (TextUtils.isEmpty(uriString)) return null;

        Uri uri = Uri.parse(uriString);
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    private void downLoadAPK(String url, String path, String name) {
        OkHttpUtil
                .get()
                .url(url)
                .build(false)
                .execute(null, new FileCallBack(path, name) {
                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        mCheckUpdateCallback.success();
                    }

                    @Override
                    public void inProgress(float progress) {
                        mProgress = progress;
                    }

                    @Override
                    public void onError(Request request, Exception e) {
                        e.printStackTrace();
                        // 调用浏览器下载或者重试
                        handler.sendEmptyMessage(DOWNLOAD_FAIL);
                        mCheckUpdateCallback.fail();
                    }

                    @Override
                    public void onResponse(File response) {
                        handler.sendEmptyMessage(UPDATE_COMPLETE);
                    }
                });
    }

    public boolean isNeedUpdate() {
        return mNeedUpdate;
    }

    /**
     * 取消下载的通知栏
     */
    public void cancelDownload(){
        handler.sendEmptyMessage(DOWNLOAD_CANCEL);
    }

    // 更新信息回调
    private ApiCallback updateCallback = new ApiCallback() {
        @Override
        public void onError(Request request, Exception e) {
            super.onError(request, e);
        }

        @Override
        public void onResponse(ApiResponseBean response) {
            if (response.getCode() == ApiCodeConstant.SUCCESS_CODE) {
                mCheckUpdateEntity = JSONObject.parseObject(response.getData(), CheckUpdateEntity.class);
                if (mCheckUpdateEntity.getNeedForcedUpdate()) {
                    mNeedUpdate = true;
                    mType = UpdateDialogFragment.TYPE_FORCE;
                     handler.obtainMessage(UPDATE_START, mType, 0).sendToTarget();
                } else if (mCheckUpdateEntity.getNeedUpdate()) {
                    mNeedUpdate = true;
                    mType = UpdateDialogFragment.TYPE_UPDATE;
                    handler.obtainMessage(UPDATE_START, mType, 0).sendToTarget();
                } else {
                    mNeedUpdate = false;
                }
            } else {
                mCheckUpdateCallback.noUpdate();
                LogUtil.i3(response.getText());
            }
        }
    };

}
