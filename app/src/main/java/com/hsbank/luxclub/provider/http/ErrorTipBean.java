package com.hsbank.luxclub.provider.http;

/**
 * Created by chenliuchun on 16/11/14.
 */

public class ErrorTipBean {
    /**
     * 是否应该弹出错误提示
     */
    private boolean isTip;

    /**
     * 是否是接口的业务错误代码
     */
    private boolean isCustomError;

    /**
     * 错误提示内容
     */
    private String message;

    public boolean isTip() {
        return isTip;
    }

    public void setTip(boolean tip) {
        isTip = tip;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isCustomError() {
        return isCustomError;
    }

    public void setCustomError(boolean customError) {
        isCustomError = customError;
    }
}
