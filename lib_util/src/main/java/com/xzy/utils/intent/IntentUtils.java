package com.xzy.utils.intent;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.annotation.StringDef;
import androidx.core.content.FileProvider;

import com.xzy.utils.UtilsApp;
import com.xzy.utils.common.Utils;
import com.xzy.utils.file.FileUtils;
import com.xzy.utils.string.StringUtils;

import java.io.File;
import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static android.Manifest.permission.CALL_PHONE;
import static com.xzy.utils.intent.IntentUtils.DocumentType.ANY;
import static com.xzy.utils.intent.IntentUtils.DocumentType.AUDIO;
import static com.xzy.utils.intent.IntentUtils.DocumentType.IMAGE;
import static com.xzy.utils.intent.IntentUtils.DocumentType.VIDEO;

/**
 * 意图相关的工具类。
 * 部分参考 https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/
 * java/com/blankj/utilcode/util/IntentUtils.java
 * 部分参考 https://github.com/xuexiangjys/XUtil
 *
 * @author xzy
 */
@SuppressWarnings("all")
public final class IntentUtils {

    private IntentUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Return whether the intent is available.
     *
     * @param intent The intent.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isIntentAvailable(final Intent intent) {
        return Utils.getApp()
                .getPackageManager()
                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
                .size() > 0;
    }

    /**
     * Return the intent of install app.
     * <p>Target APIs greater than 25 must hold
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param filePath The path of file.
     * @return the intent of install app
     */
    public static Intent getInstallAppIntent(final String filePath) {
        return getInstallAppIntent(getFileByPath(filePath), false);
    }

    /**
     * Return the intent of install app.
     * <p>Target APIs greater than 25 must hold
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param file The file.
     * @return the intent of install app
     */
    public static Intent getInstallAppIntent(final File file) {
        return getInstallAppIntent(file, false);
    }

    /**
     * Return the intent of install app.
     * <p>Target APIs greater than 25 must hold
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param filePath  The path of file.
     * @param isNewTask True to add flag of new task, false otherwise.
     * @return the intent of install app
     */
    public static Intent getInstallAppIntent(final String filePath, final boolean isNewTask) {
        return getInstallAppIntent(getFileByPath(filePath), isNewTask);
    }

