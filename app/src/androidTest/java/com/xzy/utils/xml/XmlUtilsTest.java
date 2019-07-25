package com.xzy.utils.xml;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@RunWith(AndroidJUnit4.class)
public class XmlUtilsTest {

    @Test
    public void dom2Map() {
        Document doc = null;
        try {
            FileInputStream fis = new FileInputStream
                    ("C:\\xzy_git_projects\\utils\\app\\src\\main\\java\\com\\xzy\\utils\\xml");
            byte[] b = new byte[fis.available()];
            fis.read(b);
            String str = new String(b);
            doc = DocumentHelper.parseText(str);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Map<String, Object> map = XmlUtils.Dom2Map(doc);
        Assert.assertEquals(map.get("name"), "xzy");
    }

}