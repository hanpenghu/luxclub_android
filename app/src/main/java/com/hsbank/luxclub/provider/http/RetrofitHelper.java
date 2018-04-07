package com.hsbank.luxclub.provider.http;

import com.hsbank.luxclub.BuildConfig;
import com.hsbank.util.project.util.BaseProjectApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author:      chenliuchun
 * Date:        16/11/10
 * Description: retrofit创建请求辅助单例类
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public class RetrofitHelper {

    private OkHttpClient mOkHttpClient;
    /**
     * 带client参数的请求
     */
    private OkHttpClient mOkHttpClientWithClient;

    private RetrofitHelper() {
        initOkHttp();
    }

    private static class Holder{
        private static final RetrofitHelper INSTANCE = new RetrofitHelper();
    }

    public static RetrofitHelper getInstance(){
        return Holder.INSTANCE;
    }

    /**
     * 初始化okhttp实例，设置相关参数
     */
    private void initOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 添加网络日志
        // https://drakeet.me/retrofit-2-0-okhttp-3-0-config
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addNetworkInterceptor(loggingInterceptor);
        }

        //设置超时
        builder.connectTimeout(20, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);

        //错误重连
        builder.retryOnConnectionFailure(true);
        mOkHttpClient = builder.build();

        // 添加统一的client参数
        ParamsInterceptor paramsInterceptor = new ParamsInterceptor.Builder()
                .addParam(APIConstant.CLIENT, BaseProjectApi.getInstance().getClient())
                .build();
        builder.addInterceptor(paramsInterceptor);
        mOkHttpClientWithClient = builder.build();
    }

    /**
     * 创建API接口实例
     * @param service API接口
     * @param <T> 类型
     * @return 接口实例
     */
    public <T> T create(final Class<T> service, boolean client){
        String baseurl;
        if (BuildConfig.FLAVOR.equals("product")){
            baseurl = APIConstant.ROOT_PRODUCTION;
        } else if (BuildConfig.FLAVOR.equals("env_test")){
            baseurl = APIConstant.ROOT_TEST;
        } else {
//            baseurl = APIConstant.ROOT_TEST;
            baseurl = APIConstant.ROOT_PRODUCTION;
        }
        Retrofit builder = new Retrofit.Builder()
                .baseUrl(baseurl)
                .client(client ? mOkHttpClientWithClient : mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return builder.create(service);
    }

}
