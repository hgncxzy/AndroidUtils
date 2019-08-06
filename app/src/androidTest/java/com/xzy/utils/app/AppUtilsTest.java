package com.xzy.utils.app;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

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
        Assert.assertEquals("utils",AppUtils.getAppName());
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
                .checkAppInstalledByPkName(mContext,AppUtils.getAppPackageName()));
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
        Assert.assertEquals("com.xzy.utils",AppUtils.getAppPackageName());
    }

    @Test
    public void getAppName1() {
        Assert.assertEquals("utils",AppUtils.getAppName("com.xzy.utils"));
    }

    @Test
    public void getAppName2() {
        Assert.assertEquals("utils",AppUtils.getAppName());
    }

    @Test
    public void getAppPath() {
    }

    @Test
    public void getAppPath1() {
    }

    @Test
    public void getAppVersionName() {
    }

    @Test
    public void getAppVersionName1() {
    }

    @Test
    public void getAppVersionCode() {
    }

    @Test
    public void getAppVersionCode1() {
    }

    @Test
    public void getAppSignature() {
    }

    @Test
    public void getAppSignature1() {
    }

    @Test
    public void getAppSignatureSHA1() {
    }

    @Test
    public void getAppSignatureSHA11() {
    }

    @Test
    public void getAppSignatureSHA256() {
    }

    @Test
    public void getAppSignatureSHA2561() {
    }

    @Test
    public void getAppSignatureMD5() {
    }

    @Test
    public void getAppSignatureMD51() {
    }

    @Test
    public void getAppUid() {
    }

    @Test
    public void getAppUid1() {
    }

    @Test
    public void getAppInfo() {
    }

    @Test
    public void getAppInfo1() {
    }

    @Test
    public void getAppsInfo() {
    }

    @Test
    public void getApkInfo() {
    }

    @Test
    public void getApkInfo1() {
    }
}