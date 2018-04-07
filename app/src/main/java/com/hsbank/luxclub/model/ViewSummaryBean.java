package com.hsbank.luxclub.model;

import java.util.List;

/**
 * Created by chenliuchun on 2018/1/16.
 */

public class ViewSummaryBean {

    private String content;
    private List<String> imagesUrl;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImagesUrl() {
        return imagesUrl;
    }

    public void setImagesUrl(List<String> imagesUrl) {
        this.imagesUrl = imagesUrl;
    }
}
