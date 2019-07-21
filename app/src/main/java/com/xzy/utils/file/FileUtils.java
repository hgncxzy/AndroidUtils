package com.xzy.utils.file;

import android.annotation.SuppressLint;
import android.os.Environment;

import com.xzy.utils.R;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 文件操作工具类。
 */
@SuppressWarnings("all")
public class FileUtils {

    /**
     * 文档类型
     */
    public static final int TYPE_DOC = 0;
    /**
     * apk类型
     */
    public static final int TYPE_APK = 1;
    /**
     * 压缩包类型
     */
    public static final int TYPE_ZIP = 2;


    /**
     * 判断文件是否存在
     *
     * @param path 文件的路径
     * @return
     */
    public static boolean isExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static int getFileType(String path) {
        path = path.toLowerCase();
        if (path.endsWith(".doc")
                || path.endsWith(".docx")
                || path.endsWith(".xls")
                || path.endsWith(".xlsx")
                || path.endsWith(".ppt")
                || path.endsWith(".pptx")) {
            return TYPE_DOC;
        } else if (path.endsWith(".apk")) {
            return TYPE_APK;
        } else if (path.endsWith(".zip")
                || path.endsWith(".rar")
                || path.endsWith(".tar")
                || path.endsWith(".gz")) {
            return TYPE_ZIP;
        } else {
            return -1;
        }
    }


    /**
     * 通过文件名获取文件图标
     */
    public static int getFileIconByFilePath(String path) {
        path = path.toLowerCase();
        int iconId = R.mipmap.ic_launcher;
        if (path.endsWith(".txt")) {
            iconId = R.mipmap.icon_file_txt;
        } else if (path.endsWith(".doc") || path.endsWith(".docx")) {
            iconId = R.mipmap.icon_file_word;
        } else if (path.endsWith(".xls") || path.endsWith(".xlsx")) {
            iconId = R.mipmap.icon_file_excel;
        } else if (path.endsWith(".ppt") || path.endsWith(".pptx")) {
            iconId = R.mipmap.icon_file_ppt;
        } else if (path.endsWith(".zip")
                || path.endsWith(".rar")
                || path.endsWith(".tar")
                || path.endsWith(".gz")) {
            iconId = R.mipmap.icon_file_zip;
        } else if (path.endsWith(".mp4")) {
            iconId = R.mipmap.icon_file_mp4;
        } else if (path.endsWith(".mp3")) {
            iconId = R.mipmap.icon_file_music;
        } else if (path.endsWith(".pdf")) {
            iconId = R.mipmap.icon_file_pdf;
        }
        return iconId;
    }

    /**
     * 是否是图片文件
     */
    public static boolean isPictureFile(String path) {
        path = path.toLowerCase();
        if (path.endsWith(".jpg")
                || path.endsWith(".jpeg")
                || path.endsWith(".png")
                || path.endsWith(".webp")
                || path.endsWith(".bmp")
        ) {
            return true;
        }
        return false;
    }


    /**
     * 判断SD卡是否挂载
     */
    public static boolean isSDCardAvailable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 从文件的全名得到文件的拓展名
     *
     * @param filename
     * @return
     */
    public static String getExtNameFromFilename(String filename) {
        int dotPosition = filename.lastIndexOf('.');
        if (dotPosition != -1) {
            return filename.substring(dotPosition + 1, filename.length());
        }
        return "";
    }

    /**
     * 读取文件的修改时间
     *
     * @param f
     * @return
     */
    public static String getFileModifiedTime(File f) {
        Calendar cal = Calendar.getInstance();
        long time = f.lastModified();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter
                = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cal.setTimeInMillis(time);
        // System.out.println("修改时间[2] " + formatter.format(cal.getTime()));
        // 输出：修改时间[2] 2009-08-17 10:32:38
        return formatter.format(cal.getTime());
    }


    /**
     * 得到关于文件大小的描述信息。
     *
     * @param size 文件大小 long 值
     * @return 文件大小的描述信息字符串。
     */
    public static String getFileSizeDescription(long size) {
        StringBuilder bytes = new StringBuilder();
        DecimalFormat format = new DecimalFormat("###.0");
        if (size >= 1024 * 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0 * 1024.0));
            bytes.append(format.format(i)).append("GB");
        } else if (size >= 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0));
            bytes.append(format.format(i)).append("MB");
        } else if (size >= 1024) {
            double i = (size / (1024.0));
            bytes.append(format.format(i)).append("KB");
        } else {
            if (size <= 0) {
                bytes.append("0B");
            } else {
                bytes.append((int) size).append("B");
            }
        }
        return bytes.toString();
    }

}
