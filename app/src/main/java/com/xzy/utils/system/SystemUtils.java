package com.xzy.utils.system;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.xzy.utils.file.FileUtils;
import com.xzy.utils.screen.ScreenUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * 系统相关的工具类。另外可以参考 RomUtils.java
 *
 * @author xzy
 */
@SuppressWarnings("all")
public class SystemUtils {


    private static final String SYS_EMUI = "sys_emui";
    private static final String SYS_MIUI = "sys_miui";
    private static final String SYS_FLYME = "sys_flyme";
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    private static final String KEY_EMUI_API_LEVEL = "ro.build.hw_emui_api_level";
    private static final String KEY_EMUI_VERSION = "ro.build.version.emui";
    private static final String KEY_EMUI_CONFIG_HW_SYS_VERSION = "ro.confg.hw_systemversion";

    public static String getSystem() {
        String system = "otherSystem";
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream(new File(Environment.getRootDirectory(),
                    "build.prop")));
            if (prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null) {
                system = SYS_MIUI;//小米
            } else if (prop.getProperty(KEY_EMUI_API_LEVEL, null) != null
                    || prop.getProperty(KEY_EMUI_VERSION, null) != null
                    || prop.getProperty(KEY_EMUI_CONFIG_HW_SYS_VERSION, null) != null) {
                system = SYS_EMUI;//华为
            } else if (getMeizuFlymeOSFlag().toLowerCase().contains("flyme")) {
                system = SYS_FLYME;//魅族
            }
        } catch (IOException e) {
            e.printStackTrace();
            return system;
        }
        return system;
    }

    public static String getMeizuFlymeOSFlag() {
        return getSystemProperty();
    }

    /**
     * @return 系统属性
     */
    private static String getSystemProperty() {
        try {
            @SuppressLint("PrivateApi")
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method get = clz.getMethod("get", String.class, String.class);
            return (String) get.invoke(clz, "ro.build.display.id", "otherSystem");
        } catch (Exception e) {
            Log.e("", e.getMessage());
        }
        return "otherSystem";
    }

    /**
     * @return cpu 信息
     */
    public static String getCpuInfo() {
        ProcessBuilder processBuilder;
        String[] DATA = {"/system/bin/cat", "/proc/cpuinfo"};
        InputStream inputStream;
        Process process;
        byte[] byteArry;
        String cpuInfo = "";
        byteArry = new byte[1024];
        try {
            processBuilder = new ProcessBuilder(DATA);
            process = processBuilder.start();
            inputStream = process.getInputStream();
            while (inputStream.read(byteArry) != -1) {
                cpuInfo = cpuInfo + new String(byteArry);
            }
            inputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return cpuInfo + "\n";
    }

    /**
     * 获取内存信息。
     *
     * @param context 上下文
     * @return 内存信息
     */
    public static String getMemoInfo(Context context) {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(memoryInfo);

        Runtime runtime = Runtime.getRuntime();

        String strMemInfo =
                "MemoryInfo.availMem = " + FileUtils
                        .getFileSizeDescription(memoryInfo.availMem) + "\n"
                        + "MemoryInfo.totalMem = " + FileUtils
                        .getFileSizeDescription(memoryInfo.totalMem) + "\n" // API 16
                        + "\n"
                        + "Runtime.maxMemory() = " + FileUtils
                        .getFileSizeDescription(runtime.maxMemory()) + "\n"
                        + "Runtime.totalMemory() = " + FileUtils
                        .getFileSizeDescription(runtime.totalMemory()) + "\n"
                        + "Runtime.freeMemory() = " + FileUtils
                        .getFileSizeDescription(runtime.freeMemory()) + "\n";

        return strMemInfo;
    }


    /**
     * 获取系统相关的信息。
     *
     * @return 系统信息。
     */
    public static String getSysVersionInfo() {
        String sysVersionInfo = "SERIAL: " + Build.SERIAL + "\n" +
                "品牌: " + Build.BRAND + "\n" +
                "型号: " + Build.MODEL + "\n" +
                "SDK:  " + Build.VERSION.SDK + "\n" +
                "分辨率: " + ScreenUtils.getScreenWidth() + "*" +
                ScreenUtils.getScreenHeight() + "\n" +
                "DPI: " + ScreenUtils.getScreenDensityDpi() + "\n" +
                "系统版本:" + Build.VERSION.RELEASE + "\n";
        return sysVersionInfo;
    }

}

