package com.hsbank.luxclub.model;

/**
 * 推荐开卡记录列表
 * Created by chenliuchun on 17/5/19.
 */

public class CardItemBean {

    /**
     * recordId : 1
     * cardNo : 88010001
     * actualAmount : 120000
     * createDate : 2017-05-16 10:23:34
     */

    private int recordId;
    private String cardNo;
    private String actualAmount;
    private String createDate;

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

    public String getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(String actualAmount) {
        this.actualAmount = actualAmount;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
