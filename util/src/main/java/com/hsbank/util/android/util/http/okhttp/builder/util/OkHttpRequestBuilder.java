package com.hsbank.util.android.util.http.okhttp.builder.util;

import com.hsbank.util.android.util.http.okhttp.request.util.RequestCall;

import java.util.Map;

/**
 * Created by zhy on 15/12/14.
 */
public abstract class OkHttpRequestBuilder {
    /**请求链接*/
    protected String url;
    /***/
    protected Object tag;
    /**请求头*/
    protected Map<String, String> headers;
    /**请求参数*/
    protected Map<String, String> params;

    public abstract OkHttpRequestBuilder url(String url);

    public abstract OkHttpRequestBuilder tag(Object tag);

    public abstract OkHttpRequestBuilder params(Map<String, String> params);

    public abstract OkHttpRequestBuilder addParams(String key, String val);

    public abstract OkHttpRequestBuilder headers(Map<String, String> headers);

    public abstract OkHttpRequestBuilder addHeader(String key, String val);

    public abstract RequestCall build(boolean clientFlag);


}
