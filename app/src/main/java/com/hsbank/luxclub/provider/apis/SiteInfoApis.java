package com.hsbank.luxclub.provider.apis;


import com.hsbank.luxclub.model.AlbumDetailBean;
import com.hsbank.luxclub.model.ApiResult;
import com.hsbank.luxclub.model.SiteInfoBean;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;


/**
 * Author:      chenliuchun
 * Date:        17/2/9
 * Description: 场所信息
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public interface SiteInfoApis {

    String PATH = "siteinfo";

    @POST(PATH + "/siteInfoPageList")
    @FormUrlEncoded
    Observable<ApiResult<List<SiteInfoBean>>> siteInfoPageList(@Field("areaId") int areaId, @Field("siteType") int siteType, @Field("pageSize") int pageSize, @Field("pageNumber") int pageNumber);

    @POST(PATH + "/albumDetails")
    @FormUrlEncoded
    Observable<ApiResult<AlbumDetailBean>> albumDetails(@Field("albumId") long albumId);

}
