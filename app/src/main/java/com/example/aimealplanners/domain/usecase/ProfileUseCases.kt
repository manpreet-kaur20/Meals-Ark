package com.example.aimealplanners.domain.usecase

import com.example.aimealplanners.data.remote.dto.*
import com.example.aimealplanners.domain.repository.AppRepository
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(private val repository: AppRepository) {
    suspend operator fun invoke(request: UpdateProfileRequest) = repository.updateProfile(request)
}

class ChangePasswordUseCase @Inject constructor(private val repository: AppRepository) {
    suspend operator fun invoke(request: ChangePasswordRequest) = repository.changePassword(request)
}

class ForgotPasswordUseCase @Inject constructor(private val repository: AppRepository) {
    suspend operator fun invoke(email: String) = repository.forgotPassword(email)
}

class GoogleSignInUseCase @Inject constructor(private val repository: AppRepository) {
    suspend operator fun invoke(idToken: String) = repository.googleSignIn(idToken)
}
