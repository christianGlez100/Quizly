package shared

expect class Base64Converter() {
    /**
     * Encodes image data (platform-specific) into a Base64 string.
     * @param imageData The platform-specific image data.
     * @return The Base64 encoded string, or null if encoding fails.
     */
    fun encodeImageToBase64(imageData: Any): String?

    /**
     * Decodes a Base64 string into platform-specific image data.
     * @param base64String The Base64 encoded string.
     * @return The platform-specific image data, or null if decoding fails.
     */
    fun decodeBase64ToImage(base64String: String): Any?
}