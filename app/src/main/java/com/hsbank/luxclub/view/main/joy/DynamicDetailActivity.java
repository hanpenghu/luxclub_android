package com.hsbank.luxclub.view.main.joy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.hsbank.luxclub.BuildConfig;
import com.hsbank.luxclub.R;
import com.hsbank.luxclub.model.DynamicDetailBean;
import com.hsbank.luxclub.provider.apis.DynamicApis;
import com.hsbank.luxclub.provider.http.APISubscriber;
import com.hsbank.luxclub.provider.http.RetrofitHelper;
import com.hsbank.luxclub.provider.http.RxUtil;
import com.hsbank.luxclub.util.constants.Constants;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Author:      chenliuchun
 * Date:        17/5/17
 * Description: 动态详情页面
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */
public class DynamicDetailActivity extends ProjectBaseActivity {

    public static final String KEY_DYNAMIC_ID = "key_dynamic_id";
    private WebView mWebView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dynamic_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout fly_web_container = mViewHelper.getView(R.id.fly_web_container);
        mWebView = new WebView(getApplicationContext());
        mWebView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setBackgroundColor(Color.TRANSPARENT);
        // 设置不缩放，防止图片过大。默认是按照手机屏幕密度比缩放的
//        mWebView.setInitialScale(100);
//        webviewSetting(mWebView);
        fly_web_container.addView(mWebView);

        int dynamicId = getIntent().getIntExtra(KEY_DYNAMIC_ID, -1);
        RetrofitHelper.getInstance()
                .create(DynamicApis.class, true)
                .details(dynamicId)
                .compose(RxUtil.<DynamicDetailBean>compose(this))
                .subscribe(new APISubscriber<DynamicDetailBean>() {
                    @Override
                    public void onNext(DynamicDetailBean dynamicDetailBean) {
                        updateUI(dynamicDetailBean);
                    }
                });
    }

    private void updateUI(DynamicDetailBean detail) {
        setToolbarTitle(detail.getTitle());

        mViewHelper.setText(R.id.txt_title, detail.getTitle());
        mViewHelper.setText(R.id.txt_label, detail.getLabel());
        mViewHelper.setText(R.id.txt_create_date, detail.getCreateDate());
        ImageView img_dynamic = mViewHelper.getView(R.id.img_dynamic);
        ImageView img_icon = mViewHelper.getView(R.id.img_icon);
        ImageLoader.getInstance().displayImage(detail.getImageUlr(), img_dynamic, Constants.networkOptions);
        ImageLoader.getInstance().displayImage(detail.getIconUrl(), img_icon, Constants.networkOptions);

        String content = detail.getContent();
        if (!TextUtils.isEmpty(content)) {
            mWebView.loadData(wrapContent(content), "text/html; charset=UTF-8", null); // 这么怪异的用法是解决乱码，loadDataWithBaseURL()无此问题
        }
    }

    private String wrapContent(String description) {
        String head = getString(R.string.txt_theme_head);
        String foot = getString(R.string.txt_theme_foot);
        StringBuilder builder = new StringBuilder();
        StringBuilder append = builder.append(head).append(description).append(foot);
        return append.toString();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void webviewSetting(WebView webView) {
        WebSettings webSettings = webView.getSettings();
        // 设置WebView是否可以运行JavaScript
        webSettings.setJavaScriptEnabled(true);

        // 设置缓存模式, 默认的缓存使用模式
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        // 有时候网页需要自己保存一些关键数据,Android WebView 需要自己设置
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        webSettings.setAppCachePath(appCachePath);

        // 设置文字正常缩放
        webSettings.setTextZoom((int) (Resources.getSystem().getDisplayMetrics().density * 100));
        // 支持缩放，默认为true。是下面那个的前提
//        webSettings.setSupportZoom(true);
        // 显示或不显示缩放按钮（wap网页不支持）
//        webSettings.setBuiltInZoomControls(true);
        // 设置WebView是否可以由JavaScript自动打开窗口，默认为false，通常与JavaScript的window.open()配合使用
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 指定WebView的页面布局显示形式，调用该方法会引起页面重绘。默认值为LayoutAlgorithm.NARROW_COLUMNS 废弃了
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // 缩放至屏幕的大小
//        webSettings.setLoadWithOverviewMode(true);
        // 将图片调整到适合webview的大小
//         webSettings.setUseWideViewPort(true);
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 网页调试使用
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearWebViewResource();
    }

    /**
     * release the memory of web view, otherwise it's resource will not be recycle.
     */
    public void clearWebViewResource() {
        if (mWebView != null) {
            mWebView.removeAllViews();
            // in android 5.1(sdk:21) we should invoke this to avoid memory leak
            // see (https://coolpers.github.io/webview/memory/leak/2015/07/16/
            // android-5.1-webview-memory-leak.html)
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.setTag(null);
            mWebView.clearHistory();
            mWebView.destroy();
            mWebView = null;
        }
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    public static void show(Context context, int dynamicId) {
        Intent intent = new Intent();
        intent.putExtra(KEY_DYNAMIC_ID, dynamicId);
        intent.setClass(context, DynamicDetailActivity.class);
        context.startActivity(intent);
    }
}
