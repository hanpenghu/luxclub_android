package com.hsbank.luxclub.view.main.my.event;

import com.hsbank.util.project.util.BaseProjectEvent;

import java.util.UUID;

/**
 * name：zhuzhenghua
 * mail：15821450489@163.com
 * create time:2015.11.19
 */
public class MyEvent extends BaseProjectEvent {
    public static final String COMMAND_FINISH = UUID.randomUUID().toString();
    /**我的订单*/
    public static final String COMMAND_MY_ORDER = UUID.randomUUID().toString();
    /**我的消费记录*/
    public static final String COMMAND_MY_RECORDS_CONSUMPTION = UUID.randomUUID().toString();
    /**我的充值记录*/
    public static final String COMMAND_MY_RECHARGE_CONSUMPTION = UUID.randomUUID().toString();
    /**我的订单给订单详情传orderId*/
    public static final String COMMAND_MY_ORDER_TO_DETAIL = UUID.randomUUID().toString();
    /**我的订单详情更新界面*/
    public static final String COMMAND_MY_ORDER_DETAIL = UUID.randomUUID().toString();
    /**我的消费记录往记录详情传值*/
    public static final String COMMAND_MY_RECORDS_TO_DETAIL = UUID.randomUUID().toString();
    /**我的消费记录详情*/
    public static final String COMMAND_MY_RECORDS_DETAIL = UUID.randomUUID().toString();
    /**我的充值记录详情*/
    public static final String COMMAND_MY_RECOHARGE_DETAIL = UUID.randomUUID().toString();
    /**我的页面*/
    public static final String COMMAND_MY = UUID.randomUUID().toString();
    /**我的验证密码解除手势*/
    public static final String COMMAND_MY_VERIFY_PASSWORD = UUID.randomUUID().toString();
    /**我的零钱包-消费记录*/
    public static final String COMMAND_MY_RECORDS_HISTORY = UUID.randomUUID().toString();
    /**我的零钱包-充值记录*/
    public static final String COMMAND_MY_RECHARGE_HISTORY = UUID.randomUUID().toString();
    /**我的零钱包-累计收益*/
    public static final String COMMAND_MY_PROFIT_HISTORY = UUID.randomUUID().toString();
    /**客户端未读个人消息*/
    public  static  final  String BUS_UNREAD_COUNT =UUID.randomUUID().toString();

    public MyEvent(String command) {
        super(command);
    }

    public MyEvent(String command, Object message) {
        super(command, message);
    }
}
