package com.hsbank.luxclub.provider.apis;


import com.hsbank.luxclub.model.ApiResult;
import com.hsbank.luxclub.model.MModelOrderDetailBean;
import com.hsbank.luxclub.model.MModelOrderItemBean;
import com.hsbank.luxclub.model.ModelDetialBean;
import com.hsbank.luxclub.model.ModelItemBean;
import com.hsbank.luxclub.model.ModelOrderHandleBean;
import com.hsbank.luxclub.model.ModelOrderDetailBean;
import com.hsbank.luxclub.model.ModelOrderItemBean;
import com.hsbank.luxclub.model.OrderSubmitedBean;

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

public interface ModelApis {

    String PATH = "model";
    String PATH2 = "model/order";
    String PATH3 = "model/manager/order";

    @POST(PATH + "/pageList")
    @FormUrlEncoded
    Observable<ApiResult<List<ModelItemBean>>> pageList(@Field("pageSize") int pageSize, @Field("pageNumber") int pageNumber);

    @POST(PATH + "/details")
    @FormUrlEncoded
    Observable<ApiResult<ModelDetialBean>> details(@Field("modelId") String modelId);


    // model/order

    @POST(PATH2 + "/submit")
    @FormUrlEncoded
    Observable<ApiResult<OrderSubmitedBean>> submit(@Field(TOKEN) String token, @Field("modelId") String modelId,
                                                    @Field("contactNumber") String contactNumber, @Field("siteId") int siteId,
                                                    @Field("roomNumber") String roomNumber);

    @POST(PATH2 + "/pageList")
    @FormUrlEncoded
    Observable<ApiResult<List<ModelOrderItemBean>>> pageList(@Field(TOKEN) String token,
                                                             @Field("cardno") String cardno, @Field("status") int status,
                                                             @Field("pageSize") int pageSize, @Field("pageNumber") int pageNumber);

    @POST(PATH2 + "/details")
    @FormUrlEncoded
    Observable<ApiResult<ModelOrderDetailBean>> orderDetails(@Field(TOKEN) String token, @Field("orderId") String orderId);

    @POST(PATH2 + "/cancel")
    @FormUrlEncoded
    Observable<ApiResult<ModelOrderHandleBean>> orderCancel(@Field(TOKEN) String token, @Field("orderId") String orderId);

    @POST(PATH2 + "/delete")
    @FormUrlEncoded
    Observable<ApiResult<Object>> delete(@Field(TOKEN) String token, @Field("orderId") String orderId);

    // model/manager/order
    @POST(PATH3 + "/pageList")
    @FormUrlEncoded
    Observable<ApiResult<List<MModelOrderItemBean>>> pageList(@Field(TOKEN) String token, @Field("status") int status,
                                                              @Field("pageSize") int pageSize, @Field("pageNumber") int pageNumber);

    @POST(PATH3 + "/details")
    @FormUrlEncoded
    Observable<ApiResult<MModelOrderDetailBean>> MOrderDetails(@Field(TOKEN) String token, @Field("orderId") String orderId);

    @POST(PATH3 + "/cancel")
    @FormUrlEncoded
    Observable<ApiResult<ModelOrderHandleBean>> MOrderCancel(@Field(TOKEN) String token, @Field("orderId") String orderId, @Field("cancelReason") String cancelReason);

    @POST(PATH3 + "/confirm")
    @FormUrlEncoded
    Observable<ApiResult<ModelOrderHandleBean>> MOrderConfirm(@Field(TOKEN) String token, @Field("orderId") String orderId,
                                                              @Field("modelId") String modelId, @Field("roomNumber") String roomNumber);

    @POST(PATH3 + "/checkout")
    @FormUrlEncoded
    Observable<ApiResult<ModelOrderHandleBean>> MOrderCheckout(@Field(TOKEN) String token, @Field("orderId") String orderId, @Field("amount") String amount,
                                                               @Field("vouchers") String vouchers, @Field("walletAmount") String walletAmount,
                                                               @Field("walletVouchers") String walletVouchers, @Field("walletRemarks") String walletRemarks);
}
