package com.xzy.utils.app;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;


import com.xzy.utils.log.L;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * App 相关的辅助类。
 */
@SuppressWarnings("ALL")
public class AppUtils {

    private AppUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取应用程序名称
     *
     * @param context context
     * @return 应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用程序版本名称信息
     *
     * @param context context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用程序版本号
     *
     * @param context context
     * @return 当前应用的版本名称
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 打开指定包名的 app,这里的包名指的是 app 的主包名
     * <p>
     * 我们经常会看到在微信里面可以调用QQ客户端，
     * 在一个App里面通过点击事件打开手机里面已经安装的某个app，从而满足我们的各种需求.
     * <p>
     * 一般都不知道应用程序的启动Activity的类名，而只知道包名，
     * 我们可以通过ResolveInfo类来取得启动Acitivty的类名,
     * 然后通过packageName和className来启动另一个app.
     * 在退出被打开的app后如果想回到之前的app，记得不要finish掉之前的app.
     *
     * @param packageName 包名
     */
    public static void openApp(Context context, String packageName) {
        PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(pi.packageName);

        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> apps = pm.queryIntentActivities(resolveIntent, 0);

        ResolveInfo ri = apps.iterator().next();
        if (ri != null) {
            String getPackageName = ri.activityInfo.packageName;
            String getClassName = ri.activityInfo.name;
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cn = new ComponentName(getPackageName, getClassName);
            intent.setComponent(cn);
            context.startActivity(intent);
        }
    }

    /**
     * 通过 uri 判断某个 app 是否已经安装
     *
     * @param context context
     * @param uri     uri
     * @return boolean
     */
    private boolean checkAppInstalledByUri(Context context, String uri) {
        PackageManager pm = context.getPackageManager();
        boolean installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            installed = false;
        }
        return installed;
    }

    /**
     * 通过 packageName 判断某个 app 是否已经安装
     *
     * @param context     context
     * @param packageName packageName
     * @return boolean
     */
    public boolean checkAppInstalledByPkName(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<String>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);
    }


    /**
     * 请求 root 权限。
     *
     * App实现静默安装
     * http://blog.csdn.net/androidstarjack/article/details/50349999
     * http://www.jb51.net/article/78463.htm
     * http://blog.csdn.net/h3c4lenovo/article/details/9202323
     * @param context context
     */
    public static void requestAppRootPermission(Context context) {
        Process process = null;
        DataOutputStream os = null;
        try {
            String cmd = "chmod 777 " + context.getPackageCodePath();
            // 切换到root帐号
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            L.e(e.getMessage());
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
                L.e(e.getMessage());
            }
        }
    }

}
