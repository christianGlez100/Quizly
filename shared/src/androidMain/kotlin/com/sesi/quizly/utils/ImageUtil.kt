package com.sesi.quizly.utils

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.util.Log


fun getRotationMatrixByExif(contentResolver: ContentResolver, imageUri: Uri): Matrix {
    val matrix = Matrix()
    try {
        contentResolver.openInputStream(imageUri).use { imageStream ->
            val orientation =
                ExifInterface(imageStream!!).getAttributeInt(ExifInterface.TAG_ORIENTATION, 0)
            when (orientation) {
                ExifInterface.ORIENTATION_FLIP_HORIZONTAL,
                ExifInterface.ORIENTATION_TRANSPOSE,
                ExifInterface.ORIENTATION_TRANSVERSE -> matrix.postScale(-1f, 1f)

                ExifInterface.ORIENTATION_FLIP_VERTICAL -> matrix.postScale(1f, -1f)
            }
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_270,
                ExifInterface.ORIENTATION_TRANSPOSE -> matrix.postRotate(270f)

                ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
                ExifInterface.ORIENTATION_ROTATE_90,
                ExifInterface.ORIENTATION_TRANSVERSE -> matrix.postRotate(90f)

                else -> {
                    matrix.postRotate(270f)
                }
            }
        }
    } catch (throwable: Throwable) {
        Log.e("Exif--", "Can't get image EXIF", throwable)
    }
    return matrix
}

fun rotateImageBitmap(imageBitmap: Bitmap, matrix: Matrix): Bitmap {
    val rotatedBitmap = Bitmap.createBitmap(
        imageBitmap, 0, 0, imageBitmap.width, imageBitmap.height, matrix, true
    )
    return rotatedBitmap
}
