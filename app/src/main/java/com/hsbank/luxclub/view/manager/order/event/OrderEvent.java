package com.hsbank.luxclub.view.manager.order.event;

import com.hsbank.util.project.util.BaseProjectEvent;

import java.util.UUID;

/**
 * name：zhuzhenghua
 * create time:
 */
public class OrderEvent extends BaseProjectEvent {
    /**经理-登录成功跳转*/
    public static final String COMMAND_MANAGER_lOGIN = UUID.randomUUID().toString();
    /**经理-我的订单*/
    public static final String COMMAND_MANAGER_ORDER = UUID.randomUUID().toString();
    /**经理-我的订单详情*/
    public static final String COMMAND_MANAGER_ORDER_DETAIL = UUID.randomUUID().toString();
    /**经理-我的订单详情，确认订单跳到提交凭证*/
    public static final String COMMAND_MANAGER_ORDER_CONFIRM = UUID.randomUUID().toString();
    /**经理-我的订单详情，提交凭证成功*/
    public static final String COMMAND_MANAGER_ORDER_CONFIRM_SUCCESS = UUID.randomUUID().toString();
    /**经理-我的订单详情，取消订单成功*/
    public static final String COMMAND_MANAGER_ORDER_CANCEL_SUCCESS = UUID.randomUUID().toString();
    /**经理-我的订单详情编辑给订单详情传值*/
    public static final String COMMAND_MANAGER_ORDER_EDIT = UUID.randomUUID().toString();
    /**经理-我的订单详情上传凭证成功*/
    public static final String COMMAND_MANAGER_ORDER_UPLOAD_IMAGE_SUCCESS = UUID.randomUUID().toString();
    /**经理-我的订单详情上传凭证成功*/
    public static final String COMMAND_MANAGER_ORDER_UPLOAD_IMAGE = UUID.randomUUID().toString();
    /**经理-订单详情传给选择场所*/
    public static final String COMMAND_MANAGER_TO_CHANGE_STORE = UUID.randomUUID().toString();
    /**经理-选择场所传给订单详情*/
    public static final String COMMAND_MANAGER_TO_ORDER_DETAIL = UUID.randomUUID().toString();
    /**经理-提交凭证时凭证照片删除*/
    public static final String COMMAND_MANAGER_DELETE = UUID.randomUUID().toString();
    /**经理-会员卡信息*/
    public static final String COMMAND_MEMBER_INFO = UUID.randomUUID().toString();
    /**我的订单详情，取消订单成功*/
    public static final String COMMAND_ORDER_CANCEL_SUCCESS = UUID.randomUUID().toString();

    public OrderEvent(String command, Object message) {
        super(command, message);
    }

    public OrderEvent(String command) {
        super(command);
    }
}
