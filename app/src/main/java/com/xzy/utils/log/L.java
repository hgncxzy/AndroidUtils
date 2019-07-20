package com.xzy.utils.log;

import android.util.Log;


/**
 * Log统一管理类
 */
@SuppressWarnings("unused")
public class L {

    // 是否需要打印 log，可以在 application 的 onCreate 函数里面初始化
    public static boolean isDebug = true;
    private static final String TAG = "xzy";

    private L() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("L class cannot be instantiated");
    }

    // 下面四个是默认 tag 的函数
    public static void i(String msg) {
        if (isDebug) Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (isDebug) Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (isDebug) Log.e(TAG, msg);
    }

    public static void v(String msg) {
        if (isDebug) Log.v(TAG, msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug) Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug) Log.i(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug) Log.i(tag, msg);
    }
}