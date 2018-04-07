package com.hsbank.luxclub.provider.apis;


import com.hsbank.luxclub.model.ApiResult;
import com.hsbank.luxclub.model.CardOpenBean;
import com.hsbank.luxclub.model.CardDetailBean;
import com.hsbank.luxclub.model.CardItemBean;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

import static com.hsbank.luxclub.provider.http.APIConstant.TOKEN;


/**
 * Author:      chenliuchun
 * Date:        17/2/9
 * Description: 首页——动态标签页
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public interface ActivateCardApis {

    String PATH = "activateCard";

    @POST(PATH + "/recommend")
    @FormUrlEncoded
    Observable<ApiResult<CardOpenBean>> recommend(@Field(TOKEN) String token);

    @POST(PATH + "/details")
    @FormUrlEncoded
    Observable<ApiResult<CardDetailBean>> detail(@Field(TOKEN) String token, @Field("recordId") int recordId);

    @POST(PATH + "/pageList")
    @FormUrlEncoded
    Observable<ApiResult<List<CardItemBean>>> pageList(@Field(TOKEN) String token, @Field("ruleId") int ruleId,
                                                       @Field("pageSize") int pageSize, @Field("pageNumber") int pageNumber);

    @POST(PATH + "/confirm")
    @FormUrlEncoded
    Observable<ApiResult<String>> confirm(@Field(TOKEN) String token, @Field("ruleId") int ruleId, @Field("memberName") String memberName,
                                                 @Field("memberBirthday") String memberBirthday, @Field("memberMobile") String memberMobile,
                                          @Field("stewardId") String stewardId, @Field("mobileStewardId") String mobileStewardId,
                                          @Field("interestIds") String interestIds,
                                          @Field("remarks") String remarks);

}
