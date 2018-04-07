package com.hsbank.util.android.util.http.volley.util;

/**
 * 请求回调接口
 * 2015-12-17
 */
public interface HttpCallback {
	public void onStart();
	public void onFinish();
	public void onResult(String string);
	public void onError(Exception e); 
	public void onCancelled();
}
