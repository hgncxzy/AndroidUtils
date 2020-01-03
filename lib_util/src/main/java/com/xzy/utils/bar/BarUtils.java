package com.xzy.utils.bar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import androidx.drawerlayout.widget.DrawerLayout;

import com.xzy.utils.UtilsApp;
import com.xzy.utils.common.Utils;

import java.lang.reflect.Method;

import static android.Manifest.permission.EXPAND_STATUS_BAR;

/**
 * 栏相关工具类。
 * 参考 https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main
 * /java/com/blankj/utilcode/util/BarUtils.java
 *
 * @author xzy
 */
@SuppressWarnings("all")
@SuppressLint("ObsoleteSdkInt")
public final class BarUtils {

    ///////////////////////////////////////////////////////////////////////////
    // status bar
    ///////////////////////////////////////////////////////////////////////////
    private static final String TAG_STATUS_BAR = "StatusBar";
    private static final String TAG_NAVIGATION_BAR = "NavigationBar";
    private static final String TAG_OFFSET = "TAG_OFFSET";
    /* 无效的颜色值 */
    private static final int INVALID_VAL = -1;
    /* 默认的状态栏颜色 */
    private static final int DEFAULT_STATUS_COLOR = 0x10000000;
    /* 默认的底部导航栏颜色 */
    private static final int DEFAULT_NAVIGATION_COLOR = Color.TRANSPARENT;

    private static final int KEY_OFFSET = -123;

    private BarUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Return the status bar's height.
     *
     * @return the status bar's height
     */
    public static int getStatusBarHeight() {
        Resources resources = Resources.getSystem();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen",
                "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * Set the status bar's visibility.
     *
     * @param activity  The activity.
     * @param isVisible True to set status bar visible, false otherwise.
     */
    public static void setStatusBarVisibility(@NonNull final Activity activity,
                                              final boolean isVisible) {
        setStatusBarVisibility(activity.getWindow(), isVisible);
    }

    /**
     * Set the status bar's visibility.
     *
     * @param window    The window.
     * @param isVisible True to set status bar visible, false otherwise.
     */
    public static void setStatusBarVisibility(@NonNull final Window window,
                                              final boolean isVisible) {
        if (isVisible) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            showStatusBarView(window);
            addMarginTopEqualStatusBarHeight(window);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            hideStatusBarView(window);
            subtractMarginTopEqualStatusBarHeight(window);
        }
    }

    /**
     * Return whether the status bar is visible.
     *
     * @param activity The activity.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isStatusBarVisible(@NonNull final Activity activity) {
        int flags = activity.getWindow().getAttributes().flags;
        return (flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == 0;
    }

    /**
     * Set the status bar's light mode.
     *
     * @param activity    The activity.
     * @param isLightMode True to set status bar light mode, false otherwise.
     */
    public static void setStatusBarLightMode(@NonNull final Activity activity,
                                             final boolean isLightMode) {
        setStatusBarLightMode(activity.getWindow(), isLightMode);
    }

    /**
     * Set the status bar's light mode.
     *
     * @param window      The window.
     * @param isLightMode True to set status bar light mode, false otherwise.
     */
    public static void setStatusBarLightMode(@NonNull final Window window,
                                             final boolean isLightMode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = window.getDecorView();
            if (decorView != null) {
                int vis = decorView.getSystemUiVisibility();
                if (isLightMode) {
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decorView.setSystemUiVisibility(vis);
            }
        }
    }

    /**
     * Is the status bar light mode.
     *
     * @param activity The activity.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isStatusBarLightMode(@NonNull final Activity activity) {
        return isStatusBarLightMode(activity.getWindow());
    }

    /**
     * Is the status bar light mode.
     *
     * @param window The window.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isStatusBarLightMode(@NonNull final Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = window.getDecorView();
            if (decorView != null) {
                int vis = decorView.getSystemUiVisibility();
                return (vis & View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) != 0;
            }
        }
        return false;
    }

    /**
     * Add the top margin size equals status bar's height for view.
     *
     * @param view The view.
     */
    public static void addMarginTopEqualStatusBarHeight(@NonNull View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        view.setTag(TAG_OFFSET);
        Object haveSetOffset = view.getTag(KEY_OFFSET);
        if (haveSetOffset != null && (Boolean) haveSetOffset) {
            return;
        }
        MarginLayoutParams layoutParams = (MarginLayoutParams) view.getLayoutParams();
        layoutParams.setMargins(layoutParams.leftMargin,
                layoutParams.topMargin + getStatusBarHeight(),
                layoutParams.rightMargin,
                layoutParams.bottomMargin);
        view.setTag(KEY_OFFSET, true);
    }

