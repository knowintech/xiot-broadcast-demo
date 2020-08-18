package com.knowin.iot.logger.impl;

import android.util.Log;

import cn.geekcity.xiot.logger.XLogger;

public class AndroidLoggerImpl implements XLogger {

    private String module;

    public AndroidLoggerImpl(String module) {
        this.module = module + ".";
    }

    @Override
    public void d(String tag, String msg) {
        Log.d(module + tag, msg);
    }

    @Override
    public void i(String tag, String msg) {
        Log.i(module + tag, msg);
    }

    @Override
    public void v(String tag, String msg) {
        Log.v(module + tag, msg);
    }

    @Override
    public void w(String tag, String msg) {
        Log.w(module + tag, msg);
    }

    @Override
    public void e(String tag, String msg) {
        Log.e(module + tag, msg);
    }
}
