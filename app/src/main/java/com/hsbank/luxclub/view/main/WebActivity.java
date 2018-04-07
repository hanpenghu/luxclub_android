package com.hsbank.luxclub.view.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.hsbank.luxclub.BuildConfig;
import com.hsbank.luxclub.R;
import com.hsbank.luxclub.util.ProjectUtil;
import com.hsbank.luxclub.util.constants.Constants;
import com.hsbank.luxclub.util.tool.JavaScriptLuxclub;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.util.android.util.SharedPreferencesUtil;
import com.hsbank.util.java.constant.CharSet;
import com.hsbank.util.java.tool.FileUtil;
import com.hsbank.util.java.type.NumberUtil;
import com.hsbank.util.project.util.BaseProjectCache;

import java.io.File;

/**
 * Author:      chenliuchun
 * Date:        17/2/23
 * Description: 通用web界面
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public class WebActivity extends ProjectBaseActivity {

    private static final String VAR_TYPE = "type";
    private static final String VAR_URL = "url";
    private static final String VAR_POST_PARAMS = "postParams";
    private static final String VAR_HTML_CODE = "htmlCode";
    private static final String VAR_TITLE = "title";
    private static final String VAR_MENU_TYPE = "menu_type";
    // 页面类型常量
    public static final int MENU_NULL = 0; // 无toolbar菜单
    public static final int MENU_INVITATION = 0x101; // toolbar菜单，我的邀请
    public static final int MENU_SHARE = 0x102; // toolbar菜单，分享
    private int mMenuType;

    private WebView mWebView;
    private ProgressBar progressBar;

    private String mTitle;
//    private ShareEntity mShareEntity;
//    private MenuItem itemShare; // menu 分享菜单

    // 页面用户变化情况, 两个数字代表依次刷新的情况，1：刷，0：不刷
    private int mTokenInfo = 0b00;

//    @Override
//    public void onEventMainThread(BaseProjectEvent event) {
//        super.onEventMainThread(event);
//        if (event.getCommand().equals(WebEvent.COMMAND_FINISH)) {
//            goBack();
//
//            //点击页面内的分享
//        } else if (event.getCommand().equals(WebEvent.COMMAND_SHARE)) {
//            Object message = event.getMessage();
//            if (message == null) return;
//            String json = (String) message;
//            String shareStr = MapUtil.getString(JsonUtil.toMap(json), "share");
//            if (TextUtils.isEmpty(shareStr)) return;
//            ShareEntity shareEntity = JsonUtil.toClass(shareStr, ShareEntity.class);
//            share(shareEntity);
//
//            //toolbar上的分享按钮
//        } else if (event.getCommand().equals(WebEvent.COMMAND_JS_SET_APP_PARAMS)) {
//            String message = (String) event.getMessage();
//            Map<String, Object> shareMap = JsonUtil.toMap(message);
//            String shareStr = MapUtil.getString(shareMap, "share");
//            if (TextUtils.isEmpty(shareStr)) return;
//            mShareEntity = JsonUtil.toClass(shareStr, ShareEntity.class);
//            itemShare.setVisible(true); // 显示分享按钮
//
//        } else if (event.getCommand().equals(WebEvent.COMMAND_SHARE_FAIL)) {
//            AlertUtil.getInstance().alertMessage(this, getString(R.string.txt_share_fail_wechat));
//
//        } else if (event.getCommand().equals(WebEvent.COMMAND_TITLE)) {
//            mTitle = (String) event.getMessage();
//            if (!TextUtils.isEmpty(mTitle)) {
//                if (!getSupportActionBar().isShowing()){
//                    getSupportActionBar().show();
//                }
//                setToolbarTitle(mTitle);
//            }
//        }
//    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        mMenuType = getIntent().getIntExtra(VAR_MENU_TYPE, MENU_NULL);
        mTitle = getIntent().getStringExtra(VAR_TITLE);
        processTitle(mTitle);
        FrameLayout fly_web_container = mViewHelper.getView(R.id.fly_web_container);
        mWebView = new WebView(getApplicationContext());
        mWebView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        fly_web_container.addView(mWebView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setProgress(0);
        progressBar.setVisibility(View.INVISIBLE);
        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.setWebViewClient(new MyWebViewClient());
        webviewSetting();
        mWebView.addJavascriptInterface(new JavaScriptLuxclub(this, mWebView),
                JavaScriptLuxclub.NAME_MAIN);
        Constants.WebType mType = (Constants.WebType) getIntent().getSerializableExtra(VAR_TYPE);
        if (mType == null) mType = Constants.WebType.WEB_TYPE_URL_NORMAL;

        String mUrl = getIntent().getStringExtra(VAR_URL);
        if (mUrl == null || mUrl.equals("")) {
//            mUrl = Constants.FILE_PATH_ASSET_WEB_ERROR;
        }
        String postParams = getIntent().getStringExtra(VAR_POST_PARAMS);
        String mHtmlCode = getIntent().getStringExtra(VAR_HTML_CODE);
        switch (mType) {
            case WEB_TYPE_URL_NORMAL:
                mWebView.loadUrl(mUrl);
                break;

            case WEB_TYPE_URL_POST:
                mWebView.postUrl(mUrl, postParams.getBytes());
                break;

            case WEB_TYPE_HTML_CODE:
                // 写入内存卡，添加返回的历史记录
                String name = "web_activity_" + NumberUtil.getRandomInt(100, 999) + ".html";
                File html = FileUtil.createFile(BaseProjectCache.FILE_PATH_DOWNLOAD + name);
                FileUtil.writeFile(html.getAbsolutePath(), mHtmlCode);
                String url = "file://" + html.getAbsolutePath();
                mWebView.loadDataWithBaseURL(url, mHtmlCode, "text/html", CharSet.UTF_8.value(), url);
                break;

            default:
                mWebView.loadUrl(mUrl);
                break;
        }
        // 微信获取登录信息
        String token = SharedPreferencesUtil.getInstance().getToken();
        mWebView.loadUrl("javascript:window.wechat.setLogin('" + token + "')");

        // 记录登录信息
        if (ProjectUtil.isLogin()){
            mTokenInfo |= 0b01;
        }
    }

    /**
     * 设置webview选项
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void webviewSetting() {
        WebSettings webSettings = mWebView.getSettings();
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

        // 支持缩放，默认为true。是下面那个的前提
        webSettings.setSupportZoom(true);
        // 显示或不显示缩放按钮（wap网页不支持）
        webSettings.setBuiltInZoomControls(true);
        // 缩放至屏幕的大小
        webSettings.setLoadWithOverviewMode(true);
        // 设置WebView是否可以由JavaScript自动打开窗口，默认为false，通常与JavaScript的window.open()配合使用
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 指定WebView的页面布局显示形式，调用该方法会引起页面重绘。默认值为LayoutAlgorithm.NARROW_COLUMNS
        webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        // 将图片调整到适合webview的大小
//        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 网页调试使用
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        switch (mMenuType) {
//            case MENU_INVITATION:
//                inflater.inflate(R.menu.menu_invitation, menu);
//                break;
//            case MENU_SHARE:
//                inflater.inflate(R.menu.menu_share, menu);
//                itemShare = menu.findItem(R.id.action_share);
//                itemShare.setVisible(false);
//                break;
//        }
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_invitation:
//                if (ProjectUtil.isLogin()) {
//                    InvitationActivity.show(this);
//                } else {
//                    LoginActivity.show(this);
//                }
//                return true;
//            case R.id.action_share:
//                if (!ProjectUtil.isLogin()) {
//                    LoginActivity.show(this);
//                }
//                share(mShareEntity);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // 微信获取登录信息
        String token = SharedPreferencesUtil.getInstance().getToken();
        mWebView.loadUrl("javascript:window.wechat.setLogin('" + token + "')");
        if (ProjectUtil.isLogin()){
            mTokenInfo |= 0b10;
        }
        // 如果第一次进入没有登录，第二次登录状态，刷新页面
        if (mTokenInfo == 0b10 ) {
            mWebView.reload();
            mTokenInfo |= 0b01;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearWebViewResource();
    }

    /**
     * 根据标题判断打开的页面类型, 默认无标题栏,title 为空 or null时，为默认状态
     * @param title 标题
     */
    private void processTitle(String title) {
        if (title == null) {
            getSupportActionBar().hide();
        } else {
            setToolbarTitle(title);
        }
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

    /**
     * 打开分享面板
     * @param shareEntity 被分享的信息model
     */
//    private void share(ShareEntity shareEntity) {
//        MyReturn accountMyData = GlobalObjectData.getInstance().getMyData();
//        if (accountMyData == null || accountMyData.getMobile().equals("")) {
//            LoginActivity.show(this);
//            return;
//        }
//        String mobile = HexConvert.str2HexStr(accountMyData.getMobile());
//        String url = UrlUtil.addParameter(shareEntity.getLink(), "ad=" + WeChatUrl.SHARE_APP_AD + "&hm=" + mobile);
//        // 根据页面判断是否调出带二维码分享弹窗
//        String pageUrl = getIntent().getStringExtra(VAR_URL);
//        if (pageUrl.contains("friend")) {
//            View parent = LayoutInflater.from(this).inflate(R.layout.activity_web, null);
//            // 计算弹窗高度：屏幕高度—toolbar高度
//            int screenH = UIUtil.getScreenH(this);
//            int statusBarHeight = UIUtil.getStatusBarHeight(this);
//            int toolbarHeight = mViewHelper.getView(R.id.toolbar).getMeasuredHeight();
//            int popHeight = screenH - statusBarHeight - toolbarHeight;
//
//            SocialUtil.INSTANCE.myShare(this, parent, popHeight, shareEntity.getTitle(), shareEntity.getDesc(), url,
//                    shareEntity.getImgUrl(), shareEntity.getPlatformType(), shareListener);
//        } else {
//            SocialUtil.INSTANCE.share(this, shareEntity.getTitle(), shareEntity.getDesc(), url,
//                    shareEntity.getImgUrl(), shareEntity.getPlatformType(), shareListener);
//        }
//    }

    /**
     * 获取当前页面标题
     * @return
     */
    public String getmTitle() {
        return mTitle;
    }

    private class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            progressBar.setProgress(newProgress);
            progressBar.setVisibility(View.VISIBLE);
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String pageTitle) {
            super.onReceivedTitle(view, pageTitle);
        }

        // 页面内弹窗
