package com.hsbank.luxclub.model;

/**
 * Created by chenliuchun on 2018/1/10.
 */

public class ServantItemBean {

    private String stewardId;
    private String stewardName;

    public ServantItemBean(String stewardId, String stewardName) {
        this.stewardId = stewardId;
        this.stewardName = stewardName;
    }

    public String getStewardId() {
        return stewardId;
    }

    public String getStewardName() {
        return stewardName;
    }
}
