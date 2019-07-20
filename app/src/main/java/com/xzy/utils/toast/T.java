package com.xzy.utils.toast;
import android.content.Context;
import android.widget.Toast;

/**
 * Toast 统一管理类 。
 */
@SuppressWarnings("unused")
public class T {

    // 是否需要打印 log，可以在 application 的 onCreate 函数里面初始化
    public static boolean isShow = true;

    private T() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 短时间显示 Toast
     *
     * @param context  context
     * @param message  message
     */
    public static void showShort(Context context, CharSequence message) {
        if (isShow) Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示 Toast
     *
     * @param context context
     * @param message message
     */
    public static void showShort(Context context, int message) {
        if (isShow) Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示 Toast
     *
     * @param context context
     * @param message message
     */
    public static void showLong(Context context, CharSequence message) {
        if (isShow) Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context context
     * @param stringId 字符串资源 id
     */
    public static void showLong(Context context, int stringId) {
        if (isShow) Toast.makeText(context, stringId, Toast.LENGTH_LONG).show();
    }
}
