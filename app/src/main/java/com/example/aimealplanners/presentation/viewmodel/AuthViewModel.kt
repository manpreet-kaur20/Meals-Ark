package com.example.aimealplanners.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.aimealplanners.data.local.TokenManager
import com.example.aimealplanners.data.remote.dto.*
import com.example.aimealplanners.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AuthUiState {
    data object Idle : AuthUiState()
    data object Loading : AuthUiState()
    data class Success(val message: String = "") : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val signupUseCase: SignupUseCase,
    private val verifyEmailUseCase: VerifyEmailUseCase,
    private val resendOtpUseCase: ResendOtpUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val forgotPasswordUseCase: ForgotPasswordUseCase,
    private val googleSignInUseCase: GoogleSignInUseCase,
    private val tokenManager: TokenManager
) : BaseViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            loginUseCase(LoginRequest(email, password))
                .onSuccess { response ->
                    tokenManager.saveToken(response.token)
                    tokenManager.saveUserInfo(response.user.id, response.user.email, response.user.name)
                    _uiState.value = AuthUiState.Success("Login successful")
                }
                .onFailure {
                    _uiState.value = AuthUiState.Error(handleError(it))
                }
        }
    }

    fun signup(name: String, email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            signupUseCase(SignupRequest(email, password, name))
                .onSuccess { response ->
                    tokenManager.saveToken(response.token)
                    tokenManager.saveUserInfo(response.user.id, response.user.email, response.user.name)
                    _uiState.value = AuthUiState.Success("Account created successfully")
                }
                .onFailure {
                    _uiState.value = AuthUiState.Error(handleError(it))
                }
        }
    }

    fun verifyEmail(email: String, otp: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            verifyEmailUseCase(VerifyEmailRequest(email, otp))
                .onSuccess {
                    _uiState.value = AuthUiState.Success("Email verified successfully")
                }
                .onFailure {
                    _uiState.value = AuthUiState.Error(handleError(it))
                }
        }
    }

    fun resendOtp(email: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            resendOtpUseCase(email)
                .onSuccess {
                    _uiState.value = AuthUiState.Success("OTP sent to $email")
                }
                .onFailure {
                    _uiState.value = AuthUiState.Error(handleError(it))
                }
        }
    }

    fun logout() {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            logoutUseCase()
                .onSuccess {
                    tokenManager.clearAll()
                    _uiState.value = AuthUiState.Success("Logged out")
                }
                .onFailure {
                    // Still clear local data even if API fails
                    tokenManager.clearAll()
                    _uiState.value = AuthUiState.Success("Logged out")
                }
        }
    }

    fun deleteAccount() {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            deleteAccountUseCase()
                .onSuccess {
                    tokenManager.clearAll()
                    _uiState.value = AuthUiState.Success("Account deleted")
                }
                .onFailure {
                    _uiState.value = AuthUiState.Error(handleError(it))
                }
        }
    }

    fun forgotPassword(email: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            forgotPasswordUseCase(email)
                .onSuccess {
                    _uiState.value = AuthUiState.Success("Password reset link sent to $email")
                }
                .onFailure {
                    _uiState.value = AuthUiState.Error(handleError(it))
                }
        }
    }

    fun googleSignIn(idToken: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            googleSignInUseCase(idToken)
                .onSuccess { response ->
                    tokenManager.saveToken(response.token)
                    tokenManager.saveUserInfo(response.user.id, response.user.email, response.user.name)
                    _uiState.value = AuthUiState.Success("Google sign-in successful")
                }
                .onFailure {
                    _uiState.value = AuthUiState.Error(handleError(it))
                }
        }
    }

    fun resetState() {
        _uiState.value = AuthUiState.Idle
    }
}
