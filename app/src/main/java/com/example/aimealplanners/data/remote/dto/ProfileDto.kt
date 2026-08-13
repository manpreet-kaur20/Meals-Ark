package com.example.aimealplanners.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdateProfileRequest(
    val name: String,
    val email: String? = null
)

@JsonClass(generateAdapter = true)
data class UserProfileResponse(
    val success: Boolean,
    val user: UserDto
)

@JsonClass(generateAdapter = true)
data class ChangePasswordRequest(
    val currentPassword: String,
    val newPassword: String
)

@JsonClass(generateAdapter = true)
data class ForgotPasswordRequest(
    val email: String
)

@JsonClass(generateAdapter = true)
data class GoogleSignInRequest(
    val idToken: String
)
