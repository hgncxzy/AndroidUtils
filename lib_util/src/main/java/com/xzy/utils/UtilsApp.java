package com.xzy.utils;

import android.app.Application;

import androidx.multidex.MultiDex;

import com.xzy.utils.common.Utils;

public class UtilsApp extends Application {
    public static UtilsApp INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        Utils.init(this);
        MultiDex.install(this);
    }
}
