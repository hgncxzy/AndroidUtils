package com.xzy.utils.permission;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.RequiresApi;

/**
 * 参考 https://blog.csdn.net/scimence/article/details/101050780https://blog.csdn.net/scimence/article/details/101050780
 * FloatTool.java:Android 6.0 之后应用悬浮窗权限请求
 * <p>
 * AndroidMainifest.xml中添加： <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
 * <p>
 * 用法：
 * 1、请求悬浮窗权限：FloatTool.RequestOverlayPermission(this);
 * 2、处理悬浮窗权限请求结果：FloatTool.onActivityResult(requestCode, resultCode, data, this);
 *
 * @author xzy
 */
public class FloatTool {
    public static boolean CanShowFloat = false;

    private static final int REQUEST_OVERLAY = 5004;

    /**
     * 动态请求悬浮窗权限
     */
    public static void requestOverlayPermission(Activity instance) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(instance)) {
                Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION"
                        , Uri.parse("package:" + instance.getPackageName()));
                instance.startActivityForResult(intent, REQUEST_OVERLAY);
            } else {
                CanShowFloat = true;
            }
        }
    }

    /**
     * 浮窗权限请求，Activity执行结果，回调函数
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void onActivityResult(int requestCode, int resultCode, Intent data, final Activity instance) {
        if (requestCode == REQUEST_OVERLAY) {
            if (resultCode == Activity.RESULT_OK) {
                // 设置标识为可显示悬浮窗
                CanShowFloat = true;
            } else {
                CanShowFloat = false;
                // 若当前未允许显示悬浮窗，则提示授权
                if (!Settings.canDrawOverlays(instance)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(instance);
                    builder.setCancelable(false);
                    builder.setTitle("悬浮窗权限未授权");
                    builder.setMessage("应用需要悬浮窗权限，以展示浮标");
                    builder.setPositiveButton("去添加 权限", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            requestOverlayPermission(instance);
                        }
                    });

                    builder.setNegativeButton("拒绝则 退出", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            // 若拒绝了所需的权限请求，则退出应用
                            instance.finish();
                            System.exit(0);
                        }
                    });
                    builder.show();
                }
            }
        }
    }
}