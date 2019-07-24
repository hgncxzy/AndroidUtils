package com.xzy.utils.crash;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;

import com.xzy.utils.log.L;
import com.xzy.utils.toast.T;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * UncaughtException 处理类,当程序发生未捕获到异常的时候,由该类来接管程序,
 * 并记录发送错误报告. 需要在 Application 中注册，为了要在程序启动时期
 * 就监控整个程序。
 * 使用方式  在Application 中初始化 CrashHandler#getInstance#init
 *
 * @author xzy
 */
@SuppressWarnings("ALL")
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";

    /**
     * 私有构造函数
     */
    private CrashHandler() {

    }

    /**
     * 系统默认的UncaughtException处理类
     */
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    /**
     * CrashHandler实例
     */
    private static CrashHandler mCrashHandler;
    /**
     * 程序的Context对象
     */
    private Context mContext;
    /**
     * 用来存储设备信息和异常信息
     */
    private Map<String, String> map = new HashMap<>();
    /**
     * 用于格式化日期,作为日志文件名的一部分
     */
    @SuppressLint("SimpleDateFormat")
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    /**
     * 获取CrashHandler实例 ,单例模式
     *
     * @return CrashHandler
     */
    public static CrashHandler getInstance() {
        if (mCrashHandler == null) {
            mCrashHandler = new CrashHandler();
        }
        return mCrashHandler;
    }

    /**
     * 初始化
     *
     * @param context context
     */
    public void init(Context context) {
        mContext = context;
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当 UncaughtException 发生时会转入该函数来处理
     *
     * @param thread Thread
     * @param ex     Throwable
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                L.e(TAG, "uncaughtException() InterruptedException:" + e);
            }
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex Throwable
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        // 收集设备参数信息
        collectDeviceInfo(mContext);

        // 使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                T.showShort(mContext, "很抱歉,程序出现异常,即将退出.");
                Looper.loop();
            }
        }.start();

        // 保存日志文件
        saveCrash2File(ex);
        return true;
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx Context
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                map.put("versionName", versionName);
                map.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            L.e(TAG, "collectDeviceInfo() an error occured when collect " +
                    "package info NameNotFoundException:" + e.getMessage());
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                map.put(field.getName(), field.get(null).toString());
                L.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                L.e(TAG, "collectDeviceInfo() an error occured when " +
                        "collect crash info Exception:" +
                        e.getMessage());
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex Throwable
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCrash2File(Throwable ex) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append("=").append(value).append("\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());
            String fileName = "crash-" + time + "-" + timestamp + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = Environment.getRootDirectory() + "/crash/";
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes());
                // 发送给开发人员
                sendCrashLog2PM(path + fileName);
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
            L.e(TAG, "saveCatchInfo2File() an error occured " +
                    "while writing file... Exception:" + e.getMessage());
        }
        return null;
    }

    /**
     * 将捕获的导致崩溃的错误信息发送给开发人员 目前只将log
     * 日志保存在sdcard 和输出到LogCat中，并未发送给后台。
     *
     * @param fileName 文件名
     */
    private void sendCrashLog2PM(String fileName) {
        if (!new File(fileName).exists()) {
            L.e(TAG, "sendCrashLog2PM() 日志文件不存在");
            return;
        }
        FileInputStream fis = null;
        BufferedReader reader = null;
        String s;
        try {
            fis = new FileInputStream(fileName);
            reader = new BufferedReader(new InputStreamReader(fis, "GBK"));
            while (true) {
                s = reader.readLine();
                if (s == null)
                    break;
                // 由于目前尚未确定以何种方式发送，所以先打出log日志。
                L.e(TAG, s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally { // 关闭流
            try {
                Objects.requireNonNull(reader).close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

