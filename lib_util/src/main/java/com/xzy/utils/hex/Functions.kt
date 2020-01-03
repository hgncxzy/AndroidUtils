package com.xzy.utils.hex

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import java.io.UnsupportedEncodingException
import kotlin.experimental.and
import kotlin.experimental.xor


/**
 * kotlin 版方法，关于hex 16 进制以及进制转换的工具类
 * Author: xzy
 */
@Suppress("unused")
class Functions

/**
 * 用于建立十六进制字符的输出的小写字符数组
 */
private val DIGITS_LOWER =
        charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')

/**
 * 用于建立十六进制字符的输出的大写字符数组
 */
private val DIGITS_UPPER =
        charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')

/**
 * 将字节数组转换为十六进制字符数组
 *
 * @param data     byte[]
 * @param toDigits 用于控制输出的char[]
 * @return 十六进制char[]
 */
private fun encodeHex(data: ByteArray, toDigits: CharArray): CharArray {
    val l = data.size
    val out = CharArray(l shl 1)
    // two characters form the hex value.
    var i = 0
    var j = 0
    while (i < l) {
        out[j++] = toDigits[(0xF0 and data[i].toInt()).ushr(4)]
        out[j++] = toDigits[0x0F and data[i].toInt()]
        i++
    }
    return out
}

/**
 * 将字节数组转换为十六进制字符串
 *
 * @param data        byte[]
 * @param toLowerCase `true` 传换成小写格式 ， `false` 传换成大写格式
 * @return 十六进制String
 */
@JvmOverloads
@Suppress("unused")
fun encodeHexStr(data: ByteArray, toLowerCase: Boolean = true): String {
    return encodeHexStr(data, if (toLowerCase) DIGITS_LOWER else DIGITS_UPPER)
}

/**
 * 将字节数组转换为十六进制字符串
 *
 * @param data     byte[]
 * @param toDigits 用于控制输出的char[]
 * @return 十六进制String
 */
private fun encodeHexStr(data: ByteArray?, toDigits: CharArray): String {
    if (data == null) {
        Log.e(TAG, "this data is null.")
        return ""
    }
    return String(encodeHex(data, toDigits))
}

/**
 * 将十六进制字符串转换为字节数组
 *
 * @param data 16 进制字符串
 * @return 16 进制字节数组
 */
@Suppress("unused")
fun decodeHex(data: String?): ByteArray {
    if (data == null) {
        Log.e(TAG, "this data is null.")
        return ByteArray(0)
    }
    return decodeHex(data.toCharArray())
}

/**
 * 将十六进制字符数组转换为字节数组
 *
 * @param data 十六进制char[]
 * @return byte[]
 */
private fun decodeHex(data: CharArray): ByteArray {

    val len = data.size

    if (len and 0x01 != 0) {
        //如果源十六进制字符数组是一个奇怪的长度，将抛出运行时异常
        throw RuntimeException("Odd number of characters.")
    }

    val out = ByteArray(len shr 1)

    // two characters form the hex value.
    var i = 0
    var j = 0
    while (j < len) {
        var f = toDigit(data[j], j) shl 4
        j++
        f = f or toDigit(data[j], j)
        j++
        out[i] = (f and 0xFF).toByte()
        i++
    }

    return out
}

/**
 * 将十六进制字符转换成一个整数
 *
 * @param ch    十六进制char
 * @param index 十六进制字符在字符数组中的位置
 * @return 一个整数
 */
private fun toDigit(ch: Char, index: Int): Int {
    val digit = Character.digit(ch, 16)
    if (digit == -1) {
        // 当ch不是一个合法的十六进制字符时，抛出运行时异常
        throw RuntimeException("Illegal hexadecimal character $ch at index $index")
    }
    return digit
}

/**
 * 截取字节数组
 *
 * @param src   byte []  数组源  这里填16进制的 数组
 * @param begin 起始位置 源数组的起始位置。0位置有效
 * @param count 截取长度
 * @return byte[]
 */
@Suppress("unused")
fun subBytes(src: ByteArray, begin: Int, count: Int): ByteArray {
    val bs = ByteArray(count)
    System.arraycopy(src, begin, bs, 0, count)  // bs 目的数组  0 截取后存放的数值起始位置。0位置有效
    return bs
}

