package com.hsbank.luxclub.provider.http;

/**
 * Author:      chenliuchun
 * Date:        16/11/14
 * Description: 项目API异常错误
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */public class ApiException extends Exception {

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int code;

    public ApiException(String detailMessage, int code) {
        super(detailMessage);
        this.code = code;
    }

    public ApiException(String detailMessage, Throwable throwable, int code) {
        super(detailMessage, throwable);
        this.code = code;
    }

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }
}
