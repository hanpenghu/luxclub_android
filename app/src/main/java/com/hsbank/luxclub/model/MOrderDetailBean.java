package com.hsbank.luxclub.model;

import java.util.List;

/**
 * Created by chenliuchun on 17/2/27.
 */

public class MOrderDetailBean {

    /**
     * orderId : 4365
     * orderCode : 2900201702171059
     * orderState : 6
     * siteId : 46
     * siteName : 场所名称_游艇俱乐部
     * siteAddr : 场所地址_游艇俱乐部
     * createDate : 2017-02-17 17:28:25
     * reserveNumber : 2
     * contactMobile : 17602177858
     * consumerMoney : 1.00
     * realDate : 2017-02-17 17:28
     * cancelReason :
     * reserveRequire :
     * walletAmount : 1.00
     * walletRemarks : 额
     * cardno : AVLY4536
     * siteImageUrl : http://120.76.241.15:7007null
     * voucherUrl : ["http://120.76.241.15:7007/upload/images/20170217/20170217172921_428.jpg"]
     * orderStateName : 已结账
     * walletVoucherUrl : ["http://120.76.241.15:7007/upload/images/20170217/20170217172928_370.jpg"]
     */

    private String orderId;
    private String orderCode;
    private String orderState;
    private int siteId;
    private String siteName;
    private String siteAddr;
    private String createDate;
    private String reserveNumber;
    private String contactMobile;
    private String luxclubCode;
    private String consumerMoney;
    private String realDate;
    private String cancelReason;
    private String reserveRequire;
    private String walletAmount;
    private String walletRemarks;
    private String cardno;
    private String siteImageUrl;
    private String orderStateName;
    private String mobileStewardId;
    private String mobileStewardName;
    private List<String> voucherUrl;
    private List<String> walletVoucherUrl;

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

    public String getRealDate() {
        return realDate;
    }

    public void setRealDate(String realDate) {
        this.realDate = realDate;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getReserveRequire() {
        return reserveRequire;
    }

    public void setReserveRequire(String reserveRequire) {
        this.reserveRequire = reserveRequire;
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

    public List<String> getVoucherUrl() {
        return voucherUrl;
    }

    public void setVoucherUrl(List<String> voucherUrl) {
        this.voucherUrl = voucherUrl;
    }

    public List<String> getWalletVoucherUrl() {
        return walletVoucherUrl;
    }

    public void setWalletVoucherUrl(List<String> walletVoucherUrl) {
        this.walletVoucherUrl = walletVoucherUrl;
    }

    public String getLuxclubCode() {
        return luxclubCode;
    }

    public void setLuxclubCode(String luxclubCode) {
        this.luxclubCode = luxclubCode;
    }

    public String getMobileStewardId() {
        return mobileStewardId;
    }

    public void setMobileStewardId(String mobileStewardId) {
        this.mobileStewardId = mobileStewardId;
    }

    public String getMobileStewardName() {
        return mobileStewardName;
    }

    public void setMobileStewardName(String mobileStewardName) {
        this.mobileStewardName = mobileStewardName;
    }
}
