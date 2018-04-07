package com.hsbank.luxclub.provider.custom.impl;


import android.content.Context;

import com.hsbank.luxclub.provider.custom.IProvider;
import com.hsbank.luxclub.util.ProjectApi;
import com.hsbank.util.android.util.http.okhttp.OkHttpUtil;
import com.hsbank.util.android.util.http.okhttp.callback.ApiCallback;

/**
 * 网络下的服务提供类
 * CreateDate 2016-3-11
 */

public class ProviderWifi implements IProvider {
    @Override
    public void luxclub_bindMemberCard(Context context,String cardno, String password, ApiCallback callback) {
        OkHttpUtil
                .post()
                .url(ProjectApi.getUrl(ProjectApi.LUXCLUB_bindMemberCard))
                .addParams("cardno", cardno)
                .addParams("password", password)
                .build(true)
                .execute(context,callback);
    }

    @Override
    public void luxclub_feeRecordDetails(Context context,String token, String feeId, ApiCallback callback) {
        OkHttpUtil
                .post()
                .url(ProjectApi.getUrl(ProjectApi.LUXCLUB_feeRecordDetails))
                .addParams("token", token)
                .addParams("feeId", feeId)
                .build(true)
                .execute(context,callback);
    }

    @Override
    public void luxclub_feeRecordPageList(Context context,String token, String cardno, String feeType, int pageSize, int pageNumber, ApiCallback callback) {
        OkHttpUtil
                .post()
                .url(ProjectApi.getUrl(ProjectApi.LUXCLUB_feeRecordPageList))
                .addParams("token", token)
                .addParams("cardno", cardno)
                .addParams("feeType", feeType)
                .addParams("pageSize", pageSize + "")
                .addParams("pageNumber", pageNumber + "")
                .build(true)
                .execute(context,callback);
    }

    @Override
    public void luxclub_getAreaList(Context context,ApiCallback callback) {
        OkHttpUtil
                .post()
                .url(ProjectApi.getUrl(ProjectApi.LUXCLUB_getAreaList))
                .build(true)
                .execute(context,callback);
    }

    @Override
    public void luxclub_hotSearchAreaList(Context context,ApiCallback callback) {
        OkHttpUtil
                .post()
                .url(ProjectApi.getUrl(ProjectApi.LUXCLUB_hotSearchAreaList))
                .build(true)
                .execute(context,callback);
    }

    @Override
    public void luxclub_hotSearchSiteList(Context context,String siteType, ApiCallback callback) {
        OkHttpUtil
                .post()
                .url(ProjectApi.getUrl(ProjectApi.LUXCLUB_hotSearchSiteList))
                .addParams("siteType", siteType)
                .build(true)
                .execute(context,callback);
    }

    @Override
    public void AREA_searchListByAreaName(Context context, String areaName, ApiCallback callback) {
        OkHttpUtil
                .post()
                .url(ProjectApi.getUrl(ProjectApi.AREA_searchListByAreaName))
                .addParams("areaName", areaName)
                .build(true)
                .execute(context,callback);
    }

    @Override
    public void luxclub_memberInfo(Context context,String token, String cardNo, ApiCallback callback) {
        OkHttpUtil
                .post()
                .url(ProjectApi.getUrl(ProjectApi.LUXCLUB_memberInfo))
                .addParams("token", token)
                .addParams("cardno", cardNo)
                .build(true)
                .execute(context,callback);
    }

    @Override
    public void luxclub_orderDetails(Context context,String token, String orderId, ApiCallback callback) {
        OkHttpUtil
                .post()
                .url(ProjectApi.getUrl(ProjectApi.LUXCLUB_orderDetails))
                .addParams("token", token)
                .addParams("orderId", orderId)
                .build(true)
                .execute(context,callback);
    }

    @Override
    public void luxclub_orderPageList(Context context,String token, String cardno, int state,int pageSize, int pageNumber, ApiCallback callback) {
        OkHttpUtil
                .post()
                .url(ProjectApi.getUrl(ProjectApi.LUXCLUB_orderPageList))
                .addParams("token", token)
                .addParams("cardno", cardno)
                .addParams("state", state + "")
                .addParams("pageSize", pageSize +"")
                .addParams("pageNumber", pageNumber +"")
                .build(true)
                .execute(context,callback);
    }

    @Override
    public void luxclub_seachListByAreaName(Context context,String siteType, String areaName, ApiCallback callback) {
        OkHttpUtil
                .post()
                .url(ProjectApi.getUrl(ProjectApi.LUXCLUB_searchListByAreaName))
                .addParams("siteType", siteType)
                .addParams("areaName", areaName)
                .build(true)
                .execute(context,callback);
    }

