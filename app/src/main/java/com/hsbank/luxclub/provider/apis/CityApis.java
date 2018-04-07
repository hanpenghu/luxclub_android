package com.hsbank.luxclub.provider.apis;


import com.hsbank.luxclub.model.ApiResult;
import com.hsbank.luxclub.model.CityBean;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

import static com.hsbank.luxclub.provider.http.APIConstant.CLIENT;


/**
 * Author:      chenliuchun
 * Date:        17/9/26
 * Description: 城市列表
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public interface CityApis {

    String PATH = "city/getList";

    @POST(PATH)
    @FormUrlEncoded
    Observable<ApiResult<List<CityBean>>> getList(@Field(CLIENT) String client);

}
