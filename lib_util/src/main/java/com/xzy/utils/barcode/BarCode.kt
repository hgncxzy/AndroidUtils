@file:Suppress("unused")

package com.xzy.utils.barcode

import android.graphics.Bitmap
import androidx.annotation.ColorInt
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.xzy.utils.Encoding
import com.xzy.utils.resource.appColor

@Suppress("unused")
fun createQRCode(
        content: String,
        width: Int,
        height: Int,
        charset: String = Encoding.UTF_8,
        errorCorrectionLevel: ErrorCorrectionLevel = ErrorCorrectionLevel.L,
        margin: Int = 4,
        @ColorInt colorBlack: Int = appColor(android.R.color.black),
        @ColorInt colorWhite: Int = appColor(android.R.color.white)
): Bitmap {
    val hints = mapOf(
            Pair(EncodeHintType.CHARACTER_SET, charset),
            Pair(EncodeHintType.ERROR_CORRECTION, errorCorrectionLevel),
            Pair(EncodeHintType.MARGIN, margin)
    )
    val matrix = MultiFormatWriter()
            .encode(
                    content,
                    BarcodeFormat.QR_CODE,
                    width,
                    height,
                    hints
            )
    val pixels = IntArray(width * height)
    for (y in 0 until height) {
        for (x in 0 until width) {
            if (matrix.get(x, y)) {
                pixels[y * width + x] = colorBlack
            } else {
                pixels[y * width + x] = colorWhite
            }
        }
    }

    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
    bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
    return bitmap
}
