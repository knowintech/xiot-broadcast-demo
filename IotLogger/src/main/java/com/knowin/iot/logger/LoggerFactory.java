package com.knowin.iot.logger;

import com.knowin.iot.logger.impl.AndroidLoggerImpl;

import cn.geekcity.xiot.logger.XLogger;

public class LoggerFactory {

    private LoggerFactory() {
    }

    public static XLogger create(String module) {
        return new AndroidLoggerImpl(module);
    }
}
