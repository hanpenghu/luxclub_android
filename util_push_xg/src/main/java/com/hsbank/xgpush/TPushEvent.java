package com.hsbank.xgpush;

import com.hsbank.util.project.util.BaseProjectEvent;

import java.util.UUID;

/**
 * Created by chen_liuchun on 2016/4/20.
 */
public class TPushEvent extends BaseProjectEvent {
    public TPushEvent(String command, Object message) {
        super(command, message);
    }

    public TPushEvent(String command) {
        super(command);
    }

    public static final String COMMAND_TPUSH = UUID.randomUUID().toString();
}
