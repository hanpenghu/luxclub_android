package com.hsbank.luxclub.provider.apis;


import com.hsbank.luxclub.model.ApiResult;
import com.hsbank.luxclub.model.IsServiceBean;
import com.hsbank.luxclub.model.ServantInfoBean;
import com.hsbank.luxclub.model.ServantItemBean;
import com.hsbank.luxclub.model.ServantOrderDetailBean;
import com.hsbank.luxclub.model.ServantOrderItemBean;
import com.hsbank.luxclub.model.ViewSummaryBean;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

import static com.hsbank.luxclub.provider.http.APIConstant.TOKEN;

public interface ServantApis {

    String PATH = "mobileSteward";
    String PATH_ORDER = "mobileSteward/order";

    @POST(PATH + "/list")
    @FormUrlEncoded
    Observable<ApiResult<List<ServantItemBean>>> list(@Field(TOKEN) String token);

    @POST(PATH + "/info")
    @FormUrlEncoded
    Observable<ApiResult<ServantInfoBean>> info(@Field(TOKEN) String token);


    @POST(PATH + "/isService")
    @FormUrlEncoded
    Observable<ApiResult<IsServiceBean>> isService(@Field(TOKEN) String token, @Field("stewardId") String stewardId,
                                                   @Field("realDate") String realDate);

    @POST(PATH_ORDER + "/pageList")
    @FormUrlEncoded
    Observable<ApiResult<List<ServantOrderItemBean>>> pageList(@Field(TOKEN) String token, @Field("state") int state,
                                                               @Field("pageSize") int pageSize, @Field("pageNumber") int pageNumber);

    @POST(PATH_ORDER + "/details")
    @FormUrlEncoded
    Observable<ApiResult<ServantOrderDetailBean>> details(@Field(TOKEN) String token, @Field("orderId") String orderId, @Field("stewardOrderId") String stewardOrderId);

    @POST(PATH_ORDER + "/accept")
    @FormUrlEncoded
    Observable<ApiResult<Object>> accept(@Field(TOKEN) String token, @Field("orderId") String orderId, @Field("stewardOrderId") String stewardOrderId);

    @POST(PATH_ORDER + "/finish")
    @FormUrlEncoded
    Observable<ApiResult<Object>> finish(@Field(TOKEN) String token, @Field("orderId") String orderId, @Field("stewardOrderId") String stewardOrderId);

    @POST(PATH_ORDER + "/viewSummary")
    @FormUrlEncoded
    Observable<ApiResult<ViewSummaryBean>> viewSummary(@Field(TOKEN) String token, @Field("orderId") String orderId);

    @POST(PATH_ORDER + "/submitSummary")
    @FormUrlEncoded
    Observable<ApiResult<Object>> submitSummary(@Field(TOKEN) String token, @Field("orderId") String orderId,
                                                @Field("content") String content, @Field("images") String images);

}
