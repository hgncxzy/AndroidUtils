package com.xzy.utils.app;

import android.content.Context;
import android.util.Log;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.xzy.utils.log.L;
import com.xzy.utils.path.PathUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.Objects;

@RunWith(AndroidJUnit4.class)
public class AppUtilsTest {
    private Context mContext;

    @Before
    public void setUp() {
        mContext = InstrumentationRegistry.getTargetContext();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getAppName() {
        Assert.assertEquals("utils", AppUtils.getAppName());
    }

    @Test
    public void getVersionName() {
        Assert.assertEquals("0.0.1",
                AppUtils.getVersionName(mContext));
    }

    @Test
    public void getVersionCode() {
        Assert.assertEquals(1,
                AppUtils.getVersionCode(mContext));
    }

    @Test
    public void openApp() {
        // AppUtilsActivity 中查看测试。
    }

    @Test
    public void checkAppInstalledByPkName() {
        Assert.assertEquals(true, AppUtils
                .checkAppInstalledByPkName(mContext, AppUtils.getAppPackageName()));
    }

    @Test
    public void installAPK() {
    }

    @Test
    public void registerAppStatusChangedListener() {
        // AppUtilsActivity 中查看测试。
    }

    @Test
    public void unregisterAppStatusChangedListener() {
        // AppUtilsActivity 中查看测试。
    }

    @Test
    public void installApp() {
    }

    @Test
    public void installApp1() {
    }

    @Test
    public void installApp2() {
    }

    @Test
    public void installApp3() {
    }

    @Test
    public void uninstallApp() {
        // AppUtilsActivity 中查看测试。
    }

    @Test
    public void uninstallApp1() {
        // AppUtilsActivity 中查看测试。
    }

    @Test
    public void isAppInstalled() {
        Assert.assertTrue(AppUtils.isAppInstalled(AppUtils.getAppPackageName()));
    }

    @Test
    public void isAppRoot() {
        Assert.assertFalse(AppUtils.isAppRoot());
    }

    @Test
    public void isAppDebug() {
        Assert.assertTrue(AppUtils.isAppDebug(AppUtils.getAppPackageName()));
    }

    @Test
    public void isAppDebug1() {
        Assert.assertTrue(AppUtils.isAppDebug());
    }

    @Test
    public void isAppSystem() {
        Assert.assertFalse(AppUtils.isAppSystem());
    }

    @Test
    public void isAppSystem1() {
        Assert.assertFalse(AppUtils.isAppSystem(AppUtils.getAppPackageName()));
    }

    @Test
    public void isAppForeground() {
        Assert.assertFalse(AppUtils.isAppForeground());
    }

    @Test
    public void isAppForeground1() {
        Assert.assertFalse(AppUtils.isAppForeground(AppUtils.getAppPackageName()));
    }

    @Test
    public void isAppRunning() {
        Assert.assertFalse(AppUtils.isAppRunning(AppUtils.getAppPackageName()));
    }

    @Test
    public void launchApp() {
        // AppUtilsActivity 中查看测试。
    }

    @Test
    public void launchApp1() {
        // AppUtilsActivity 中查看测试。
    }

    @Test
    public void relaunchApp() {
        // AppUtilsActivity 中查看测试。
    }

    @Test
    public void relaunchApp1() {
        // AppUtilsActivity 中查看测试。
    }

    @Test
    public void launchAppDetailsSettings() {
        // AppUtilsActivity 中查看测试。
    }

    @Test
    public void launchAppDetailsSettings1() {
        // AppUtilsActivity 中查看测试。
    }

    @Test
    public void exitApp() {
        // AppUtilsActivity 中查看测试。
    }

    @Test
    public void getAppIcon() {
        // AppUtilsActivity 中查看测试。
    }

    @Test
    public void getAppIcon1() {
        // AppUtilsActivity 中查看测试。
    }

    @Test
    public void getAppPackageName() {
        Assert.assertEquals("com.xzy.utils", AppUtils.getAppPackageName());
    }

    @Test
    public void getAppName1() {
        Assert.assertEquals("utils",
                AppUtils.getAppName("com.xzy.utils"));
    }

    @Test
    public void getAppName2() {
        Assert.assertEquals("utils", AppUtils.getAppName());
    }

    @Test
    public void getAppPath() {
        L.getInstance(mContext).d(Objects
                .requireNonNull(AppUtils.getAppPath())+"");
    }

    @Test
    public void getAppPath1() {
        L.getInstance(mContext).d(Objects
                .requireNonNull(AppUtils.getAppPath(AppUtils.getAppPackageName()))+"");
    }

    @Test
    public void getAppVersionName() {
        L.getInstance(mContext).d(Objects
                .requireNonNull(AppUtils.getAppVersionName())+"");
    }

    @Test
    public void getAppVersionName1() {
        L.getInstance(mContext).d(Objects
                .requireNonNull(AppUtils.getAppVersionName(AppUtils.getAppPackageName()))+"");
    }

    @Test
    public void getAppVersionCode() {
        L.getInstance(mContext).d(Objects
                .requireNonNull(AppUtils.getAppVersionCode(AppUtils.getAppPackageName()))+"");
    }

    @Test
    public void getAppVersionCode1() {
        L.getInstance(mContext).d(Objects
                .requireNonNull(AppUtils.getAppVersionCode())+"");
    }

    @Test
    public void getAppSignature() {
        L.getInstance(mContext).d(Objects
                .requireNonNull(AppUtils.getAppSignature()[0].toCharsString()));
    }

    @Test
    public void getAppSignature1() {
        L.getInstance(mContext).d(Objects.requireNonNull(AppUtils
                .getAppSignature(AppUtils.getAppPackageName()))[0].toCharsString());
    }

    @Test
    public void getAppSignatureSHA1() {
        L.getInstance(mContext).d(AppUtils.getAppSignatureSHA1(AppUtils.getAppPackageName()));
    }

    @Test
    public void getAppSignatureSHA11() {
        L.getInstance(mContext).d(AppUtils.getAppSignatureSHA1());
    }

    @Test
    public void getAppSignatureSHA256() {
        L.getInstance(mContext).d(AppUtils.getAppSignatureSHA256(AppUtils.getAppPackageName()));
    }

    @Test
    public void getAppSignatureSHA2561() {
        L.getInstance(mContext).d(AppUtils.getAppSignatureSHA256());
    }

    @Test
    public void getAppSignatureMD5() {
        L.getInstance(mContext).d(AppUtils.getAppSignatureMD5(AppUtils.getAppPackageName()));
    }

    @Test
    public void getAppSignatureMD51() {
        L.getInstance(mContext).d(AppUtils.getAppSignatureMD5());
    }

    @Test
    public void getAppUid() {
        Assert.assertTrue(AppUtils.getAppUid("com.xzy.utils")>0);
    }

    @Test
    public void getAppUid1() {
        Assert.assertTrue(AppUtils.getAppUid()>0);
    }

    @Test
    public void getAppInfo() {
        Assert.assertEquals("com.xzy.utils",AppUtils.getAppInfo("com.xzy.utils").getPackageName());
    }

    @Test
    public void getAppInfo1() {
        Assert.assertEquals("com.xzy.utils",AppUtils.getAppInfo().getPackageName());
    }

    @Test
    public void getAppsInfo() {
        Assert.assertTrue(AppUtils.getAppsInfo().size() > 10);
    }

    @Test
    public void getApkInfo() {
        L.getInstance(mContext).d(Objects.requireNonNull(AppUtils
                .getApkInfo(new File(PathUtils.getRootPath() + "/test.apk"))).toString());
    }

    @Test
    public void getApkInfo1() {
        L.getInstance(mContext).d(Objects.requireNonNull(AppUtils
                .getApkInfo(PathUtils.getRootPath() + "/test.apk")).toString());
    }
}