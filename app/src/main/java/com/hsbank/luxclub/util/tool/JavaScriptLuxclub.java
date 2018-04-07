package com.hsbank.luxclub.util.tool;

import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.hsbank.luxclub.util.ProjectUtil;
import com.hsbank.util.android.AndroidUtil;
import com.hsbank.util.android.util.SharedPreferencesUtil;

/**
 * Author:      chenliuchun
 * Date:        17/2/23
 * Description: 微信页面js调用java类
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public class JavaScriptLuxclub {
    public static final String NAME_MAIN = "luxclub";

    private FragmentActivity activity;
    private WebView webView ;
    private Handler handler = new Handler();

    public JavaScriptLuxclub(FragmentActivity context, WebView webView) {
        this.activity = context;
        this.webView = webView;
    }

    /**
     * 错误页面拨打客服电话
     *
     * @param phone 手机号
     */
    @JavascriptInterface
    public void callAndroid(String phone) {
        AndroidUtil.callPhone(activity, phone);
    }

    /**
     * 错误页面刷新当前页
     *
     * @param url 网页url
     */
    @JavascriptInterface
    public void refreshAndroid(final String url) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(url);
            }
        });
    }

    /**
     * （微信端-->App）用户是否已登录
     */
    @JavascriptInterface
    public String hasLogin() {
        if (ProjectUtil.isLogin()) {
            return SharedPreferencesUtil.getInstance().getToken();
        } else {
            return "";
        }
    }

}