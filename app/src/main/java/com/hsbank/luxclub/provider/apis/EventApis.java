package com.hsbank.luxclub.provider.apis;


import com.hsbank.luxclub.model.ApiResult;
import com.hsbank.luxclub.model.CheckUpdateBean;
import com.hsbank.luxclub.model.JoyPageBean;
import com.hsbank.luxclub.provider.http.APIConstant;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;


/**
 * Author:      chenliuchun
 * Date:        2017-02-23
 * Description:
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public interface EventApis {

    String PATH = "event";

    @POST(PATH + "/getCarouselList")
    @FormUrlEncoded
    Observable<ApiResult<List<JoyPageBean>>> getCarouselList(@Field(APIConstant.CLIENT) String client);

    @POST(PATH + "/getAdPositionInfo")
    @FormUrlEncoded
    Observable<ApiResult<CheckUpdateBean>> getAdPosition(@Field("adCode") String adCode);

}
