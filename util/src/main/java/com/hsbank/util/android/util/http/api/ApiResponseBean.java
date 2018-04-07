package com.hsbank.util.android.util.http.api;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Api响应对象：遵循同样的结构，包含（code、text、data)三个部分
 * Created by Administrator on 2016/1/10.
 */
public class ApiResponseBean {
    /**未处理过的响应字符串*/
    String response = null;
    /**状态代码*/
    int code = -1;
    /**状态描述*/
    String text = null;
    /**响应数据*/
    String data = null;

    public String getResponse() {
        if (response == null) {
            response = "";
        }
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        if (text == null) {
            text = "";
        }
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getData() {
        if (data == null) {
            data = "";
        }
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String toString(){
        return ReflectionToStringBuilder.toString(this);
    }
}
