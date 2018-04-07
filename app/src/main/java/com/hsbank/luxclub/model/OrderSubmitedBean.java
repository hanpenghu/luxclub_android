package com.hsbank.luxclub.model;

/**
 * Author:      chenliuchun
 * Date:        17/2/14
 * Description: 订单提交后的结果
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public class OrderSubmitedBean {

    private String orderCode;
    private String createDt;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }
}
