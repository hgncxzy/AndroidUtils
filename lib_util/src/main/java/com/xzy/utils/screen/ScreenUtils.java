package com.xzy.utils.screen;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;

import com.xzy.utils.UtilsApp;
import com.xzy.utils.common.Utils;

import java.lang.reflect.Method;
import java.util.Objects;

import static android.Manifest.permission.WRITE_SETTINGS;

/**
 * 获得屏幕相关的辅助类.
 * 部分参考 https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/
 * java/com/blankj/utilcode/util/ScreenUtils.java
 */
@SuppressWarnings("ALL")
public class ScreenUtils {
    /* 获取屏幕休眠时长的错误码 */
    public final static int GET_SLEEPTIME_ERROR = -123;

    private ScreenUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获得屏幕高度
     *
     * @param context context
     * @return int
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        Objects.requireNonNull(wm).getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context context
     * @return int
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        Objects.requireNonNull(wm).getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context context
     * @return int
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            @SuppressLint("PrivateApi") Class<?> clazz =
                    Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(Objects.
                    requireNonNull(clazz.getField("status_bar_height")
                            .get(object)).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity activity
     * @return Bitmap
     */
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;

    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity activity
     * @return Bitmap
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }


    /**
     * Return the width of screen, in pixel.
     *
     * @return the width of screen, in pixel
     */
    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) Utils.getApp().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return -1;
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }

    /**
     * Return the height of screen, in pixel.
     *
     * @return the height of screen, in pixel
     */
    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) Utils.getApp().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return -1;
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }

    /**
     * Return the application's width of screen, in pixel.
     *
     * @return the application's width of screen, in pixel
     */
    public static int getAppScreenWidth() {
        WindowManager wm = (WindowManager) Utils.getApp().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return -1;
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return point.x;
    }

    /**
     * Return the application's height of screen, in pixel.
     *
     * @return the application's height of screen, in pixel
     */
    public static int getAppScreenHeight() {
        WindowManager wm = (WindowManager) Utils.getApp().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return -1;
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return point.y;
    }

    /**
     * Return the density of screen.
     *
     * @return the density of screen
     */
    public static float getScreenDensity() {
        return Resources.getSystem().getDisplayMetrics().density;
    }

    /**
     * Return the screen density expressed as dots-per-inch.
     *
     * @return the screen density expressed as dots-per-inch
     */
    public static int getScreenDensityDpi() {
        return Resources.getSystem().getDisplayMetrics().densityDpi;
    }

    /**
     * Set full screen.
     *
     * @param activity The activity.
     */
    public static void setFullScreen(@NonNull final Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * Set non full screen.
     *
     * @param activity The activity.
     */
    public static void setNonFullScreen(@NonNull final Activity activity) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * Toggle full screen.
     *
     * @param activity The activity.
     */
    public static void toggleFullScreen(@NonNull final Activity activity) {
        boolean isFullScreen = isFullScreen(activity);
        Window window = activity.getWindow();
        if (isFullScreen) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    /**
     * Return whether screen is full.
     *
     * @param activity The activity.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isFullScreen(@NonNull final Activity activity) {
        int fullScreenFlag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        return (activity.getWindow().getAttributes().flags & fullScreenFlag) == fullScreenFlag;
    }

    /**
     * Return whether screen is portrait.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isPortrait() {
        return Utils.getApp().getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * Return the rotation of screen.
     *
     * @param activity The activity.
     * @return the rotation of screen
     */
    public static int getScreenRotation(@NonNull final Activity activity) {
        switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
            default:
                return 0;
        }
    }

    /**
     * Return the bitmap of screen.
     *
     * @param activity The activity.
     * @return the bitmap of screen
     */
    public static Bitmap screenShot(@NonNull final Activity activity) {
        return screenShot(activity, false);
    }

    /**
     * Return the bitmap of screen.
     *
     * @param activity          The activity.
     * @param isDeleteStatusBar True to delete status bar, false otherwise.
     * @return the bitmap of screen
     */
    public static Bitmap screenShot(@NonNull final Activity activity, boolean isDeleteStatusBar) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        decorView.setWillNotCacheDrawing(false);
        Bitmap bmp = decorView.getDrawingCache();
        if (bmp == null) return null;
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Bitmap ret;
        if (isDeleteStatusBar) {
            Resources resources = activity.getResources();
            int resourceId = resources.getIdentifier("status_bar_height", "dimen",
                    "android");
            int statusBarHeight = resources.getDimensionPixelSize(resourceId);
            ret = Bitmap.createBitmap(
                    bmp,
                    0,
                    statusBarHeight,
                    dm.widthPixels,
                    dm.heightPixels - statusBarHeight
            );
        } else {
            ret = Bitmap.createBitmap(bmp, 0, 0, dm.widthPixels, dm.heightPixels);
        }
        decorView.destroyDrawingCache();
        return ret;
    }

    /**
     * Return whether screen is locked.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isScreenLock() {
        KeyguardManager km =
                (KeyguardManager) Utils.getApp().getSystemService(Context.KEYGUARD_SERVICE);
        if (km == null) return false;
        return km.inKeyguardRestrictedInputMode();
    }

    /**
     * Set the duration of sleep.
     * <p>Must hold {@code <uses-permission android:name="android.permission.WRITE_SETTINGS" />}</p>
     *
     * @param duration The duration.
     */
    @RequiresPermission(WRITE_SETTINGS)
    public static void setSleepDuration(final int duration) {
        Settings.System.putInt(
                Utils.getApp().getContentResolver(),
                Settings.System.SCREEN_OFF_TIMEOUT,
                duration
        );
    }

    /**
     * Return the duration of sleep.
     *
     * @return the duration of sleep.
     */
    public static int getSleepDuration() {
        try {
            return Settings.System.getInt(
                    Utils.getApp().getContentResolver(),
                    Settings.System.SCREEN_OFF_TIMEOUT
            );
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return -123;
        }
    }



    /**
     * 获取屏幕的宽度和高度（单位：px）
     * <p>android 从 3.2 版本开始获取屏幕区域高度会自动减去状态栏高度</p>
     *
     * @return Point
     */
    public static Point getScreenSize() {
        DisplayMetrics metrics = UtilsApp.INSTANCE.getResources().getDisplayMetrics();
        return new Point(metrics.widthPixels, metrics.heightPixels);
    }

    /**
     * 获取屏幕的物理尺寸（单位：英寸）
     *
     * @return 物理尺寸
     */
    public static float getScreenSizeOfDevice() {
        DisplayMetrics metrics = UtilsApp.INSTANCE.getResources().getDisplayMetrics();
        float xdpi = metrics.xdpi;
        float ydpi = metrics.ydpi;
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        float w = (width / xdpi) * (width / xdpi);
        float h = (height / ydpi) * (width / xdpi);
        return (float) Math.sqrt(w + h);
    }

    /**
     * 获取屏幕高和宽的比
     *
     * @return 屏幕高和宽的比
     */
    public static float getScreenRatio() {
        Point P = getScreenSize();
        float H = P.y;
        float W = P.x;
        return (H / W);
    }

    /**
     * 获取屏幕密度（指每平方英寸中的像素数）
     * <p>例如：0.75 / 1 / 1.5 / ...（dpi/160 可得）</p>
     *
     * @return 屏幕密度
     */
    public static float getDensity() {
        return UtilsApp.INSTANCE.getResources().getDisplayMetrics().density;
    }

    /**
     * 获取屏幕像素密度 dpi （指每英寸中的像素数）
     * <p>例如：120 / 160 / 240 /...</p>
     *
     * @return 像素密度 dpi
     */
    public static int getDensityDpi() {
        return UtilsApp.INSTANCE.getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * 获取状态栏的高度
     *
     * @return 状态栏的高度
     */
    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = UtilsApp.INSTANCE.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = UtilsApp.INSTANCE.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取底部导航栏的高度
     *
     * @return 底部导航栏的高度
     */
    public static int getNavBarHeight() {
        int result = 0;
        int resourceId = 0;
        // 判断底部导航栏是否显示
        int rid = UtilsApp.INSTANCE.getResources().getIdentifier("config_showNavigationBar",
                "bool", "android");
        if (rid != 0) {
            resourceId = UtilsApp.INSTANCE.getResources().getIdentifier(
                    "navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = UtilsApp.INSTANCE.getResources().getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    /**
     * 判断是否有底部导航栏
     *
     * @return {@code true}: 有底部导航栏<br>{@code false}: 没有底部导航栏
     */
    public static boolean hasNavigationBar() {
        boolean hasNavigationBar = false;
        Resources rs = UtilsApp.INSTANCE.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0)
            hasNavigationBar = rs.getBoolean(id);
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
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

    /**
     * 截屏
     *
     * @param activity
     * @return 返回的 bitmap 不包括状态栏
     */
    public static Bitmap screenShotNoStatusBar(@NonNull Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        Point point = getScreenSize();
        Bitmap bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, point.x, point.y);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 设置屏幕为横屏
     * <p>还有一种就是在 Activity 中加属性 android:screenOrientation="landscape"</p>
     * <p>不设置 Activity 的 android:configChanges 时，切屏会重新调用各个生命周期，切横屏时会执行一次，切竖屏时会执行两次</p>
     * <p>设置 Activity 的 android:configChanges="orientation"时，切屏还是会重新调用各个生命周期，切横、竖屏时只会执行一次</p>
     * <p>设置 Activity 的 android:configChanges="orientation|keyboardHidden|screenSize"（4.0 以上必须带最后一个参数）时
     * 切屏不会重新调用各个生命周期，只会执行 onConfigurationChanged 方法</p>
     *
     * @param activity activity
     */
    public static void setLandscape(@NonNull Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * 设置屏幕为竖屏
     *
     * @param activity activity
     */
    public static void setPortrait(@NonNull final Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 判断是否横屏
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isLandscape() {
        return UtilsApp.INSTANCE.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }



    /**
     * 设置进入休眠时长
     *
     * @param duration 时长
     */
    public static void setSleepTime(final int duration) {
        Settings.System.putInt(UtilsApp.INSTANCE.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, duration);
    }

    /**
     * 获取进入休眠时长
     *
     * @return 进入休眠时长，报错返回 {@link #GET_SLEEPTIME_ERROR}
     */
    public static int getSleepTime() {
        try {
            return Settings.System.getInt(UtilsApp.INSTANCE.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return GET_SLEEPTIME_ERROR;
        }
    }

    /**
     * 判断是否是平板
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isTablet() {
        return (UtilsApp.INSTANCE.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}

