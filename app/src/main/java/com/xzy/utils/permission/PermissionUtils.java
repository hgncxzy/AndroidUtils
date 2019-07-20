package com.xzy.utils.permission;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

/**
 * 权限工具类。
 *
 * @author xzy
 */
@SuppressWarnings("unused")
public class PermissionUtils {

    /**
     * 判断是是否有录音权限.
     *
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
     * @param context Context
     * @return boolean
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

