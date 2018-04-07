package com.hsbank.util.android.util.http.okhttp.callback;

import com.hsbank.util.android.util.http.okhttp.callback.util.OkHttpCallback;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by zhy on 15/12/14.
 */
public abstract class StringCallback extends OkHttpCallback<String> {
    @Override
    public String parseNetworkResponse(Response response) throws IOException {
        return response == null ? "" : response.body().string();
    }

}
