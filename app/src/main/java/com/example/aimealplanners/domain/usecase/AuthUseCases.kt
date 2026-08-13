package com.example.aimealplanners.domain.usecase

import com.example.aimealplanners.data.remote.dto.*
import com.example.aimealplanners.domain.repository.AppRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: AppRepository) {
    suspend operator fun invoke(request: LoginRequest) = repository.login(request)
}

class SignupUseCase @Inject constructor(private val repository: AppRepository) {
    suspend operator fun invoke(request: SignupRequest) = repository.signup(request)
}

class VerifyEmailUseCase @Inject constructor(private val repository: AppRepository) {
    suspend operator fun invoke(request: VerifyEmailRequest) = repository.verifyEmail(request)
}

class ResendOtpUseCase @Inject constructor(private val repository: AppRepository) {
    suspend operator fun invoke(email: String) = repository.resendOtp(email)
}

class LogoutUseCase @Inject constructor(private val repository: AppRepository) {
    suspend operator fun invoke() = repository.logout()
}

class DeleteAccountUseCase @Inject constructor(private val repository: AppRepository) {
    suspend operator fun invoke() = repository.deleteAccount()
}

class GenerateQrCodeUseCase @Inject constructor(private val repository: AppRepository) {
    suspend operator fun invoke() = repository.generateQrCode()
}
