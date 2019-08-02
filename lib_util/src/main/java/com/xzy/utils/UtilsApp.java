package com.xzy.utils;

import android.app.Application;

import com.xzy.utils.common.Utils;

public class UtilsApp extends Application {
    public static UtilsApp INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        INSTANCE = this;
    }
}