    @Override
    public void luxclub_searchListBySiteName(Context context,String areaName, String siteType, ApiCallback callback) {
        OkHttpUtil
                .post()
                .url(ProjectApi.getUrl(ProjectApi.LUXCLUB_searchListBySiteName))
                .addParams("siteName", areaName)
                .addParams("siteType", siteType)
                .build(true)
                .execute(context,callback);
    }

    @Override
    public void luxclub_siteInfoDetails(Context context,String siteId, ApiCallback callback) {
        OkHttpUtil
                .post()
                .url(ProjectApi.getUrl(ProjectApi.LUXCLUB_siteInfoDetails))
                .addParams("siteId", siteId)
                .build(true)
                .execute(context,callback);
    }

    @Override
    public void luxclub_siteInfoPageList(Context context, String cityId, int siteType, int pageSize, int pageNumber, ApiCallback callback) {
        OkHttpUtil
                .post()
                .url(ProjectApi.getUrl(ProjectApi.LUXCLUB_siteInfoPageList))
                .addParams("cityId", cityId)
                .addParams("siteType", siteType + "")
                .addParams("pageSize", pageSize + "")
                .addParams("pageNumber", pageNumber+ "")
                .build(true)
                .execute(context,callback);
    }

    @Override
    public void luxclub_modelPageList(Context context, int pageSize, int pageNumber, ApiCallback callback) {
        OkHttpUtil
                .post()
                .url(ProjectApi.getUrl(ProjectApi.LUXCLUB_modelPageList))
                .addParams("pageSize", pageSize + "")
                .addParams("pageNumber", pageNumber+ "")
                .build(true)
                .execute(context,callback);
    }

    @Override
    public void luxclub_submitOrder(Context context, String token, String cardno, String siteId, String contactMobile,
                                    String reserveNumber, String reserveDate, String reserveCost,
                                    String reserveRequire, String scene, ApiCallback callback) {
        OkHttpUtil
                .post()
                .url(ProjectApi.getUrl(ProjectApi.LUXCLUB_submitOrder))
                .addParams("token", token)
                .addParams("cardno", cardno)
                .addParams("siteId", siteId)
                .addParams("contactMobile", contactMobile)
                .addParams("reserveNumber", reserveNumber)
                .addParams("reserveDate", reserveDate)
                .addParams("reserveCost", reserveCost)
                .addParams("reserveRequire", reserveRequire)
                .addParams("scene", scene)
                .build(true)
                .execute(context,callback);
    }

    @Override
    public void luxclub_unbindMemberCard(Context context,String token,String cardno, String password, ApiCallback callback) {
        OkHttpUtil
                .post()
                .url(ProjectApi.getUrl(ProjectApi.LUXCLUB_unbindMemberCard))
                .addParams("token", token)
                .addParams("cardno", cardno)
                .addParams("password", password)
                .build(true)
                .execute(context,callback);
    }

    @Override
    public void luxclub_updateCardPassword(Context context,String token, String cardno, String oldPassword, String newPassword, ApiCallback callback) {
        OkHttpUtil
                .post()
                .url(ProjectApi.getUrl(ProjectApi.LUXCLUB_updateCardPassword))
                .addParams("token", token)
                .addParams("cardno", cardno)
                .addParams("oldPassword", oldPassword)
                .addParams("newPassword", newPassword)
                .build(true)
                .execute(context,callback);
    }

    //------------------------------------经理端接口---------------------------------------------
    @Override
    public void manager_login(Context context,String loginName, String password, ApiCallback callback) {
        OkHttpUtil
                .post()
                .url(ProjectApi.getUrl(ProjectApi.LUXCLUB_managerLogin))
                .addParams("loginName", loginName)
                .addParams("password", password)
                .build(true)
                .execute(context,callback);
    }

    @Override
    public void message_messagePageList(Context context,String token, String account, int pageSize, int pageNumber, ApiCallback callback) {
        OkHttpUtil
                .post()
                .url(ProjectApi.getUrl(ProjectApi.LUXCLUB_messagePageList))
                .addParams("token", token)
                .addParams("account", account)
                .addParams("pageSize", pageSize + "")
                .addParams("pageNumber", pageNumber+ "")
                .build(true)
                .execute(context,callback);
    }

    @Override
    public void manager_orderCancel(Context context,String token, String orderId, String cancelReason, ApiCallback callback) {
        OkHttpUtil
                .post()
                .url(ProjectApi.getUrl(ProjectApi.LUXCLUB_managerOrderCancel))
                .addParams("token", token)
                .addParams("orderId", orderId)
                .addParams("cancelReason", cancelReason)
                .build(true)
                .execute(context,callback);
    }

