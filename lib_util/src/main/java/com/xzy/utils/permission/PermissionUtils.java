package com.xzy.utils.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Build;

import java.util.ArrayList;

/**
 * 权限工具类。
 * 用法见博客 https://blog.csdn.net/jdfkldjlkjdl/article/details/78365651
 *
 * @author xzy
 */
@SuppressWarnings("unused")
public class PermissionUtils {

    public static final int REQUEST_PERMISSION_CODE = 1001;

    /**
     * 默认构造方法
     */
    private PermissionUtils() {
    }

    /**
     * 定义一个接口，处理回调结果
     */
    public interface PermissionRequestListener {
        /**
         * 处理回调结果的接口方法
         *
         * @param reqCode 请求码
         * @param isAllow 是否被授权(eg:如有 N 个权限，只要有其中一个权限没有被授予,
         *                那么 isAllow 为 false，否则为 true)
         */
        void onPermissionReqResult(int reqCode, boolean isAllow);
    }


    /**
     * 处理回调结果
     *
     * @param listener     回调结果接口方法
     * @param reqCode      请求码
     * @param grantResults 权限请求结果集
     */
    public static void solvePermissionRequest(PermissionRequestListener listener,
                                              int reqCode, int[] grantResults) {
        if (grantResults == null) {
            return;
        }
        boolean isAllow = true;
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                isAllow = false;
                break;
            }
        }
        if (listener != null) {
            // 调用接口
            listener.onPermissionReqResult(reqCode, isAllow);
        }
    }

    /**
     * @param context     上下文
     * @param permissions 权限数组
     * @param requestCode 请求码
     * @return boolean 检查是否需要进行权限动态申请 true：需要动态申请，false：不需要动态申请
     */
    public static boolean judgePermissionOver23(Context context, String[] permissions,
                                                int requestCode) {
        try {
            if (permissions == null || permissions.length == 0) {
                return true;
            }
            if (Build.VERSION.SDK_INT >= 23) {
                ArrayList<String> checkResult = new ArrayList<>();
                for (String permission : permissions) {
                    if (context.checkSelfPermission(permission) != PackageManager
                            .PERMISSION_GRANTED) {
                        checkResult.add(permission);
                    }
                }
                if (checkResult.size() == 0) {
                    return true;
                }
                int i = 0;
                String[] reqPermissions = new String[checkResult.size()];
                for (String string : checkResult) {
                    reqPermissions[i] = string;
                    i++;
                }
                ((Activity) context).requestPermissions(reqPermissions, requestCode);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 判断是是否有录音权限.
     * <p>
     * steps:
     * 检测是否有权限--有--执行相关操作
     * --无权限--
     * <p>
     * --判断系统版本
     * --小于6.0 直接获取
     * --大于6.0 动态申请权限
     * --对申请结果的处理回调
     * <p>
     * --允许
     * <p>
     * --拒绝
     * <p>
     * test:
     * test1 build.gradle minsdk <23    真机android7.1 清单文件中配置了录音权限
     * test2 build.gradle minsdk >=23    真机android7.1 清单文件中配置了录音权限
     *
     * @param context Context
     * @return boolean 有录音权限返回 true ，没有录音权限返回 false
     */
    public static boolean checkAudioPermission(Context context) {
        // 音频获取源
        int audioSource = MediaRecorder.AudioSource.MIC;
        // 设置音频采样率，44100是目前的标准，但是某些设备仍然支持22050，16000，11025
        int sampleRateInHz = 44100;
        // 设置音频的录制的声道CHANNEL_IN_STEREO为双声道，CHANNEL_CONFIGURATION_MONO为单声道
        int channelConfig = AudioFormat.CHANNEL_IN_STEREO;
        // 音频数据格式:PCM 16位每个样本。保证设备支持。PCM 8位每个样本。不一定能得到设备支持。
        int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
        // 缓冲区字节大小
        int bufferSizeInBytes = AudioRecord.getMinBufferSize(sampleRateInHz,
                channelConfig, audioFormat);
        AudioRecord audioRecord = new AudioRecord(audioSource, sampleRateInHz,
                channelConfig, audioFormat, bufferSizeInBytes);
        //开始录制音频
        try {
            // 防止某些手机崩溃，例如联想
            audioRecord.startRecording();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        // 根据开始录音判断是否有录音权限s
        if (audioRecord.getRecordingState() != AudioRecord.RECORDSTATE_RECORDING) {
            return false;
        }
        audioRecord.stop();
        // 释放资源
        audioRecord.release();
        return true;
    }
}

