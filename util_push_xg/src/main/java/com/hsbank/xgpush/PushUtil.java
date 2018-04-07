package com.hsbank.xgpush;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.common.Constants;

/**
 * Created by Administrator on 2016/2/22.
 * 枚举单例模式，参考：
 * http://hukai.me/java-notes-singleton-pattern/
 * http://blog.csdn.net/horace20/article/details/37562513
 */
public enum PushUtil {
    INSTANCE;

    //          2100252721 测试账号id
//          2100191593 正式账号id
//          AI5654D1NPGB 测试key
//          A9GE3W396KNC 正式key
    public static final long ID_ACCESS_TEST = 2100252721L;
    public static final long ID_ACCESS_PRDC = 2100191593L;
    public static final String KEY_ACCESS_TEST = "AI5654D1NPGB";
    public static final String KEY_ACCESS_PRDC = "A9GE3W396KNC";

    /**
     * 无账号下的推送注册
     *
     * @param context
     */
    public void registerMyPush(Context context) {
        XGPushManager.registerPush(context, new XGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
                Log.w(Constants.LogTag, "register push success. token:" + data);
            }

            @Override
            public void onFail(Object data, int errCode, String msg) {
                Log.w(Constants.LogTag,
                        "register push fail. token:" + data + ", errCode:" + errCode + ",msg:" + msg);
            }
        });
    }

    /**
     * 有账号下的推送注册
     *
     * @param context
     * @param accountID 账号唯一的标志
     */
    public void registerMyPush(Context context, String accountID) {
        if (!TextUtils.isEmpty(accountID)) {
            XGPushManager.registerPush(context, accountID, new XGIOperateCallback() {
                @Override
                public void onSuccess(Object data, int flag) {
                    Log.w(Constants.LogTag, "register push success. token:" + data);
                }

                @Override
                public void onFail(Object data, int errCode, String msg) {
                    Log.w(Constants.LogTag,
                            "register push fail. token:" + data + ", errCode:" + errCode + ",msg:" + msg);
                }
            });
        } else {
            registerMyPush(context);
        }
    }
}

