package com.xzy.utils.http;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Objects;

/**
 * Http请求的工具类。
 *
 * @author xzy
 */
@SuppressWarnings("unused")
public class HttpUtils {

    private static final int TIMEOUT_IN_MILLIONS = 5000;

    /**
     * 回调接口。
     */
    interface HttpCallBackListener {

        /**
         * 回调成功。
         * @param response 成功响应
         */
        void onFinish(String response);

        /**
         * 回调失败。
         * @param e 异常回调
         */
        void onError(Exception e);
    }


    /**
     * 异步的Get请求
     *
     * @param urlStr   urlStr
     * @param callBack CallBack
     */
    public static void doGetAsync(final String urlStr,
                                  final Map<String,String> params,
                                  final HttpCallBackListener callBack) {
        new Thread() {
            public void run() {
                try {
                    doGet(urlStr,params,callBack);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 异步的Post请求
     *
     * @param urlStr   请求 url
     * @param params map 类型的请求参数
     * @param callBack 回调
     */
    public static void doPostAsync(final String urlStr,
                                   final Map<String, String> params,
                                   final HttpCallBackListener callBack) {
        new Thread() {
            public void run() {
                try {
                    doPost(urlStr, params,callBack);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 供外部调用的 get 请求。
     * @param path 请求 url
     * @param params 请求参数 map 类型
     */
    public static void doGet(String path, Map<String, String> params,
                           HttpCallBackListener httpCallBackListener){
        StringBuilder sb = new StringBuilder(path);
        if(params!=null && !params.isEmpty()){
            sb.append("?");
            for(Map.Entry<String, String> entry : params.entrySet()){
                sb.append(entry.getKey()).append("=");
                try {
                    sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    if(httpCallBackListener != null){
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
            if(httpCallBackListener != null){
                httpCallBackListener.onError(e);
            }
        }
        Objects.requireNonNull(conn).setConnectTimeout(5000);
        try {
            conn.setRequestMethod("GET");
        } catch (ProtocolException e) {
            if(httpCallBackListener!= null){
                httpCallBackListener.onError(e);
            }
        }
        try {
            if(conn.getResponseCode() == 200){
                InputStream in = conn.getInputStream();
                BufferedReader bufferReader = new BufferedReader(new InputStreamReader(in));
                String line;
                StringBuilder response = new StringBuilder();
                while((line = bufferReader.readLine())!= null){
                    response.append(line);
                }
                if(httpCallBackListener != null){
                    httpCallBackListener.onFinish(response.toString());
                }
            }
        } catch (IOException e) {
            if(httpCallBackListener != null){
                httpCallBackListener.onError(e);
            }
        }finally {
            conn.disconnect();
        }
    }

    /**
     * @param path 请求的 url
     * @param params map 类型的请求参数
     * @param httpCallBackListener 回调
     */
    public static void doPost(String path, Map<String, String> params,
                            HttpCallBackListener httpCallBackListener){
        StringBuilder sb = new StringBuilder();
        if(params!=null && !params.isEmpty()){
            for(Map.Entry<String, String> entry : params.entrySet()){
                sb.append(entry.getKey()).append("=");
                try {
                    sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    if(httpCallBackListener != null){
                        httpCallBackListener.onError(e);
                    }
                }
                sb.append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        byte[] data = sb.toString().getBytes();
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(path).openConnection();
        }catch (IOException e) {
            if(httpCallBackListener != null){
                httpCallBackListener.onError(e);
            }
        }
        Objects.requireNonNull(conn).setConnectTimeout(5000);
        try {
            conn.setRequestMethod("POST");
        } catch (ProtocolException e) {
            if(httpCallBackListener != null){
                httpCallBackListener.onError(e);
            }
        }
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", data.length+"");
        OutputStream outStream = null;
        try {
            outStream = conn.getOutputStream();
        } catch (IOException e) {
            if(httpCallBackListener != null){
                httpCallBackListener.onError(e);
            }
        }
        try {
            Objects.requireNonNull(outStream).write(data);
            outStream.flush();
            if(conn.getResponseCode() == 200){
                InputStream in = conn.getInputStream();
                BufferedReader bufferReader = new BufferedReader(new InputStreamReader(in));
                String line;
                StringBuilder response = new StringBuilder();
                while((line = bufferReader.readLine())!= null){
                    response.append(line);
                }
                if(httpCallBackListener != null){
                    httpCallBackListener.onFinish(response.toString());
                }
            }
        } catch (IOException e) {
            if(httpCallBackListener != null){
                httpCallBackListener.onError(e);
            }
        }finally {
            conn.disconnect();
        }
    }
}
