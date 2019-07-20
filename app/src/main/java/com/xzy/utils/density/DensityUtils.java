package com.xzy.utils.density;

import android.content.Context;
import android.util.TypedValue;

/**
 * 常用单位转换的辅助类。
 */
@SuppressWarnings("unused")
public class DensityUtils {

    private DensityUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * dp 转 px.
     */
    public static int dp2px(Context context, float dp) {
        //获取设备密度
        float density = context.getResources().getDisplayMetrics().density;
        //4.3, 4.9, 加0.5是为了四舍五入
        return (int) (dp * density + 0.5f);
    }

    /**
     * px 转 dp.
     */
    public static float px2dp(Context context, int px) {
        //获取设备密度
        float density = context.getResources().getDisplayMetrics().density;
        return px / density;
    }

    /**
     * sp 转 px
     *
     * @param context context
     * @param spValue spValue
     * @return int
     */
    public static int sp2px(Context context, float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spValue, context.getResources().getDisplayMetrics());
    }

    /**
     * px 转 dp
     *
     * @param context context
     * @param pxValue pxValue
     * @return float
     */
    public static float px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxValue / scale);
    }


    /**
     * px 转 sp
     *
     * @param context context
     * @param pxValue pxValue
     * @return float
     */
    public static float px2sp(Context context, float pxValue) {
        return (pxValue / context.getResources().getDisplayMetrics().scaledDensity);
    }

}


