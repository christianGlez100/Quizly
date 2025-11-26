package shared

import platform.Foundation.NSData
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.base64Encoding
import platform.Foundation.create
import platform.Foundation.dataUsingEncoding
import platform.UIKit.UIImage
import platform.UIKit.UIImagePNGRepresentation

actual class Base64Converter {

    actual fun encodeImageToBase64(imageData: Any): String? {
        return try {
            val uiImage = imageData as UIImage // Cast Any to UIImage
            // Convert UIImage to NSData (e.g., PNG representation)
            val imageData: NSData? = UIImagePNGRepresentation(uiImage)
            // You could use UIImageJPEGRepresentation for JPEG

            imageData?.base64Encoding() // Encode NSData to Base64 String
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    actual fun decodeBase64ToImage(base64String: String): Any? {
        return try {
            // Decode Base64 string to NSData
            // 1. Create an NSString from the base64String
            val nsString = NSString.create(string = base64String)

            // 2. Call dataUsingEncoding on the NSString instance
            //    Cast NSUTF8StringEncoding to UInt
            val imageData: NSData? = nsString.dataUsingEncoding(encoding = NSUTF8StringEncoding)

            // Decode NSData into UIImage
            if (imageData != null) {
                UIImage(data = imageData)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}