package com.hsbank.luxclub.util;

import android.util.Log;

import com.hsbank.util.java.http.UrlUtil;
import com.hsbank.util.project.util.BaseProjectApi;
import com.hsbank.util.project.util.BaseProjectConstant;

/**
 * Api公共类
 * Created by Administrator on 2016/3/11.
 */
public class ProjectApi extends BaseProjectApi {
    /**Api的根Url：开发环境*/
    public static final String API_ROOT_URL_DEVELOPMENT = "https://fakeapi.fdjf.net:3001";
    /**Api的根Url：测试环境*/
    public static final String API_ROOT_URL_TEST = "http://120.76.241.15:7007/f/api";
    /**Api的根Url：正式环境*/
    public static final String API_ROOT_URL_PRODUCTION = "http://api.ylzun.com/f/api";
    //========================================luxclub=================================================
    /**绑定会员卡*/
    public static final String LUXCLUB_bindMemberCard = "/memberCard/bindMemberCard";
    /**消费/充值记录详细信息*/
    public static final String LUXCLUB_feeRecordDetails = "/feeRecord/feeRecordDetails";
    /**消费/充值记录分页列表*/
    public static final String LUXCLUB_feeRecordPageList = "/feeRecord/feeRecordPageList";
    /**城市列表*/
    public static final String LUXCLUB_getAreaList = "/area/getAreaList";
    /**热门搜索--城市列表*/
    public static final String LUXCLUB_hotSearchAreaList = "/area/hotSearchAreaList";
    /**根据城市名称搜索*/
    public static final String AREA_searchListByAreaName = "/area/searchListByAreaName";
    /**热门搜索--场所列表*/
    public static final String LUXCLUB_hotSearchSiteList = "/siteinfo/hotSearchSiteList";
    /**会员卡信息*/
    public static final String LUXCLUB_memberInfo = "/memberCard/memberInfo";
    /**订单详细信息*/
    public static final String LUXCLUB_orderDetails = "/orderBusiness/orderDetails";
    /**订单分页列表*/
    public static final String LUXCLUB_orderPageList = "/orderBusiness/queryOrderPageList";
    /**根据城市名称搜索*/
    public static final String LUXCLUB_searchListByAreaName = "/feeRecord/feeRecordPageList";
    /**根据场所名称搜索*/
    public static final String LUXCLUB_searchListBySiteName = "/siteinfo/searchListBySiteName";
    /**场所详细信息*/
    public static final String LUXCLUB_siteInfoDetails = "/siteinfo/siteInfoDetails";
    /**场所信息分页列表*/
    public static final String LUXCLUB_siteInfoPageList = "/siteinfo/siteInfoPageList";

    public static final String LUXCLUB_modelPageList = "/model/pageList";
    /**提交订单*/
    public static final String LUXCLUB_submitOrder = "/orderBusiness/submitOrder";
    /**修改会员卡密码*/
    public static final String LUXCLUB_updateCardPassword = "/memberCard/updateCardPassword";
    /**解绑会员卡*/
    public static final String LUXCLUB_unbindMemberCard = "/memberCard/unbindMemberCard";
    /**零钱包消费/充值记录分页列表*/
    public static final String LUXCLUB_walletFeePageList = "/walletFee/walletFeePageList";
    //------------------------------------经理端接口---------------------------------------------
    /**登录*/
    /**经理端登录*/
    public static final String LUXCLUB_managerLogin = "/manager/login";
    /**我的订单*/
    public static final String LUXCLUB_managerOrderMyorder = "/manager/order/myOrder";
    /**我的订单详情*/
    public static final String LUXCLUB_managerOrderDetail = "/manager/order/orderDetails";
    /**修改密码*/
    public static final String LUXCLUB_managerUpdatePassword = "/manager/updatePassword";
    /**个人信息*/
    public static final String LUXCLUB_messagePageList = "/message/messagePageList";
    /**订单取消*/
    public static final String LUXCLUB_managerOrderCancel = "/manager/order/cancel";
    /**订单确认*/
    public static final String LUXCLUB_managerOrderConfirm = "/manager/order/confirm";
    /**订单待结账*/
    public static final String LUXCLUB_managerOrderCheckout = "/manager/order/checkout";
    /**已读信息*/
    public static final String LUXCLUB_messageRead = "/message/read";
    /**未读信息数量*/
    public static final String LUXCLUB_message_unreadCount = "/message/unreadCount";
    /**经理端退出登录*/
    public static final String LUXCLUB_message_logout = "/manager/logout";
    /**上传图片*/
    public static final String LUXCLUB_upload_image = "/upload/image";
    /**应用更新*/
    public static final String EVENT_checkUpdate = "/event/checkUpdate";

    /**
     * 得到指定接口的Url
     * @param key
     * @return
     */
    public static String getUrl(String key) {
        String resultValue = key;
        if (ProjectConfig.getInstance().getEnvironmentFlag().equals(BaseProjectConstant.ENVIRONMENT_FLAG_TEST)) {
            resultValue = API_ROOT_URL_TEST;
        } else if (ProjectConfig.getInstance().getEnvironmentFlag().equals(BaseProjectConstant.ENVIRONMENT_FLAG_PRODUCTION)) {
            resultValue = API_ROOT_URL_PRODUCTION;
        } else {
            resultValue = API_ROOT_URL_DEVELOPMENT;
        }
        resultValue = UrlUtil.dealUrl(resultValue + key);
        Log.d(ProjectApi.class.getName(), resultValue);
        return resultValue;
    }
}
