package com.hsbank.luxclub.provider.apis;


import com.hsbank.luxclub.model.ApiResult;
import com.hsbank.luxclub.model.OrderItemBean;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

import static com.hsbank.luxclub.provider.http.APIConstant.TOKEN;


/**
 * Author:      chenliuchun
 * Date:        17/2/9
 * Description: 用户订单列表
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public interface OrderApis {

    String PATH = "orderBusiness";
    String PATH2 = "visitorOrder";

    @POST(PATH + "/queryOrderPageList")
    @FormUrlEncoded
    Observable<ApiResult<List<OrderItemBean>>> pageList(@Field(TOKEN) String token,
                                                        @Field("cardno") String cardno, @Field("state") int state,
                                                        @Field("pageSize") int pageSize, @Field("pageNumber") int pageNumber);

    @POST(PATH + "/submitEvaluate")
    @FormUrlEncoded
    Observable<ApiResult<Object>> submitEvaluate(@Field(TOKEN) String token, @Field("orderId") String orderId, @Field("clubEvaluate") String clubEvaluate,
                                                 @Field("waiterEvaluate") String waiterEvaluate, @Field("message") String message);

    @POST(PATH2 + "/submitOrder")
    @FormUrlEncoded
    Observable<ApiResult<Object>> submitOrder(@Field("siteId") int siteId, @Field("roomNumber") String roomNumber,
                                              @Field("contactName") String contactName, @Field("contactMobile") String contactMobile,
                                              @Field("reserveNumber") String reserveNumber, @Field("reserveDate") String reserveDate,
                                              @Field("reserveCost") String reserveCost, @Field("scene") String scene,
                                              @Field("reserveRequire") String reserveRequire);

    @POST(PATH + "/delete")
    @FormUrlEncoded
    Observable<ApiResult<Object>> delete(@Field(TOKEN) String token, @Field("orderId") int orderId);

}
