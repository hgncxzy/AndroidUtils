package com.xzy.utils.json

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

@Suppress("unused")
object JsonReadFile {
    // xxx.json 文件路径为 res/raw/xxx.json
    // 调用方式 JsonReadFile.getFromRaw(context,R.raw.xxx)
    fun getFromRaw(context: Context, rawID: Int): String? {
        try {
            val inputReader =
                    InputStreamReader(context.resources.openRawResource(rawID))
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