/**
 * int转byte数组
 *
 * @param bb 数组
 * @param x 需要转换的整数
 * @param index 第几位开始
 * @param flag 标识高低位顺序，高位在前为true，低位在前为false
 */
@Suppress("unused")
fun intToByte(bb: ByteArray, x: Int, index: Int, flag: Boolean) {
    if (flag) {
        bb[index] = (x shr 24).toByte()
        bb[index + 1] = (x shr 16).toByte()
        bb[index + 2] = (x shr 8).toByte()
        bb[index + 3] = x.toByte()
    } else {
        bb[index + 3] = (x shr 24).toByte()
        bb[index + 2] = (x shr 16).toByte()
        bb[index + 1] = (x shr 8).toByte()
        bb[index] = x.toByte()
    }
}

/**
 * byte数组转int
 *
 * @param bb 数组
 * @param index 第几位开始
 * @param flag 标识高低位顺序，高位在前为true，低位在前为false
 * @return int
 */
@Suppress("unused")
fun byteToInt(bb: ByteArray, index: Int, flag: Boolean): Int {
    return if (flag) {
        ((bb[index] and 0xff.toByte()).toInt() shl 24
                or ((bb[index + 1] and 0xff.toByte()).toInt() shl 16)
                or ((bb[index + 2] and 0xff.toByte()).toInt() shl 8)
                or (bb[index + 3] and 0xff.toByte()).toInt())
    } else {
        ((bb[index + 3] and 0xff.toByte()).toInt() shl 24
                or ((bb[index + 2] and 0xff.toByte()).toInt() shl 16)
                or ((bb[index + 1] and 0xff.toByte()).toInt() shl 8)
                or ((bb[index] and 0xff.toByte())).toInt())
    }
}


/**
 * 字节数组逆序
 *
 * @param data byte[]
 * @return byte[]
 */
@Suppress("unused")
fun reverse(data: ByteArray): ByteArray {
    val reverseData = ByteArray(data.size)
    for (i in data.indices) {
        reverseData[i] = data[data.size - 1 - i]
    }
    return reverseData
}

/**
 * 蓝牙传输 16进制 高低位 读数的 转换
 *
 * @param data 截取数据源，字节数组
 * @param index 截取数据开始位置
 * @param count 截取数据长度，只能为2、4、8个字节
 * @param flag 标识高低位顺序，高位在前为true，低位在前为false
 * @return long
 */
@Suppress("unused")
fun byteToLong(data: ByteArray, index: Int, count: Int, flag: Boolean): Long {
    var lg: Long = 0
    if (flag) {
        when (count) {
            2 -> lg = data[index].toLong() and 0xff shl 8 or (data[index + 1].toLong() and 0xff)

            4 -> lg = (data[index].toLong() and 0xff shl 24
                    or (data[index + 1].toLong() and 0xff shl 16)
                    or (data[index + 2].toLong() and 0xff shl 8)
                    or (data[index + 3].toLong() and 0xff))

            8 -> lg = (data[index].toLong() and 0xff shl 56
                    or (data[index + 1].toLong() and 0xff shl 48)
                    or (data[index + 2].toLong() and 0xff shl 40)
                    or (data[index + 3].toLong() and 0xff shl 32)
                    or (data[index + 4].toLong() and 0xff shl 24)
                    or (data[index + 5].toLong() and 0xff shl 16)
                    or (data[index + 6].toLong() and 0xff shl 8)
                    or (data[index + 7].toLong() and 0xff))
        }
        return lg
    } else {
        when (count) {
            2 -> lg = data[index + 1].toLong() and 0xff shl 8 or (data[index].toLong() and 0xff)
            4 -> lg = (data[index + 3].toLong() and 0xff shl 24
                    or (data[index + 2].toLong() and 0xff shl 16)
                    or (data[index + 1].toLong() and 0xff shl 8)
                    or (data[index].toLong() and 0xff))
            8 -> lg = (data[index + 7].toLong() and 0xff shl 56
                    or (data[index + 6].toLong() and 0xff shl 48)
                    or (data[index + 5].toLong() and 0xff shl 40)
                    or (data[index + 4].toLong() and 0xff shl 32)
                    or (data[index + 3].toLong() and 0xff shl 24)
                    or (data[index + 2].toLong() and 0xff shl 16)
                    or (data[index + 1].toLong() and 0xff shl 8)
                    or (data[index].toLong() and 0xff))
        }
        return lg
    }
}


