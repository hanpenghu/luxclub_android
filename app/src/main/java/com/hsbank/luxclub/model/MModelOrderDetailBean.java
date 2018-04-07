package com.hsbank.luxclub.model;

import java.util.List;

/**
 * Author:      chenliuchun
 * Date:        17/2/21
 * Description: 经理端——模特订单详情
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public class MModelOrderDetailBean {

    /**
     * cardno : AVLY4536
     * orderId : 4
     * orderCode : 161102093929068051
     * orderStatus : 6
     * orderStatusName : 已结账
     * modelId : 3
     * nickname : 苍老师
     * roomNumber :
     * createDate : 2016-11-02 09:39:29
     * contactNumber : 15637779762
     * siteId : 39
     * consumerMoney : 100.00
     * consumerVoucherUrl : ["http://120.76.241.15:7007/upload/images/20161102/20161102151035_558.jpg"]
     * walletAmount : 10.00
     * walletVoucherUrl : ["http://120.76.241.15:7007/upload/images/20161102/20161102151046_264.jpg"]
     * walletRemarks : 无
     * modelHead : http://120.76.241.15:7007/userfiles/1/images/luxclub/modelInfo/2016/10/u%3D2473362471%2C4143141621%26fm%3D21%26gp%3D0.gif
     * siteAddress : 嫖客
     */

    private String cardno;
    private String orderId;
    private String orderCode;
    private String orderStatus;
    private String orderStatusName;
    private String modelId;
    private String nickname;
    private String roomNumber;
    private String createDate;
    private String contactNumber;
    private String luxclubCode;
    private String siteId;
    private String consumerMoney;
    private String walletAmount;
    private String walletRemarks;
    private String modelHead;
    private String siteAddress;
    private List<String> consumerVoucherUrl;
    private List<String> walletVoucherUrl;

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

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

    public int getOrderStatus() {
        return Integer.parseInt(orderStatus);
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getConsumerMoney() {
        return consumerMoney;
    }

    public void setConsumerMoney(String consumerMoney) {
        this.consumerMoney = consumerMoney;
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

    public String getModelHead() {
        return modelHead;
    }

    public void setModelHead(String modelHead) {
        this.modelHead = modelHead;
    }

    public String getSiteAddress() {
        return siteAddress;
    }

    public void setSiteAddress(String siteAddress) {
        this.siteAddress = siteAddress;
    }

    public List<String> getConsumerVoucherUrl() {
        return consumerVoucherUrl;
    }

    public void setConsumerVoucherUrl(List<String> consumerVoucherUrl) {
        this.consumerVoucherUrl = consumerVoucherUrl;
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
}
