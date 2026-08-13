package com.example.aimealplanners.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequest(
    val email: String,
    val password: String
)

@JsonClass(generateAdapter = true)
data class SignupRequest(
    val email: String,
    val password: String,
    val name: String
)

@JsonClass(generateAdapter = true)
data class AuthResponse(
    val token: String,
    val user: UserDto
)

@JsonClass(generateAdapter = true)
data class UserDto(
    val id: String,
    val email: String,
    val name: String,
    val isVerified: Boolean
)

@JsonClass(generateAdapter = true)
data class VerifyEmailRequest(
    val email: String,
    val otp: String
)

@JsonClass(generateAdapter = true)
data class QrCodeResponse(
    val qrCodeUrl: String,
    val data: String
)

@JsonClass(generateAdapter = true)
data class BaseResponse(
    val success: Boolean,
    val message: String
)

@JsonClass(generateAdapter = true)
data class AiMealRequest(
    val prompt: String
)
