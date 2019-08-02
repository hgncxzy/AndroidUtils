package com.xzy.utils.system;

import android.content.Context;
import android.util.Log;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SystemUtilsTest {
    private Context context;
    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getSystem() {
        Log.d("getSystem()",SystemUtils.getSystem());
    }

    @Test
    public void getMeizuFlymeOSFlag() {
        Log.d("getMeizuFlymeOSFlag()",SystemUtils.getMeizuFlymeOSFlag());
    }

    @Test
    public void getCpuInfo() {
        Log.d("getCpuInfo()",SystemUtils.getCpuInfo());
    }

    @Test
    public void getMemoInfo() {
        Log.d("getMemoInfo()",SystemUtils.getMemoInfo(context));
    }

    @Test
    public void getSysVersionInfo() {
        Log.d("getSysVersionInfo()",SystemUtils.getSysVersionInfo());
    }
}