    /**
     * Return the intent of install app.
     * <p>Target APIs greater than 25 must hold
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param file      The file.
     * @param isNewTask True to add flag of new task, false otherwise.
     * @return the intent of install app
     */
    public static Intent getInstallAppIntent(final File file, final boolean isNewTask) {
        if (file == null) return null;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data;
        String type = "application/vnd.android.package-archive";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            data = Uri.fromFile(file);
        } else {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            String authority = Utils.getApp().getPackageName() + ".utilcode.provider";
            data = FileProvider.getUriForFile(Utils.getApp(), authority, file);
        }
        intent.setDataAndType(data, type);
        return getIntent(intent, isNewTask);
    }

    /**
     * Return the intent of uninstall app.
     *
     * @param packageName The name of the package.
     * @return the intent of uninstall app
     */
    public static Intent getUninstallAppIntent(final String packageName) {
        return getUninstallAppIntent(packageName, false);
    }

    /**
     * Return the intent of uninstall app.
     *
     * @param packageName The name of the package.
     * @param isNewTask   True to add flag of new task, false otherwise.
     * @return the intent of uninstall app
     */
    public static Intent getUninstallAppIntent(final String packageName, final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + packageName));
        return getIntent(intent, isNewTask);
    }

    /**
     * Return the intent of launch app.
     *
     * @param packageName The name of the package.
     * @return the intent of launch app
     */
    public static Intent getLaunchAppIntent(final String packageName) {
        return getLaunchAppIntent(packageName, false);
    }

    /**
     * Return the intent of launch app.
     *
     * @param packageName The name of the package.
     * @param isNewTask   True to add flag of new task, false otherwise.
     * @return the intent of launch app
     */
    public static Intent getLaunchAppIntent(final String packageName, final boolean isNewTask) {
        Intent intent = Utils.getApp().getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent == null) return null;
        return getIntent(intent, isNewTask);
    }

    /**
     * Return the intent of launch app details settings.
     *
     * @param packageName The name of the package.
     * @return the intent of launch app details settings
     */
    public static Intent getLaunchAppDetailsSettingsIntent(final String packageName) {
        return getLaunchAppDetailsSettingsIntent(packageName, false);
    }

    /**
     * Return the intent of launch app details settings.
     *
     * @param packageName The name of the package.
     * @param isNewTask   True to add flag of new task, false otherwise.
     * @return the intent of launch app details settings
     */
    public static Intent getLaunchAppDetailsSettingsIntent(final String packageName,
                                                           final boolean isNewTask) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + packageName));
        return getIntent(intent, isNewTask);
    }

    /**
     * Return the intent of share text.
     *
     * @param content The content.
     * @return the intent of share text
     */
    public static Intent getShareTextIntent(final String content) {
        return getShareTextIntent(content, false);
    }

    /**
     * Return the intent of share text.
     *
     * @param content   The content.
     * @param isNewTask True to add flag of new task, false otherwise.
     * @return the intent of share text
     */

    public static Intent getShareTextIntent(final String content, final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        return getIntent(intent, isNewTask);
    }

    /**
     * Return the intent of share image.
     *
     * @param content   The content.
     * @param imagePath The path of image.
     * @return the intent of share image
     */
    public static Intent getShareImageIntent(final String content, final String imagePath) {
        return getShareImageIntent(content, imagePath, false);
    }

    /**
     * Return the intent of share image.
     *
     * @param content   The content.
     * @param imagePath The path of image.
     * @param isNewTask True to add flag of new task, false otherwise.
     * @return the intent of share image
     */
    public static Intent getShareImageIntent(final String content,
                                             final String imagePath,
                                             final boolean isNewTask) {
        if (imagePath == null || imagePath.length() == 0) return null;
        return getShareImageIntent(content, new File(imagePath), isNewTask);
    }

    /**
     * Return the intent of share image.
     *
     * @param content The content.
     * @param image   The file of image.
     * @return the intent of share image
     */
    public static Intent getShareImageIntent(final String content, final File image) {
        return getShareImageIntent(content, image, false);
    }

    /**
     * Return the intent of share image.
     *
     * @param content   The content.
     * @param image     The file of image.
     * @param isNewTask True to add flag of new task, false otherwise.
     * @return the intent of share image
     */
    public static Intent getShareImageIntent(final String content,
                                             final File image,
                                             final boolean isNewTask) {
        if (image == null || !image.isFile()) return null;
        return getShareImageIntent(content, file2Uri(image), isNewTask);
    }

    /**
     * Return the intent of share image.
     *
     * @param content The content.
     * @param uri     The uri of image.
     * @return the intent of share image
     */
    public static Intent getShareImageIntent(final String content, final Uri uri) {
        return getShareImageIntent(content, uri, false);
    }

    /**
     * Return the intent of share image.
     *
     * @param content   The content.
     * @param uri       The uri of image.
     * @param isNewTask True to add flag of new task, false otherwise.
     * @return the intent of share image
     */
    public static Intent getShareImageIntent(final String content,
                                             final Uri uri,
                                             final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/*");
        return getIntent(intent, isNewTask);
    }

    /**
     * Return the intent of share images.
     *
     * @param content    The content.
     * @param imagePaths The paths of images.
     * @return the intent of share images
     */
    public static Intent getShareImageIntent(final String content, final LinkedList<String> imagePaths) {
        return getShareImageIntent(content, imagePaths, false);
    }

    /**
     * Return the intent of share images.
     *
     * @param content    The content.
     * @param imagePaths The paths of images.
     * @param isNewTask  True to add flag of new task, false otherwise.
     * @return the intent of share images
     */
    public static Intent getShareImageIntent(final String content,
                                             final LinkedList<String> imagePaths,
                                             final boolean isNewTask) {
        if (imagePaths == null || imagePaths.isEmpty()) return null;
        List<File> files = new ArrayList<>();
        for (String imagePath : imagePaths) {
            files.add(new File(imagePath));
        }
        return getShareImageIntent(content, files, isNewTask);
    }

    /**
     * Return the intent of share images.
     *
     * @param content The content.
     * @param images  The files of images.
     * @return the intent of share images
     */
    public static Intent getShareImageIntent(final String content, final List<File> images) {
        return getShareImageIntent(content, images, false);
    }

    /**
     * Return the intent of share images.
     *
     * @param content   The content.
     * @param images    The files of images.
     * @param isNewTask True to add flag of new task, false otherwise.
     * @return the intent of share images
     */
    public static Intent getShareImageIntent(final String content,
                                             final List<File> images,
                                             final boolean isNewTask) {
        if (images == null || images.isEmpty()) return null;
        ArrayList<Uri> uris = new ArrayList<>();
        for (File image : images) {
            if (!image.isFile()) continue;
            uris.add(file2Uri(image));
        }
        return getShareImageIntent(content, uris, isNewTask);
    }

    /**
     * Return the intent of share images.
     *
     * @param content The content.
     * @param uris    The uris of images.
     * @return the intent of share images
     */
    public static Intent getShareImageIntent(final String content, final ArrayList<Uri> uris) {
        return getShareImageIntent(content, uris, false);
    }

    /**
     * Return the intent of share images.
     *
     * @param content   The content.
     * @param uris      The uris of image.
     * @param isNewTask True to add flag of new task, false otherwise.
     * @return the intent of share image
     */
    public static Intent getShareImageIntent(final String content,
                                             final ArrayList<Uri> uris,
                                             final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        intent.setType("image/*");
        return getIntent(intent, isNewTask);
    }

    /**
     * Return the intent of component.
     *
     * @param packageName The name of the package.
     * @param className   The name of class.
     * @return the intent of component
     */
    public static Intent getComponentIntent(final String packageName, final String className) {
        return getComponentIntent(packageName, className, null, false);
    }

    /**
     * Return the intent of component.
     *
     * @param packageName The name of the package.
     * @param className   The name of class.
     * @param isNewTask   True to add flag of new task, false otherwise.
     * @return the intent of component
     */
    public static Intent getComponentIntent(final String packageName,
                                            final String className,
                                            final boolean isNewTask) {
        return getComponentIntent(packageName, className, null, isNewTask);
    }

    /**
     * Return the intent of component.
     *
     * @param packageName The name of the package.
     * @param className   The name of class.
     * @param bundle      The Bundle of extras to add to this intent.
     * @return the intent of component
     */
    public static Intent getComponentIntent(final String packageName,
                                            final String className,
                                            final Bundle bundle) {
        return getComponentIntent(packageName, className, bundle, false);
    }

    /**
     * Return the intent of component.
     *
     * @param packageName The name of the package.
     * @param className   The name of class.
     * @param bundle      The Bundle of extras to add to this intent.
     * @param isNewTask   True to add flag of new task, false otherwise.
     * @return the intent of component
     */
    public static Intent getComponentIntent(final String packageName,
                                            final String className,
                                            final Bundle bundle,
                                            final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (bundle != null) intent.putExtras(bundle);
        ComponentName cn = new ComponentName(packageName, className);
        intent.setComponent(cn);
        return getIntent(intent, isNewTask);
    }

    /**
     * Return the intent of shutdown.
     * <p>Requires root permission
     * or hold {@code android:sharedUserId="android.uid.system"},
     * {@code <uses-permission android:name="android.permission.SHUTDOWN" />}
     * in manifest.</p>
     *
     * @return the intent of shutdown
     */
    public static Intent getShutdownIntent() {
        return getShutdownIntent(false);
    }

    /**
     * Return the intent of shutdown.
     * <p>Requires root permission
     * or hold {@code android:sharedUserId="android.uid.system"},
     * {@code <uses-permission android:name="android.permission.SHUTDOWN" />}
     * in manifest.</p>
     *
     * @param isNewTask True to add flag of new task, false otherwise.
     * @return the intent of shutdown
     */
    public static Intent getShutdownIntent(final boolean isNewTask) {
        Intent intent = new Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN");
        intent.putExtra("android.intent.extra.KEY_CONFIRM", false);
        return getIntent(intent, isNewTask);
    }

    /**
     * Return the intent of dial.
     *
     * @param phoneNumber The phone number.
     * @return the intent of dial
     */
    public static Intent getDialIntent(final String phoneNumber) {
        return getDialIntent(phoneNumber, false);
    }

    /**
     * Return the intent of dial.
     *
     * @param phoneNumber The phone number.
     * @param isNewTask   True to add flag of new task, false otherwise.
     * @return the intent of dial
     */
    public static Intent getDialIntent(final String phoneNumber, final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        return getIntent(intent, isNewTask);
    }

    /**
     * Return the intent of call.
     * <p>Must hold {@code <uses-permission android:name="android.permission.CALL_PHONE" />}</p>
     *
     * @param phoneNumber The phone number.
     * @return the intent of call
     */
    @RequiresPermission(CALL_PHONE)
    public static Intent getCallIntent(final String phoneNumber) {
        return getCallIntent(phoneNumber, false);
    }

    /**
     * Return the intent of call.
     * <p>Must hold {@code <uses-permission android:name="android.permission.CALL_PHONE" />}</p>
     *
     * @param phoneNumber The phone number.
     * @param isNewTask   True to add flag of new task, false otherwise.
     * @return the intent of call
     */
    @RequiresPermission(CALL_PHONE)
    public static Intent getCallIntent(final String phoneNumber, final boolean isNewTask) {
        Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNumber));
        return getIntent(intent, isNewTask);
    }

    /**
     * Return the intent of send SMS.
     *
     * @param phoneNumber The phone number.
     * @param content     The content of SMS.
     * @return the intent of send SMS
     */
    public static Intent getSendSmsIntent(final String phoneNumber, final String content) {
        return getSendSmsIntent(phoneNumber, content, false);
    }

    /**
     * Return the intent of send SMS.
     *
     * @param phoneNumber The phone number.
     * @param content     The content of SMS.
     * @param isNewTask   True to add flag of new task, false otherwise.
     * @return the intent of send SMS
     */
    public static Intent getSendSmsIntent(final String phoneNumber,
                                          final String content,
                                          final boolean isNewTask) {
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", content);
        return getIntent(intent, isNewTask);
    }

    /**
     * Return the intent of capture.
     *
     * @param outUri The uri of output.
     * @return the intent of capture
     */
    public static Intent getCaptureIntent(final Uri outUri) {
        return getCaptureIntent(outUri, false);
    }

    /**
     * Return the intent of capture.
     *
     * @param outUri    The uri of output.
     * @param isNewTask True to add flag of new task, false otherwise.
     * @return the intent of capture
     */
    public static Intent getCaptureIntent(final Uri outUri, final boolean isNewTask) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        return getIntent(intent, isNewTask);
    }

    private static Intent getIntent(final Intent intent, final boolean isNewTask) {
        return isNewTask ? intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) : intent;
    }

    ///////////////////////////////////////////////////////////////////////////
    // other utils methods
    ///////////////////////////////////////////////////////////////////////////

    private static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static Uri file2Uri(final File file) {
        if (file == null) return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String authority = Utils.getApp().getPackageName() + ".utilcode.provider";
            return FileProvider.getUriForFile(Utils.getApp(), authority, file);
        } else {
            return Uri.fromFile(file);
        }
    }

    /**
     * 获取选择照片的 Intent
     *
     * @return
     */
    public static Intent getPickIntentWithGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        return intent.setType("image*//*");
    }

    /**
     * 获取从文件中选择照片的 Intent
     *
     * @return
     */
    public static Intent getPickIntentWithDocuments() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        return intent.setType("image*//*");
    }


    public static Intent buildImageGetIntent(final Uri saveTo, final int outputX, final int outputY, final boolean returnData) {
        return buildImageGetIntent(saveTo, 1, 1, outputX, outputY, returnData);
    }

    public static Intent buildImageGetIntent(Uri saveTo, int aspectX, int aspectY,
                                             int outputX, int outputY, boolean returnData) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT < 19) {
            intent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        intent.setType("image*//*");
        intent.putExtra("output", saveTo);
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", returnData);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        return intent;
    }

    public static Intent buildImageCropIntent(final Uri uriFrom, final Uri uriTo, final int outputX, final int outputY, final boolean returnData) {
        return buildImageCropIntent(uriFrom, uriTo, 1, 1, outputX, outputY, returnData);
    }

    public static Intent buildImageCropIntent(Uri uriFrom, Uri uriTo, int aspectX, int aspectY,
                                              int outputX, int outputY, boolean returnData) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uriFrom, "image*//*");
        intent.putExtra("crop", "true");
        intent.putExtra("output", uriTo);
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", returnData);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        return intent;
    }

    public static Intent buildImageCaptureIntent(final Uri uri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        return intent;
    }

    /**
     * 获取安装 App（支持 8.0）的意图
     * <p>8.0 需添加权限
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param filePath  文件路径
     * @param authority 7.0 及以上安装需要传入清单文件中的{@code <provider>}的 authorities 属性
     *                  <br>参看 https://developer.android.com/reference/android/support/v4/content/FileProvider.html
     * @return 安装 App（支持 8.0）的意图
     */
    public static Intent getInstallAppIntent(final String filePath, final String authority) {
        return getInstallAppIntent(FileUtils.getFileByPath(filePath), authority);
    }

    /**
     * 获取安装 App(支持 8.0)的意图
     * <p>8.0 需添加权限
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param file      文件
     * @param authority 7.0 及以上安装需要传入清单文件中的{@code <provider>}的 authorities 属性
     *                  <br>参看 https://developer.android.com/reference/android/support/v4/content/FileProvider.html
     * @return 安装 App(支持 8.0)的意图
     */
    public static Intent getInstallAppIntent(final File file, final String authority) {
        return getInstallAppIntent(file, authority, false);
    }

    /**
     * 获取安装 App(支持 8.0)的意图
     * <p>8.0 需添加权限
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param file      文件
     * @param authority 7.0 及以上安装需要传入清单文件中的{@code <provider>}的 authorities 属性
     *                  <br>参看 https://developer.android.com/reference/android/support/v4/content/FileProvider.html
     * @param isNewTask 是否开启新的任务栈
     * @return 安装 App(支持 8.0)的意图
     */
    public static Intent getInstallAppIntent(final File file,
                                             final String authority,
                                             final boolean isNewTask) {
        if (file == null) return null;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data;
        String type = "application/vnd.android.package-archive";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            data = Uri.fromFile(file);
        } else {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            data = FileProvider.getUriForFile(UtilsApp.INSTANCE, authority, file);
        }
        intent.setDataAndType(data, type);
        return getIntent(intent, isNewTask);
    }


    /**
     * 获取 App 具体设置的意图
     *
     * @param packageName 包名
     * @return App 具体设置的意图
     */
    public static Intent getAppDetailsSettingsIntent(final String packageName) {
        return getAppDetailsSettingsIntent(packageName, false);
    }

    /**
     * 获取 App 具体设置的意图
     *
     * @param packageName 包名
     * @param isNewTask   是否开启新的任务栈
     * @return App 具体设置的意图
     */
    public static Intent getAppDetailsSettingsIntent(final String packageName,
                                                     final boolean isNewTask) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + packageName));
        return getIntent(intent, isNewTask);
    }


    //==========================数据获取==============================//

    /**
     * 获取附加数据
     *
     * @param intent
     * @return
     */
    public static Bundle getExtras(Intent intent) {
        return intent != null ? intent.getExtras() : null;
    }

    /**
     * 获取Intent中的Bundle
     *
     * @param intent
     * @param key
     * @return
     */
    public static Bundle getBundleExtra(Intent intent, String key) {
        return intent != null ? intent.getBundleExtra(key) : null;
    }

    /**
     * 获取Intent中的String
     *
     * @param intent
     * @param key
     * @return
     */
    public static String getStringExtra(Intent intent, String key) {
        return intent != null ? intent.getStringExtra(key) : null;
    }

    /**
     * 获取Intent中的Boolean
     *
     * @param intent
     * @param key
     * @param defValue 默认值
     * @return
     */
    public static boolean getBooleanExtra(Intent intent, String key, boolean defValue) {
        return intent != null ? intent.getBooleanExtra(key, defValue) : defValue;
    }

    /**
     * 获取Intent中的Int
     *
     * @param intent
     * @param key
     * @param defValue 默认值
     * @return
     */
    public static int getIntExtra(Intent intent, String key, int defValue) {
        return intent != null ? intent.getIntExtra(key, defValue) : defValue;
    }

    /**
     * 获取Intent中的Float
     *
     * @param intent
     * @param key
     * @param defValue 默认值
     * @return
     */
    public static float getFloatExtra(Intent intent, String key, float defValue) {
        return intent != null ? intent.getFloatExtra(key, defValue) : defValue;
    }

    /**
     * 获取Intent中的Serializable数据
     *
     * @param intent
     * @param key
     * @return
     */
    public static <T> T getSerializableExtra(Intent intent, String key) {
        return intent != null ? (T) intent.getSerializableExtra(key) : null;
    }

    /**
     * 获取Intent中的Bundle携带的Serializable数据
     *
     * @param intent
     * @param key
     * @return
     */
    public static <T> T getBundleSerializable(Intent intent, String key) {
        Bundle bundle = getExtras(intent);
        return bundle != null ? (T) bundle.getSerializable(key) : null;
    }

    /**
     * 获取Intent意图
     *
     * @param context
     * @param cls     类名
     * @param action  动作
     * @return
     */
    @NonNull
    public static Intent getIntent(Context context, Class<?> cls, String action) {
        return getIntent(context, cls, action, false);
    }

    /**
     * 获取Intent意图
     *
     * @param context
     * @param cls     类名
     * @param action  动作
     * @return
     */
    @NonNull
    public static Intent getIntent(Context context, Class<?> cls, String action, boolean isNewTask) {
        Intent intent = new Intent();
        if (cls != null) {
            intent.setClass(context, cls);
        }
        if (action != null) {
            intent.setAction(action);
        }
        if (isNewTask) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        return intent;
    }

    /**
     * 传递数据
     *
     * @param intent
     * @param key    关键字
     * @param param  数据
     * @return
     */
    public static Intent putExtra(Intent intent, String key, Object param) {
        if (param instanceof Serializable) {
            intent.putExtra(key, (Serializable) param);
        } else if (param instanceof String) {
            intent.putExtra(key, (String) param);
        } else if (param instanceof String[]) {
            intent.putExtra(key, (String[]) param);
        } else if (param instanceof boolean[]) {
            intent.putExtra(key, (boolean[]) param);
        } else if (param instanceof short[]) {
            intent.putExtra(key, (short[]) param);
        } else if (param instanceof int[]) {
            intent.putExtra(key, (int[]) param);
        } else if (param instanceof long[]) {
            intent.putExtra(key, (long[]) param);
        } else if (param instanceof float[]) {
            intent.putExtra(key, (float[]) param);
        } else if (param instanceof double[]) {
            intent.putExtra(key, (double[]) param);
        } else if (param instanceof Bundle) {
            intent.putExtra(key, (Bundle) param);
        } else if (param instanceof byte[]) {
            intent.putExtra(key, (byte[]) param);
        } else if (param instanceof char[]) {
            intent.putExtra(key, (char[]) param);
        } else if (param instanceof Parcelable) {
            intent.putExtra(key, (Parcelable) param);
        } else if (param instanceof Parcelable[]) {
            intent.putExtra(key, (Parcelable[]) param);
        } else if (param instanceof CharSequence) {
            intent.putExtra(key, (CharSequence) param);
        } else if (param instanceof CharSequence[]) {
            intent.putExtra(key, (CharSequence[]) param);
        }
        return intent;
    }


    /**
     * 传递数据
     *
     * @param bundle
     * @param key    关键字
     * @param param  数据
     * @return
     */
    public static Bundle putBundle(Bundle bundle, String key, Object param) {
        if (param instanceof Serializable) {
            bundle.putSerializable(key, (Serializable) param);
        } else if (param instanceof String) {
            bundle.putString(key, (String) param);
        } else if (param instanceof String[]) {
            bundle.putStringArray(key, (String[]) param);
        } else if (param instanceof boolean[]) {
            bundle.putBooleanArray(key, (boolean[]) param);
        } else if (param instanceof short[]) {
            bundle.putShortArray(key, (short[]) param);
        } else if (param instanceof int[]) {
            bundle.putIntArray(key, (int[]) param);
        } else if (param instanceof long[]) {
            bundle.putLongArray(key, (long[]) param);
        } else if (param instanceof float[]) {
            bundle.putFloatArray(key, (float[]) param);
        } else if (param instanceof double[]) {
            bundle.putDoubleArray(key, (double[]) param);
        } else if (param instanceof Bundle) {
            bundle.putBundle(key, (Bundle) param);
        } else if (param instanceof byte[]) {
            bundle.putByteArray(key, (byte[]) param);
        } else if (param instanceof char[]) {
            bundle.putCharArray(key, (char[]) param);
        } else if (param instanceof Parcelable) {
            bundle.putParcelable(key, (Parcelable) param);
        } else if (param instanceof Parcelable[]) {
            bundle.putParcelableArray(key, (Parcelable[]) param);
        } else if (param instanceof CharSequence) {
            bundle.putCharSequence(key, (CharSequence) param);
        } else if (param instanceof CharSequence[]) {
            bundle.putCharSequenceArray(key, (CharSequence[]) param);
        }
        return bundle;
    }


    /**
     * 获取文件选择的 Intent
     *
     * @param documentType 文件类型
     * @return
     */
    public static Intent getDocumentPickerIntent(@DocumentType String documentType) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        if (!StringUtils.isEmpty(documentType)) {
            return intent.setType(documentType);
        } else {
            return intent.setType(ANY);
        }
    }


    /**
     * 选择文件的类型
     */
    @StringDef({IMAGE, AUDIO, VIDEO, ANY})
    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.SOURCE)
    public @interface DocumentType {
        String IMAGE = "image/*";
        String AUDIO = "audio/*";
        String VIDEO = "video/*";
        String ANY = "*/*";
    }
}

