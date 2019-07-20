package com.xzy.utils.file;

import java.text.DecimalFormat;

/**
 * 文件操作工具类。
 */
@SuppressWarnings("unused")
public class FileUtils {

    /**
     * 得到关于文件大小的描述信息。
     * @param size 文件大小 long 值
     * @return 文件大小的描述信息字符串。
     */
    public static String getFileSizeDescription(long size) {
        StringBuilder bytes = new StringBuilder();
        DecimalFormat format = new DecimalFormat("###.0");
        if (size >= 1024 * 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0 * 1024.0));
            bytes.append(format.format(i)).append("GB");
        }
        else if (size >= 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0));
            bytes.append(format.format(i)).append("MB");
        }
        else if (size >= 1024) {
            double i = (size / (1024.0));
            bytes.append(format.format(i)).append("KB");
        }
        else {
            if (size <= 0) {
                bytes.append("0B");
            }
            else {
                bytes.append((int) size).append("B");
            }
        }
        return bytes.toString();
    }

}
