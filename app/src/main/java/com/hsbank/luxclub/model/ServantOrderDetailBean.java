package com.hsbank.luxclub.model;

/**
 * Author:      chenliuchun
 * Date:        2018/1/11
 * Description:
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public class ServantOrderDetailBean {

    private String orderId;
    private String orderCode;
    private String stewardOrderId;
    private String state;
    private String stateName;
    private int siteId;
    private String siteName;
    private String siteAddr;
    private String createDate;
    private String reserveNumber;
    private String reserveRequire;
    private String contactMobile;
    private String luxclubCode;
    private String consumerMoney;
    private String reserveDate;
    private String cancelReason;
    private String otherRequire;
    private String walletAmount;
    private String walletRemarks;
    private String cardno;
    private String memberName;
    private String mobileStewardId;
    private String mobileStewardName;
    private int sex;
    private String sexLabel;
    private boolean isFinishSummary;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public int getState() {
        return Integer.parseInt(state);
    }

    public void setState(String state) {
        this.state = state;
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

    public String getReserveNumber() {
        return reserveNumber;
    }

    public void setReserveNumber(String reserveNumber) {
        this.reserveNumber = reserveNumber;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getConsumerMoney() {
        return consumerMoney;
    }

    public void setConsumerMoney(String consumerMoney) {
        this.consumerMoney = consumerMoney;
    }

    public String getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(String reserveDate) {
        this.reserveDate = reserveDate;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getOtherRequire() {
        return otherRequire;
    }

    public void setOtherRequire(String otherRequire) {
        this.otherRequire = otherRequire;
    }

    public String getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(String walletAmount) {
        this.walletAmount = walletAmount;
    }

    public String getWalletRemarks() {
        return walletRemarks;
    }

    public void setWalletRemarks(String walletRemarks) {
        this.walletRemarks = walletRemarks;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getLuxclubCode() {
        return luxclubCode;
    }

    public void setLuxclubCode(String luxclubCode) {
        this.luxclubCode = luxclubCode;
    }

    public String getStateName() {
        return stateName;
    }

    public String getReserveRequire() {
        return reserveRequire;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getMobileStewardId() {
        return mobileStewardId;
    }

    public String getMobileStewardName() {
        return mobileStewardName;
    }

    public boolean isFinishSummary() {
        return isFinishSummary;
    }

    public int getSex() {
        return sex;
    }

    public String getSexLabel() {
        return sexLabel;
    }

    public String getStewardOrderId() {
        return stewardOrderId;
    }

    public void setStewardOrderId(String stewardOrderId) {
        this.stewardOrderId = stewardOrderId;
    }
}
