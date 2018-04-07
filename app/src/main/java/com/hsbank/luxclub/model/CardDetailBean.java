package com.hsbank.luxclub.model;

import java.util.List;

/**
 * 开卡详情
 * Created by chenliuchun on 17/5/19.
 */

public class CardDetailBean {

    /**
     * recordId : 1
     * cardNo : 88038888
     * ruleName : 30万送6万
     * amount : 300000
     * actualAmount : 360000
     * issuingDate : 2017-05-17
     * memberName : VIP会员
     * memberMobile : 138132345678
     * memberBirthday : 1990-01-01
     * stewardName : 小琪
     * status : 1
     * statusLabel : 正常
     * interests : ["高端旅游","高尔夫","飞行俱乐部","高端餐饮","生活管家"]
     * remarks : 备注，备注
     */

    private int recordId;
    private String cardNo;
    private String ruleName;
    private String amount;
    private String actualAmount;
    private String issuingDate;
    private String memberName;
    private String memberMobile;
    private String memberBirthday;
    private String stewardName;
    private String status;
    private String statusLabel;
    private String remarks;
    private List<String> interests;

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(String actualAmount) {
        this.actualAmount = actualAmount;
    }

    public String getIssuingDate() {
        return issuingDate;
    }

    public void setIssuingDate(String issuingDate) {
        this.issuingDate = issuingDate;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberMobile() {
        return memberMobile;
    }

    public void setMemberMobile(String memberMobile) {
        this.memberMobile = memberMobile;
    }

    public String getMemberBirthday() {
        return memberBirthday;
    }

    public void setMemberBirthday(String memberBirthday) {
        this.memberBirthday = memberBirthday;
    }

    public String getStewardName() {
        return stewardName;
    }

    public void setStewardName(String stewardName) {
        this.stewardName = stewardName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }
}
