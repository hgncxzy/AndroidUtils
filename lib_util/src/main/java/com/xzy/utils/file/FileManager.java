package com.xzy.utils.file;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;

import com.xzy.utils.file.bean.AppInfo;
import com.xzy.utils.file.bean.FileBean;
import com.xzy.utils.file.bean.ImgFolderBean;
import com.xzy.utils.file.bean.Music;
import com.xzy.utils.file.bean.Video;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 件管理者, 可以获取本机的各种文件
 *
 * @author xzy
 */
@SuppressWarnings("unused")
public class FileManager {

    @SuppressLint("StaticFieldLeak")
    private static FileManager mInstance;
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private static ContentResolver mContentResolver;
    private static final Object mLock = new Object();

    public static FileManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (mLock) {
                if (mInstance == null) {
                    mInstance = new FileManager();
                    mContext = context;
                    mContentResolver = context.getContentResolver();
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取本机音乐列表
     *
     * @return List<Music>
     */
    public List<Music> getMusics() {
        ArrayList<Music> musics = new ArrayList<>();
        try (Cursor c = mContentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, null, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER)) {

            while (Objects.requireNonNull(c).moveToNext()) {
                // 路径
                String path = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));

                if (!new File(path).exists()) {
                    continue;
                }
                // 歌曲名
                String name = c.getString(c.getColumnIndexOrThrow(MediaStore
                        .Audio.Media.DISPLAY_NAME));
                // 专辑
                String album = c.getString(c.getColumnIndexOrThrow(MediaStore
                        .Audio.Media.ALBUM));
                // 作者
                String artist = c.getString(c.getColumnIndexOrThrow(MediaStore
                        .Audio.Media.ARTIST));
                // 大小
                long size = c.getLong(c.getColumnIndexOrThrow(MediaStore.Audio
                        .Media.SIZE));
                // 时长
                int duration = c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio
                        .Media.DURATION));
                // 歌曲的id
                int albumId = c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio
                        .Media._ID));
                // int albumId = c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));

                Music music = new Music(name, path, album, artist, size, duration);
                musics.add(music);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return musics;
    }

    /**
     * 获取本机视频列表
     *
     * @return List<Video>
     */
    public List<Video> getVideos() {
        List<Video> videos = new ArrayList<>();
        try (Cursor c = mContentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null,
                null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER)) {
            // String[] mediaColumns = { "_id", "_data", "_display_name",
            // "_size", "date_modified", "duration", "resolution" };
            while (Objects.requireNonNull(c).moveToNext()) {
                // 路径
                String path = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                if (!new File(path).exists()) {
                    continue;
                }
                // 视频的id
                int id = c.getInt(c.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                // 视频名称
                String name = c.getString(c.getColumnIndexOrThrow(MediaStore
                        .Video.Media.DISPLAY_NAME));
                //分辨率
                String resolution = c.getString(c.getColumnIndexOrThrow(MediaStore
                        .Video.Media.RESOLUTION));
                // 大小
                long size = c.getLong(c.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
                // 时长
                long duration = c.getLong(c.getColumnIndexOrThrow(MediaStore
                        .Video.Media.DURATION));
                //修改时间
                long date = c.getLong(c.getColumnIndexOrThrow(MediaStore
                        .Video.Media.DATE_MODIFIED));
                Video video = new Video(id, path, name, resolution, size, date, duration);
                videos.add(video);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return videos;
    }

    // 获取视频缩略图
    public Bitmap getVideoThumbnail(int id) {
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        bitmap = MediaStore.Video.Thumbnails.getThumbnail(mContentResolver, id,
                MediaStore.Images.Thumbnails.MICRO_KIND, options);
        return bitmap;
    }

    /**
     * 通过文件类型得到相应文件的集合
     *
     * @param fileType 文件类型
     * @return 某个类型文件的集合
     */
    public List<FileBean> getFileListByType(int fileType) {
        List<FileBean> files = new ArrayList<>();
        // 扫描files文件库
        try (Cursor c = mContentResolver.query(MediaStore
                        .Files.getContentUri("external"),
                new String[]{"_id", "_data", "_size"}, null,
                null, null)) {
            int dataIndex = Objects.requireNonNull(c)
                    .getColumnIndex(MediaStore.Files.FileColumns.DATA);
            int sizeIndex = c.getColumnIndex(MediaStore.Files.FileColumns.SIZE);

            while (c.moveToNext()) {
                String path = c.getString(dataIndex);

                if (FileUtils.getFileType(path) == fileType) {
                    if (!FileUtils.isExists(path)) {
                        continue;
                    }
                    long size = c.getLong(sizeIndex);
                    FileBean fileBean = new FileBean(path, FileUtils.getFileIconByFilePath(path));
                    files.add(fileBean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return files;
    }

    /**
     * 得到图片文件夹集合
     */
    public List<ImgFolderBean> getImgFoldersList() {
        List<ImgFolderBean> folders = new ArrayList<>();
        // 扫描图片
        try (Cursor c = mContentResolver.query(MediaStore.Images.Media
                        .EXTERNAL_CONTENT_URI, null,
                MediaStore.Images.Media.MIME_TYPE + "= ? or "
                        + MediaStore.Images.Media.MIME_TYPE + "= ?",
                new String[]{"image/jpeg", "image/png", "image/bmp", "image/webp"},
                MediaStore.Images.Media.DATE_MODIFIED)) {
            // 用于保存已经添加过的文件夹目录
            List<String> mDirs = new ArrayList<>();
            while (Objects.requireNonNull(c).moveToNext()) {
                // 路径
                String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
                File parentFile = new File(path).getParentFile();
                if (parentFile == null)
                    continue;

                String dir = parentFile.getAbsolutePath();
                // 如果已经添加过
                if (mDirs.contains(dir))
                    continue;
                // 添加到保存目录的集合中
                mDirs.add(dir);
                ImgFolderBean folderBean = new ImgFolderBean();
                folderBean.setDir(dir);
                folderBean.setFistImgPath(path);
                if (parentFile.list() == null)
                    continue;
                int count = Objects.requireNonNull(parentFile.list(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String filename) {
                        return filename.endsWith(".jpeg")
                                || filename.endsWith(".jpg")
                                || filename.endsWith(".bmp")
                                || filename.endsWith(".webp")
                                || filename.endsWith(".png");
                    }
                })).length;

                folderBean.setCount(count);
                folders.add(folderBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return folders;
    }

    /**
     *
     * 通过图片文件夹的路径获取该目录下的图片
     * @param dir 路径
     * @return  包含图片路径的  list
     */
    public List<String> getImgListByDir(String dir) {
        ArrayList<String> imgPaths = new ArrayList<>();
        File directory = new File(dir);
        if (!directory.exists()) {
            return imgPaths;
        }
        File[] files = directory.listFiles();
        for (File file : Objects.requireNonNull(files)) {
            String path = file.getAbsolutePath();
            if (FileUtils.isPictureFile(path)) {
                imgPaths.add(path);
            }
        }
        return imgPaths;
    }

    /**
     * 获取已安装 apk 的列表
     * @return List<AppInfo>
     */
    public List<AppInfo> getInstalledAppList() {

        ArrayList<AppInfo> appInfoArrayList = new ArrayList<>();
        //获取到包的管理者
        PackageManager packageManager = mContext.getPackageManager();
        //获得所有的安装包
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);

        //遍历每个安装包，获取对应的信息
        for (PackageInfo packageInfo : installedPackages) {
            AppInfo appInfo = new AppInfo();
            appInfo.setApplicationInfo(packageInfo.applicationInfo);
            appInfo.setVersionCode(packageInfo.versionCode);
            //得到icon
            Drawable drawable = packageInfo.applicationInfo.loadIcon(packageManager);
            appInfo.setIcon(drawable);
            //得到程序的名字
            String apkName = packageInfo.applicationInfo.loadLabel(packageManager).toString();
            appInfo.setApkName(apkName);
            //得到程序的包名
            String packageName = packageInfo.packageName;
            appInfo.setApkPackageName(packageName);
            //得到程序的资源文件夹
            String sourceDir = packageInfo.applicationInfo.sourceDir;
            File file = new File(sourceDir);
            //得到apk的大小
            long size = file.length();
            appInfo.setApkSize(size);
            System.out.println("---------------------------");
            System.out.println("程序的名字:" + apkName);
            System.out.println("程序的包名:" + packageName);
            System.out.println("程序的大小:" + size);
            //获取到安装应用程序的标记
            int flags = packageInfo.applicationInfo.flags;

            if ((flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                //表示系统app
                appInfo.setIsUserApp(false);
            } else {
                //表示用户app
                appInfo.setIsUserApp(true);
            }
            if ((flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0) {
                //表示在sd卡
                appInfo.setIsRom(false);
            } else {
                //表示内存
                appInfo.setIsRom(true);
            }
            appInfoArrayList.add(appInfo);
        }
        return appInfoArrayList;
    }

}
