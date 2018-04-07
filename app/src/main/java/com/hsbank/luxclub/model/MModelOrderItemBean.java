package com.hsbank.luxclub.model;

/**
 * Author:      chenliuchun
 * Date:        17/2/20
 * Description: 经理端——模特订单列表条目
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public class MModelOrderItemBean {

    /**
     * orderId : 17
     * orderCode : 161109140011081701
     * orderStatus : 5
     * orderStatusName : 已预约
     * modelId : 6
     * nickname : 模特儿
     * roomNumber :
     * createDate : 2016-11-09 14:00:11
     * consumerMoney : 0.00
     * walletAmount : 0.00
     * modelHead : http://120.76.241.15:7007/userfiles/1/images/luxclub/modelInfo/2016/11/QQ%E6%88%AA%E5%9B%BE20160608150453.png
     */

    private String orderId;
    private String orderCode;
    private String orderStatus;
    private String orderStatusName;
    private String modelId;
    private String nickname;
    private String roomNumber;
    private String createDate;
    private String consumerMoney;
    private String walletAmount;
    private String modelHead;

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

    public String getModelHead() {
        return modelHead;
    }

    public void setModelHead(String modelHead) {
        this.modelHead = modelHead;
    }
}
