package com.hsbank.util.android.util.http.okhttp.builder;

import com.hsbank.util.android.util.http.okhttp.builder.util.OkHttpRequestBuilder;
import com.hsbank.util.android.util.http.okhttp.request.GetRequest;
import com.hsbank.util.android.util.http.okhttp.request.util.RequestCall;
import com.hsbank.util.project.util.BaseProjectApi;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Created by zhy on 15/12/14.
 */
public class GetBuilder extends OkHttpRequestBuilder {
    @Override
    public RequestCall build(boolean clientFlag) {
        if (clientFlag) {
            //设置公共的client参数
            addParams(BaseProjectApi.CLIENT_KEY, BaseProjectApi.getInstance().getClient());
        }
        url = appendParams(url, params);
        return new GetRequest(url, tag, params, headers).build();
    }

    private String appendParams(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(url + "?");
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                sb.append(key).append("=").append(params.get(key)).append("&");
            }
        }
        sb = sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    @Override
    public GetBuilder url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public GetBuilder tag(Object tag) {
        this.tag = tag;
        return this;
    }

    @Override
    public GetBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    @Override
    public GetBuilder addParams(String key, String val) {
        if (this.params == null) {
            params = new IdentityHashMap<>();
        }
        params.put(key, val);
        return this;
    }

    @Override
    public GetBuilder headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    @Override
    public GetBuilder addHeader(String key, String val) {
        if (this.headers == null) {
            headers = new IdentityHashMap<>();
        }
        headers.put(key, val);
        return this;
    }
}
