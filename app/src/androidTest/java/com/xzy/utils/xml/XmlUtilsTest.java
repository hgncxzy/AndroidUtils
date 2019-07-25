package com.xzy.utils.xml;


import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class XmlUtilsTest {

    Context context;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void parseXMLFromAsserts() {
        XmlUtils.parseXMLFromAsserts(context, "test.xml");
    }
}