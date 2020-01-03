package com.xzy.utils.hex;

/**
 * Description : 16 进制以及进制转换相关
 *
 * @author hgncxzy
 */
@SuppressWarnings("unused")
public class HexUtil {
    /**
     * 字节数组转 16 进制字符串.
     *
     * @param b 字节数组
     * @return 16 进制字符串
     */
    public static String parseBytesToHexString(byte[] b) {
        StringBuilder stringBuffer = new StringBuilder();
        for (byte aB : b) {
            int temp = aB & 0xFF;
            String hex = Integer.toHexString(temp);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            stringBuffer.append(hex.toUpperCase()).append(" ");
        }
        return stringBuffer.toString();
    }

    /**
     * 16 进制字符串转 10 进制整数
     *
     * @param hexString 16 进制字符串  // 不能带 0x
     * @return 10 进制整数
     */
    public static int hexStringToInt(String hexString) {
        String s = Integer.valueOf(hexString, 16).toString();
        return Integer.parseInt(s);
    }

    /**
     * 字节数组合并
     */
    public static byte[] byteMerger(byte[] bt1, byte[] bt2) {
        byte[] bt3 = new byte[bt1.length + bt2.length];
        System.arraycopy(bt1, 0, bt3, 0, bt1.length);
        System.arraycopy(bt2, 0, bt3, bt1.length, bt2.length);
        return bt3;
    }


    private static String hexStr = "0123456789ABCDEF";

    private static String[] binaryArray =
            {"0000", "0001", "0010", "0011",
                    "0100", "0101", "0110", "0111",
                    "1000", "1001", "1010", "1011",
                    "1100", "1101", "1110", "1111"};

    /**
     * @return 二进制数组转换为二进制字符串   2-2
     */
    private static String bytes2BinStr(byte[] bArray) {

        StringBuilder outStr = new StringBuilder();
        int pos;
        for (byte b : bArray) {
            //高四位
            pos = (b & 0xF0) >> 4;
            outStr.append(binaryArray[pos]);
            //低四位
            pos = b & 0x0F;
            outStr.append(binaryArray[pos]);
        }
        return outStr.toString();
    }

    /**
     * @param bytes 二进制数组
     * @return 将字节数组转换为十六进制字符串  2-16
     */
    public static String bin2HexStr(byte[] bytes) {

        StringBuilder result = new StringBuilder();
        String hex;
        for (byte aByte : bytes) {
            //字节高4位
            hex = String.valueOf(hexStr.charAt((aByte & 0xF0) >> 4));
            //字节低4位
            hex += String.valueOf(hexStr.charAt(aByte & 0x0F));
            result.append(hex).append(" ");
        }
        return result.toString();
    }

    /**
     * @param hexString 十六进制字符串
     * @return 将十六进制转换为二进制字节数组   16-2
     */
    private static byte[] hexStr2BinArr(String hexString) {
        //hexString的长度对2取整，作为bytes的长度
        int len = hexString.length() / 2;
        byte[] bytes = new byte[len];
        //字节高四位
        byte high;
        //字节低四位
        byte low;
        for (int i = 0; i < len; i++) {
            //右移四位得到高位
            high = (byte) ((hexStr.indexOf(hexString.charAt(2 * i))) << 4);
            low = (byte) hexStr.indexOf(hexString.charAt(2 * i + 1));
            //高地位做或运算
            bytes[i] = (byte) (high | low);
        }
        return bytes;
    }

    /**
     * @param hexString 十六进制字符串
     * @return 将十六进制转换为二进制字符串   16-2
     */
    public static String hexStr2BinStr(String hexString) {
        return bytes2BinStr(hexStr2BinArr(hexString));
    }


    public static String hexString2binaryString(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0) {
            return null;
        }

        StringBuilder bString = new StringBuilder();
        String tmp;
        for (int i = 0; i < hexString.length(); i++) {
            tmp = "0000" + Integer.toBinaryString(Integer.parseInt(hexString.substring(i, i + 1), 16));
            bString.append(tmp.substring(tmp.length() - 4));
        }
        return bString.toString();
    }

    /**
     * 将二进制字符串转换为 16 进制字符串
     *
     * @param bString 二进制字符串 ps：二进制字符串的长度必须是 8 的整数倍长度
     * @return 16 进制字符串
     */
    public static String binaryString2hexString(String bString) {
        if (bString == null || "".equals(bString) || bString.length() % 8 != 0) {
            return null;
        }
        StringBuilder tmp = new StringBuilder();
        int iTmp;
        for (int i = 0; i < bString.length(); i += 4) {
            iTmp = 0;
            for (int j = 0; j < 4; j++) {
                iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
            }
            tmp.append(Integer.toHexString(iTmp));
        }
        return tmp.toString();
    }

    /**
     * 整数值转 2 进制字符串(不足 8 位，自动补零)
     **/
    public static String intValueToBinary(int num) {
        StringBuilder str = new StringBuilder();
        while (num != 0) {
            str.insert(0, num % 2);
            num = num / 2;
        }
        StringBuilder temp = new StringBuilder();
        if (str.length() < 8) {
            for (int i = 0; i < 8 - str.length(); i++) {
                temp.append("0");
            }
            str = new StringBuilder((temp + str.toString()).trim());
        }
        return str.toString();
    }
}
