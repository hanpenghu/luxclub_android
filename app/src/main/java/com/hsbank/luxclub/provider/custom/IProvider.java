package com.hsbank.luxclub.provider.custom;


import android.content.Context;

import com.hsbank.util.android.util.http.okhttp.callback.ApiCallback;

/**
 * 接口
 *
 * @author wuyuan.xie
 *         CreateDate 2007-09-11
 */
public interface IProvider {
    /**
     * 绑定会员卡
     *
     * @param cardno
     * @param password
     * @param callback
     */
    public void luxclub_bindMemberCard(Context context, String cardno, String password, ApiCallback callback);

    /**
     * 消费/充值记录详细信息
     *
     * @param token
     * @param feeId
     * @param callback
     */
    public void luxclub_feeRecordDetails(Context context, String token, String feeId, ApiCallback callback);

    /**
     * 消费/充值记录分页列表
     *
     * @param token
     * @param cardno
     * @param feeType
     * @param pageSize
     * @param pageNumber
     * @param callback
     */
    public void luxclub_feeRecordPageList(Context context, String token, String cardno, String feeType, int pageSize, int pageNumber, ApiCallback callback);

    /**
     * 城市列表
     */
    public void luxclub_getAreaList(Context context, ApiCallback callback);

    /**
     * 热门搜索--城市列表
     */
    public void luxclub_hotSearchAreaList(Context context, ApiCallback callback);

    /**
     * 热门搜索--场所列表
     *
     * @param siteType
     */
    public void luxclub_hotSearchSiteList(Context context, String siteType, ApiCallback callback);

    /**
     * 根据城市名称搜索
     *
     * @param areaName
     */
    public void AREA_searchListByAreaName(Context context, String areaName, ApiCallback callback);

    /**
     * 会员卡信息
     *
     * @param token
     * @param cardNo
     */
    public void luxclub_memberInfo(Context context, String token, String cardNo, ApiCallback callback);

    /**
     * 订单详细信息
     *
     * @param token
     * @param orderId
     * @param callback
     */
    public void luxclub_orderDetails(Context context, String token, String orderId, ApiCallback callback);

    /**
     * 订单分页列表
     *
     * @param token
     * @param cardno
     * @param pageSize
     * @param pageNumber
     * @param callback
     */
    public void luxclub_orderPageList(Context context, String token, String cardno, int state, int pageSize, int pageNumber, ApiCallback callback);

    /**
     * 根据城市名称搜索
     *
     * @param siteType
     * @param areaName
     * @param callback
     */
    public void luxclub_seachListByAreaName(Context context, String siteType, String areaName, ApiCallback callback);

    /**
     * 根据场所名称搜索
     *
     * @param siteType
     * @param areaName
     * @param callback
     */
    public void luxclub_searchListBySiteName(Context context, String areaName, String siteType, ApiCallback callback);


    /**
     * 场所详细信息
     *
     * @param siteId
     * @param callback
     */
    public void luxclub_siteInfoDetails(Context context, String siteId, ApiCallback callback);

    /**
     * 场所信息分页列表
     *
     * @param areaId
     * @param siteType
     * @param pageSize
     * @param pageNumber
     * @param callback
     */
    public void luxclub_siteInfoPageList(Context context, String areaId, int siteType, int pageSize, int pageNumber, ApiCallback callback);

    public void luxclub_modelPageList(Context context, int pageSize, int pageNumber, ApiCallback callback);

    /**
     * 提交订单
     *
     * @param token
     * @param cardno
     * @param siteId
     * @param contactMobile
     * @param reserveNumber
     * @param reserveDate
     * @param reserveCost
     * @param reserveRequire
     * @param callback
     */
    public void luxclub_submitOrder(Context context, String token, String cardno, String siteId, String contactMobile,
                                    String reserveNumber, String reserveDate, String reserveCost, String reserveRequire,
                                    String scene, ApiCallback callback);

    /**
     * 解绑会员卡
     *
     * @param cardno
     * @param password
     * @param callback
     */
    public void luxclub_unbindMemberCard(Context context, String token, String cardno, String password, ApiCallback callback);

    /**
     * 修改会员卡密码
     *
     * @param token
     * @param oldPassword
     * @param newPassword
     * @param callback
     */
    public void luxclub_updateCardPassword(Context context, String token, String cardno, String oldPassword, String newPassword, ApiCallback callback);

    //------------------------------------经理端接口---------------------------------------------

    /**
     * 修改密码
     *
     * @param token
     * @param oldPassword
     * @param newPassword
     * @param callback
     */
    public void manager_updatePassword(Context context, String token, String oldPassword, String newPassword, ApiCallback callback);


    /**
     * 登录
     *
     * @param loginName
     * @param password
     * @param callback
     */
    public void manager_login(Context context, String loginName, String password, ApiCallback callback);

    /**
     * 我的订单
     *
     * @param token
     * @param status
     * @param pageSize
     * @param pageNumber
     * @param callback
     */
    public void manager_myOrder(Context context, String token, String status, int pageSize, int pageNumber, ApiCallback callback);

    /**
     * 订单详情
     *
     * @param token
     * @param orderId
     * @param callback
     */
    public void manager_orderDetails(Context context, String token, String orderId, ApiCallback callback);

    /**
     * 个人信息
     *
     * @param token
     * @param pageSize
     * @param pageNumber
     * @param callback
     */
    public void message_messagePageList(Context context, String token, String account, int pageSize, int pageNumber, ApiCallback callback);

    /**
     * 订单取消
     *
     * @param token
     * @param orderId
     * @param cancelReason
     * @param callback
     */
    public void manager_orderCancel(Context context, String token, String orderId, String cancelReason, ApiCallback callback);

    /**
     * 订单确认
     *
     * @param token
     * @param orderId
     * @param siteId
     * @param reserveNumber
     * @param realDate
     * @param otherRequire
     * @param callback
     */
    public void manager_orderConfirm(Context context, String token, String orderId, String siteId, String reserveNumber,
                                     String realDate, String otherRequire, ApiCallback callback);

    /**
     * 订单待结账-提交凭证
     *
     * @param token
     * @param orderId
     * @param amount
     * @param voucherNames
     * @param callback
     */
    public void manager_orderCheckout(Context context, String token, String orderId, String amount, String voucherNames, String walletAmount,
                                      String walletVoucher, String walletRemarks, ApiCallback callback);

    /**
     * 已读消息
     *
     * @param token
     * @param messageIds
     * @param callback
     */
    public void message_read(Context context, String token, String messageIds, ApiCallback callback);

    /**
     * 未读消息数量
     *
     * @param token
     * @param callback
     */
    public void message_unreadCount(Context context, String token, String account, ApiCallback callback);

    /**
     * 经理端退出登录
     *
     * @param token
     * @param callback
     */
    public void manager_logout(Context context, String token, ApiCallback callback);

    /**
     * 零钱包消费/充值记录分页列表
     *
     * @param token
     * @param cardno
     * @param feeType
     * @param pageSize
     * @param pageNumber
     * @param callback
     */
    public void walletfee_walletFeePageList(Context context, String token, String cardno, String feeType, int pageSize, int pageNumber, ApiCallback callback);

    /**
     * 上传图片
     *
     * @param token
     * @param image
     * @param callback
     */
    public void upload_image(Context context, String token, String image, ApiCallback callback);

    public void checkUpdate(Context context, ApiCallback callback);

}
