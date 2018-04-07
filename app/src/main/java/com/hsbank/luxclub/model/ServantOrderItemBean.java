package com.hsbank.luxclub.model;

/**
 * Author:      chenliuchun
 * Date:        2018-01-11
 * Description: 移动管家 订单列表条目
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public class ServantOrderItemBean {

    private String orderId;
    private String stewardOrderId;
    private int state;
    private String stateName;
    private int siteId;
    private String siteName;
    private String siteImageUrl;
    private String reserveDate;
    private String memberName;
    private String sex;
    private String sexLabel;
    private boolean isFinishSummary;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getState() {
        return state;
    }

    public String getStateName() {
        return stateName;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(String reserveDate) {
        this.reserveDate = reserveDate;
    }

    public String getSiteImageUrl() {
        return siteImageUrl;
    }

    public void setSiteImageUrl(String siteImageUrl) {
        this.siteImageUrl = siteImageUrl;
    }

    public String getSexLabel() {
        return sexLabel;
    }

    public void setSexLabel(String sexLabel) {
        this.sexLabel = sexLabel;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getSex() {
        return sex;
    }

    public boolean isFinishSummary() {
        return isFinishSummary;
    }

    public String getStewardOrderId() {
        return stewardOrderId;
    }

    public void setStewardOrderId(String stewardOrderId) {
        this.stewardOrderId = stewardOrderId;
    }
}
