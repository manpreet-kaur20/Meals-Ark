package com.example.aimealplanners.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.aimealplanners.data.remote.dto.*
import com.example.aimealplanners.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ProfileUiState {
    data object Idle : ProfileUiState()
    data object Loading : ProfileUiState()
    data class Success(val message: String) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val generateQrCodeUseCase: GenerateQrCodeUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Idle)
    val uiState: StateFlow<ProfileUiState> = _uiState

    private val _qrCodeUrl = MutableStateFlow<String?>(null)
    val qrCodeUrl: StateFlow<String?> = _qrCodeUrl

    fun updateProfile(name: String) {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            updateProfileUseCase(UpdateProfileRequest(name = name))
                .onSuccess {
                    _uiState.value = ProfileUiState.Success("Profile updated successfully")
                }
                .onFailure {
                    _uiState.value = ProfileUiState.Error(handleError(it))
                }
        }
    }

    fun changePassword(currentPassword: String, newPassword: String) {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            changePasswordUseCase(ChangePasswordRequest(currentPassword, newPassword))
                .onSuccess {
                    _uiState.value = ProfileUiState.Success("Password updated successfully")
                }
                .onFailure {
                    _uiState.value = ProfileUiState.Error(handleError(it))
                }
        }
    }

    fun generateQrCode() {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            generateQrCodeUseCase()
                .onSuccess {
                    _qrCodeUrl.value = it.qrCodeUrl
                    _uiState.value = ProfileUiState.Success("QR code generated")
                }
                .onFailure {
                    _uiState.value = ProfileUiState.Error(handleError(it))
                }
        }
    }

    fun resetState() {
        _uiState.value = ProfileUiState.Idle
    }
}
