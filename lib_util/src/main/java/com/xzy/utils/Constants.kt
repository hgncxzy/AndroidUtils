package com.xzy.utils

import java.nio.charset.Charset

@Suppress("unused")
object Const {
    val DIGITS_LOWER =
            charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')

    val DIGITS_UPPER =
            charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')

    const val ZIP_EXT = ".zip"

    const val LINE_SEPARATOR = "\n"

    const val SD_CARD_PATH = "/mnt/media_rw/extsd"
}

@Suppress("unused", "MemberVisibilityCanBePrivate")
object Encoding {
    const val ISO_8859_1 = "ISO-8859-1"
    const val US_ASCII = "US-ASCII"
    const val UTF_16 = "UTF-16"
    const val UTF_16BE = "UTF-16BE"
    const val UTF_16LE = "UTF-16LE"
    const val UTF_8 = "UTF-8"
    const val GB2312 = "GB2312"

    val CHARSET_ISO_8859_1: Charset = Charset.forName(ISO_8859_1)
    val CHARSET_US_ASCII: Charset = Charset.forName(US_ASCII)
    val CHARSET_UTF_16: Charset = Charset.forName(UTF_16)
    val CHARSET_UTF_16BE: Charset = Charset.forName(UTF_16BE)
    val CHARSET_UTF_16LE: Charset = Charset.forName(UTF_16LE)
    val CHARSET_UTF_8: Charset = Charset.forName(UTF_8)
    val CHARSET_GB2312: Charset = Charset.forName(GB2312)
}
