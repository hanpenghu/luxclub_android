package com.hsbank.util.android.util.http.volley;

import java.util.Map;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hsbank.util.android.util.http.volley.util.HttpCallback;
import com.hsbank.util.android.util.http.volley.util.RequestInfo;

/**
 * Volley封装工具类
 * <p/>
 * Volley是Google在2013年Google I/O大会上推出的一个新的网络请求框架，它将各种网络请求都简单化，
 * 并且把AsyncHttpClient和Universal-Image-Loader两大框架的优点集一身，
 * Volley在数据量不大的网络请求操作时它的性能表现的非常出色，
 * 但是在进行数据量大的网络操作时（如下载文件等），表现得比较糟糕。
 * <p/>
 * Volley有这么几大功能：
 * 1、普通数据、JSON、图片的异步加载
 * 2、网络请求优先级处理
 * 3、自带硬盘缓存（普通数据、图片、JSON），另外我们在加载图片时候通过ImageLoader还可加入LruCache
 * 4、取消请求
 * 5、与Activity生命周期联动（Activity退出时同时取消所有的请求）
 * <p/>
 * Volley框架的原理：
 * 它在内部通过一个请求队列（RequestQueue）来维护所有请求，我们新创建一个请求（request）后通过
 * RequestQueue.add()方法将请求添加到请求队列中，然后调用RequestQueue.start()方法执行请求。
 * <p/>
 * Volley中包含这么几种类型的请求：
 * 1、StringRequest:       返回字符串数据
 * 2、JsonObjectRequest:   返回JSONArray数据
 * 3、JsonArrayRequest:    返回JSONObject数据
 * 4、ImageRequest:        返回Bitmap类型数据
 * <p/>
 * Goole官方库：git clone https://android.googlesource.com/platform/frameworks/volley
 * Githup的库： https://github.com/mcxiaoke/android-volley
 * <p/>
 * 2015-12-17
 */
public class VolleyUtil {
    private Context mContext;
    private static RequestQueue mRequestQueue;

    /**
     * 构造函数
     * @param context
     */
    public VolleyUtil(Context context) {
        mContext = context.getApplicationContext();
    }

    /**
     * 初始化
     * <br/>
     * 不必为每一次HTTP请求都创建一个RequestQueue对象，推荐在application中初始化
     * <br/>
     * @param context
     */
    public static void init(Context context) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
    }
    
    public static void stop() {
        if (mRequestQueue != null) {
            mRequestQueue.stop();
            mRequestQueue = null;
        }
    }
    
    public static RequestQueue getHttpRequestQueue() {
        return mRequestQueue;
    }
    
    /**
     * get 请求
     * @param url               请求的url
     * @param paramsMap         请求的参数
     * @param httpCallback        请求的结果
     */
    public void get(String url, Map<String, String> paramsMap, final HttpCallback httpCallback) {
        get(new RequestInfo(url, paramsMap), httpCallback);
    }
    
    /**
     * get 请求
     * @param requestInfo
     * @param httpCallback
     */
    public void get(RequestInfo requestInfo, final HttpCallback httpCallback) {
        sendRequest(Request.Method.GET, requestInfo, httpCallback);
    }

    /**
     * post 请求
     * @param url
     * @param paramsMap
     * @param httpCallback
     */
    public void post(final String url, final Map<String, String> paramsMap, final HttpCallback httpCallback) {
        post(new RequestInfo(url, paramsMap), httpCallback);
    }
    
    /**
     * post请求
     * @param requestInfo
     * @param httpCallback
     */
    public void post(RequestInfo requestInfo, final HttpCallback httpCallback) {
        sendRequest(Request.Method.POST, requestInfo, httpCallback);
    }
    
    /**
     * delete 请求
     * @param requestInfo
     * @param httpCallback
     */
    public void delete(RequestInfo requestInfo, final HttpCallback httpCallback) {
        sendRequest(Request.Method.DELETE, requestInfo, httpCallback);
    }
    
    /**
     * put 请求
     * @param requestInfo
     * @param httpCallback
     */
    public void put(RequestInfo requestInfo, final HttpCallback httpCallback) {
        sendRequest(Request.Method.PUT, requestInfo, httpCallback);
    }

    /**
     * 发送http请求
     * @param request
     */
    public <T> void sendRequest(Request<T> request) {
    	if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext);
        }
    	request.setTag(this);
        mRequestQueue.add(request);
    }

    /**
     * 发送http请求
     * @param method
     * @param requestInfo
     * @param httpCallback
     */
    private void sendRequest(final int method, final RequestInfo requestInfo, final HttpCallback httpCallback) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext);
        }
        if (httpCallback != null) {
            httpCallback.onStart();
        }
        if (requestInfo == null || TextUtils.isEmpty(requestInfo.url)) {
            if (httpCallback != null) {
                httpCallback.onError(new Exception("url can not be empty!"));
                httpCallback.onFinish();
            }
            return;
        }
        switch (method) {
        case Request.Method.GET:
            requestInfo.url = requestInfo.getFullUrl();
            VolleyLog.d("get->%s", requestInfo.getUrl());
            break;
        case Request.Method.DELETE:
            requestInfo.url = requestInfo.getFullUrl();
            VolleyLog.d("delete->%s", requestInfo.getUrl());
            break;
        default:
            break;
        }
        final StringRequest request = new StringRequest(method, requestInfo.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (httpCallback != null) {
                    httpCallback.onResult(response);
                    httpCallback.onFinish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (httpCallback != null) {
                    httpCallback.onError(error);
                    httpCallback.onFinish();
                }
            }
        }){
            @Override
            public void cancel() {
                super.cancel();
                if (httpCallback != null) {
                    httpCallback.onCancelled();
                }
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (method == Method.POST || method == Method.PUT) {
                    VolleyLog.d((method == Method.POST ? "post->%s" : "put->%s"), requestInfo.getUrl() + ",params->" + requestInfo.getParams().toString());
                    return requestInfo.getParams();
                } 
                return super.getParams();
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return requestInfo.getHeaders();
            }
        };
        request.setTag(this);
        mRequestQueue.add(request);
    }
    
    public void cancelAllRequest() {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(this);
        }
    }
}