    @Override
    public void manager_orderConfirm(Context context,String token, String orderId, String siteId, String reserveNumber, String realDate, String otherRequire, ApiCallback callback) {
        OkHttpUtil
                .post()
                .url(ProjectApi.getUrl(ProjectApi.LUXCLUB_managerOrderConfirm))
                .addParams("token", token)
                .addParams("orderId", orderId)
                .addParams("siteId", siteId)
                .addParams("reserveNumber", reserveNumber)
                .addParams("realDate", realDate)
                .addParams("otherRequire", otherRequire)
                .build(true)
                .execute(context,callback);
    }

    @Override
    public void manager_orderCheckout(Context context,String token, String orderId, String amount, String voucherNames, String walletAmount,String walletVoucher, String walletRemarks, ApiCallback callback) {
        OkHttpUtil
                .post()
                .url(ProjectApi.getUrl(ProjectApi.LUXCLUB_managerOrderCheckout))
                .addParams("token", token)
                .addParams("orderId", orderId)
                .addParams("amount", amount)
                .addParams("voucherNames", voucherNames)
                .addParams("walletAmount", walletAmount)
                .addParams("walletVoucher", walletVoucher)
                .addParams("walletRemarks", walletRemarks)
                .build(true)
                .execute(context,callback);
    }

    @Override
    public void message_read(Context context,String token, String messageIds, ApiCallback callback) {
        OkHttpUtil
                .post()
                .url(ProjectApi.getUrl(ProjectApi.LUXCLUB_messageRead))
                .addParams("token", token)
                .addParams("messageIds", messageIds)
                .build(true)
                .execute(context,callback);

    }

    @Override
    public void message_unreadCount(Context context,String token,String account, ApiCallback callback) {
        OkHttpUtil
                .post()
                .url(ProjectApi.getUrl(ProjectApi.LUXCLUB_message_unreadCount))
                .addParams("token", token)
                .addParams("account", account)
                .build(true)
                .execute(context,callback);
    }

    @Override
    public void manager_logout(Context context,String token, ApiCallback callback) {
        OkHttpUtil
                .post()
                .url(ProjectApi.getUrl(ProjectApi.LUXCLUB_message_logout))
                .addParams("token", token)
                .build(true)
                .execute(context,callback);
    }

    @Override
    public void manager_updatePassword(Context context,String token, String oldPassword, String newPassword, ApiCallback callback) {
        OkHttpUtil
                .post()
                .url(ProjectApi.getUrl(ProjectApi.LUXCLUB_managerUpdatePassword))
                .addParams("token", token)
                .addParams("oldPassword", oldPassword)
                .addParams("newPassword", newPassword)
                .build(true)
                .execute(context,callback);

    }

    @Override
    public void manager_myOrder(Context context,String token, String status, int pageSize, int pageNumber, ApiCallback callback) {
        OkHttpUtil
                .post()
                .url(ProjectApi.getUrl(ProjectApi.LUXCLUB_managerOrderMyorder))
                .addParams("token", token)
                .addParams("status", status)
                .addParams("pageSize", pageSize + "")
                .addParams("pageNumber", pageNumber + "")
                .build(true)
                .execute(context,callback);
    }

    @Override
    public void manager_orderDetails(Context context,String token, String orderId, ApiCallback callback) {
        OkHttpUtil
                .post()
                .url(ProjectApi.getUrl(ProjectApi.LUXCLUB_managerOrderDetail))
                .addParams("token", token)
                .addParams("orderId", orderId)
                .build(true)
                .execute(context,callback);
    }

    @Override
    public void walletfee_walletFeePageList(Context context,String token, String cardno, String feeType, int pageSize, int pageNumber, ApiCallback callback) {
        OkHttpUtil
                .post()
                .url(ProjectApi.getUrl(ProjectApi.LUXCLUB_walletFeePageList))
                .addParams("token", token)
                .addParams("cardno", cardno)
                .addParams("feeType", feeType)
                .addParams("pageSize", pageSize + "")
                .addParams("pageNumber", pageNumber + "")
                .build(true)
                .execute(context,callback);
    }

    @Override
    public void upload_image(Context context, String token, String image, ApiCallback callback) {
        OkHttpUtil
                .post()
                .url(ProjectApi.getUrl(ProjectApi.LUXCLUB_upload_image))
                .addParams("token", token)
                .addParams("image", image)
                .build(true)
                .execute(context,callback);
    }

    @Override
    public void checkUpdate(Context context, ApiCallback callback) {
        OkHttpUtil
                .post()
                .url(ProjectApi.getUrl(ProjectApi.EVENT_checkUpdate))
                .build(true)
                .execute(context,callback);
    }
}
