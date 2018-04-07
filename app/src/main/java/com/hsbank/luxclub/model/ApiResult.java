package com.hsbank.luxclub.model;

/**
 * Created by chen_liuchun on 2016/10/20.
 */

public class ApiResult<T> {

    /**
     * 状态代码
     */
    private int code = -1;
    /**
     * 状态描述
     */
    private String text;
    /**
     * 响应数据
     */
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
