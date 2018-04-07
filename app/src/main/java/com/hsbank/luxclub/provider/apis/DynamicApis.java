package com.hsbank.luxclub.provider.apis;


import com.hsbank.luxclub.model.ApiResult;
import com.hsbank.luxclub.model.DynamicDetailBean;
import com.hsbank.luxclub.model.DynamicItemBean;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;


/**
 * Author:      chenliuchun
 * Date:        17/2/9
 * Description: 首页——动态标签页
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public interface DynamicApis {

    String PATH = "dynamic";

    @POST(PATH + "/pageList")
    @FormUrlEncoded
    Observable<ApiResult<List<DynamicItemBean>>> pageList(@Field("pageSize") int pageSize, @Field("pageNumber") int pageNumber);

    @POST(PATH + "/details")
    @FormUrlEncoded
    Observable<ApiResult<DynamicDetailBean>> details(@Field("dynamicId") int dynamicId);

}
