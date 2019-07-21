package com.xzy.utils.file;


import android.content.Intent;
import android.net.Uri;

import java.io.File;
import java.util.Locale;

/**
 * Android中如何使用代码打开各种类型的文件。
 *
 * @author xzy
 */
@SuppressWarnings("unused")
public class OpenFileUtils {

    public static Intent openFile(String filePath) {

        File file = new File(filePath);
        if (!file.exists())
            return null;
        // 取得扩展名
        String end = file.getName().substring(file.getName()
                .lastIndexOf(".") + 1).toLowerCase(Locale.getDefault());
        // 依扩展名的类型决定MimeType
        switch (end) {
            case "m4a":
            case "mp3":
            case "mid":
            case "xmf":
            case "ogg":
            case "wav":
                return getAudioFileIntent(filePath);
            case "3gp":
            case "mp4":
                return getVideoFileIntent(filePath);
            case "jpg":
            case "gif":
            case "png":
            case "jpeg":
            case "bmp":
                return getImageFileIntent(filePath);
            case "apk":
                return getApkFileIntent(filePath);
            case "ppt":
                return getPptFileIntent(filePath);
            case "xls":
                return getExcelFileIntent(filePath);
            case "doc":
                return getWordFileIntent(filePath);
            case "pdf":
                return getPdfFileIntent(filePath);
            case "chm":
                return getChmFileIntent(filePath);
            case "txt":
                return getTextFileIntent(filePath, false);
            default:
                return getAllIntent(filePath);
        }
    }

    /**
     * Android 获取一个用于打开某个类型文件的 intent
     *
     * @param path 文件路径
     * @return Intent
     */
    public static Intent getAllIntent(String path) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(path));
        intent.setDataAndType(uri, "*/*");
        return intent;
    }

    /**
     * Android 获取一个用于打开 APK 文件的 intent
     *
     * @param path 文件路径
     * @return Intent
     */
    public static Intent getApkFileIntent(String path) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(path));
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        return intent;
    }

    /**
     * Android 获取一个用于打开 VIDEO 文件的 intent
     *
     * @param path 文件路径
     * @return Intent
     */
    public static Intent getVideoFileIntent(String path) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(new File(path));
        intent.setDataAndType(uri, "video/*");
        return intent;
    }

    /**
     * Android 获取一个用于打开 AUDIO 文件的 intent
     *
     * @param path 文件路径
     * @return Intent
     */
    public static Intent getAudioFileIntent(String path) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(new File(path));
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }

    /**
     * Android 获取一个用于打开 Html 文件的 intent
     *
     * @param path html 路径
     * @return
     */
    public static Intent getHtmlFileIntent(String path) {
        Uri uri = Uri.parse(path).buildUpon()
                .encodedAuthority("com.android.htmlfileprovider")
                .scheme("content").encodedPath(path).build();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "text/html");
        return intent;
    }

    /**
     * Android 获取一个用于打开图片文件的 intent
     *
     * @param path 图片路径
     * @return Intent
     */
    public static Intent getImageFileIntent(String path) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(path));
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    /**
     * Android 获取一个用于打开 PPT 文件的 intent
     *
     * @param path 文件路径
     * @return Intent
     */
    public static Intent getPptFileIntent(String path) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(path));
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    /**
     * Android 获取一个用于打开 Excel 文件的 intent
     *
     * @param path 文件路径
     * @return Intent
     */
    public static Intent getExcelFileIntent(String path) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(path));
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    /**
     * Android 获取一个用于打开 Word 文件的 intent
     *
     * @param path 文件路径
     * @return Intent
     */
    public static Intent getWordFileIntent(String path) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(path));
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }


    /**
     * Android 获取一个用于打开 CHM 文件的 intent
     *
     * @param path 文件路径
     * @return Intent
     */
    public static Intent getChmFileIntent(String path) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(path));
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }


    /**
     * Android获取一个用于打开文本文件的intent
     *
     * @param path      文件路径 或者 uri 路径
     * @param isUriPath 是否是 uri 路径
     * @return Intent
     */
    public static Intent getTextFileIntent(String path, boolean isUriPath) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (isUriPath) {
            Uri uri1 = Uri.parse(path);
            intent.setDataAndType(uri1, "text/plain");
        } else {
            Uri uri2 = Uri.fromFile(new File(path));
            intent.setDataAndType(uri2, "text/plain");
        }
        return intent;
    }


    /**
     * Android 获取一个用于打开 PDF 文件的 intent
     *
     * @param path 文件路径
     * @return Intent
     */
    public static Intent getPdfFileIntent(String path) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(path));
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }

}
