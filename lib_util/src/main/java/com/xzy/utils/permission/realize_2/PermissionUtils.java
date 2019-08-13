/*
 * Copyright (C) 2018 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xzy.utils.permission.realize_2;

import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import android.view.MotionEvent;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.xzy.utils.UtilsApp;
import com.xzy.utils.constants.PermissionConstants;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 权限相关工具类
 * 参考 https://github.com/xuexiangjys/XUtil
 * @author xzy
 */
@SuppressWarnings("all")
public final class PermissionUtils {

    private static final List<String> PERMISSIONS = getPermissions();

    private static PermissionUtils sInstance;

    private OnRationaleListener mOnRationaleListener;
    private SimpleCallback      mSimpleCallback;
    private FullCallback        mFullCallback;
    private ThemeCallback       mThemeCallback;
    private Set<String>         mPermissions;
    private List<String>        mPermissionsRequest;
    private List<String>        mPermissionsGranted;
    private List<String>        mPermissionsDenied;
    private List<String>        mPermissionsDeniedForever;

    /**
     * 获取应用权限
     *
     * @return 清单文件中的权限列表
     */
    public static List<String> getPermissions() {
        return getPermissions(UtilsApp.INSTANCE.getPackageName());
    }

    /**
     * 获取应用权限
     *
     * @param packageName 包名
     * @return 清单文件中的权限列表
     */
    public static List<String> getPermissions(final String packageName) {
        PackageManager pm = UtilsApp.INSTANCE.getPackageManager();
        try {
            return Arrays.asList(
                    pm.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
                            .requestedPermissions
            );
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    /**
     * 判断权限是否被授予
     *
     * @param permissions 权限
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isGranted(final String... permissions) {
        for (String permission : permissions) {
            if (!isGranted(permission)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isGranted(final String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M
                || PackageManager.PERMISSION_GRANTED
                == ContextCompat.checkSelfPermission(UtilsApp.INSTANCE, permission);
    }

    /**
     * 打开应用具体设置
     */
    public static void openAppSettings() {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + UtilsApp.INSTANCE.getPackageName()));
        UtilsApp.INSTANCE.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    /**
     * 设置请求权限
     *
     * @param permissions 要请求的权限
     * @return {@link PermissionUtils}
     */
    public static PermissionUtils permission(@PermissionConstants.Permission final String... permissions) {
        return new PermissionUtils(permissions);
    }

    private PermissionUtils(final String... permissions) {
        mPermissions = new LinkedHashSet<>();
        for (String permission : permissions) {
            for (String aPermission : PermissionConstants.getPermissions(permission)) {
                if (PERMISSIONS.contains(aPermission)) {
                    mPermissions.add(aPermission);
                }
            }
        }
        sInstance = this;
    }

    /**
     * 设置拒绝权限后再次请求的回调接口
     *
     * @param listener 拒绝权限后再次请求的回调接口
     * @return {@link PermissionUtils}
     */
    public PermissionUtils rationale(final OnRationaleListener listener) {
        mOnRationaleListener = listener;
        return this;
    }

    /**
     * 设置回调
     *
     * @param callback 简单回调接口
     * @return {@link PermissionUtils}
     */
    public PermissionUtils callback(final SimpleCallback callback) {
        mSimpleCallback = callback;
        return this;
    }

    /**
     * 设置回调
     *
     * @param callback 完整回调接口
     * @return {@link PermissionUtils}
     */
    public PermissionUtils callback(final FullCallback callback) {
        mFullCallback = callback;
        return this;
    }

    /**
     * 设置主题
     *
     * @param callback 主题回调接口
     * @return {@link PermissionUtils}
     */
    public PermissionUtils theme(final ThemeCallback callback) {
        mThemeCallback = callback;
        return this;
    }

    /**
     * 开始请求
     */
    public void request() {
        mPermissionsGranted = new ArrayList<>();
        mPermissionsRequest = new ArrayList<>();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mPermissionsGranted.addAll(mPermissions);
            requestCallback();
        } else {
            for (String permission : mPermissions) {
                if (isGranted(permission)) {
                    mPermissionsGranted.add(permission);
                } else {
                    mPermissionsRequest.add(permission);
                }
            }
            if (mPermissionsRequest.isEmpty()) {
                requestCallback();
            } else {
                startPermissionActivity();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void startPermissionActivity() {
        mPermissionsDenied = new ArrayList<>();
        mPermissionsDeniedForever = new ArrayList<>();
        PermissionActivity.start(UtilsApp.INSTANCE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean rationale(final Activity activity) {
        boolean isRationale = false;
        if (mOnRationaleListener != null) {
            for (String permission : mPermissionsRequest) {
                if (activity.shouldShowRequestPermissionRationale(permission)) {
                    getPermissionsStatus(activity);
                    mOnRationaleListener.rationale(new OnRationaleListener.ShouldRequest() {
                        @Override
                        public void again(boolean again) {
                            if (again) {
                                startPermissionActivity();
                            } else {
                                requestCallback();
                            }
                        }
                    });
                    isRationale = true;
                    break;
                }
            }
            mOnRationaleListener = null;
        }
        return isRationale;
    }

    private void getPermissionsStatus(final Activity activity) {
        for (String permission : mPermissionsRequest) {
            if (isGranted(permission)) {
                mPermissionsGranted.add(permission);
            } else {
                mPermissionsDenied.add(permission);
                if (!activity.shouldShowRequestPermissionRationale(permission)) {
                    mPermissionsDeniedForever.add(permission);
                }
            }
        }
    }

    private void requestCallback() {
        if (mSimpleCallback != null) {
            if (mPermissionsRequest.size() == 0
                    || mPermissions.size() == mPermissionsGranted.size()) {
                mSimpleCallback.onGranted();
            } else {
                if (!mPermissionsDenied.isEmpty()) {
                    mSimpleCallback.onDenied();
                }
            }
            mSimpleCallback = null;
        }
        if (mFullCallback != null) {
            if (mPermissionsRequest.size() == 0
                    || mPermissions.size() == mPermissionsGranted.size()) {
                mFullCallback.onGranted(mPermissionsGranted);
            } else {
                if (!mPermissionsDenied.isEmpty()) {
                    mFullCallback.onDenied(mPermissionsDeniedForever, mPermissionsDenied);
                }
            }
            mFullCallback = null;
        }
        mOnRationaleListener = null;
        mThemeCallback = null;
    }

    private void onRequestPermissionsResult(final Activity activity) {
        getPermissionsStatus(activity);
        requestCallback();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static class PermissionActivity extends Activity {

        public static void start(final Context context) {
            Intent starter = new Intent(context, PermissionActivity.class);
            starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(starter);
        }

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                    | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
            if (sInstance == null) {
                super.onCreate(savedInstanceState);
                finish();
                return;
            }
            if (sInstance.mThemeCallback != null) {
                sInstance.mThemeCallback.onActivityCreate(this);
            }
            super.onCreate(savedInstanceState);

            if (sInstance.rationale(this)) {
                finish();
                return;
            }
            if (sInstance.mPermissionsRequest != null) {
                int size = sInstance.mPermissionsRequest.size();
                if (size <= 0) {
                    finish();
                    return;
                }
                requestPermissions(sInstance.mPermissionsRequest.toArray(new String[size]), 1);
            }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode,
                                               @NonNull String[] permissions,
                                               @NonNull int[] grantResults) {
            sInstance.onRequestPermissionsResult(this);
            finish();
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            finish();
            return true;
        }
    }


    public interface OnRationaleListener {

        void rationale(ShouldRequest shouldRequest);

        interface ShouldRequest {
            void again(boolean again);
        }
    }

    public interface SimpleCallback {
        void onGranted();

        void onDenied();
    }

    public interface FullCallback {
        void onGranted(List<String> permissionsGranted);

        void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied);
    }

    public interface ThemeCallback {
        void onActivityCreate(Activity activity);
    }

    /**
     * 打开通知权限
     *
     * @param activity
     */
    public static void requestNotifications(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!isNotificationEnable(activity)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    openAppNotificationSettings(activity);
                }
            }
        }
    }

    /**
     * 打开APP的通知权限设置界面
     * @param activity
     */
    private static void openAppNotificationSettings(Activity activity) {
        Intent intent = new Intent();
        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
        intent.putExtra("app_package", activity.getPackageName());
        intent.putExtra("app_uid", activity.getApplicationInfo().uid);
        activity.startActivity(intent);
    }

    /**
     * 通知权限是否打开
     * @param context
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static boolean isNotificationEnable(Context context) {
        AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;
        try {
            Class appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod("checkOpNoThrow", Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField("OP_POST_NOTIFICATION");
            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(appOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 申请android.permission.SYSTEM_ALERT_WINDOW权限
     * @param activity
     */
    public static void requestSystemAlertWindow(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(activity)) {
                Intent serviceIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + activity.getPackageName()));
                activity.startActivityForResult(serviceIntent,
                        PermissionConstants.REQUEST_OVERLAY_PERMISSION_CODE);
            }
        }
    }

    /**
     * 申请android.permission.WRITE_SETTINGS权限
     * @param activity
     */
    public static void requestWriteSettings(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(activity)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                        Uri.parse("package:" + activity.getPackageName()));
                activity.startActivityForResult(intent,
                        PermissionConstants.REQUEST_WRITE_SETTINGS_PERMISSION_CODE);
            }
        }
    }
}