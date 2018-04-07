package com.hsbank.luxclub.provider.apis;


import com.hsbank.luxclub.model.ApiResult;
import com.hsbank.luxclub.model.ContentBean;
import com.hsbank.luxclub.model.MemberInfo;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

import static com.hsbank.luxclub.provider.http.APIConstant.TOKEN;


/**
 * Author:      chenliuchun
 * Date:        2017-03-30
 * Description: 会员卡信息 API
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public interface MemberApis {

    String PATH = "memberCard";

    @POST(PATH + "/forgotCardPassword")
    @FormUrlEncoded
    Observable<ApiResult<Object>> forgotCardPassword(@Field("mobile") String mobile);

    @POST(PATH + "/memberDetails")
    @FormUrlEncoded
    Observable<ApiResult<ContentBean>> memberDetails(@Field(TOKEN) String token, @Field("cardno") String cardno);

    @POST(PATH + "/memberInfo")
    @FormUrlEncoded
    Observable<ApiResult<MemberInfo>> memberInfo(@Field(TOKEN) String token, @Field("cardno") String cardno);


}
