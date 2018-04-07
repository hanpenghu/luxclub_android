package com.hsbank.util.android.util.http.okhttp.callback.util;

import android.util.Log;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public abstract class OkHttpCallback<T> {
    /**
     * UI Thread
     * @param request
     */
    public void onBefore(Request request) {
    }

    /**
     * UI Thread
     * @param
     */
    public void onAfter() {
    }

    /**
     * UI Thread
     * @param progress
     */
    public void inProgress(float progress) {
    }

    /**
     * Thread Pool Thread
     * @param response
     */
    public abstract T parseNetworkResponse(Response response) throws IOException;

    public abstract void onError(Request request, Exception e);

    public abstract void onResponse(T response);

    public static OkHttpCallback CALLBACK_DEFAULT = new OkHttpCallback() {

        @Override
        public Object parseNetworkResponse(Response response) throws IOException {
            return null;
        }

        @Override
        public void onError(Request request, Exception e) {
            Log.e(this.getClass().getName(), e.getMessage());
            e.printStackTrace();
        }

        @Override
        public void onResponse(Object response) {
            Log.d(this.getClass().getName(), response == null ? "" : response.toString());
        }
    };

}