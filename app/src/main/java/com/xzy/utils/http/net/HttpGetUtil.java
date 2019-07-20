package com.xzy.utils.http.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Objects;

/**
 * get 请求工具类封装。使用的是标准的 java 接口：java.net
 *
 * @author xzy
 */
@SuppressWarnings("unused")
class HttpGetUtil {
    /**
     * 供外部调用的 get 请求。
     *
     * @param path   请求 url
     * @param params 请求参数 map 类型
     */
    static void getRequest(String path, Map<String, String> params,
                           HttpCallBackListener httpCallBackListener) {
        StringBuilder sb = new StringBuilder(path);
        if (params != null && !params.isEmpty()) {
            sb.append("?");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(entry.getKey()).append("=");
                try {
                    sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    if (httpCallBackListener != null) {
                        httpCallBackListener.onError(e);
                    }
                }
                sb.append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(sb.toString()).openConnection();
        } catch (IOException e) {
            if (httpCallBackListener != null) {
                httpCallBackListener.onError(e);
            }
        }
        Objects.requireNonNull(conn).setConnectTimeout(5000);
        try {
            conn.setRequestMethod("GET");
        } catch (ProtocolException e) {
            if (httpCallBackListener != null) {
                httpCallBackListener.onError(e);
            }
        }
        try {
            if (conn.getResponseCode() == 200) {
                InputStream in = conn.getInputStream();
                BufferedReader bufferReader = new BufferedReader(new InputStreamReader(in));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = bufferReader.readLine()) != null) {
                    response.append(line);
                }
                if (httpCallBackListener != null) {
                    httpCallBackListener.onFinish(response.toString());
                }
            }
        } catch (IOException e) {
            if (httpCallBackListener != null) {
                httpCallBackListener.onError(e);
            }
        } finally {
            conn.disconnect();
        }
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


