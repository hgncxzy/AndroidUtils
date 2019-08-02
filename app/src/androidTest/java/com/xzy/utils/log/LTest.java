package com.xzy.utils.log;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
@RunWith(AndroidJUnit4.class)
public class LTest {
    private Context mContext;
    private L mLog;

    @Before
    public void setUp() {
        mContext = InstrumentationRegistry.getTargetContext();
        mLog = L.getInstance(mContext)
                .setDebug(true)
                .setLog2File(true)
                .setTAG("test");
    }

    @After
    public void tearDown() {
    }


    @Test
    public void isIsDebug() {
        assertTrue(mLog.isIsDebug());
    }

    @Test
    public void getTAG() {
        Assert.assertEquals(mLog.getTAG(), mLog.TAG);
    }

    @Test
    public void isIsLog2File() {
        mLog.isLog2File = true;
        assertTrue(mLog.isIsLog2File());
    }

    @Test
    public void setIsLog2File() {
        mLog.setLog2File(false);
        assertFalse(mLog.isIsLog2File());
    }

    @Test
    public void i() {
        mLog.i("hello world");
    }

    @Test
    public void d() {
        mLog.d("hello world");
    }

    @Test
    public void e() {
        mLog.e("hello world");
    }

    @Test
    public void w() {
        mLog.w(new NullPointerException());
    }

    @Test
    public void i1() {
        mLog.i("test", "hello world");
    }

    @Test
    public void d1() {
        mLog.d("test", "hello world");
    }

    @Test
    public void e1() {
        mLog.e("test", "hello world");
    }

    @Test
    public void w1() {
        mLog.w("test", new NumberFormatException());
    }
}