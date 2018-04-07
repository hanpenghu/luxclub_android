package com.hsbank.util.android.util.http.okhttp.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.hsbank.util.android.util.http.okhttp.callback.util.OkHttpCallback;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by zhy on 15/12/14.
 */
public abstract class BitmapCallback extends OkHttpCallback<Bitmap> {
    @Override
    public Bitmap parseNetworkResponse(Response response) throws IOException {
        return BitmapFactory.decodeStream(response.body().byteStream());
    }

}
