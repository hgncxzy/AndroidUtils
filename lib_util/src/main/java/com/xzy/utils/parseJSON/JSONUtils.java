package com.xzy.utils.parseJSON;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * json 处理工具类,
 * 从 internet 获取 json 数据，并转换为 list。
 *
 * @author xzy
 */
@SuppressWarnings("unused")
public class JSONUtils {

    /**
     * 解析 jsonArray 字符串数组
     * 要解析的数据结构 [{"stuNo":100,"name":"小明"},{"stuNo":101,"name":"小张"}]
     *
     * @param jsonArrayStr jsonArray 字符串
     * @return List<Map < String, Object>>
     */
    public static List<Map<String, Object>> parseJsonArray(String jsonArrayStr) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map;
        // 数据形式：[{"stuNo":100,"name":"小明"},{"stuNo":101,"name":"小张"}]
        // 数据为数组形式，
        // 直接用 android框架
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(jsonArrayStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                // 获取每条数据中的对象
                JSONObject item = jsonArray.getJSONObject(i);
                // 注意key值要一致*/
                Object stuNo = item.getJSONObject("stuNo");
                Object name = item.getJSONObject("name");
                map = new HashMap<>();
                map.put("stuNo", stuNo);
                map.put("name", name);
                list.add(map);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 解析"对象形式"的JSON数据
     * 数据形式：{"total":2,"success":true,"appList":[{"id":1,"name":"小猪"},
     * {"id":2,"name":"小猫"}]}
     * 返回的数据形式是一个 Object 类型，所以可以直接转换成一个 Object
     *
     * @param jsonObject 需要解析的 json 对象
     * @return 返回 List
     */
    public static List<Map<String, Object>> parseJsonObject(String jsonObject) throws Exception {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map;
        JSONObject object = new JSONObject(jsonObject);
        // json对象中有一个数组数据，又可以使用 getJSONArray 获取数组
        JSONArray jsonArray = object.getJSONArray("appList");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            Object id = item.getJSONObject("id");
            Object name = item.getJSONObject("name");
            map = new HashMap<>();
            map.put("id", id);
            map.put("name", name);
            list.add(map);
        }
        return list;
    }


    /**
     * 解析类型复杂的 JSON 数据
     * <p>
     * 数据形式：
     * {"name":"小猪", "age":23, "content":{"questionsTotal":2, "questions":
     * [ { "question": "what's your name?",
     * "answer": "小猪"}, {"question": "what's your age", "answer": "23"}] } }
     *
     * @param complexJsonStr 复杂 json 对象的字符串
     * @return list
     */
    public static List<Map<String, Object>> parseComplexJson(String complexJsonStr) throws Exception {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map;
        JSONObject jsonObject = new JSONObject(complexJsonStr);
        String name = jsonObject.getString("name");
        int age = jsonObject.getInt("age");
        Log.i("abc", "name:" + name + " | age:" + age);
        JSONObject contentObject = jsonObject.getJSONObject("content");
        String questionsTotal = contentObject.getString("questionsTotal");
        JSONArray contentArray = contentObject.getJSONArray("questions");
        for (int i = 0; i < contentArray.length(); i++) {
            JSONObject item = contentArray.getJSONObject(i);
            Object question = item.getJSONObject("question");
            Object answer = item.getJSONObject("answer");
            map = new HashMap<>();
            map.put("question", question);
            map.put("answer", answer);
            map.put("total", questionsTotal);
            list.add(map);
        }
        return list;
    }


    /**
     * 获取"数组形式"的JSON数据
     * 要解析的数据结构 [{"stuNo":100,"name":"小明"},{"stuNo":101,"name":"小张"}]
     *
     * @param path 请求 url
     * @return list
     */
    public static List<Map<String, String>> getJSONArray(String path) {
        String json;
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map;
        URL url = null;
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        // HttpURLConnection对象,从网络中获取网页数据
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) Objects.requireNonNull(url).openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 设置超时时间为5秒
        Objects.requireNonNull(conn).setConnectTimeout(5 * 1000);
        // HttpURLConnection是通过HTTP协议请求path路径的，所以需要设置请求方式,可以不设置，
        // 因为默认为 GET
        try {
            conn.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        // 判断请求是否成功，成功时请求码为200，否则失败
        try {
            if (conn.getResponseCode() == 200) {
                // 获取数据输入流
                InputStream is = conn.getInputStream();
                // 把输入流转换成字符数组
                byte[] data = readStream2Array(is);
                // 字符数组转换成字符串
                json = new String(data);
                // 数据形式：[{"stuNo":100,"name":"小明"},{"stuNo":101,"name":"小张"}]
                // 数据为数组形式，
                // 直接用 android框架
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    // 获取每条数据中的对象
                    JSONObject item = jsonArray.getJSONObject(i);
                    // 注意key值要一致*/
                    String stuNo = item.getString("stuNo");
                    String name = item.getString("name");
                    map = new HashMap<>();
                    map.put("stuNo", stuNo);
                    map.put("name", name);
                    list.add(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取"对象形式"的JSON数据
     * 数据形式：{"total":2,"success":true,"appList":[{"id":1,"name":"小猪"},
     * {"id":2,"name":"小猫"}]}
     * 返回的数据形式是一个 Object 类型，所以可以直接转换成一个 Object
     *
     * @param path 请求 url
     * @return 返回 List
     */
    public static List<Map<String, String>> getJSONObject(String path) throws Exception {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map;
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 单位是毫秒，设置超时时间为5秒
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == 200) {
            InputStream is = conn.getInputStream();
            byte[] data = readStream2Array(is);
            String json = new String(data);
            JSONObject jsonObject = new JSONObject(json);
            // json对象中有一个数组数据，又可以使用getJSONArray获取数组
            JSONArray jsonArray = jsonObject.getJSONArray("appList");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                String id = item.getString("id");
                String name = item.getString("name");
                map = new HashMap<>();
                map.put("id", id);
                map.put("name", name);
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 获取类型复杂的 JSON 数据
     * <p>
     * 数据形式： {"name":"小猪", "age":23, "content":{"questionsTotal":2, "questions":
     * [ { "question": "what's your name?",
     * "answer": "小猪"}, {"question": "what's your age", "answer": "23"}] } }
     *
     * @param path 请求 url
     * @return list
     */
    public static List<Map<String, String>> getComplexJSON(String path) {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map;
        URL url = null;
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) Objects.requireNonNull(url).openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Objects.requireNonNull(conn).setConnectTimeout(5 * 1000);
        try {
            conn.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        try {
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                byte[] data = readStream2Array(is);
                String json = new String(data);

                JSONObject jsonObject = new JSONObject(json);
                String name = jsonObject.getString("name");
                int age = jsonObject.getInt("age");
                Log.i("abc", "name:" + name + " | age:" + age);
                JSONObject contentObject = jsonObject.getJSONObject("content");
                String questionsTotal = contentObject.getString("questionsTotal");
                JSONArray contentArray = contentObject.getJSONArray("questions");
                for (int i = 0; i < contentArray.length(); i++) {
                    JSONObject item = contentArray.getJSONObject(i);
                    String question = item.getString("question");
                    String answer = item.getString("answer");
                    map = new HashMap<>();
                    map.put("question", question);
                    map.put("answer", answer);
                    map.put("total", questionsTotal);
                    list.add(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 把输入流转换成字符数组
     */
    private static byte[] readStream2Array(InputStream inputStream) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while (true) {
            try {
                if ((len = inputStream.read(buffer)) == -1) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            bout.write(buffer, 0, len);
        }
        try {
            bout.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bout.toByteArray();
    }
}
