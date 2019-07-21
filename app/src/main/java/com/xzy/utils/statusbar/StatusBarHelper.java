package com.xzy.utils.statusbar;

import android.app.Activity;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 状态栏设置工具类。
 *
 * @author xzy
 */
@SuppressWarnings("all")
public class StatusBarHelper {
    @IntDef({
            OTHER,
            MIUI,
            FLYME,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface SystemType {

    }

    public static final int OTHER = -1;
    public static final int MIUI = 1;
    public static final int FLYME = 2;


    /**
     * 设置状态栏黑色字体图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @param activity Activity
     * @return int  1:MIUI 2:Flyme 3:android6.0
     */
    public static int statusBarLightMode(Activity activity) {
        @SystemType int result = 0;
        if (new MiuiHelper().setStatusBarLightMode(activity, true)) {
            result = MIUI;
        } else if (new FlymeHelper().setStatusBarLightMode(activity, true)) {
            result = FLYME;
        }
        return result;
    }

    /**
     * 已知系统类型时，设置状态栏黑色字体图标。
     * 适配 4.4 以上版本 MIUI6、Flyme 和 6.0 以上版本其他 Android
     *
     * @param activity Activity
     * @param type     1:MIUI 2:Flyme 3:android6.0
     */
    public static void statusBarLightMode(Activity activity, @SystemType int type) {
        statusBarMode(activity, type, true);

    }

    /**
     * 清除 MIUI 或 flyme 或 6.0 以上版本状态栏黑色字体
     *
     * @param activity Activity
     * @param type     1:MIUI 2:Flyme 3:android6.0
     */
    public static void statusBarDarkMode(Activity activity, @SystemType int type) {
        statusBarMode(activity, type, false);
    }

    private static void statusBarMode(Activity activity,
                                      @SystemType int type, boolean isFontColorDark) {
        if (type == MIUI) {
            new MiuiHelper().setStatusBarLightMode(activity, isFontColorDark);
        } else if (type == FLYME) {
            new FlymeHelper().setStatusBarLightMode(activity, isFontColorDark);
        }
    }

}
