package com.sesi.quizly.model.response

import kotlinx.serialization.Serializable

@Serializable
sealed class BaseResponse<T> {
    abstract val status: String
    abstract val message: String?
}

// Data class for successful responses
@Serializable
data class SuccessResponse<T>(
    override val status: String, // Standard status for success
    override val message: String? = null, // Optional message
    val data: T // The actual data payload
) : BaseResponse<T>()

// Data class for error responses
@Serializable
data class ErrorResponse<T>(
    override val status: String, // Standard status for error
    override val message: String?, // Error message
    val data: T? = null
) : BaseResponse<T>() // Use Nothing as the data type for errors