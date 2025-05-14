package shared

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.ui.graphics.asImageBitmap
import java.io.ByteArrayOutputStream

actual class Base64Converter {
    actual fun encodeImageToBase64(imageData: Any): String? {
        return try {
            val bitmap = imageData as Bitmap // Cast Any to Bitmap
            ByteArrayOutputStream().use { outputStream ->
                // Compress the bitmap to a ByteArray
                // You can adjust the format (PNG/JPEG) and quality
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                val byteArray = outputStream.toByteArray()
                // Encode the ByteArray to Base64
                Base64.encodeToString(byteArray, Base64.DEFAULT)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    actual fun decodeBase64ToImage(base64String: String): Any? {
        return try {
            // Decode the Base64 string to a ByteArray
            val byteArray = Base64.decode(base64String, Base64.DEFAULT)
            // Decode the ByteArray into a Bitmap
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size).asImageBitmap()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}