package com.xzy.utils.snackbar;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.xzy.utils.R;

/**
 * SnackBar 工具类。
 * SnackBar开发详解 https://blog.csdn.net/qq_19431333/article/details/52862348
 *
 * @author xzy
 */
@SuppressWarnings("ALL")
public class SnackBarUtils {
    //默认 SnackBar 字体颜色白色
    public static final int messageColor = 0xFFFFFF;
    public static final int actionColor = 0xFFFFFF;
    //默认 SnackBar 背景色为 app 主题颜色
    public static final int backgroundColor = 0x66B3FF;


    /**
     * 短时间显示 SnackBar
     * 默认 SnackBar 字体颜色白色，背景颜色黑色
     *
     * @param view    view
     * @param message 显示的文字
     * @return SnackBar
     */
    public static Snackbar ShortSnackBar(View view, String message) {
        return ShortSnackBar(view, message, messageColor, backgroundColor);
    }

    /**
     * 短时间显示 SnackBar，可以自定义文字颜色和背景颜色
     *
     * @param view            view
     * @param message         显示的文字
     * @param messageColor    文字颜色
     * @param backgroundColor 背景颜色
     * @return SnackBar
     */
    public static Snackbar ShortSnackBar(View view, String message, int messageColor,
                                         int backgroundColor) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        setSnackBarColor(snackbar, messageColor, backgroundColor);
        snackbar.show();
        return snackbar;
    }

    /**
     * 长时间显示 SnackBar
     * 默认 SnackBar 字体颜色白色，背景颜色黑色
     *
     * @param view    view
     * @param message showMessage
     * @return SnackBar
     */
    public static Snackbar LongSnackBar(View view, String message) {
        return LongSnackBar(view, message, messageColor, backgroundColor, actionColor);
    }

    /**
     * 长时间显示 SnackBar,可以自定义字体颜色、背景色
     *
     * @param view            view
     * @param message         showMsg
     * @param messageColor    textColor
     * @param backgroundColor bgColor
     * @return
     */
    public static Snackbar LongSnackBar(View view, String message, int messageColor,
                                        int backgroundColor) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        setSnackBarColor(snackbar, messageColor, backgroundColor);
        snackbar.show();
        return snackbar;
    }

    /**
     * 长时间显示 SnackBar,可以自定义字体颜色、背景色、action 颜色
     *
     * @param view            view
     * @param message         showMsg
     * @param messageColor    msgColor
     * @param backgroundColor bgColor
     * @param actionColor     actionColor
     * @return SnackBar
     */
    public static Snackbar LongSnackBar(View view, String message, int messageColor,
                                        int backgroundColor, int actionColor) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        setSnackBarColor(snackbar, messageColor, backgroundColor, actionColor);
        snackbar.show();
        return snackbar;
    }

    /**
     * 自定义显示
     *
     * @param view    view
     * @param message showMsg
     * @return SnackBar
     */
    public static Snackbar CustomizeSnackBar(View view, String message) {
        return CustomizeSnackBar(view, message, messageColor, backgroundColor, actionColor);
    }

    /**
     * 自定义  SnackBar
     *
     * @param view            view
     * @param message         showMsg
     * @param messageColor    msgColor
     * @param backgroundColor bgColor
     * @param actionColor     actionColor
     * @return SnackBar
     */
    public static Snackbar CustomizeSnackBar(View view, String message, int messageColor,
                                             int backgroundColor, int actionColor) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE);
        setSnackBarColor(snackbar, messageColor, backgroundColor, actionColor);
        snackbar.show();
        return snackbar;
    }

    /**
     * 顶部 SnackBar
     *
     * @param view    view
     * @param message showMsg
     * @return 顶部 SnackBar
     */
    public static Snackbar topSnackBar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        Snackbar.SnackbarLayout snackarLayout = (Snackbar.SnackbarLayout) snackbarView;
        View add_view = LayoutInflater.from(snackbarView.getContext())
                .inflate(null, null);//加载布局文件新建View

        return snackbar;
    }

    /**
     * 设置消息颜色，背景颜色，Action 文字颜色
     *
     * @param snackBar        SnackBar
     * @param messageColor    msgColor
     * @param backgroundColor bgColor
     * @param actionColor     actionColor
     */
    public static void setSnackBarColor(Snackbar snackBar, int messageColor, int backgroundColor,
                                        int actionColor) {
        snackBar.setActionTextColor(actionColor);
        View view = snackBar.getView();
        view.setBackgroundColor(backgroundColor);
        ((TextView) view.findViewById(R.id.snackbar_text))
                .setTextColor(messageColor);
    }

    /**
     * 设置消息颜色和背景颜色
     *
     * @param snackBar        snackBar
     * @param messageColor    msgColor
     * @param backgroundColor bgColor
     */
    public static void setSnackBarColor(Snackbar snackBar, int messageColor, int backgroundColor) {
        setSnackBarColor(snackBar, messageColor, backgroundColor, 0);
    }
}
