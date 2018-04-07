package com.hsbank.luxclub.view.manager.my.event;

import com.hsbank.util.project.util.BaseProjectEvent;

import java.util.UUID;

/**
 * Created by pc on 2016/4/5.
 */
public class MyEvent extends BaseProjectEvent {
    /**个人信息*/
    public static final String COMMAND_MANAGER_MESSAGE = UUID.randomUUID().toString();
    /**未读个人消息*/
    public  static  final  String COMMAND_MANAGER_MESSAGE_UNREADCOUNT=UUID.randomUUID().toString();
    /**退出*/
    public  static  final  String COMMAND_MANAGER_EXIT=UUID.randomUUID().toString();
    public MyEvent(String command, Object message) {
        super(command, message);
    }

    public MyEvent(String command) {
        super(command);
    }
}
