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
        Assert.assertEquals("0.0.1",AppUtils.getVersionName(mContext));
    }

    @Test
    public void getVersionCode() {
        Assert.assertEquals("1",AppUtils.getVersionCode(mContext));
    }

    @Test
    public void openApp() {
    }

    @Test
    public void checkAppInstalledByPkName() {
    }

    @Test
    public void requestAppRootPermission() {
    }

    @Test
    public void installAPK() {
    }

    @Test
    public void registerAppStatusChangedListener() {
    }

    @Test
    public void unregisterAppStatusChangedListener() {
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
    }

    @Test
    public void uninstallApp1() {
    }

    @Test
    public void isAppInstalled() {
    }

    @Test
    public void isAppRoot() {
    }

    @Test
    public void isAppDebug() {
    }

    @Test
    public void isAppDebug1() {
    }

    @Test
    public void isAppSystem() {
    }

    @Test
    public void isAppSystem1() {
    }

    @Test
    public void isAppForeground() {
    }

    @Test
    public void isAppForeground1() {
    }

    @Test
    public void isAppRunning() {
    }

    @Test
    public void launchApp() {
    }

    @Test
    public void launchApp1() {
    }

    @Test
    public void relaunchApp() {
    }

    @Test
    public void relaunchApp1() {
    }

    @Test
    public void launchAppDetailsSettings() {
    }

    @Test
    public void launchAppDetailsSettings1() {
    }

    @Test
    public void exitApp() {
    }

    @Test
    public void getAppIcon() {
    }

    @Test
    public void getAppIcon1() {
    }

    @Test
    public void getAppPackageName() {
    }

    @Test
    public void getAppName1() {
    }

    @Test
    public void getAppName2() {
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