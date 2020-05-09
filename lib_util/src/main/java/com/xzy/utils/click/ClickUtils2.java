package com.xzy.utils.click;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;


/**
 * 参考：https://www.cnblogs.com/liupengfei005257/p/7448609.html
 * Android 之有效防止按钮多次重复点击的方法
 * <p>
 * 调用：
 * if (!ButtonUtils.isFastDoubleClick(viewId)) {
 * //写你相关操作即可
 * }
 *
 * @author xzy
 */
@SuppressWarnings("unused")
public class ClickUtils2 {
    private static long lastClickTime = 0;
    private static long DIFF = 1000;
    private static int lastButtonId = -1;

    /**
     * 判断两次点击的间隔，如果小于1000，则认为是多次无效点击
     *
     * @return
     */
    public static boolean isFastDoubleClick() {
        return isFastDoubleClick(-1, DIFF);
    }

    /**
     * 判断两次点击的间隔，如果小于1000，则认为是多次无效点击
     *
     * @return
     */
    public static boolean isFastDoubleClick(int buttonId) {
        return isFastDoubleClick(buttonId, DIFF);
    }

    /**
     * 判断两次点击的间隔，如果小于diff，则认为是多次无效点击
     *
     * @param diff
     * @return
     */
    public static boolean isFastDoubleClick(int buttonId, long diff) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (lastButtonId == buttonId && lastClickTime > 0 && timeD < diff) {
            Log.v("isFastDoubleClick", "短时间内按钮多次触发");
            return true;
        }
        lastClickTime = time;
        lastButtonId = buttonId;
        return false;
    }
}
