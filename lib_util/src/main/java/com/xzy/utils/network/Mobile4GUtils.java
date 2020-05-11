package com.xzy.utils.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by xzy.
 * 4G模块操作工具类
 * 参考 https://blog.csdn.net/qq_24800377/article/details/55657048
 */

public class Mobile4GUtils {

    /**
     * 获取数据网络状态
     * 4.4 及之前的版本
     * <p>
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     * <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE"/>
     */
    public static boolean getMobileEnabled(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            TelephonyManager telephonyService = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            try {
                Method getMobileDataEnabledMethod = telephonyService.getClass().getMethod("getDataEnabled");
                if (null != getMobileDataEnabledMethod) {
                    return (boolean) getMobileDataEnabledMethod.invoke(telephonyService);
                }
            } catch (Exception e) {
                Log.e("InstallActivity", "Errot setting"
                        + ((InvocationTargetException) e).getTargetException()
                        + telephonyService);
            }
            return false;
        }else {
            ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            Class[] getArgArray = null;
            Object[] getArgInvoke = null;
            try {
                Method mGetMethod = conManager.getClass().getMethod("getMobileDataEnabled", getArgArray);
                boolean isOpen = (Boolean) mGetMethod.invoke(conManager, getArgInvoke);//获取状态
                return isOpen;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    /**
     * 设置移动网络开/关
     *
     * @param context
     * @param isopen
     */
    public static void setMobileData(Context context, boolean isopen) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            TelephonyManager telephonyService = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            try {
                Method setMobileDataEnabledMethod = telephonyService.getClass()
                        .getDeclaredMethod("setDataEnabled", boolean.class);
                if (null != setMobileDataEnabledMethod) {
                    setMobileDataEnabledMethod.invoke(telephonyService,isopen);
                }
            } catch (Exception e) {
                Log.e("InstallActivity", "Errot setting"
                        + ((InvocationTargetException) e).getTargetException()
                        + telephonyService);
            }
        } else {
            ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            Class[] setArgArray = new Class[]{boolean.class};
            try {
                Method mSetMethod = conManager.getClass().getMethod("setMobileDataEnabled", setArgArray);
                mSetMethod.invoke(conManager, isopen);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
