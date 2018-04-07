package com.hsbank.util.android.util.http.okhttp.callback;

import android.util.Log;

import com.hsbank.util.android.util.JsonUtil;
import com.hsbank.util.android.util.http.api.ApiParameterConstant;
import com.hsbank.util.android.util.http.api.ApiResponseBean;
import com.hsbank.util.android.util.http.okhttp.callback.util.OkHttpCallback;
import com.hsbank.util.java.collection.MapUtil;
import com.hsbank.util.java.type.StringUtil;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Map;

/**
 * Api回调对象
 * Created by Arthur.Xie on 2016-01-10
 */
public abstract class ApiCallback extends OkHttpCallback<ApiResponseBean> {
    @Override
    public void onError(Request request, Exception e) {
        e.printStackTrace();
    }

    @Override
    public void onResponse(ApiResponseBean response) {
        Log.d(this.getClass().getName(), response == null ? "" : response.toString());
    }

    @Override
    public ApiResponseBean parseNetworkResponse(Response response) throws IOException {
        ApiResponseBean resultValue = new ApiResponseBean();
        String s = response == null ? "" : response.body().string();
        s = StringUtil.dealString(s);
        Log.d(this.getClass().getName(), s);
        Map<String, Object> map = JsonUtil.toMap(s);
        resultValue.setResponse(s);
        resultValue.setCode(MapUtil.getInt(map, ApiParameterConstant.CODE, -1));
        resultValue.setText(MapUtil.getString(map, ApiParameterConstant.TEXT));
        resultValue.setData(MapUtil.getString(map, ApiParameterConstant.DATA));
        return resultValue;
    }

}
