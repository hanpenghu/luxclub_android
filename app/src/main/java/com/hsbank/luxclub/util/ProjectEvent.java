package com.hsbank.luxclub.util;

import com.hsbank.util.project.util.BaseProjectEvent;

import java.util.UUID;

/**
 * 工程_事件_基础类
 * Created by Administrator on 2016/1/13.
 */
public class ProjectEvent extends BaseProjectEvent {
    /**演示事件*/
    public static final String DEMO = UUID.randomUUID().toString();
    
    public ProjectEvent(String command, Object message) {
        super(command, message);
    }

    public ProjectEvent(String command) {
        super(command);
    }
}
