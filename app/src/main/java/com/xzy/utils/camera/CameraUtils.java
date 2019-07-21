package com.xzy.utils.camera;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;


import com.xzy.utils.log.L;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Function:相机工具类、调用相机拍照、调用相机录像并保存到目录
 *
 * @author xzy
 */
@SuppressWarnings("ALL")
public class CameraUtils {
    public static File tempFile;
    public static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    public static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 2;// 拍摄视频

    /**
     * 打开相机拍照
     *
     * @param activity
     */
    public static void openCamera(Context context) {
        // 獲取系統版本
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        // 激活相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                    "yyyy_MM_dd_HH_mm_ss");
            String filename = timeStampFormat.format(new Date());
            tempFile = new File(Environment.getExternalStorageDirectory(),
                    filename + ".jpg");
            if (currentapiVersion < 24) {
                // 从文件中创建uri
                Uri uri = Uri.fromFile(tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            } else {
                //兼容android7.0 使用共享文件的形式
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
                Uri uri = context.getApplicationContext().getContentResolver()
                        .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            }
        }
        // 开启一个带有返回值的Activity，请求码为 PHOTO_REQUEST_CAREMA
        ((Activity) context).startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
    }


    /**
     * 打开相机录像
     */
    public static void startToRecordVideo(Activity activity) {
        //獲取系統版本
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        Uri fileUri = null;
        File file = null;
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        file = createMediaFile();
        if (file.exists()) {
            // create a file to save the video
            fileUri = Uri.fromFile(file);
        }
        if (currentapiVersion < 24) {
            // set the image file name
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            // set the video image quality to high
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        } else {
            // 兼容 android7.0
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
            Uri uri = activity.getApplication().getContentResolver()
                    .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        // start the Video Capture Intent
        activity.startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
    }


    /**
     * 判断 sdcard 是否被挂载
     *
     * @return boolean 有 sd 卡，返回 true，否则返回 false
     */
    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 创建并保存录制得到的视频文件
     *
     * @return File 视频文件
     */
    public static File createMediaFile() {
        if (hasSdcard()) {
            File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_MOVIES), "CameraVideos");
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    return null;
                }
            }
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "VID_" + timeStamp;
            String suffix = ".mp4";
            File mediaFile = new File(mediaStorageDir + File.separator + imageFileName + suffix);
            return mediaFile;
        } else {
            L.e("SD卡没有被挂载");
        }
        return null;
    }
}

