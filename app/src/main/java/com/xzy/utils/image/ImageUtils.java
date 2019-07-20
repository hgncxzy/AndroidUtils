package com.xzy.utils.image;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.xzy.utils.density.DensityUtils;
import com.xzy.utils.screen.ScreenUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Hashtable;

/**
 * 图片处理工具类。
 */
@SuppressWarnings({"unused"})
public class ImageUtils {

    /**
     * 图片的八个位置
     **/
    public static final int TOP = 0;      //上
    public static final int BOTTOM = 1;      //下
    public static final int LEFT = 2;      //左
    public static final int RIGHT = 3;      //右
    public static final int LEFT_TOP = 4;    //左上
    public static final int LEFT_BOTTOM = 5;  //左下
    public static final int RIGHT_TOP = 6;    //右上
    public static final int RIGHT_BOTTOM = 7;  //右下

    /**
     * 图像的放大缩小方法
     *
     * @param src    源位图对象
     * @param scaleX 宽度比例系数
     * @param scaleY 高度比例系数
     * @return 返回位图对象
     */
    public static Bitmap zoomBitmap(Bitmap src, float scaleX, float scaleY) {
        Matrix matrix = new Matrix();
        matrix.setScale(scaleX, scaleY);
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight(), matrix, true);
    }

    /**
     * 图像放大缩小--根据宽度和高度
     *
     * @param src 源文件
     * @param width 宽度
     * @param height 高度
     * @return Bitmap
     */
    public static Bitmap zoomBitmap(Bitmap src, int width, int height) {
        return Bitmap.createScaledBitmap(src, width, height, true);
    }


    /**
     * 将 Bitmap 转换为 Drawable 对象
     *
     * @param bitmap Bitmap
     * @return Drawable
     */
    public static Drawable bitmap2Drawable(Bitmap bitmap) {
        return new BitmapDrawable(bitmap);
    }

    /**
     * Bitmap转byte[]
     *
     * @param bitmap bitmap
     * @return byte[]
     */
    public static byte[] bitmap2Byte(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        return out.toByteArray();
    }

    /**
     * byte[] 转 Bitmap
     *
     * @param data  byte[]
     * @return Bitmap
     */
    public static Bitmap byte2Bitmap(byte[] data) {
        if (data.length != 0) {
            return BitmapFactory.decodeByteArray(data, 0, data.length);
        }
        return null;
    }

    /**
     * 绘制带圆角的图像
     *
     * @param src Bitmap
     * @param radius 半径
     * @return Bitmap
     */
    public static Bitmap createRoundedCornerBitmap(Bitmap src, int radius) {
        final int w = src.getWidth();
        final int h = src.getHeight();
        // 高清量32位图
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        Canvas canvas = new Canvas(bitmap);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(0xff424242);
        // 防止边缘的锯齿
        paint.setFilterBitmap(true);
        Rect rect = new Rect(0, 0, w, h);
        RectF rectf = new RectF(rect);
        // 绘制带圆角的矩形
        canvas.drawRoundRect(rectf, radius, radius, paint);
        // 取两层绘制交集，显示上层
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // 绘制图像
        canvas.drawBitmap(src, rect, rect, paint);
        return bitmap;
    }

    /**
     * 创建选中带提示图片
     *
     * @param context 上下文
     * @param srcId  原图
     * @param tipId 提示图
     * @return Drawable
     */
    public static Drawable createSelectedTip(Context context, int srcId, int tipId) {
        Bitmap src = BitmapFactory.decodeResource(context.getResources(), srcId);
        Bitmap tip = BitmapFactory.decodeResource(context.getResources(), tipId);
        final int w = src.getWidth();
        final int h = src.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        Canvas canvas = new Canvas(bitmap);
        //绘制原图
        canvas.drawBitmap(src, 0, 0, paint);
        //绘制提示图片
        canvas.drawBitmap(tip, (w - tip.getWidth()), 0, paint);
        return bitmap2Drawable(bitmap);
    }

    /**
     * 带倒影的图像
     *
     * @param src Bitmap 源文件
     * @return  Bitmap
     */
    public static Bitmap createReflectionBitmap(Bitmap src) {
        // 两个图像间的空隙
        final int spacing = 4;
        final int w = src.getWidth();
        final int h = src.getHeight();
        // 绘制高质量32位图
        Bitmap bitmap = Bitmap.createBitmap(w, h + h / 2 + spacing, Bitmap.Config.ARGB_8888);
        // 创建燕X轴的倒影图像
        Matrix m = new Matrix();
        m.setScale(1, -1);
        Bitmap t_bitmap = Bitmap.createBitmap(src, 0, h / 2, w, h / 2, m, true);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        //  绘制原图像
        canvas.drawBitmap(src, 0, 0, paint);
        // 绘制倒影图像
        canvas.drawBitmap(t_bitmap, 0, h + spacing, paint);
        // 线性渲染-沿Y轴高到低渲染
        Shader shader = new LinearGradient(0, h + spacing, 0, h + spacing + h / 2,
                0x70ffffff, 0x00ffffff, Shader.TileMode.MIRROR);
        paint.setShader(shader);
        // 取两层绘制交集，显示下层。
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // 绘制渲染倒影的矩形
        canvas.drawRect(0, h + spacing, w, h + h / 2 + spacing, paint);
        return bitmap;
    }

    /**
     * 创建灰度图
     * @param src 源文件
     * @return Bitmap
     */
    public static Bitmap createGreyBitmap(Bitmap src) {
        final int w = src.getWidth();
        final int h = src.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        // 颜色变换的矩阵
        ColorMatrix matrix = new ColorMatrix();
        // saturation 饱和度值，最小可设为 0，此时对应的是灰度图；为 1 表示饱和度不变，设置大于 1，就显示过饱和
        matrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        paint.setColorFilter(filter);
        canvas.drawBitmap(src, 0, 0, paint);
        return bitmap;
    }

    /**
     * 添加水印效果
     *
     * @param src       源位图
     * @param watermark 水印
     * @param direction 方向
     * @param spacing   间距
     * @return Bitmap
     */
    public static Bitmap createWatermark(Bitmap src, Bitmap watermark,
                                         int direction, int spacing) {
        final int w = src.getWidth();
        final int h = src.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(src, 0, 0, null);
        if (direction == LEFT_TOP) {
            canvas.drawBitmap(watermark, spacing, spacing, null);
        } else if (direction == LEFT_BOTTOM) {
            canvas.drawBitmap(watermark, spacing, h - watermark.getHeight()
                    - spacing, null);
        } else if (direction == RIGHT_TOP) {
            canvas.drawBitmap(watermark, w - watermark.getWidth() - spacing,
                    spacing, null);
        } else if (direction == RIGHT_BOTTOM) {
            canvas.drawBitmap(watermark, w - watermark.getWidth() - spacing,
                    h - watermark.getHeight() - spacing, null);
        }
        return bitmap;
    }


    /**
     * 合成图像
     *
     * @param direction 方向
     * @param bitmaps bitmap 合集
     * @return Bitmap
     */
    public static Bitmap composeBitmap(int direction, Bitmap... bitmaps) {
        if (bitmaps.length < 2) {
            return null;
        }
        Bitmap firstBitmap = bitmaps[0];
        for (Bitmap bitmap : bitmaps) {
            firstBitmap = composeBitmap(firstBitmap, bitmap, direction);
        }
        return firstBitmap;
    }

    /**
     * 合成两张图像
     *
     * @param firstBitmap 第一张图
     * @param secondBitmap 第二张图
     * @param direction 合成方向
     * @return Bitmap
     */
    private static Bitmap composeBitmap(Bitmap firstBitmap, Bitmap secondBitmap,
                                        int direction) {
        if (firstBitmap == null) {
            return null;
        }
        if (secondBitmap == null) {
            return firstBitmap;
        }
        final int fw = firstBitmap.getWidth();
        final int fh = firstBitmap.getHeight();
        final int sw = secondBitmap.getWidth();
        final int sh = secondBitmap.getHeight();
        Bitmap bitmap = null;
        Canvas canvas;
        if (direction == TOP) {
            bitmap = Bitmap.createBitmap(sw > fw ? sw : fw, fh +
                    sh, Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmap);
            canvas.drawBitmap(secondBitmap, 0, 0, null);
            canvas.drawBitmap(firstBitmap, 0, sh, null);
        } else if (direction == BOTTOM) {
            bitmap = Bitmap.createBitmap(fw > sw ? fw : sw, fh +
                    sh, Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmap);
            canvas.drawBitmap(firstBitmap, 0, 0, null);
            canvas.drawBitmap(secondBitmap, 0, fh, null);
        } else if (direction == LEFT) {
            bitmap = Bitmap.createBitmap(fw + sw, sh > fh ? sh :
                    fh, Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmap);
            canvas.drawBitmap(secondBitmap, 0, 0, null);
            canvas.drawBitmap(firstBitmap, sw, 0, null);
        } else if (direction == RIGHT) {
            bitmap = Bitmap.createBitmap(fw + sw, fh > sh ? fh : sh,
                    Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmap);
            canvas.drawBitmap(firstBitmap, 0, 0, null);
            canvas.drawBitmap(secondBitmap, fw, 0, null);
        }
        return bitmap;
    }

    /**
     * 根据原图和变长绘制圆形图片
     *
     * @param source 源文件
     * @param min    圆形图片半径
     * @return bitmap
     */
    public static Bitmap createCircleImage(Bitmap source, int min) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        // 产生一个同样大小的画布
        Canvas canvas = new Canvas(target);
        // 首先绘制圆形
        float size = min >> 1;
        canvas.drawCircle(size, size, size, paint);
        // 使用 SRC_IN
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // 绘制图片
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

    /**
     * 得到有一定透明度的图片
     *
     * @param source 源文件
     * @param number 透明度值
     * @return Bitmap
     */
    public static Bitmap getTransparentBitmap(Bitmap source, int number) {
        if (source == null) {
            return null;
        }
        int[] argb = new int[source.getWidth() * source.getHeight()];
        source.getPixels(argb, 0, source.getWidth(), 0, 0, source
                .getWidth(), source.getHeight());// 获得图片的ARGB值
        number = number * 255 / 100;
        for (int i = 0; i < argb.length; i++) {
            argb[i] = (number << 24) | (argb[i] & 0x00FFFFFF);
        }
        source = Bitmap.createBitmap(argb, source.getWidth(), source
                .getHeight(), Bitmap.Config.ARGB_8888);
        return source;
    }

    /**
     * 保存 bitmap 到指定路径
     *
     * @param source 源文件
     * @param path   保存的路径
     * @param format   Bitmap.CompressFormat.JPEG,
     *                 Bitmap.CompressFormat.PNG,
     *                 Bitmap.CompressFormat.WEBP;
     * @return boolean
     */
    public static boolean saveBitmap2Path(Bitmap source, String path,Bitmap.CompressFormat format) {
        if (source == null) {
            return false;
        }
        File f = new File(path);
        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(f);
            source.compress(format, 100, fOut);
            fOut.flush();
            fOut.close();
            return true;
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 生成 bitmap 文件
     *
     * @param context 上下文
     * @param resId   资源 id
     * @return Bitmap
     */
    public static Bitmap getBitmap(Context context, int resId) {
        Resources res = context.getResources();
        return BitmapFactory.decodeResource(res, resId);
    }

    /**
     * 从文件路径中获取 bitmap
     *
     * @param path 文件路径
     * @return Bitmap
     */
    public static Bitmap getBitmapFromFile(String path) {
        Bitmap bitmap = null;
        try {
            FileInputStream fis = new FileInputStream(path);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * drawable 转 bitmap
     *
     * @param drawable Drawable
     * @return Bitmap
     */
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }


    /**
     * drawable 缩放
     *
     * @param drawable Drawable
     * @param w        宽度
     * @param h        高度
     * @return Drawable
     */
    public static Drawable zoomDrawable(Drawable drawable, int w, int h) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        // drawable转换成bitmap
        Bitmap oldBitmap = drawable2Bitmap(drawable);
        // 创建操作图片用的Matrix对象
        Matrix matrix = new Matrix();
        // 计算缩放比例
        float sx = ((float) w / width);
        float sy = ((float) h / height);
        // 设置缩放比例
        matrix.postScale(sx, sy);
        // 建立新的bitmap，其内容是对原bitmap的缩放后的图
        Bitmap newBitmap = Bitmap.createBitmap(oldBitmap, 0, 0, width, height,
                matrix, true);
        return new BitmapDrawable(newBitmap);
    }

    /**
     * 得到新尺寸的 bitmap
     *
     * @param bm        源文件
     * @param newHeight 目标高度
     * @param newWidth  目标宽度
     * @return Bitmap
     */
    public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
    }


    /**
     * 获取网络图片资源
     *
     * @param url 网络 url
     * @return Bitmap
     */
    public static Bitmap getBitmapFromInternet(String url) {
        URL myFileURL;
        Bitmap bitmap = null;
        try {
            myFileURL = new URL(url);
            //获得连接
            HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection();
            //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(6000);
            //连接设置获得数据流
            conn.setDoInput(true);
            //不使用缓存
            conn.setUseCaches(false);
            //这句可有可无，没有影响
            //conn.connect();
            //得到数据流
            InputStream is = conn.getInputStream();
            //解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
            //关闭数据流
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 通过字符串生成一个二维码
     *
     * @param string       给定的字符串
     * @param qrCodeWidth  二维码宽度
     * @param qrCodeHeight 二维码高度
     * @return bitmap  Bitmap
     * @throws WriterException 生成二维码异常
     */
    public static Bitmap createQRCode(String string, int qrCodeWidth, int qrCodeHeight) throws WriterException {
        Hashtable<EncodeHintType, String> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix matrix = new MultiFormatWriter().encode(string, BarcodeFormat.QR_CODE,
                qrCodeWidth, qrCodeHeight, hints);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                } else {
                    // -1 相当于0xffffffff 白色
                    pixels[y * width + x] = 0xffffffff;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }


    /**
     * 生成中间带图片的二维码
     *
     * @param context   上下文
     * @param content   二维码内容
     * @param logo      二维码中间的图片
     * @param halfWidth 宽度值，影响中间图片大小 可以设置为 40
     * @param dpWidth   二维码宽度
     * @param dpHeight  二维码高度
     * @return 生成的二维码图片
     * @throws WriterException 生成二维码异常
     */
    public static Bitmap createCode(Context context, String content, Bitmap logo,
                                    int halfWidth,
                                    int dpWidth, int dpHeight) throws WriterException {
        Matrix m = new Matrix();
        float sx = (float) 2 * halfWidth / logo.getWidth();
        float sy = (float) 2 * halfWidth / logo.getHeight();
        // 设置缩放信息
        m.setScale(sx, sy);
        // 将logo图片按 martix 设置的信息缩放
        logo = Bitmap.createBitmap(logo, 0, 0, logo.getWidth(),
                logo.getHeight(), m, false);
        MultiFormatWriter writer = new MultiFormatWriter();
        Hashtable hst = new Hashtable();
        // 设置字符编码
        hst.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 设置二维码容错率
        hst.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 生成二维码矩阵信息
        BitMatrix matrix = writer.encode(content,BarcodeFormat.QR_CODE,
                DensityUtils.dp2px(context, dpWidth),
                DensityUtils.dp2px(context, dpHeight), hst);
        // 矩阵高度
        int width = matrix.getWidth();
        // 矩阵宽度
        int height = matrix.getHeight();
        int halfW = width / 2;
        int halfH = height / 2;
        // 定义数组长度为矩阵高度*矩阵宽度，用于记录矩阵中像素信息
        int[] pixels = new int[width * height];
        // 从行开始迭代矩阵
        for (int y = 0; y < height; y++) {
            // 迭代列
            for (int x = 0; x < width; x++) {
                // 该位置用于存放图片信息
                if (x > halfW - halfWidth && x < halfW + halfWidth
                        && y > halfH - halfWidth
                        && y < halfH + halfWidth) {
                    // 记录图片每个像素信息
                    pixels[y * width + x] = logo.getPixel(x - halfW
                            + halfWidth, y - halfH + halfWidth);
                } else {
                    // 如果有黑块点，记录信息
                    if (matrix.get(x, y)) {
                        // 记录黑块信息
                        pixels[y * width + x] = 0xff000000;
                    } else {
                        pixels[y * width + x] = Color.WHITE;
                    }
                }

            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // 通过像素数组生成bitmap
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /**
     * 质量压缩方法 -- 压缩图片
     *
     * @param source 源文件
     * @return 压缩后的 Bitmap
     */
    public static Bitmap qualityCompressBitmap(Bitmap source) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 质量压缩方法，这里 100 表示不压缩，把压缩后的数据存放到 byteArrayOutputStream 中
        source.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        int options = 100;
        Log.d("qualityCompressBitmap", "压缩前图片占内存大小（KB）====" +
                byteArrayOutputStream.toByteArray().length / 1024);
        // 循环判断如果压缩后图片是否大于 80kb,大于继续压缩
        while (byteArrayOutputStream.toByteArray().length / 1024 > 80) {
            // 重置 byteArrayOutputStream 即清空 byteArrayOutputStream
            byteArrayOutputStream.reset();
            // 这里压缩 options%，把压缩后的数据存放到 byteArrayOutputStream 中
            source.compress(Bitmap.CompressFormat.JPEG, options, byteArrayOutputStream);
            // 每次都减少 10
            options -= 10;
            Log.d("qualityCompressBitmap", "压缩后图片占内存大小（KB）" +
                    byteArrayOutputStream.toByteArray().length / 1024);
        }
        if (!source.isRecycled()) {
            source.recycle();
            System.gc();
        }
        // 把压缩后的数据 byteArrayOutputStream 存放到 ByteArrayInputStream 中
        ByteArrayInputStream isBm = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        // 把 ByteArrayInputStream 数据生成图片
        return BitmapFactory.decodeStream(isBm, null, null);
    }

    /**
     * 图片按比例大小压缩方法（根据 Bitmap 图片压缩）
     *
     * @param source 源文件
     * @return Bitmap
     */
    public static Bitmap proportionCompressBitmap(Bitmap source) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        source.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        // 判断如果图片大于 1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
        if (byteArrayOutputStream.toByteArray().length / 1024 > 1024) {
            //重置 byteArrayOutputStream 即清空 byteArrayOutputStream
            byteArrayOutputStream.reset();
            //这里压缩 50%，把压缩后的数据存放到 byteArrayOutputStream 中
            source.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        }
        ByteArrayInputStream isBm;
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把 options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap;
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是 800*480 分辨率，所以高和宽我们设置为
        // 这里设置高度为 800f
        float hh = 480f;
        // 这里设置宽度为 480f
        float ww = 640f;
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        // be = 1 表示不缩放
        int be = 1;
        // 如果宽度大的话根据宽度固定大小缩放
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            // 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        // 重新读入图片，注意此时已经把 options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        // 压缩好比例大小后再进行质量压缩
        if (bitmap != null) {
            return qualityCompressBitmap(bitmap);
            // 压缩好比例大小
            // return bitmap;
        }
        return null;
    }

    /**
     * 将 byte[] 转换成 bitmap 并设置照片
     *
     * @param imageView    iamgeView
     * @param bytes        byte[]
     * @param inSampleSize 缩放系数 图片宽高都为原来的 inSampleSize 分之一，
     *                     即图片为原来的 inSampleSize*inSampleSize 分之一
     */
    public static void setBitmap2ImageView(ImageView imageView, byte[] bytes, int inSampleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        // 图片宽高都为原来的 inSampleSize 分之一，即图片为原来的 inSampleSize*inSampleSize 分之一
        options.inSampleSize = inSampleSize;
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        imageView.setImageBitmap(bitmap);
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.isRecycled();
            System.gc();
        }
    }

    /**
     * byte[] 转 bitmap
     *
     * @param bytes        byte[]
     * @param inSampleSize 缩放系数
     * @return Bitmap
     */
    public static Bitmap getBitmap(byte[] bytes, int inSampleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        // 图片宽高都为原来的 inSampleSize 分之一，即图片为原来的 inSampleSize*inSampleSize 分之一
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    /**
     * 将图片转成指定弧度（角度）的图片
     *
     * @param bitmap 需要修改的图片
     * @param pixels 圆角的弧度
     * @return 圆角图片 Bitmap
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        //根据图片创建画布
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(0xff424242);
        canvas.drawRoundRect(rectF, (float) pixels, (float) pixels, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
}
