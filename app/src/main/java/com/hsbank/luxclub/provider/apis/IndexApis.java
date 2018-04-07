package com.hsbank.luxclub.provider.apis;


import com.hsbank.luxclub.model.ApiResult;
import com.hsbank.luxclub.model.UploadImageBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

import static com.hsbank.luxclub.provider.http.APIConstant.TOKEN;


/**
 * Author:      chenliuchun
 * Date:        17/2/9
 * Description: 各种其他API
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public interface IndexApis {

    String PATH = "upload/image";

    @POST(PATH)
    @FormUrlEncoded
    Observable<ApiResult<UploadImageBean>> uploadImage(@Field(TOKEN) String token, @Field("image") String image);

}
