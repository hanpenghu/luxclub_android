package com.hsbank.luxclub.provider.apis;


import com.hsbank.luxclub.model.ApiResult;
import com.hsbank.luxclub.model.MOrderDetailBean;
import com.hsbank.luxclub.model.MOrderItemBean;
import com.hsbank.luxclub.model.ManagerLoginBean;
import com.hsbank.luxclub.model.ModelOrderHandleBean;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

import static com.hsbank.luxclub.provider.http.APIConstant.TOKEN;


/**
 * Author:      chenliuchun
 * Date:        17/2/9
 * Description: 模特相关信息
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public interface ManagerApis {

    String PATH = "manager";
    String PATH2 = "manager/order";


    // model/order

    @POST(PATH + "/login")
    @FormUrlEncoded
    Observable<ApiResult<ManagerLoginBean>> login(@Field(TOKEN) String token,
                                                  @Field("loginName") int loginName, @Field("password") int password);

    @POST(PATH2 + "/myOrder")
    @FormUrlEncoded
    Observable<ApiResult<List<MOrderItemBean>>> pageList(@Field(TOKEN) String token, @Field("status") int status,
                                                         @Field("pageSize") int pageSize, @Field("pageNumber") int pageNumber);

    @POST(PATH2 + "/orderDetails")
    @FormUrlEncoded
    Observable<ApiResult<MOrderDetailBean>> orderDetails(@Field(TOKEN) String token, @Field("orderId") String orderId);

    @POST(PATH2 + "/cancel")
    @FormUrlEncoded
    Observable<ApiResult<ModelOrderHandleBean>> cancel(@Field(TOKEN) String token, @Field("orderId") String orderId, @Field("cancelReason") String cancelReason);

    @POST(PATH2 + "/confirm")
    @FormUrlEncoded
    Observable<ApiResult<ModelOrderHandleBean>> confirm(@Field(TOKEN) String token, @Field("orderId") String orderId,
                                                        @Field("siteId") int siteId, @Field("reserveNumber") String reserveNumber,
                                                        @Field("realDate") String realDate, @Field("mobileStewardId") String mobileStewardId,
                                                        @Field("reserveRequire") String otherRequire);

    @POST(PATH2 + "/checkout")
    @FormUrlEncoded
    Observable<ApiResult<ModelOrderHandleBean>> checkout(@Field(TOKEN) String token, @Field("orderId") String orderId, @Field("amount") String amount,
                                                         @Field("voucherNames") String voucherNames, @Field("walletAmount") String walletAmount,
                                                         @Field("walletVoucher") String walletVoucher, @Field("walletRemarks") String walletRemarks);

}
