package com.hsbank.luxclub.view.main.joy.event;

import com.hsbank.util.project.util.BaseProjectEvent;

import java.util.UUID;

/**
 * Created by chen_liuchun on 2016/3/16.
 */
public class JoyEvent extends BaseProjectEvent {

    public static final String COMMAND_FINISH = UUID.randomUUID().toString();
    // 场所 列表
    public static final String COMMAND_JOY_LIST = UUID.randomUUID().toString();

    public static final String COMMAND_MODEL_LIST = UUID.randomUUID().toString();
    // 场所 详情
    public static final String COMMAND_JOY_DETAIL = UUID.randomUUID().toString();
    // 场所 包房预订
    public static final String COMMAND_JOY_SUBSCRIBE = UUID.randomUUID().toString();
    // 城市列表
    public static final String COMMAND_JOY_AREALIST = UUID.randomUUID().toString();
    // 热门城市列表
    public static final String COMMAND_CITY_HOT = UUID.randomUUID().toString();
    // 城市定位
    public static final String COMMAND_CITY_LOCATION = UUID.randomUUID().toString();
    // 搜索场所列表
    public static final String COMMAND_SITE_SEARCH = UUID.randomUUID().toString();
    // 热门场所列表
    public static final String COMMAND_SITE_HOT = UUID.randomUUID().toString();
    // 场所选择
    public static final String COMMAND_SITE_STORE = UUID.randomUUID().toString();
    // 城市选择
    public static final String COMMAND_SITE_CHANGE = UUID.randomUUID().toString();


    public JoyEvent(String command) {
        super(command);
    }

    public JoyEvent(String command, Object message) {
        super(command, message);
    }

}
