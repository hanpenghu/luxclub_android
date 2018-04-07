package com.hsbank.util.android.util.http.okhttp.builder;

import com.hsbank.util.android.util.http.okhttp.builder.util.OkHttpRequestBuilder;
import com.hsbank.util.android.util.http.okhttp.request.PostFileRequest;
import com.hsbank.util.android.util.http.okhttp.request.util.RequestCall;
import com.hsbank.util.project.util.BaseProjectApi;
import com.squareup.okhttp.MediaType;

import java.io.File;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Created by zhy on 15/12/14.
 */
public class PostFileBuilder extends OkHttpRequestBuilder {
    private File file;
    private MediaType mediaType;

    public OkHttpRequestBuilder file(File file) {
        this.file = file;
        return this;
    }

    public OkHttpRequestBuilder mediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build(boolean clientFlag) {
        if (clientFlag) {
            //设置公共的client参数
            addParams(BaseProjectApi.CLIENT_KEY, BaseProjectApi.getInstance().getClient());
        }
        return new PostFileRequest(url, tag, params, headers, file, mediaType).build();
    }

    @Override
    public PostFileBuilder url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public PostFileBuilder tag(Object tag) {
        this.tag = tag;
        return this;
    }

    @Override
    public PostFileBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    @Override
    public PostFileBuilder addParams(String key, String val) {
        if (this.params == null) {
            params = new IdentityHashMap<>();
        }
        params.put(key, val);
        return this;
    }

    @Override
    public PostFileBuilder headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    @Override
    public PostFileBuilder addHeader(String key, String val) {
        if (this.headers == null) {
            headers = new IdentityHashMap<>();
        }
        headers.put(key, val);
        return this;
    }
}