/**
 * 执行异或操作
 * @param bytes 需要操作的数组
 * @return byte
 */
@Suppress("unused")
fun xOrVerify(bytes: ByteArray): Byte {
    var xOrValue: Byte = 0x00
    var i: Byte = 0x00
    while (i < bytes.size) {
        xOrValue = (bytes[i.toInt()] xor xOrValue)
        i++
    }
    return xOrValue
}

/**
 * java 字节转 16 进制字符串.
 *
 * @param b 字节数组
 * @return 16 进制字符串
 */
@SuppressLint("DefaultLocale")
@Suppress("unused")
fun parseBytesToHexString(b: ByteArray): String {
    val stringBuffer = StringBuilder()
    for (aB in b) {
        val temp = aB and 0xFF.toByte()
        var hex = Integer.toHexString(temp.toInt())
        if (hex.length == 1) {
            hex = "0$hex"
        }
        stringBuffer.append(hex.toLowerCase()).append(" ")
    }
    return stringBuffer.toString().replace("f", "")
}

/**
 * java 字节转 16 进制字符串.
 *
 * @param b 字节数组
 * @return 16 进制字符串 ps:使用 replace 函数，替换了 16 进制中的 FF 字符串
 */
@Suppress("unused")
@SuppressLint("DefaultLocale")
fun parseBytes2HexString(b: ByteArray): String {
    val stringBuffer = StringBuilder()
    for (aB in b) {
        val temp = aB and 0xFF.toByte()
        //val temp = aB
        var hex = Integer.toHexString(temp.toInt())
        if (hex.length == 1) {
            hex = "0$hex"
        }
        stringBuffer.append(hex.toLowerCase())
    }
    return stringBuffer.toString().replace("f", "")
}

/**
 * 十六进制字符串转 10 进制整数
 *
 * @param hexString 十六进制字符串转
 * @return 10 进制整数
 */
@Suppress("unused")
fun hexStringToInt(hexString: String?): Int {
    val s = Integer.valueOf(hexString!!, 16).toString()
    return s.toInt()
}
@Suppress("unused")
fun toUtf8(str: String): String? {
    var result: String? = null
    try {
        result = String(str.toByteArray(charset("utf-8")))
    } catch (e: UnsupportedEncodingException) {
        e.printStackTrace()
    }
    return result
}

/**
 * 整数转二进制字符串 -- 自动补齐 8 位
 */
@Suppress("unused")
fun toBinary(numValue: Int): String? {
    var num = numValue
    var str = java.lang.StringBuilder()
    while (num != 0) {
        str.insert(0, num % 2)
        num /= 2
    }
    val temp = java.lang.StringBuilder()
    if (str.length < 8) {
        for (i in 0 until 8 - str.length) {
            temp.append("0")
        }
        str = java.lang.StringBuilder((temp.toString() + str.toString()).trim { it <= ' ' })
    }
    return str.toString()
}


/**
 * 16进制字符串转字节数组
 * **/
@SuppressLint("DefaultLocale")
@Suppress("unused")
fun hexString2Bytes(hexString: String?): ByteArray? {
    var hex = hexString
    return if (hex == null || hex == "") {
        null
    } else if (hex.length % 2 != 0) {
        null
    } else {
        hex = hex.toUpperCase()
        val len = hex.length / 2
        val b = ByteArray(len)
        val hc = hex.toCharArray()
        for (i in 0 until len) {
            val p = 2 * i
            b[i] =
                    (charToByte(hc[p]).toInt().shl(4) or charToByte(hc[p + 1]).toInt()).toByte()
        }
        b
    }
}

/**
 * 字符转换为字节
 * */
private fun charToByte(c: Char): Byte {
    return "0123456789ABCDEF".indexOf(c).toByte()
}