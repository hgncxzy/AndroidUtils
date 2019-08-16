package com.xzy.utils.xml;


import android.content.Context;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * xml 解析工具。
 *
 * @author xzy
 */

@SuppressWarnings("all")
public class XmlUtils {

    /**
     * 解析 assert 目录下面的 xml 文件。
     * 用法举例:
     * 比如要读取 assets/test.xml)，你需要这样做：
     * 1. 如果没有 assets 文件夹，请先新建，其中 assets 与 java 和 res 在同一个层次。
     * 2. 调用该方法，传入 test.xml 文件名，记得包含后缀
     * 3. 针对 xml 的具体的字段，修改该方法中对应的字段，以获取到对应的 key 保存的 object 对象
     * 4. 将 object 对象取出（通过 map#get）,并转换为具体的类型(String,Int...)
     *
     * @param context  上下文
     * @param fileName xml 的文件名 包含后缀
     * @return Map<String, Object>
     */
    public static Map<String, Object> parseXMLFromAsserts(Context context, String fileName) {
        int index = 0;
        Map<String, Object> map = new HashMap<>();
        try {
            try {
                InputStream is = context.getResources().getAssets().open(fileName);
                XmlPullParser xmlParser = Xml.newPullParser();
                try {
                    xmlParser.setInput(is, "UTF-8");
                    int event = xmlParser.getEventType();
                    while (event != XmlPullParser.END_DOCUMENT) {
                        switch (event) {
                            case XmlPullParser.START_DOCUMENT:
                                break;
                            case XmlPullParser.START_TAG:
                                // String title = xmlParser.nextText();
                                // String bg = xmlParser.getAttributeValue(null, "Background");

                                index++;
                                String parserName = xmlParser.getName();
                                if ("realName".equals(parserName)) {
                                    String realName = xmlParser.nextText();
                                    map.put("realName", realName);
                                    Log.d(parserName, (String) map.get("realName"));
                                } else if ("identityNumber".equals(parserName)) {
                                    String identityNumber = xmlParser.nextText();
                                    map.put("identityNumber", identityNumber);
                                    Log.d(parserName, (String) map.get("identityNumber"));
                                } else if ("phone".equals(parserName)) {
                                    String phone = xmlParser.nextText();
                                    map.put("phone", phone);
                                    Log.d(parserName, (String) map.get("phone"));
                                } else if ("value".equals(parserName)) {
                                    String value = xmlParser.nextText();
                                    map.put("value" + index, value);
                                    Log.d(parserName, (String) map.get("value" + index));
                                } else if ("name".equals(parserName)) {
                                    String name = xmlParser.nextText();
                                    map.put("name" + index, name);
                                    Log.d(parserName, (String) map.get("name" + index));
                                } else if ("hehe".equals(parserName)) {
                                    String hehe = xmlParser.nextText();
                                    map.put("hehe" + index, hehe);
                                    Log.d(parserName, (String) map.get("hehe" + index));
                                } else if ("age".equals(parserName)) {
                                    String age = xmlParser.nextText();
                                    map.put("age" + index, age);
                                    Log.d(parserName, (String) map.get("age" + index));
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                break;
                        }
                        event = xmlParser.next();
                    }
                    is.close();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

}