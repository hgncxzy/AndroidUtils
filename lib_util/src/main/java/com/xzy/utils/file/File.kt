@file:Suppress("unused")

package com.xzy.utils.file


import com.xzy.utils.Const.ZIP_EXT
import com.xzy.utils.Encoding.CHARSET_GB2312
import com.xzy.utils.Encoding.CHARSET_ISO_8859_1
import java.io.File
import java.io.File.separator
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream

/** Check if a [File] exists. */
fun File?.isExist() = this?.exists() ?: false

/**
 * Creates a new, empty file named by this abstract pathname if
 * and only if a file with this name does not yet exist.
 *
 * @return `true` if file exist, `false` if file does not exist
 */
fun File.create(): Boolean {
    if (exists()) return true
    val dir: File? = parentFile

    if (dir != null) {
        if (!dir.exists()) {
            dir.mkdirs()
        }
    }

    createNewFile()
    return exists()
}

/** Calculate the size of file. */
fun File.calSize(): Long {
    var size = 0L
    if (isDirectory) {
        listFiles()?.iterator()?.forEach {
            size += if (it.isDirectory) {
                it.calSize()
            } else {
                it.length()
            }
        }
    } else if (isFile) {
        size += length()
    }
    return size
}

/**
 * Compress file or directory, if compressed file path([compressedFilePath]) is not specified,
 * it uses origin file path suffix with ‘.zip’ as compressed file path, if origin file needs to be
 * cleaned after compressing, you can make parameter [clean] to be `true`.
 */
fun File.zip(compressedFilePath: String? = null, clean: Boolean = false): File {
    val filePath = compressedFilePath ?: absolutePath + ZIP_EXT
    File(filePath).parentFile?.mkdirs()
    val zos = ZipOutputStream(FileOutputStream(filePath))

    fun action(file: File, path: String?) {
        val entryName = if (!path.isNullOrBlank()) {
            path + separator + file.name
        } else {
            file.name
        }
        if (file.isDirectory) {
            file.listFiles()?.forEach { action(it, entryName) }
        } else {
            zos.putNextEntry(ZipEntry(entryName))
            file.forEachBlock { buffer, bytesRead ->
                zos.write(buffer, 0, bytesRead)
            }
            zos.flush()
            zos.closeEntry()
        }
    }

    zos.use {
        action(this@zip, null)
        if (clean) {
            this@zip.deleteRecursively()
        }
    }
    return File(filePath)
}

/**
 * Uncompress a zip file to specified folder, if [folderPath] is not specified, it use origin zip
 * file's parent folder as target folder, if origin zip file needs to be cleaned after
 * uncompressing, you can make parameter [clean] to be `true`.
 */
fun File.unzip(folderPath: String? = null, clean: Boolean = false): File {
    val path = folderPath ?: parent ?: ""
    File(path).mkdirs()

    fun getRealFile(baseDir: String, absFileName: String): File {
        val dirs = absFileName.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        var file = File(baseDir)
        if (dirs.size > 1) {
            dirs.dropLast(1).forEach {
                val sub = String(it.toByteArray(CHARSET_ISO_8859_1), CHARSET_GB2312)
                file = File(file, sub)
            }
            if (!file.exists()) {
                file.mkdirs()
            }
            val sub =
                    String(dirs.last().toByteArray(CHARSET_ISO_8859_1), CHARSET_GB2312)
            file = File(file, sub)
        } else {
            file = File(baseDir, absFileName)
        }
        return file
    }

    fun action(file: File, path: String) {
        val zipFile = ZipFile(file)
        zipFile.use { zip ->
            zip.entries().iterator().forEach {
                if (it.isDirectory) {
                    val dir = String(
                            (path + it.name).toByteArray(CHARSET_ISO_8859_1),
                            CHARSET_GB2312
                    )
                    File(dir).mkdir()
                } else {
                    zipFile.getInputStream(it).copyTo(getRealFile(path, it.name).outputStream())
                }
            }
        }
    }

    action(this, path)
    if (clean) {
        delete()
    }
    return File(path, nameWithoutExtension)
}
