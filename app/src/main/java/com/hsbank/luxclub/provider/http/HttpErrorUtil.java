package com.hsbank.luxclub.provider.http;

import android.net.ParseException;

import com.google.gson.JsonParseException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;

/**
 * Author:      chenliuchun
 * Date:        16/11/14
 * Description: 异常处理工具类
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */
public class HttpErrorUtil {

    /**
     * 统一处理异常方法
     *
     * @param e 收到的异常
     * @return 错误类
     */
    public static ErrorTipBean handleException(Throwable e) {
        ErrorTipBean errorTip = new ErrorTipBean();
        // 网络连接错误
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            switch (httpException.code()) {
                case HttpError.UNAUTHORIZED:
                case HttpError.FORBIDDEN:
                case HttpError.NOT_FOUND:
                case HttpError.REQUEST_TIMEOUT:
                case HttpError.GATEWAY_TIMEOUT:
                case HttpError.INTERNAL_SERVER_ERROR:
                case HttpError.BAD_GATEWAY:
                case HttpError.SERVICE_UNAVAILABLE:
                default:
                    errorTip.setMessage("网络连接错误");
                    errorTip.setTip(true);
                    break;
            }

            // 项目API约定异常
        } else if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            switch (apiException.getCode()) {
                case ApiError.UNKNOWN:
                    errorTip.setMessage(apiException.getMessage());
                    errorTip.setTip(false);
                    break;
                case ApiError.TOKEN_INVALID:
                    errorTip.setMessage("Token失效，请重新登陆");
                    errorTip.setTip(false);
                    break;
                default:
                    errorTip.setMessage(apiException.getMessage());
                    errorTip.setTip(true);
                    errorTip.setCustomError(true);
                    break;
            }

            // json 数据解析异常
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            errorTip.setMessage("数据解析错误");
            errorTip.setTip(false);

            // 网络连接异常
        } else if (e instanceof ConnectException) {
            errorTip.setMessage("网络连接失败");
            errorTip.setTip(true);

            // SSL 错误
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            errorTip.setMessage("证书验证失败");
            errorTip.setTip(false);

            // 连接超时错误
        } else if (e instanceof ConnectTimeoutException || e instanceof SocketTimeoutException) {
            errorTip.setMessage("网络连接超时");
            errorTip.setTip(true);

            // 未知错误
        } else {
            e.printStackTrace();
            errorTip.setMessage("错误: " + e.toString());
            errorTip.setTip(false);
        }
        return errorTip;
    }


    /**
     * 网络错误异常
     */
    static class HttpError {
        static final int UNAUTHORIZED = 401;
        static final int FORBIDDEN = 403;
        static final int NOT_FOUND = 404;
        static final int REQUEST_TIMEOUT = 408;
        static final int INTERNAL_SERVER_ERROR = 500;
        static final int BAD_GATEWAY = 502;
        static final int SERVICE_UNAVAILABLE = 503;
        static final int GATEWAY_TIMEOUT = 504;
    }

    /**
     * 本项目约定异常
     */
    static class ApiError {
        /**
         * 未知错误
         */
        static final int UNKNOWN = -1;
        /**
         * token失效
         */
        static final int TOKEN_INVALID = 1;
    }

    public static class ResponeThrowable extends Exception {
        public int code;
        public String message;

        public ResponeThrowable(Throwable throwable, int code) {
            super(throwable);
            this.code = code;

        }
    }

    public class ServerException extends RuntimeException {
        public int code;
        public String message;
    }
}