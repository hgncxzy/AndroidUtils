package com.xzy.utils.statusbar;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
/**
 * Flyme 状态栏设置。
 * @author xzy
 */
@SuppressWarnings("ALL")
public class FlymeHelper implements IHelper {

    @Override
    public boolean setStatusBarLightMode(Activity activity, boolean isFontColorDark) {
        Window window = activity.getWindow();
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (isFontColorDark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