    /**
     * Subtract the top margin size equals status bar's height for view.
     *
     * @param view The view.
     */
    public static void subtractMarginTopEqualStatusBarHeight(@NonNull View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        Object haveSetOffset = view.getTag(KEY_OFFSET);
        if (haveSetOffset == null || !(Boolean) haveSetOffset) {
            return;
        }
        MarginLayoutParams layoutParams = (MarginLayoutParams) view.getLayoutParams();
        layoutParams.setMargins(layoutParams.leftMargin,
                layoutParams.topMargin - getStatusBarHeight(),
                layoutParams.rightMargin,
                layoutParams.bottomMargin);
        view.setTag(KEY_OFFSET, false);
    }

    private static void addMarginTopEqualStatusBarHeight(final Window window) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        View withTag = window.getDecorView().findViewWithTag(TAG_OFFSET);
        if (withTag == null) {
            return;
        }
        addMarginTopEqualStatusBarHeight(withTag);
    }

    private static void subtractMarginTopEqualStatusBarHeight(final Window window) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        View withTag = window.getDecorView().findViewWithTag(TAG_OFFSET);
        if (withTag == null) {
            return;
        }
        subtractMarginTopEqualStatusBarHeight(withTag);
    }

    /**
     * Set the status bar's color.
     *
     * @param activity The activity.
     * @param color    The status bar's color.
     */
    public static View setStatusBarColor(@NonNull final Activity activity,
                                         @ColorInt final int color) {
        return setStatusBarColor(activity, color, false);
    }

    /**
     * Set the status bar's color.
     *
     * @param activity The activity.
     * @param color    The status bar's color.
     * @param isDecor  True to add fake status bar in DecorView,
     *                 false to add fake status bar in ContentView.
     */
    public static View setStatusBarColor(@NonNull final Activity activity,
                                         @ColorInt final int color,
                                         final boolean isDecor) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return null;
        }
        transparentStatusBar(activity);
        return applyStatusBarColor(activity, color, isDecor);
    }

    /**
     * Set the status bar's color.
     *
     * @param fakeStatusBar The fake status bar view.
     * @param color         The status bar's color.
     */
    public static void setStatusBarColor(@NonNull final View fakeStatusBar,
                                         @ColorInt final int color) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        Activity activity = getActivityByView(fakeStatusBar);
        if (activity == null) {
            return;
        }
        transparentStatusBar(activity);
        fakeStatusBar.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams layoutParams = fakeStatusBar.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = getStatusBarHeight();
        fakeStatusBar.setBackgroundColor(color);
    }

    /**
     * Set the custom status bar.
     *
     * @param fakeStatusBar The fake status bar view.
     */
    public static void setStatusBarCustom(@NonNull final View fakeStatusBar) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        Activity activity = getActivityByView(fakeStatusBar);
        if (activity == null) {
            return;
        }
        transparentStatusBar(activity);
        fakeStatusBar.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams layoutParams = fakeStatusBar.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight()
            );
            fakeStatusBar.setLayoutParams(layoutParams);
        } else {
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = getStatusBarHeight();
        }
    }

    /**
     * Set the status bar's color for DrawerLayout.
     * <p>DrawLayout must add {@code android:fitsSystemWindows="true"}</p>
     *
     * @param drawer        The DrawLayout.
     * @param fakeStatusBar The fake status bar view.
     * @param color         The status bar's color.
     */
    public static void setStatusBarColor4Drawer(@NonNull final DrawerLayout drawer,
                                                @NonNull final View fakeStatusBar,
                                                @ColorInt final int color) {
        setStatusBarColor4Drawer(drawer, fakeStatusBar, color, false);
    }

    /**
     * Set the status bar's color for DrawerLayout.
     * <p>DrawLayout must add {@code android:fitsSystemWindows="true"}</p>
     *
     * @param drawer        The DrawLayout.
     * @param fakeStatusBar The fake status bar view.
     * @param color         The status bar's color.
     * @param isTop         True to set DrawerLayout at the top layer, false otherwise.
     */
    public static void setStatusBarColor4Drawer(@NonNull final DrawerLayout drawer,
                                                @NonNull final View fakeStatusBar,
                                                @ColorInt final int color,
                                                final boolean isTop) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        Activity activity = getActivityByView(fakeStatusBar);
        if (activity == null) {
            return;
        }
        transparentStatusBar(activity);
        drawer.setFitsSystemWindows(false);
        setStatusBarColor(fakeStatusBar, color);
        for (int i = 0, count = drawer.getChildCount(); i < count; i++) {
            drawer.getChildAt(i).setFitsSystemWindows(false);
        }
        if (isTop) {
            hideStatusBarView(activity);
        } else {
            setStatusBarColor(activity, color, false);
        }
    }

    private static View applyStatusBarColor(final Activity activity,
                                            final int color,
                                            boolean isDecor) {
        ViewGroup parent = isDecor ?
                (ViewGroup) activity.getWindow().getDecorView() :
                (ViewGroup) activity.findViewById(android.R.id.content);
        View fakeStatusBarView = parent.findViewWithTag(TAG_STATUS_BAR);
        if (fakeStatusBarView != null) {
            if (fakeStatusBarView.getVisibility() == View.GONE) {
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
            fakeStatusBarView.setBackgroundColor(color);
        } else {
            fakeStatusBarView = createStatusBarView(activity, color);
            parent.addView(fakeStatusBarView);
        }
        return fakeStatusBarView;
    }

    private static void hideStatusBarView(final Activity activity) {
        hideStatusBarView(activity.getWindow());
    }

    private static void hideStatusBarView(final Window window) {
        ViewGroup decorView = (ViewGroup) window.getDecorView();
        View fakeStatusBarView = decorView.findViewWithTag(TAG_STATUS_BAR);
        if (fakeStatusBarView == null) {
            return;
        }
        fakeStatusBarView.setVisibility(View.GONE);
    }

    private static void showStatusBarView(final Window window) {
        ViewGroup decorView = (ViewGroup) window.getDecorView();
        View fakeStatusBarView = decorView.findViewWithTag(TAG_STATUS_BAR);
        if (fakeStatusBarView == null) {
            return;
        }
        fakeStatusBarView.setVisibility(View.VISIBLE);
    }

    private static View createStatusBarView(final Activity activity,
                                            final int color) {
        View statusBarView = new View(activity);
        statusBarView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight()));
        statusBarView.setBackgroundColor(color);
        statusBarView.setTag(TAG_STATUS_BAR);
        return statusBarView;
    }

    private static void transparentStatusBar(final Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int vis = window.getDecorView().getSystemUiVisibility()
                        & View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                window.getDecorView().setSystemUiVisibility(option | vis);
            } else {
                window.getDecorView().setSystemUiVisibility(option);
            }
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // action bar
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Return the action bar's height.
     *
     * @return the action bar's height
     */
    public static int getActionBarHeight() {
        TypedValue tv = new TypedValue();
        if (Utils.getApp().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv,
                true)) {
            return TypedValue.complexToDimensionPixelSize(
                    tv.data, Utils.getApp().getResources().getDisplayMetrics()
            );
        }
        return 0;
    }

    ///////////////////////////////////////////////////////////////////////////
    // notification bar
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Set the notification bar's visibility.
     * <p>Must hold {@code <uses-permission android:name="android.permission.EXPAND_STATUS_BAR" />}
     * </p>
     *
     * @param isVisible True to set notification bar visible, false otherwise.
     */
    @RequiresPermission(EXPAND_STATUS_BAR)
    public static void setNotificationBarVisibility(final boolean isVisible) {
        String methodName;
        if (isVisible) {
            methodName = (Build.VERSION.SDK_INT <= 16) ? "expand" : "expandNotificationsPanel";
        } else {
            methodName = (Build.VERSION.SDK_INT <= 16) ? "collapse" : "collapsePanels";
        }
        invokePanels(methodName);
    }

    private static void invokePanels(final String methodName) {
        try {
            @SuppressLint("WrongConstant")
            Object service = Utils.getApp().getSystemService("statusbar");
            @SuppressLint("PrivateApi")
            Class<?> statusBarManager = Class.forName("android.app.StatusBarManager");
            Method expand = statusBarManager.getMethod(methodName);
            expand.invoke(service);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // navigation bar
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Return the navigation bar's height.
     *
     * @return the navigation bar's height
     */
    public static int getNavBarHeight() {
        Resources res = Resources.getSystem();
        int resourceId = res.getIdentifier("navigation_bar_height", "dimen",
                "android");
        if (resourceId != 0) {
            return res.getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }
    }

    /**
     * Set the navigation bar's visibility.
     *
     * @param activity  The activity.
     * @param isVisible True to set navigation bar visible, false otherwise.
     */
    public static void setNavBarVisibility(@NonNull final Activity activity, boolean isVisible) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        setNavBarVisibility(activity.getWindow(), isVisible);

    }

    /**
     * Set the navigation bar's visibility.
     *
     * @param window    The window.
     * @param isVisible True to set navigation bar visible, false otherwise.
     */
    public static void setNavBarVisibility(@NonNull final Window window, boolean isVisible) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        final ViewGroup decorView = (ViewGroup) window.getDecorView();
        for (int i = 0, count = decorView.getChildCount(); i < count; i++) {
            final View child = decorView.getChildAt(i);
            final int id = child.getId();
            if (id != View.NO_ID) {
                String resourceEntryName = Utils.getApp()
                        .getResources()
                        .getResourceEntryName(id);
                if ("navigationBarBackground".equals(resourceEntryName)) {
                    child.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
                }
            }
        }
        final int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        if (isVisible) {
            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & ~uiOptions);
        } else {
            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | uiOptions);
        }
    }

    /**
     * Return whether the navigation bar visible.
     * <p>Call it in onWindowFocusChanged will get right result.</p>
     *
     * @param activity The activity.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isNavBarVisible(@NonNull final Activity activity) {
        return isNavBarVisible(activity.getWindow());
    }

    /**
     * Return whether the navigation bar visible.
     * <p>Call it in onWindowFocusChanged will get right result.</p>
     *
     * @param window The window.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isNavBarVisible(@NonNull final Window window) {
        boolean isVisible = false;
        ViewGroup decorView = (ViewGroup) window.getDecorView();
        for (int i = 0, count = decorView.getChildCount(); i < count; i++) {
            final View child = decorView.getChildAt(i);
            final int id = child.getId();
            if (id != View.NO_ID) {
                String resourceEntryName = Utils.getApp()
                        .getResources()
                        .getResourceEntryName(id);
                if ("navigationBarBackground".equals(resourceEntryName)
                        && child.getVisibility() == View.VISIBLE) {
                    isVisible = true;
                    break;
                }
            }
        }
        if (isVisible) {
            int visibility = decorView.getSystemUiVisibility();
            isVisible = (visibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0;
        }
        return isVisible;
    }

    /**
     * Set the navigation bar's color.
     *
     * @param activity The activity.
     * @param color    The navigation bar's color.
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setNavBarColor(@NonNull final Activity activity, @ColorInt final int color) {
        setNavBarColor(activity.getWindow(), color);
    }

    /**
     * Set the navigation bar's color.
     *
     * @param window The window.
     * @param color  The navigation bar's color.
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setNavBarColor(@NonNull final Window window, @ColorInt final int color) {
        window.setNavigationBarColor(color);
    }

    /**
     * Return the color of navigation bar.
     *
     * @param activity The activity.
     * @return the color of navigation bar
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public static int getNavBarColor(@NonNull final Activity activity) {
        return getNavBarColor(activity.getWindow());
    }

    /**
     * Return the color of navigation bar.
     *
     * @param window The window.
     * @return the color of navigation bar
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public static int getNavBarColor(@NonNull final Window window) {
        return window.getNavigationBarColor();
    }

    /**
     * Return whether the navigation bar visible.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    @SuppressLint("ObsoleteSdkInt")
    public static boolean isSupportNavBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            WindowManager wm = (WindowManager) Utils.getApp()
                    .getSystemService(Context.WINDOW_SERVICE);
            if (wm == null) {
                return false;
            }
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            return realSize.y != size.y || realSize.x != size.x;
        }
        boolean menu = ViewConfiguration.get(Utils.getApp()).hasPermanentMenuKey();
        boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        return !menu && !back;
    }

    private static Activity getActivityByView(@NonNull final View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        Log.e("BarUtils", "the view's Context is not an Activity.");
        return null;
    }

    /***********************************************************************************************
     ****  沉浸式系统栏（状态栏与导航栏），着色模式，不可全透明（sdk >= 4.4）
     **********************************************************************************************/
    /**
     * 设置状态栏和底部导航栏的显示方式
     *
     * @param activity        activity
     * @param fitSystemWindow {@code true}: 内容不会显示到状态栏和导航栏上<br>{@code false}:
     *                                    内容显示到状态栏和导航栏上
     * @param clipToPadding   {@code true}: 裁剪 padding 区域，padding 区域不可使用<br>{@code false}:
     *                                   不裁剪 padding 区域，padding 区域可使用
     */
    public static void setDisplayOption(@NonNull Activity activity, boolean fitSystemWindow,
                                        boolean clipToPadding) {
        // 获取根布局
        ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content))
                .getChildAt(0);
        rootView.setClipToPadding(clipToPadding);
        rootView.setFitsSystemWindows(fitSystemWindow);
    }

    /**
     * 设置顶部状态栏
     *
     * @param activity
     * @param color    设置顶部状态栏的颜色
     */
    public static void setupStatusBar(@NonNull Activity activity, @ColorInt int color) {
        int statusHeight = getStatusBarHeight();
        int statusColor = DEFAULT_STATUS_COLOR;
        if (color != INVALID_VAL) {
            statusColor = color;
        }
        // 设置状态栏透明
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        // 防止重复添加 statusBarView
        removeStatusBarView(decorView);
        // 绘制一个和状态栏一样高的矩形 View
        View statusBarView = createStatusBarView(activity, statusHeight, statusColor);
        // 添加 statusBarView 到整个Window的最顶层布局中,这里的 statusBarView 只是作为状态栏的背景，
        // 它的 visible 不能影响到状态栏的 visible
        decorView.addView(statusBarView);
    }

    /**
     * 绘制一个和状态栏一样高的矩形View
     *
     * @param activity
     * @param statusHeight 绘制的矩形的高度
     * @param statusColor  绘制的矩形的颜色
     * @return 绘制的矩形 View
     */
    private static View createStatusBarView(@NonNull Activity activity, int statusHeight,
                                            @ColorInt int statusColor) {
        View statusBarView = new View(activity);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout
                .LayoutParams.MATCH_PARENT, statusHeight);
        params.gravity = Gravity.TOP;
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(statusColor);
        statusBarView.setTag(TAG_STATUS_BAR);
        return statusBarView;
    }

    /**
     * 移除已经存在的 statusBarView
     *
     * @param decorView
     */
    private static void removeStatusBarView(@NonNull ViewGroup decorView) {
        View statusBarView = decorView.findViewWithTag(TAG_STATUS_BAR);
        if (statusBarView != null) {
            decorView.removeView(statusBarView);
        }
    }

    /**
     * 设置状态栏的透明度
     *
     * @param activity
     * @param alpha    透明度（0-1）
     */
    public static void setStatusBarAlpha(@NonNull Activity activity, @FloatRange(from = 0, to = 1)
            float alpha) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View statusView = decorView.findViewWithTag(TAG_STATUS_BAR);
        if (statusView != null) {
            statusView.setAlpha(alpha);
        }
    }

    /**
     * 设置状态栏的显示和隐藏
     *
     * @param activity
     * @param isShow   {@code true}: 显示<br>{@code false}: 隐藏
     */
    public static void showStatusBar(@NonNull Activity activity, boolean isShow) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View statusView = decorView.findViewWithTag(TAG_STATUS_BAR);
        if (isShow) {
            if (statusView != null) {
                statusView.setVisibility(View.VISIBLE);
            }
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            if (statusView != null) {
                statusView.setVisibility(View.GONE);
            }
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    /**
     * 设置底部导航栏
     *
     * @param activity
     * @param color    底部导航栏的颜色
     */
    public static void setupNavBar(@NonNull Activity activity, @ColorInt int color) {
        if (hasNavigationBar()) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            int navHeight = getNavBarHeight();
            int navColor = DEFAULT_NAVIGATION_COLOR;
            if (color != INVALID_VAL) {
                navColor = color;
            }
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            removeNavBarView(decorView);
            View navBarView = createNavBarView(activity, navHeight, navColor);
            decorView.addView(navBarView);
        }
    }

    /**
     * 绘制一个和底部导航栏一样高的矩形 View
     *
     * @param activity
     * @param navHeight 绘制的矩形的高度
     * @param navColor  绘制的矩形的颜色
     * @return 绘制的矩形 View
     */
    private static View createNavBarView(@NonNull Activity activity, int navHeight,
                                         @ColorInt int navColor) {
        View navBarView = new View(activity);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout
                .LayoutParams.MATCH_PARENT, navHeight);
        params.gravity = Gravity.BOTTOM;
        navBarView.setLayoutParams(params);
        navBarView.setBackgroundColor(navColor);
        navBarView.setTag(TAG_NAVIGATION_BAR);
        return navBarView;
    }

    /**
     * 移除已经存在的 navBarView
     *
     * @param decorView
     */
    private static void removeNavBarView(@NonNull ViewGroup decorView) {
        View navBarView = decorView.findViewWithTag(TAG_NAVIGATION_BAR);
        if (navBarView != null) {
            decorView.removeView(navBarView);
        }
    }

    /**
     * 设置底部导航栏的透明度
     *
     * @param activity
     * @param alpha    透明度（0-1）
     */
    public static void setNavBarAlpha(@NonNull Activity activity, @FloatRange(from = 0, to = 1)
            float alpha) {
        if (hasNavigationBar()) {
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            View navBarView = decorView.findViewWithTag(TAG_NAVIGATION_BAR);
            if (navBarView != null) {
                navBarView.setAlpha(alpha);
            }
        }
    }

    /**
     * 设置底部导航栏的显示和隐藏
     *
     * @param activity activity
     * @param isShow   {@code true}: 显示<br>{@code false}: 隐藏
     */
    public static void showNavBar(@NonNull Activity activity, boolean isShow) {
        if (hasNavigationBar()) {
            View decorView = activity.getWindow().getDecorView();
            if (isShow) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            } else {
                decorView.setSystemUiVisibility(View.INVISIBLE);
            }
        }
    }

    /***********************************************************************************************
     ****  沉浸式系统栏（状态栏与导航栏），全透明模式，（sdk >= 5.0）
     **********************************************************************************************/
    /**
     * 设置系统栏全透明
     *
     * @param activity
     * @param statusBar 状态栏是否全透明
     * @param navBar    导航栏是否全透明
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void fullTransparentBar(Activity activity, boolean statusBar, boolean navBar) {
        Window window = activity.getWindow();
        window.clearFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        if (statusBar && navBar) {
            window.getDecorView().setSystemUiVisibility(
                    // 全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity 顶端布局部分会被状态遮住
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            // 隐藏导航栏
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            // 防止系统栏隐藏时内容区域大小发生变化
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        } else if (statusBar && !navBar) {
            window.getDecorView().setSystemUiVisibility(
                    // 全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity 顶端布局部分会被状态遮住
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            // 防止系统栏隐藏时内容区域大小发生变化
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (!statusBar && navBar) {
            window.getDecorView().setSystemUiVisibility(
                    // 全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity 顶端布局部分会被状态遮住
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            // 隐藏导航栏
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            // 防止系统栏隐藏时内容区域大小发生变化
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 判断是否有底部导航栏
     *
     * @return {@code true}: 有底部导航栏<br>{@code false}: 没有底部导航栏
     */
    public static boolean hasNavigationBar() {
        boolean hasNavigationBar = false;
        Resources rs = UtilsApp.INSTANCE.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool",
                "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass,
                    "qemu.hw.mainkeys");
            if (navBarOverride.equals("1")) {
                hasNavigationBar = false;
            } else if (navBarOverride.equals("0")) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasNavigationBar;
    }



    public static void hideStatusBar(Window window) {
        WindowManager.LayoutParams attrs = window.getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        window.setAttributes(attrs);
    }

    public static void showStatusBar(Window window) {
        WindowManager.LayoutParams attrs = window.getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        window.setAttributes(attrs);
    }

}
