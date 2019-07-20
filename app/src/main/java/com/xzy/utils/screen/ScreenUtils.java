package com.xzy.utils.screen;

import android.content.Context;

/**
 * 屏幕相关工具类。
 */
public class ScreenUtils {

    /**
     * dp 转 px.
     */
    public static int dp2px(Context context, float dp) {
        //获取设备密度
        float density = context.getResources().getDisplayMetrics().density;
        //4.3, 4.9, 加0.5是为了四舍五入
        int px = (int) (dp * density + 0.5f);
        return px;
    }

    /**
     * px 转 dp.
     */
    public static float px2dp(Context context, int px) {
        //获取设备密度
        float density = context.getResources().getDisplayMetrics().density;
        return px / density;
    }
}
