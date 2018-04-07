package com.hsbank.luxclub.model;

/**
 * Author:      chenliuchun
 * Date:        17/2/20
 * Description: 经理端——订单列表条目
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public class MOrderItemBean {

    /**
     * orderId : 4363
     * orderCode : 2900201702151057
     * orderState : 2
     * siteId : 47
     * siteName : 12
     * siteAddr : 12
     * createDate : 2017-02-18 10:36:00
     * reserveNumberType : 0
     * siteImageUrl : http://120.76.241.15:7007/userfiles/1/images/luxclub/siteInfo/2016/11/QQ%E6%88%AA%E5%9B%BE20160606111537.png
     * orderStateName : 已确认
     * reserveNumber : 1
     */

    private int orderId;
    private String orderCode;
    private String orderState;
    private int siteId;
    private String siteName;
    private String siteAddr;
    private String createDate;
    private int reserveNumberType;
    private String siteImageUrl;
    private String orderStateName;
    private String cardno;
    private String contactMobile;
    private String memberName;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public int getOrderState() {
        return Integer.parseInt(orderState);
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
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

    public String getSiteAddr() {
        return siteAddr;
    }

    public void setSiteAddr(String siteAddr) {
        this.siteAddr = siteAddr;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getReserveNumberType() {
        return reserveNumberType;
    }

    public void setReserveNumberType(int reserveNumberType) {
        this.reserveNumberType = reserveNumberType;
    }

    public String getSiteImageUrl() {
        return siteImageUrl;
    }

    public void setSiteImageUrl(String siteImageUrl) {
        this.siteImageUrl = siteImageUrl;
    }

    public String getOrderStateName() {
        return orderStateName;
    }

    public void setOrderStateName(String orderStateName) {
        this.orderStateName = orderStateName;
    }

    public String getCardno() {
        return cardno;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public String getMemberName() {
        return memberName;
    }
}
