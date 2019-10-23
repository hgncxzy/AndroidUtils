package com.xzy.utils.permission.realize_3;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * xzy
 * 请求权限组
 */
@SuppressWarnings("all")
public class Permission {
    public static final int GROUP_CAMERA = 0x01;
    public static final int GROUP_CONTACTS = 0x02;
    public static final int GROUP_LOCATION = 0x03;
    public static final int GROUP_MICROPHONE = 0x04;
    public static final int GROUP_PHONE = 0x05;
    public static final int GROUP_PHONE_BELOW_O = 0x06;
    public static final int GROUP_SENSORS = 0x07;
    public static final int GROUP_SMS = 0x08;
    public static final int GROUP_STORAGE = 0x09;


    /**
     * 请求 Camera 权限
     *
     * @param activity 上下文
     * @return boolean
     */
    public static boolean isGrantCameraPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{
                    Manifest.permission.CAMERA,
            }, GROUP_CAMERA);

            return false;
        }
        return true;
    }

    /**
     * 请求联系人权限
     *
     * @param activity 上下文
     * @return boolean
     */
    public static boolean isGrantContactsPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_CONTACTS,
                    Manifest.permission.GET_ACCOUNTS,
            }, GROUP_CONTACTS);

            return false;
        }
        return true;
    }

    /**
     * 请求位置权限
     *
     * @param activity 上下文
     * @return boolean
     */
    public static boolean isGrantLocationPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
            }, GROUP_LOCATION);

            return false;
        }
        return true;
    }

    /**
     * 请求麦克风权限
     *
     * @param activity 上下文
     * @return boolean
     */
    public static boolean isGrantMicrophonePermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{
                    Manifest.permission.RECORD_AUDIO,
            }, GROUP_MICROPHONE);

            return false;
        }
        return true;
    }

    /**
     * 请求电话权限
     *
     * @param activity 上下文
     * @return boolean
     */
    public static boolean isGrantPhonePermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_PHONE_NUMBERS,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_CALL_LOG,
                    Manifest.permission.WRITE_CALL_LOG,
                    Manifest.permission.ADD_VOICEMAIL,
                    Manifest.permission.USE_SIP,
                    Manifest.permission.PROCESS_OUTGOING_CALLS,
                    Manifest.permission.ANSWER_PHONE_CALLS
            }, GROUP_PHONE);

            return false;
        }
        return true;
    }

    /**
     * 请求电话权限 针对 Android 8.0 以下
     *
     * @param activity 上下文
     * @return boolean
     */
    public static boolean isGrantPhoneBelowAndroidOPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_PHONE_NUMBERS,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_CALL_LOG,
                    Manifest.permission.WRITE_CALL_LOG,
                    Manifest.permission.ADD_VOICEMAIL,
                    Manifest.permission.USE_SIP,
                    Manifest.permission.PROCESS_OUTGOING_CALLS
            }, GROUP_PHONE_BELOW_O);

            return false;
        }
        return true;
    }

    /**
     * 请求传感器权限
     *
     * @param activity 上下文
     * @return boolean
     */
    public static boolean isGrantSensorsPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{
                    Manifest.permission.BODY_SENSORS,
            }, GROUP_SENSORS);

            return false;
        }
        return true;
    }

    /**
     * 请求短信权限
     *
     * @param activity 上下文
     * @return boolean
     */
    public static boolean isGrantSMSPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{
                    Manifest.permission.SEND_SMS,
                    Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.READ_SMS,
                    Manifest.permission.RECEIVE_WAP_PUSH,
                    Manifest.permission.RECEIVE_MMS,
            }, GROUP_SMS);

            return false;
        }
        return true;
    }

    /**
     * 请求存储权限
     *
     * @param activity 上下文
     * @return boolean
     */
    public static boolean isGrantStoragePermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
            }, GROUP_STORAGE);

            return false;
        }
        return true;
    }
}

