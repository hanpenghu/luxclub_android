package com.hsbank.luxclub.model;

/**
 * 上传图片的返回
 * Created by Administrator on 2017/2/21.
 */

public class UploadImageBean {

    private String imageName;

    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
