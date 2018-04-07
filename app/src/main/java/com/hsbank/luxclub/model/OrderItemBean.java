package com.hsbank.luxclub.model;

import java.util.ArrayList;

/**
 * Author:      chenliuchun
 * Date:        17/2/15
 * Description: 我的订单列表条目
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public class OrderItemBean {
    
    /**
     * orderId : 4362
     * orderCode : 2900201702151056
     * orderState : 0
     * siteId : 21
     * siteName : KING COIN（上海）
     * siteAddr : 上海市普陀区长寿路831号3楼智慧广场（近武宁南路）
     * createDate : 2017-02-15 10:35:00
     * reserveNumber : 1
     * consumerMoney : 0
     * payModel : 1
     * siteImageUrl : http://120.76.241.15:7007/luxclub/userfiles/1/images/luxclub/siteInfo/2016/06/%E4%B8%BB%E7%85%A7%E7%89%87.jpg
     * remarks : 客户已提交订单，请后台工作人员完成（派单）
     * cancelReason : 
     * otherRequire : 
     * orderStateName : 预约中
     */

    private int orderId;
    private String orderCode;
    private String actualAmount;
    private String orderState;
    private String orderStateName;
    private int siteId;
    private String siteName;
    private String createDate;
    private String reserveNumber;
    private int consumerMoney;
    private String payModel;
    private String siteImageUrl;
    private ArrayList<String> voucherUrl;
    private String remarks;

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

    public void setOrderState(int orderState) {
        this.orderState = String.valueOf(orderState);
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

    public int getConsumerMoney() {
        return consumerMoney;
    }

    public void setConsumerMoney(int consumerMoney) {
        this.consumerMoney = consumerMoney;
    }

    public String getPayModel() {
        return payModel;
    }

    public void setPayModel(String payModel) {
        this.payModel = payModel;
    }

    public String getSiteImageUrl() {
        return siteImageUrl;
    }

    public void setSiteImageUrl(String siteImageUrl) {
        this.siteImageUrl = siteImageUrl;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getOrderStateName() {
        return orderStateName;
    }

    public void setOrderStateName(String orderStateName) {
        this.orderStateName = orderStateName;
    }

    public String getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(String actualAmount) {
        this.actualAmount = actualAmount;
    }

    public ArrayList<String> getVoucherUrl() {
        return voucherUrl;
    }

    public void setVoucherUrl(ArrayList<String> voucherUrl) {
        this.voucherUrl = voucherUrl;
    }
}
