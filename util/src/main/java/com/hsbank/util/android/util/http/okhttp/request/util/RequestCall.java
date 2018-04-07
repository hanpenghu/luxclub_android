package com.hsbank.util.android.util.http.okhttp.request.util;

import android.content.Context;

import com.hsbank.util.android.util.http.okhttp.OkHttpUtil;
import com.hsbank.util.android.util.http.okhttp.callback.util.OkHttpCallback;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhy on 15/12/15.
 */
public class RequestCall {
    private OkHttpRequest okHttpRequest;
    private Request request;
    private Call call;

    private long readTimeOut;
    private long writeTimeOut;
    private long connTimeOut;

    private OkHttpClient clone;

    public RequestCall(OkHttpRequest request) {
        this.okHttpRequest = request;
    }

    public RequestCall readTimeOut(long readTimeOut) {
        this.readTimeOut = readTimeOut;
        return this;
    }

    public RequestCall writeTimeOut(long writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
        return this;
    }

    public RequestCall connTimeOut(long connTimeOut) {
        this.connTimeOut = connTimeOut;
        return this;
    }

    public Call generateCall(OkHttpCallback callback) {
        request = generateRequest(callback);
        readTimeOut = readTimeOut > 0 ? readTimeOut : OkHttpUtil.DEFAULT_MILLISECONDS;
        writeTimeOut = writeTimeOut > 0 ? writeTimeOut : OkHttpUtil.DEFAULT_MILLISECONDS;
        connTimeOut = connTimeOut > 0 ? connTimeOut : OkHttpUtil.DEFAULT_MILLISECONDS;
        if (readTimeOut > 0 || writeTimeOut > 0 || connTimeOut > 0) {
            cloneClient();
            clone.setReadTimeout(readTimeOut, TimeUnit.MILLISECONDS);
            clone.setWriteTimeout(writeTimeOut, TimeUnit.MILLISECONDS);
            clone.setConnectTimeout(connTimeOut, TimeUnit.MILLISECONDS);
            call = clone.newCall(request);
        } else {
            call = OkHttpUtil.getInstance().getOkHttpClient().newCall(request);
        }
        return call;
    }

    private Request generateRequest(OkHttpCallback callback) {
        return okHttpRequest.generateRequest(callback);
    }

    public void execute(final Context context, OkHttpCallback callback) {
        generateCall(callback);
        if (callback != null) {
            callback.onBefore(request);
        }
        OkHttpUtil.getInstance().execute(context, this, callback);
    }

    public Call getCall() {
        return call;
    }

    public Request getRequest() {
        return request;
    }

    public Response execute() throws IOException {
        generateCall(null);
        return call.execute();
    }

    private void cloneClient() {
        clone = OkHttpUtil.getInstance().getOkHttpClient().clone();
    }

    public void cancel() {
        if (call != null) {
            call.cancel();
        }
    }


}
