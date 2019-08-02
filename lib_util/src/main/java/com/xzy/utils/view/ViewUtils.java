package com.xzy.utils.view;

import android.view.View;
import android.view.ViewGroup;

import com.xzy.utils.common.Utils;

/**
 * View 相关的工具类。
 * 参考 https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/
 * java/com/blankj/utilcode/util/ViewUtils.java
 *
 * @author xzy
 */
@SuppressWarnings("all")
public class ViewUtils {

    /**
     * Set the enabled state of this view.
     *
     * @param view    The view.
     * @param enabled True to enabled, false otherwise.
     */
    public static void setViewEnabled(View view, boolean enabled) {
        setViewEnabled(view, enabled, (View) null);
    }

    /**
     * Set the enabled state of this view.
     *
     * @param view     The view.
     * @param enabled  True to enabled, false otherwise.
     * @param excludes The excludes.
     */
    public static void setViewEnabled(View view, boolean enabled, View... excludes) {
        if (view == null) return;
        if (excludes != null) {
            for (View exclude : excludes) {
                if (view == exclude) return;
            }
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                setViewEnabled(viewGroup.getChildAt(i), enabled, excludes);
            }
        }
        view.setEnabled(enabled);
    }

    /**
     * @param runnable The runnable
     */
    public static void runOnUiThread(final Runnable runnable) {
        Utils.runOnUiThread(runnable);
    }

    /**
     * @param runnable    The runnable.
     * @param delayMillis The delay (in milliseconds) until the Runnable will be executed.
     */
    public static void runOnUiThreadDelayed(final Runnable runnable, long delayMillis) {
        Utils.runOnUiThreadDelayed(runnable, delayMillis);
    }

    /**
     * 用于解决ScrollView嵌套ListView/GridView/WebView/RecyclerView等无法置顶问题
     *
     * @param view ScrollView嵌套的跟视图
     */
    public static void fixScrollViewTopping(View view) {
        view.setFocusable(false);
        ViewGroup viewGroup = null;
        if (view instanceof ViewGroup) {
            viewGroup = (ViewGroup) view;
        }
        if (viewGroup == null) {
            return;
        }
        for (int i = 0, n = viewGroup.getChildCount(); i < n; i++) {
            View childAt = viewGroup.getChildAt(i);
            childAt.setFocusable(false);
            if (childAt instanceof ViewGroup) {
                fixScrollViewTopping(childAt);
            }
        }
    }

}