@file:Suppress("unused")

package com.xzy.utils.os

import android.os.Build
import android.os.Environment
import android.os.Process
import android.os.StatFs
import androidx.core.content.pm.PackageInfoCompat
import com.xzy.utils.Const
import com.xzy.utils.appctx.appCtx
import com.xzy.utils.file.isExist
import java.io.File
import java.text.DecimalFormat
import java.util.Locale
import java.util.UUID

/** The manufacturer of the product/hardware. */
val manufacturer: String get() = Build.MANUFACTURER

/** The end-user-visible name for the end product. */
val model: String get() = Build.MODEL

/** The name of the overall product. */
val product: String get() = Build.PRODUCT

/** The consumer-visible brand with which the product/hardware will be associated, if any. */
val brand: String get() = Build.BRAND

/** The SDK version of the software currently running on this hardware device. */
val osVerCode: Int get() = Build.VERSION.SDK_INT

/** The user-visible version string.  E.g., "1.0" or "3.4b5". */
val osVerName: String get() = Build.VERSION.RELEASE

/** A build ID string meant for displaying to the user. */
val osVerDisplayName: String get() = Build.DISPLAY

/** Application version code. */
fun appVerCode(pkgName: String? = null): Int {
    val info = appCtx.packageManager.getPackageInfo(pkgName ?: appCtx.packageName, 0)
    return PackageInfoCompat.getLongVersionCode(info).toInt()
}

/** Application version name. */
fun appVerName(pkgName: String? = null): String {
    val info = appCtx.packageManager.getPackageInfo(pkgName ?: appCtx.packageName, 0)
    return info.versionName ?: ""
}

/** System language. */
val language: String get() = Locale.getDefault().language

/** System language and country info. */
val languageAndCountry: String
    get() {
        val locale = Locale.getDefault()
        return "${locale.language}-${locale.country}"
    }

/** Process name. */
val processName: String
    get() = File("/proc/${Process.myPid()}/cmdline").readLines().first().trim { it <= ' ' }

/** Memory total size, unit is MB. */
val memTotalSize: Int get() = readMemSize(MemInfo.MEM_TOTAL)

/** Memory available size, unit is MB. */
val memAvailableSize: Int
    get() {
        return readMemSize(MemInfo.MEM_FREE) + readMemSize(MemInfo.BUFFERS) +
                readMemSize(MemInfo.CACHED)
    }

/** Available memory percent, its format seems like '00.00'. */
val memAvailablePercent: String
    get() {
        val percent = (memAvailableSize.toDouble() / memTotalSize.toDouble()) * 100
        return DecimalFormat("00.00").format(percent)
    }

/** Globally unique identifier. */
val guid: String by lazy {
    val file = File(appCtx.filesDir, "INSTALLATION")
    if (!file.isExist()) {
        file.writeText(UUID.randomUUID().toString())
    }
    file.readText()
}

/** Check external storage writable or not. */
@Suppress("unused")
val externalStorageWritable: Boolean
    get() = Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()

/** Check external storage readable or not. */
@Suppress("unused")
val externalStorageReadable: Boolean
    get() = Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() ||
            Environment.MEDIA_MOUNTED_READ_ONLY == Environment.getExternalStorageState()

/** SD card total size, unit is MB. */
val sdCardTotalSize: Int
    get() {
        val file = File(Const.SD_CARD_PATH)
        if (file.isExist()) {
            return 0
        }
        val stat = StatFs(Const.SD_CARD_PATH)
        return (stat.blockSizeLong * stat.blockCountLong / (1024 * 1024)).toInt()
    }

/** SD card available size, unit is MB. */
val sdCardAvailableSize: Int
    get() {
        val file = File(Const.SD_CARD_PATH)
        if (file.isExist()) {
            return 0
        }
        val stat = StatFs(Const.SD_CARD_PATH)
        return (stat.blockSizeLong * stat.availableBlocksLong / (1024 * 1024)).toInt()
    }

/** Available SD card percent, its format seems like '00.00'. */
@Suppress("unused")
val sdCardAvailablePercent: String
    get() {
        val file = File(Const.SD_CARD_PATH)
        if (file.isExist()) {
            return "0"
        }
        val percent = (sdCardAvailableSize.toDouble() / sdCardTotalSize.toDouble()) * 100
        return DecimalFormat("00.00").format(percent)
    }

private fun readMemSize(memInfo: MemInfo): Int {
    File("/proc/meminfo").readLines().forEach {
        if (it.contains(memInfo.value)) {
            // line = "MemTotal:        1535124 kB"
            // list = ["MemTotal", "        1535124 kB"]
            val list = it.split(":".toRegex(), 2)
            // list[1] = [        1535124 kB]
            // subList = ["1535124", "KB"]
            val subList = list[1].trim().split(" ".toRegex())
            // subList[0] = "1535124"
            return subList[0].toInt() / 1024
        }
    }
    return 0
}

enum class MemInfo(val value: String) {
    MEM_TOTAL("MemTotal"),
    MEM_FREE("MemFree"),
    BUFFERS("Buffers"),
    CACHED("Cached")
}
