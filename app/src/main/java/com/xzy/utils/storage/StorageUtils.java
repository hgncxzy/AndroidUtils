package com.xzy.utils.storage;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Locale;

/**
 * 存储相关.
 *
 * @author xzy
 */
@SuppressWarnings("ALL")
public class StorageUtils {

    private static final String TAG = "StorageUtils";

    private StorageUtils() {
    }

    /**
     * 判断外部主存储是否可写.
     *
     * @return true - 可写，false - 不可写
     */
    public static boolean isExternalPrimaryStorageWritable() {
        String state;
        try {
            state = Environment.getExternalStorageState();
        } catch (NullPointerException exception) {
            state = "";
        }
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /**
     * 判断外部主存储是否可读.
     *
     * @return true - 可读，false - 不可读
     */
    public static boolean isExternalPrimaryStorageReadable() {
        String state;
        try {
            state = Environment.getExternalStorageState();
        } catch (NullPointerException exception) {
            state = "";
        }
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    /**
     * 获取外部挂载的次存储路径，例如 sdcard.
     *
     * @return 次存储路径，没有挂载时返回空字符串
     */
    public static String getExternalSecondaryStorage() {
        final HashSet<String> out = new HashSet<>();
        String reg = "(?i).*vold.*(vfat|ntfs|exfat|fat32|ext3|ext4).*rw.*";
        StringBuilder sb = new StringBuilder();
        try {
            final Process process = new ProcessBuilder()
                    .command("mount")
                    .redirectErrorStream(true)
                    .start();
            process.waitFor();
            final InputStream is = process.getInputStream();
            final byte[] buffer = new byte[1024];
            while (is.read(buffer) != -1) {
                sb.append(new String(buffer));
            }
            is.close();
        } catch (final Exception e) {
            Log.e(TAG, "获取外部挂载的次存储失败", e);
        }

        // parse output
        final String[] lines = sb.toString().split("\n");
        for (String line : lines) {
            if (!line.toLowerCase(Locale.US).contains("asec")) {
                if (line.matches(reg)) {
                    String[] parts = line.split(" ");
                    for (String part : parts) {
                        if (part.startsWith("/")) {
                            if (!part.toLowerCase(Locale.US).contains("vold")) {
                                out.add(part);
                            }
                        }
                    }
                }
            }
        }
        return out.iterator().hasNext() ? out.iterator().next() : "";
    }

    /**
     * 判断外部次存储是否可用.
     *
     * @return rue - 可用，false - 不可用
     */
    public static boolean isExternalSecondaryStorageAvailable() {
        //return FileUtils.isExist(getExternalSecondaryStorage());
        return false;
    }

    /**
     * 获取外部次存储的总大小，单位：MB.
     *
     * @return 外部次存储空间的总大小
     */
    public static int getExternalSecondaryStorageTotalSize() {
        String path = getExternalSecondaryStorage();
        if (TextUtils.isEmpty(path)) {
            return 0;
        }
        StatFs stat = new StatFs(path);

        long blockSize = getBlockSizeJellyBeanMr2(stat);
        long totalBlocks = getBlockCountJellyBeanMr2(stat);
        return (int) ((totalBlocks * blockSize) / (1024 * 1024));
    }

    /**
     * 获取外部次存储的可用大小，单位：MB.
     *
     * @return 外部次存储空间的可用大小
     */
    public static int getExternalSecondaryStorageAvailableSize() {
        String path = getExternalSecondaryStorage();
        if (TextUtils.isEmpty(path)) {
            return 0;
        }
        StatFs stat = new StatFs(path);
        long blockSize = getBlockSizeJellyBeanMr2(stat);
        long totalBlocks = getAvailableBlocksJellyBeanMr2(stat);
        return (int) ((totalBlocks * blockSize) / (1024 * 1024));
    }

    /**
     * 获取外部主存储的总大小，单位：MB.
     *
     * @return 外部存储空间的总大小
     */
    public static int getExternalPrimaryStorageTotalSize() {
        if (isExternalPrimaryStorageWritable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());

            long blockSize = getBlockSizeJellyBeanMr2(stat);
            long totalBlocks = getBlockCountJellyBeanMr2(stat);
            return (int) ((totalBlocks * blockSize) / (1024 * 1024));
        }
        return 0;
    }

    /**
     * 获取外部主存储的可用大小，单位：MB.
     *
     * @return 外部存储空间的可用大小
     */
    public static int getExternalPrimaryStorageAvailableSize() {
        if (isExternalPrimaryStorageWritable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());

            long blockSize = getBlockSizeJellyBeanMr2(stat);
            long totalBlocks = getAvailableBlocksJellyBeanMr2(stat);
            return (int) ((totalBlocks * blockSize) / (1024 * 1024));
        }
        return 0;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private static long getBlockSizeJellyBeanMr2(@NonNull StatFs stat) {
        return stat.getBlockSizeLong();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private static long getBlockCountJellyBeanMr2(@NonNull StatFs stat) {
        return stat.getBlockCountLong();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private static long getAvailableBlocksJellyBeanMr2(@NonNull StatFs stat) {
        return stat.getAvailableBlocksLong();
    }
}
