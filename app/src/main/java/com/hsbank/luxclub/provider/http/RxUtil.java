package com.hsbank.luxclub.provider.http;

import android.content.Context;

import com.hsbank.luxclub.model.ApiResult;
import com.trello.rxlifecycle.LifecycleProvider;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Author:      chenliuchun
 * Date:        16/11/10
 * Description: RxJava 对 Retrofit Observable 的数据转化处理工具类
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */
public class RxUtil {

    /**
     * 统一处理结果：
     * 1. 处理线程切换IO——>Main；
     * 2. 处理返回结果，抛出异常；
     * 3. 集中分类处理异常
     * @param <T> 数据实体类型
     * @return 数据转换器
     */
    public static <T> Observable.Transformer<ApiResult<T>, T> compose(final Context context) {
        return new Observable.Transformer<ApiResult<T>, T>() {
            @Override
            public Observable<T> call(Observable<ApiResult<T>> apiResultObservable) {
                return apiResultObservable
                        // 处理线程切换IO——>Main
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        // 处理返回结果，抛出异常
                        .flatMap(new Func1<ApiResult<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(ApiResult<T> apiResult) {
                                // 正常情况下code为0
                                if (apiResult.getCode() == 0) {
                                    return getData(apiResult.getData());
                                } else {
                                    return Observable.error(new ApiException(apiResult.getText(), apiResult.getCode()));
                                }
                            } })
                        // 绑定生命周期
                        .compose(((LifecycleProvider<T>)context).<T>bindToLifecycle());
            }
        };
    }

    /**
     * 统一处理结果集为List集合：
     * 1. 处理线程切换IO——>Main；
     * 2. 处理返回结果，抛出异常；
     * 3. 集中分类处理异常
     * @param <T> 数据实体类型
     * @return 数据转换器
     */
    public static <T> Observable.Transformer<ApiResult<List<T>>, T> composeList(final Context context) {
        return new Observable.Transformer<ApiResult<List<T>>, T>() {
            @Override
            public Observable<T> call(Observable<ApiResult<List<T>>> apiResultObservable) {
                return apiResultObservable
                        // 处理线程切换IO——>Main
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        // 处理返回结果，抛出异常
                        .flatMap(new Func1<ApiResult<List<T>>, Observable<List<T>>>() {
                            @Override
                            public Observable<List<T>> call(ApiResult<List<T>> apiResult) {
                                // 正常情况下code为0
                                if (apiResult.getCode() == 0) {
                                    return getData(apiResult.getData());
                                } else {
                                    return Observable.error(new ApiException(apiResult.getText(), apiResult.getCode()));
                                }
                            } })
                        .flatMap(new Func1<List<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(List<T> ts) {
                                return Observable.from(ts);
                            }
                        })
                        // 绑定生命周期
                        .compose(((LifecycleProvider<T>)context).<T>bindToLifecycle());
            }
        };
    }

    /**
     * 基本的线程处理
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> convertScheduler() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> Observable<T> convertScheduler(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 处理API结果，包含错误处理
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<ApiResult<T>, T> convertResult() {   //compose判断结果
        return new Observable.Transformer<ApiResult<T>, T>() {
            @Override
            public Observable<T> call(Observable<ApiResult<T>> apiResultObservable) {
                return apiResultObservable
                        .flatMap(new Func1<ApiResult<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(ApiResult<T> apiResult) {
                                // 正常情况下code为0
                                if (apiResult.getCode() == 0) {
                                    return getData(apiResult.getData());
                                } else {
                                    return Observable.error(new ApiException(apiResult.getText(), apiResult.getCode()));
                                }
                            }
                        });
            }
        };
    }

    public static <T> Observable<T> convertResult(Observable<ApiResult<T>> observable) {
        return observable.flatMap(new Func1<ApiResult<T>, Observable<T>>() {
            @Override
            public Observable<T> call(ApiResult<T> apiResult) {
                // 正常情况下code为0
                if (apiResult.getCode() == 0) {
                    return getData(apiResult.getData());
                } else {
                    return Observable.error(new ApiException(apiResult.getText(), apiResult.getCode()));
                }
            }
        });
    }

    /**
     * 统一处理错误
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> handleError(){
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        // 判断异常类型并处理

                    }
                });
            }
        };
    }

    public static <T> Observable<T> handleError(Observable<T> observable) {
        return observable.doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                // 判断异常类型并处理

            }
        });
    }

    /**
     * 生成API data 的 Observable
     * @param <T>
     * @return
     */
    public static <T> Observable<T> getData(final T t) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(t);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        });
    }
}
