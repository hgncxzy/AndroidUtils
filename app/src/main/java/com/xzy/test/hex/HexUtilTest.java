package com.xzy.test.hex;

import com.xzy.utils.hex.HexUtil;
import com.xzy.utils.log.LogUtils;

/**
 * Author: xzy
 * Date:
 * Description:
 */
public class HexUtilTest {
    private static final String TAG = "HexUtilTest";

    public  void test() {
        // 字节数组转 16 进制字符串
        byte[] byte1 = {0x11};
        LogUtils.dTag(TAG, "Test HexUtil.parseBytesToHexString ->" + HexUtil.parseBytesToHexString(byte1));

        // 16 进制字符串转 10 进制整数
        // 不能带 0x
        String hexString = "11";
        LogUtils.dTag(TAG, "Test HexUtil.hexStringToInt ->" + HexUtil.hexStringToInt(hexString));

        // 字节数组合并
        byte[] bytea = {0x11, 0x22};
        byte[] byteb = {0x33, 0x44};
        LogUtils.dTag(TAG, "Test HexUtil.byteMerger ->" + HexUtil.byteMerger(bytea, byteb).length);

        // 将字节数组转换为十六进制字符串
        LogUtils.dTag(TAG, "Test HexUtil.bin2HexStr ->" + HexUtil.bin2HexStr(bytea));

        // 将十六进制字符串转换为二进制字符串
        LogUtils.dTag(TAG, "Test HexUtil.hexStr2BinStr ->" + HexUtil.hexStr2BinStr(hexString));

        // 将十六进制字符串转换为二进制字符串
        LogUtils.dTag(TAG, "Test HexUtil.hexString2binaryString ->" + HexUtil.hexString2binaryString(hexString));

        // 将二进制字符串转换为 16 进制字符串
        String bStr = "00001001";
        LogUtils.dTag(TAG, "Test HexUtil.binaryString2hexString ->" + HexUtil.binaryString2hexString(bStr));

        // 整数值转 2 进制字符串(不足 8 位，自动补零)
        LogUtils.dTag(TAG, "Test HexUtil.intValueToBinary ->" + HexUtil.intValueToBinary(1));
    }
}
