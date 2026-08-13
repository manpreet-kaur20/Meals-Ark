package com.example.aimealplanners.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.aimealplanners.data.remote.dto.*
import com.example.aimealplanners.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class TrackingUiState {
    data object Idle : TrackingUiState()
    data object Loading : TrackingUiState()
    data class Success(val message: String = "") : TrackingUiState()
    data class Error(val message: String) : TrackingUiState()
}

@HiltViewModel
class TrackingViewModel @Inject constructor(
    private val trackMealUseCase: TrackMealUseCase,
    private val getDailySummaryUseCase: GetDailySummaryUseCase,
    private val updateWeightUseCase: UpdateWeightUseCase,
    private val getSavedMealsUseCase: GetSavedMealsUseCase,
    private val saveMealRemoteUseCase: SaveMealRemoteUseCase,
    private val deleteSavedMealUseCase: DeleteSavedMealUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow<TrackingUiState>(TrackingUiState.Idle)
    val uiState: StateFlow<TrackingUiState> = _uiState

    private val _dailySummary = MutableStateFlow<DailySummaryResponse?>(null)
    val dailySummary: StateFlow<DailySummaryResponse?> = _dailySummary

    private val _savedMeals = MutableStateFlow<List<SavedMealDto>>(emptyList())
    val savedMeals: StateFlow<List<SavedMealDto>> = _savedMeals

    fun trackMeal(
        mealType: String,
        foodName: String,
        calories: Int,
        protein: Double,
        carbs: Double,
        fat: Double,
        servingSize: String,
        date: String,
        imageUrl: String? = null,
        barcode: String? = null
    ) {
        viewModelScope.launch {
            _uiState.value = TrackingUiState.Loading
            val request = TrackMealRequest(
                mealType = mealType,
                foodName = foodName,
                calories = calories,
                protein = protein,
                carbs = carbs,
                fat = fat,
                servingSize = servingSize,
                date = date,
                imageUrl = imageUrl,
                barcode = barcode
            )
            trackMealUseCase(request)
                .onSuccess {
                    _uiState.value = TrackingUiState.Success("Meal tracked successfully")
                    // Refresh daily summary
                    loadDailySummary(date)
                }
                .onFailure {
                    _uiState.value = TrackingUiState.Error(handleError(it))
                }
        }
    }

    fun loadDailySummary(date: String) {
        viewModelScope.launch {
            getDailySummaryUseCase(date)
                .onSuccess { summary ->
                    _dailySummary.value = summary
                }
                .onFailure {
                    // Silently fail for summary refresh
                }
        }
    }

    fun updateWeight(weight: Double, unit: String = "kg", date: String) {
        viewModelScope.launch {
            _uiState.value = TrackingUiState.Loading
            updateWeightUseCase(weight, unit, date)
                .onSuccess {
                    _uiState.value = TrackingUiState.Success("Weight updated successfully")
                }
                .onFailure {
                    _uiState.value = TrackingUiState.Error(handleError(it))
                }
        }
    }

    fun loadSavedMeals() {
        viewModelScope.launch {
            _uiState.value = TrackingUiState.Loading
            getSavedMealsUseCase()
                .onSuccess { response ->
                    _savedMeals.value = response.savedMeals
                    _uiState.value = TrackingUiState.Success()
                }
                .onFailure {
                    _uiState.value = TrackingUiState.Error(handleError(it))
                }
        }
    }

    fun saveMeal(name: String, calories: Int, protein: Double, carbs: Double, fat: Double, imageUrl: String? = null) {
        viewModelScope.launch {
            _uiState.value = TrackingUiState.Loading
            saveMealRemoteUseCase(SaveMealRequest(name, calories, protein, carbs, fat, imageUrl))
                .onSuccess {
                    _uiState.value = TrackingUiState.Success("Meal saved")
                    loadSavedMeals()
                }
                .onFailure {
                    _uiState.value = TrackingUiState.Error(handleError(it))
                }
        }
    }

    fun deleteSavedMeal(id: Long) {
        viewModelScope.launch {
            _uiState.value = TrackingUiState.Loading
            deleteSavedMealUseCase(id)
                .onSuccess {
                    _uiState.value = TrackingUiState.Success("Meal removed")
                    loadSavedMeals()
                }
                .onFailure {
                    _uiState.value = TrackingUiState.Error(handleError(it))
                }
        }
    }

    fun resetState() {
        _uiState.value = TrackingUiState.Idle
    }
}