//        @Override
//        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//            builder.setTitle(R.string.str_tip_worm)
//                    .setMessage(message)
//                    .setPositiveButton(R.string.str_confirm, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            result.confirm();
//                        }
//                    })
//                    .show();
//            return true;
//        }
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            view.stopLoading();
//            view.loadUrl(Constants.FILE_PATH_ASSET_WEB_ERROR);
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            if (!url.equals(mUrl)){
//                mWebView.getSettings().setUseWideViewPort(true); // 页面尺寸比例不一致的临时解决办法
//            }
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onScaleChanged(WebView view, float oldScale, float newScale) {
            super.onScaleChanged(view, oldScale, newScale);
        }

        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            return super.shouldOverrideKeyEvent(view, event);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 因为网页加载在堆栈中，所以能一级一级的返回，通过对返回键的监听来返回上一堆栈
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 判断是否可以返回
            if (mWebView.canGoBack()) {
                mWebView.goBack(); // 可以返回时，就返回
                return true;
            } else { // 当不能返回时，关闭程序。
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 普通web页面
     *
     * @param context    上下文对象
     * @param type       启动类型:0普通页面
     * @param url        url地址
     * @param postParams 请求参数
     * @param htmlCode   html代码
     * @param title      标题，当标题为null或者空时，显示web页面的标题
     */
    public static void show(Context context, Constants.WebType type, String url, String postParams, String htmlCode, String title) {
        Intent intent = new Intent();
        intent.putExtra(VAR_TYPE, type);
        intent.putExtra(VAR_URL, url);
        intent.putExtra(VAR_POST_PARAMS, postParams);
        intent.putExtra(VAR_HTML_CODE, htmlCode);
        intent.putExtra(VAR_TITLE, title);
        intent.setClass(context, WebActivity.class);
        context.startActivity(intent);
    }

    /**
     * 普通页面打开, 带标题
     *
     * @param context
     * @param url
     * @param title
     */
    public static void show(Context context, String url, String title) {
        Intent intent = new Intent();
        intent.putExtra(VAR_URL, url);
        intent.putExtra(VAR_TITLE, title);
        intent.setClass(context, WebActivity.class);
        context.startActivity(intent);
    }

    /**
     * 普通页面打开, 带标题，带toolbar菜单
     *
     * @param context
     * @param url
     * @param title
     * @param menuType @see MENU_INVITATION MENU_SHARE MENU_NULL
     */
    public static void show(Context context, String url, String title, int menuType) {
        Intent intent = new Intent();
        intent.putExtra(VAR_URL, url);
        intent.putExtra(VAR_TITLE, title);
        intent.putExtra(VAR_MENU_TYPE, menuType);
        intent.setClass(context, WebActivity.class);
        context.startActivity(intent);
    }

    /**
     * 普通页面打开, 无标题标题
     *
     * @param context
     * @param url
     */
    public static void show(Context context, String url) {
        Intent intent = new Intent();
        intent.putExtra(VAR_URL, url);
        intent.setClass(context, WebActivity.class);
        context.startActivity(intent);
    }
}