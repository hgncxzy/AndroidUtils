package com.xzy.utils.asserts

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

@Suppress("unused")
object AssetReadFile {

    // 注意 assets 文件夹和 res 同级，传入上下文和 asset 资源文件名
    fun getFromAssets(context: Context, fileName: String): String? {
        try {
            val inputReader =
                    InputStreamReader(context.resources.assets.open(fileName))
            val bufReader = BufferedReader(inputReader)
            var line: String?
            var result: String? = ""
            while (bufReader.readLine().also { line = it } != null) result += line
            return result
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}

