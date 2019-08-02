package com.xzy.utils.jinzhi;

/**
 * 进制转换工具类。
 *
 * @author xzy
 */
@SuppressWarnings("unused")
public class OperationUtils {

    /**
     * 总进制转换
     *
     * @param typeFrom     从进制
     * @param typeTo       到进制
     * @param transferText 需要转换的数字
     * @return 转换后的数字字符串
     */
    public String transfer(String typeFrom, String typeTo, String transferText) {
        return from10(typeTo, to10(typeFrom, transferText));
    }

    /**
     * 从某个进制转 10 进制
     *
     * @param from1 从进制
     * @param txt   要转换的数
     * @return 转换后结果
     */
    private int to10(String from1, String txt) {
        int from = Integer.parseInt(from1);
        int sum = 0;
        int i = txt.length() - 1;
        int len = i;
        while (i >= 0) {
            // 判断输入文本的当前取出的char值是数字，并且值不为0
            if (Character.isDigit(txt.charAt(i))) {
                if (txt.charAt(i) != '0') {
                    int temp = Integer.parseInt(String.valueOf(txt.charAt(i)));
                    for (int j = 0; j < len - i; j++) {
                        temp *= from;
                    }
                    sum += temp;
                }
            } else {
                int temp;
                switch (txt.charAt(i)) {
                    case 'A':
                        temp = 10;
                        break;
                    case 'B':
                        temp = 11;
                        break;
                    case 'C':
                        temp = 12;
                        break;
                    case 'D':
                        temp = 13;
                        break;
                    case 'E':
                        temp = 14;
                        break;
                    case 'F':
                        temp = 15;
                        break;
                    default:
                        return 0;
                }
                for (int j = 0; j < len - i; j++) {
                    temp *= from;
                }
                sum += temp;
            }
            i--;
        }
        return sum;
    }

    /**
     * 从 10 进制转为其他进制
     *
     * @param toType 要转换后的进制
     * @param num 要转的 10 进制数
     * @return 转换后的值(String)
     */
    private String from10(String toType, int num) {
        int to = Integer.parseInt(toType);
        StringBuilder jg = new StringBuilder();
        while (num != 0) {
            switch (num % to) {
                case 10:
                    jg.insert(0, "A");
                    break;
                case 11:
                    jg.insert(0, "B");
                    break;
                case 12:
                    jg.insert(0, "C");
                    break;
                case 13:
                    jg.insert(0, "D");
                    break;
                case 14:
                    jg.insert(0, "E");
                    break;
                case 15:
                    jg.insert(0, "F");
                    break;
                default:
                    jg.insert(0, num % to);
                    break;
            }
            num = num / to;
        }
        return jg.toString();
    }
}

