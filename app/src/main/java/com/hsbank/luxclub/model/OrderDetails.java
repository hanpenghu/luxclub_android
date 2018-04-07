package com.hsbank.luxclub.model;

/**
 * 经理-订单详情可编辑内容
 * name：zhuzhenghua
 * create time:
 */
public class OrderDetails {
    private String contactMobile;
    private String reserveNumberType;
    private String realDate;
    private String siteAddr;
    private String otherRequire;

    public OrderDetails() {
    }

    public OrderDetails(String contactMobile, String reserveNumberType, String realDate, String siteAddr, String otherRequire) {
        this.contactMobile = contactMobile;
        this.reserveNumberType = reserveNumberType;
        this.realDate = realDate;
        this.siteAddr = siteAddr;
        this.otherRequire = otherRequire;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getReserveNumberType() {
        return reserveNumberType;
    }

    public void setReserveNumberType(String reserveNumberType) {
        this.reserveNumberType = reserveNumberType;
    }

    public String getRealDate() {
        return realDate;
    }

    public void setRealDate(String realDate) {
        this.realDate = realDate;
    }

    public String getSiteAddr() {
        return siteAddr;
    }

    public void setSiteAddr(String siteAddr) {
        this.siteAddr = siteAddr;
    }

    public String getOtherRequire() {
        return otherRequire;
    }

    public void setOtherRequire(String otherRequire) {
        this.otherRequire = otherRequire;
    }
}
