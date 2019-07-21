package com.xzy.utils.log;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志工具类。
 *
 * @author xzy
 */
@SuppressWarnings("all")
public class L {
    @SuppressLint("StaticFieldLeak")
    private static L mInstance;
    private static final Object mLock = new Object();
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    public static L getInstance(Context context) {
        if (mInstance == null) {
            synchronized (mLock) {
                if (mInstance == null) {
                    mInstance = new L();
                    mContext = context;
                }
            }
        }
        return mInstance;
    }

    /**
     * 是否打印日志
     */
    public static final boolean isDebug = true;
    private static String className;
    private static final String TAG = "xzy";
    private static long fileSize = 0;
    private static boolean isLog2File = false;

    public static boolean isIsDebug() {
        return isDebug;
    }

    public static String getTAG() {
        return TAG;
    }

    public static boolean isIsLog2File() {
        return isLog2File;
    }

    public static void setIsLog2File(boolean isLog2File) {
        L.isLog2File = isLog2File;
    }

    @SuppressLint("SimpleDateFormat")
    // 日志的输出格式
    private static final SimpleDateFormat LOG_SDF =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @SuppressLint("SimpleDateFormat")
    // 日志文件格式
    private static final SimpleDateFormat LOG_FILE =
            new SimpleDateFormat("yyyy-MM-dd");

    // 下面四个是默认 tag 的函数
    public static void i(String msg) {
        if (isIsDebug()) i(getTAG(), msg);
    }

    public static void d(String msg) {
        if (isIsDebug()) d(getTAG(), msg);
    }

    public static void e(String msg) {
        if (isIsDebug()) e(getTAG(), msg);
    }

    public static void w(Throwable ex) {
        if (isIsDebug()) w(getTAG(), ex);
    }

    public static void i(String tag, String info) {
        if (isIsDebug()) {
            try {
                className = (new Throwable().getStackTrace())[1].getFileName();
                if (className == null) {
                    className = "";
                }
            } catch (Exception ignored) {
            }
            Log.i(getTAG(), className.replace(".java", "") + " -> " + info);
        }
        onLog2File(info, className);
    }

    public static void d(String tag, String info) {
        if (isIsDebug()) {
            try {
                className = (new Throwable().getStackTrace())[1].getFileName();
                if (className == null) {
                    className = "";
                }
            } catch (Exception ignored) {
            }
            Log.d(getTAG(), className.replace(".java", "") + " -> " + info);
        }
        onLog2File(info, className);
    }

    public static void e(String tag, String info) {
        if (isIsDebug()) {
            try {
                className = (new Throwable().getStackTrace())[1].getFileName();
                if (className == null) {
                    className = "";
                }
            } catch (Exception ignored) {
            }
            Log.e(getTAG(), className.replace(".java", "") + " -> " + info);
        }
        onLog2File(info, className);
    }

    public static void w(String tag, Throwable ex) {
        if (isIsDebug()) {
            try {
                className = (new Throwable().getStackTrace())[1].getFileName();
                if (className == null) {
                    className = "";
                }
            } catch (Exception ignored) {
            }
            Log.i(getTAG(), className.replace(".java", "") + " -> ");
            Log.w(getTAG(), ex);
        }
        onLog2File(ex.getMessage(), className);
    }

    private static File getFilePath(Context context) {
        return context.getExternalFilesDir(Environment.DIRECTORY_PODCASTS);
    }

    private static synchronized void SwitchFile() {
        if (fileSize > 1024 * 1024) {
            for (int i = 5; i > 0; i--) {
                File file = new File(getFilePath(mContext)
                        + "/log/", "running_log" + (i - 1) + ".txt");
                if (file.exists()) {
                    File temp = new File(getFilePath(mContext)
                            + "/log/", "running_log" + i + ".txt");
                    file.renameTo(temp);
                }
            }
            File file = new File(getFilePath(mContext)
                    + "/log/", "running_log.txt");
            if (file.exists()) {
                File temp = new File(getFilePath(mContext)
                        + "/log/", "running_log1.txt");
                file.renameTo(temp);
            }
        }
    }

    /**
     * 打开日志文件并写入日志
     *
     * @param text 需要写入的日志信息
     * @param type 日志类型
     */
    private static void onLog2File(String text, String type) {
        if (!isIsLog2File()) {
            return;
        }
        Date nowTime = new Date();
        String needWriteFile = LOG_FILE.format(nowTime);
        String needWriteMessage = LOG_SDF.format(nowTime)
                + "    "
                + type
                + " "
                + getTAG()
                + "    "
                + text;
        File temp = new File(Environment.getRootDirectory() + "/log");
        if (!temp.exists()) {
            temp.mkdirs();
        }
        SwitchFile();
        File file = new File(getFilePath(mContext)
                + "/log/", "running_log.txt");
        try {
            fileSize = file.length();
            // 后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖
            FileWriter filerWriter = new FileWriter(file, true);
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(needWriteMessage);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
