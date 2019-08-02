package com.xzy.utils.match;


import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据校验工具类。
 *
 * @author xzy
 */
@SuppressWarnings("unused")
public class MatchUtil {

    /**
     * 检查字符串是否全部是数字
     *
     * @param text 需要检查的字符串
     * @return boolean
     */
    public static boolean isAllNumber(String text) {
        Pattern mPattern = Pattern.compile("^[0-9]+");
        Matcher mMatcher = mPattern.matcher(text);
        return mMatcher.matches();
    }

    /**
     * 检查是否全部是字符(非数字)
     *
     * @param text 需要检查的字符串
     * @return boolean
     */
    public static boolean isAllCharacter(String text) {
        Pattern mPattern = Pattern.compile("^[a-zA-Z]+");
        Matcher mMatcher = mPattern.matcher(text);
        return mMatcher.matches();
    }

    /**
     * 判断是否是手机号
     *
     * @param phoneNum 手机号
     * @return boolean
     */
    public static boolean isPhoneNumber(String phoneNum) {
        String telRegex =
                "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";
        // "[1]"代表第1位为数字1，
        // "[358]"代表第二位可以为3、5、8中的一个，
        // "\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(phoneNum)) {
            return false;
        } else {
            return phoneNum.matches(telRegex);
        }
    }

    /**
     * 判断是否是邮政编码
     *
     * @param postCode 邮政编码
     * @return boolean
     */
    public static boolean isPostCode(String postCode) {
        Pattern mPattern = Pattern.compile("^[a-zA-Z0-9_-]{6}$");
        Matcher mMatcher = mPattern.matcher(postCode);
        return mMatcher.matches();
    }

    /**
     * 判断是否是邮件
     *
     * @param mail 电子邮件
     * @return boolean
     */
    public static boolean isMail(String mail) {
        Pattern mPattern = Patterns.EMAIL_ADDRESS;
        // mPattern =
        // Pattern.compile("^[A-Za-zd]+([-_.][A-Za-zd]+)*@([A-Za-zd]+[-.])+[A-Za-zd]{2,5}$");
        Matcher mMatcher = mPattern.matcher(mail);
        return mMatcher.matches();
    }

    /**
     * 判断是否是合法的 ip 地址。
     *
     * @param ip ip 地址
     * @return boolean
     */
    public static boolean isIPAddress(String ip) {
        Pattern mPattern = Patterns.IP_ADDRESS;
        Matcher mMatcher = mPattern.matcher(ip);
        return mMatcher.matches();
    }

}
