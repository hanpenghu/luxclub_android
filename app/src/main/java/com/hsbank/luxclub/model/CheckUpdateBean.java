package com.hsbank.luxclub.model;

/**
 * Created by chenliuchun on 17/2/23.
 */

public class CheckUpdateBean {

    /**
     * adCode : 1002
     * imageUrl : http://120.76.241.15:7007/userfiles/19/images/luxclub/advertising/2017/02/557087170346075473.jpg?version=1
     * isClick : 1
     * target : http://ylzun.com/static/download.html
     * description :
     */

    private String adCode;
    private String imageUrl;
    private int isClick;
    private String target;
    private String description;

    public String getAdCode() {
        return adCode;
    }

    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getIsClick() {
        return isClick;
    }

    public void setIsClick(int isClick) {
        this.isClick = isClick;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
