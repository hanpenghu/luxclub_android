package com.hsbank.luxclub.provider.http;

import com.hsbank.luxclub.BuildConfig;
import com.hsbank.luxclub.util.tool.LogUtil;
import com.hsbank.luxclub.util.tool.ToastUtil;

import rx.Subscriber;

/**
 * Author:      chenliuchun
 * Date:        16/11/14
 * Description: 自定义异常处理的订阅者
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public abstract class APISubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        // 判断异常类型并处理
        ErrorTipBean errorTip = HttpErrorUtil.handleException(e);
        LogUtil.e3(errorTip.getMessage());

        if (errorTip.isCustomError()){
            // 调用者自行处理业务错误
            onCustomError(errorTip); // TODO: 17/5/22 自定义错误处理，ErrorTipBean缺少错误码
        } else {
            // 默认的处理策略
            if (BuildConfig.DEBUG){
                ToastUtil.show(errorTip.getMessage());
            } else {
                if (errorTip.isTip()){
                    ToastUtil.show(errorTip.getMessage());
                }
            }
        }
    }

    @Override
    public abstract void onNext(T t);

    /**
     *  当前接口自定义的错误处理方式
     * @param errorTip
     */
    public void onCustomError(ErrorTipBean errorTip){
    }
}
