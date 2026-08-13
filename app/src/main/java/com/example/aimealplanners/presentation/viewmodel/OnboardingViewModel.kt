package com.example.aimealplanners.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.aimealplanners.data.remote.dto.OnboardingRequest
import com.example.aimealplanners.domain.usecase.SaveOnboardingDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class OnboardingUiState {
    data object Idle : OnboardingUiState()
    data object Loading : OnboardingUiState()
    data object Success : OnboardingUiState()
    data class Error(val message: String) : OnboardingUiState()
}

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val saveOnboardingDataUseCase: SaveOnboardingDataUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow<OnboardingUiState>(OnboardingUiState.Idle)
    val uiState: StateFlow<OnboardingUiState> = _uiState

    fun saveOnboardingData(
        goals: List<String>,
        activityLevel: String,
        sex: String?,
        age: Int,
        heightCm: Double?,
        weightKg: Double?,
        cuisines: List<String>,
        dietaryApproach: String,
        allergies: List<String>,
        budget: String,
        sameLunchDinner: Boolean,
        pantryFirst: Boolean,
        mealVariety: String,
        calorieGoal: Int,
        specialRequests: String,
        attribution: String
    ) {
        viewModelScope.launch {
            _uiState.value = OnboardingUiState.Loading
            val request = OnboardingRequest(
                goals = goals,
                activityLevel = activityLevel,
                sex = sex,
                age = age,
                heightCm = heightCm,
                weightKg = weightKg,
                cuisines = cuisines,
                dietaryApproach = dietaryApproach,
                allergies = allergies,
                budget = budget,
                sameLunchDinner = sameLunchDinner,
                pantryFirst = pantryFirst,
                mealVariety = mealVariety,
                calorieGoal = calorieGoal,
                specialRequests = specialRequests,
                attribution = attribution
            )
            saveOnboardingDataUseCase(request)
                .onSuccess {
                    _uiState.value = OnboardingUiState.Success
                }
                .onFailure {
                    _uiState.value = OnboardingUiState.Error(handleError(it))
                }
        }
    }

    fun resetState() {
        _uiState.value = OnboardingUiState.Idle
    }
}
