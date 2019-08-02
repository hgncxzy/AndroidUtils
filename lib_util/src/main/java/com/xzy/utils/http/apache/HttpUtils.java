package com.xzy.utils.http.apache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * 使用 apache 接口实现的 get 请求、post 请求、图片下载 封装工具类。
 * 需要在 build.gradle 中设置使用 apache 依赖库 useLibrary 'org.apache.http.legacy'
 * 并且添加 apache 依赖  implementation 'org.apache:apache:21'
 *
 * @author xzy
 */
@SuppressWarnings("ALL")
class HttpUtils {

    /**
     * apache 方式实现的 get 请求。
     *
     * @param url 请求 url
     */
    public static String get(String url) {
        HttpResponse httpResponse;
        StringBuilder result = new StringBuilder();
        try {
            HttpGet httpGet = new HttpGet(url);
            HttpClient httpClient = new DefaultHttpClient();
            httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            StringBuilder append = result.append(EntityUtils.
                    toString(httpEntity, "utf-8"));
            StatusLine statusLine = httpResponse.getStatusLine();
            result.append(statusLine.getProtocolVersion()).append("\r\n");
            int statusCode = statusLine.getStatusCode();
            result.append(statusCode).append("\r\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * apache 方式实现的 post 请求。
     *
     * @param url 请求 url
     */
    public static String post(String url) {
        HttpResponse httpResponse;
        StringBuilder result = new StringBuilder();
        try {
            HttpPost httpPost = new HttpPost(url);
            HttpClient httpClient = new DefaultHttpClient();
            httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            StringBuilder append = result.append(EntityUtils.
                    toString(httpEntity, "utf-8"));
            StatusLine statusLine = httpResponse.getStatusLine();
            statusLine.getProtocolVersion();
            int statusCode = statusLine.getStatusCode();
            result.append(statusCode).append("\r\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * apache 接口实现的图片下载。
     *
     * @param url 请求图片的 url。
     * @return bitmap
     */
    public static Bitmap getImage(String url) {
        try {
            HttpGet httpGet = new HttpGet(url);
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            InputStream inputStream = httpEntity.getContent();
            System.out.println(inputStream.available());
            return BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 回调接口。
     */
    interface HttpCallBackListener {

        /**
         * 回调成功。
         *
         * @param response 成功响应
         */
        void onFinish(String response);

        /**
         * 回调失败。
         *
         * @param e 异常回调
         */
        void onError(Exception e);
    }
}
