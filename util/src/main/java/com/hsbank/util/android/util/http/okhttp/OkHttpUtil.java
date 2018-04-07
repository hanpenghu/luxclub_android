package com.hsbank.util.android.util.http.okhttp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.hsbank.util.android.util.dialog.LoadingDialog;
import com.hsbank.util.android.util.http.okhttp.builder.GetBuilder;
import com.hsbank.util.android.util.http.okhttp.builder.PostFileBuilder;
import com.hsbank.util.android.util.http.okhttp.builder.PostFormBuilder;
import com.hsbank.util.android.util.http.okhttp.builder.PostStringBuilder;
import com.hsbank.util.android.util.http.okhttp.callback.util.OkHttpCallback;
import com.hsbank.util.android.util.http.okhttp.https.OkHttpsUtil;
import com.hsbank.util.android.util.http.okhttp.request.util.RequestCall;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.net.CookieManager;
import java.net.CookiePolicy;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * OkHttpUtil封裝工具类
 * <p>
 * https://github.com/hongyangAndroid/okhttp-utils
 * <p>
 * Created by zhy on 15/8/17.
 */
public class OkHttpUtil {
    public static final long DEFAULT_MILLISECONDS = 20000;
    /**MediaType: JSON*/
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    /**单例*/
    private static OkHttpUtil mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;

    private OkHttpUtil() {
        mOkHttpClient = new OkHttpClient();
        //cookie enabled
        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        mDelivery = new Handler(Looper.getMainLooper());
        if (false) {
            mOkHttpClient.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {return true;
                }
            });
        }
    }

    public static OkHttpUtil getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpUtil.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtil();
                }
            }
        }
        return mInstance;
    }

    public Handler getDelivery() {
        return mDelivery;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public static GetBuilder get() {
        return new GetBuilder();
    }

    public static PostStringBuilder postString() {
        return new PostStringBuilder();
    }

    public static PostFileBuilder postFile() {
        return new PostFileBuilder();
    }

    public static PostFormBuilder post() {
        return new PostFormBuilder();
    }

    public void execute(final Context context, final RequestCall requestCall, OkHttpCallback callback) {
        //显示加载等待对话框
        LoadingDialog.getInstance().show(context);
        if (callback == null) {
            callback = OkHttpCallback.CALLBACK_DEFAULT;
        }
        final OkHttpCallback finalCallback = callback;
        requestCall.getCall().enqueue(new com.squareup.okhttp.Callback() {
            @Override
            public void onFailure(final Request request, final IOException e) {
                //取消加载等待对话框
                LoadingDialog.getInstance().cancel(context);
                sendFailResultCallback(request, e, finalCallback);
            }

            @Override
            public void onResponse(final Response response) {
                //延时5秒
                //ThreadUtil.sleep(5000);
                //取消加载等待对话框
                LoadingDialog.getInstance().cancel(context);
                if (response.code() >= 400 && response.code() <= 599) {
                    try {
                        sendFailResultCallback(requestCall.getRequest(), new RuntimeException(response.body().string()), finalCallback);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                try {
                    Object o = finalCallback.parseNetworkResponse(response);
                    sendSuccessResultCallback(o, finalCallback);
                } catch (IOException e) {
                    sendFailResultCallback(response.request(), e, finalCallback);
                }
            }
        });
    }

    public void sendFailResultCallback(final Request request, final Exception e, final OkHttpCallback callback) {
        if (callback == null) {return;}
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(request, e);
                callback.onAfter();
            }
        });
    }

    public void sendSuccessResultCallback(final Object object, final OkHttpCallback callback) {
        if (callback == null) { return;}
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(object);
                callback.onAfter();
            }
        });
    }

    public void cancelTag(Object tag) {
        mOkHttpClient.cancel(tag);
    }

    public void setCertificates(InputStream... certificates) {
        OkHttpsUtil.setCertificates(getOkHttpClient(), certificates, null, null);
    }

}

