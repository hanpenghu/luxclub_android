package com.hsbank.luxclub.util;

import com.hsbank.util.android.util.SharedPreferencesUtil;
import com.hsbank.util.project.util.BaseProjectUtil;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * name：zhuzhenghua
 * create time:
 */
public class ProjectUtil extends BaseProjectUtil {
    /**
     * 得到当前用户登录卡号
     * @return
     */
    public static String getCardNo() {
        return SharedPreferencesUtil.getInstance().getString(ProjectConstant.CARDNO, "");
    }

    /**
     * 存入当前用户登录卡号
     * @return
     */
    public static void setCardNo(String cardNo) {
        SharedPreferencesUtil.getInstance().setString(ProjectConstant.CARDNO, cardNo);
    }

    /**
     * 得到管家电话
     *
     * @return
     */
    public static String getBusinessMobile() {
        return SharedPreferencesUtil.getInstance().getString(ProjectConstant.BUSINESS_MOBILE);
    }

    /**
     * 存入管家电话
     *
     * @return
     */
    public static void setBusinessMobile(String businessMobile) {
        SharedPreferencesUtil.getInstance().setString(ProjectConstant.BUSINESS_MOBILE, businessMobile);
    }

    /**
     * 得到当前用户登录手机号
     * @return
     */
    public static String getUserMobile() {
        return SharedPreferencesUtil.getInstance().getString(ProjectConstant.USER_MOBILE, "");
    }

    /**
     * 存入当前用户登录手机号
     * @return
     */
    public static void setUserMobile(String mobile) {
        SharedPreferencesUtil.getInstance().setString(ProjectConstant.USER_MOBILE, mobile);
    }

    /**
     * 得到当前用户登录密码
     * @return
     */
    public static String getPassword() {
        return SharedPreferencesUtil.getInstance().getString(ProjectConstant.PASSWORD, "");
    }

    /**
     * 得到当前经理登录凭证
     * @return
     */
    public static String getManagerToken() {
        return SharedPreferencesUtil.getInstance().getString(ProjectConstant.MANAGER_TOKEN, "");
    }

    /**
     * 得到当前经理登录id
     * @return
     */
    public static String getManagerUserId() {
        return SharedPreferencesUtil.getInstance().getString(ProjectConstant.MANAGER_USER_ID, "");
    }

    /**
     * 经理端用户是否是移动管家，用户类型(5:管家(客服),6:移动管家)，默认不是
     *
     * @return
     */
    public static boolean isServantType() {
        return SharedPreferencesUtil.getInstance().getString(ProjectConstant.USER_TYPE, "5").equals("6");
    }

    public static String getPriceFloatString(String price) {
        if (price == null || price.equals("")) {
            price = "0";
        }
        BigDecimal priceBigDecimal = new BigDecimal(price);
        return String.format(Locale.CHINA, "%1$,.2f", priceBigDecimal.doubleValue());
    }

    public static String getFloatString(String price) {
        if (price == null || price.equals("")) {
            price = "0";
        }
        BigDecimal priceBigDecimal = new BigDecimal(price);
        return String.format(Locale.CHINA, "%1$.2f", priceBigDecimal.doubleValue());
    }

}
