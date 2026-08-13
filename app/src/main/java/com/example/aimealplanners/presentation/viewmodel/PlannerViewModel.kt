package com.example.aimealplanners.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.aimealplanners.domain.model.MealPlan
import com.example.aimealplanners.domain.usecase.GetWeeklyMealPlanUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

sealed class PlannerUiState {
    data object Loading : PlannerUiState()
    data class Success(val weeklyPlan: List<MealPlan>) : PlannerUiState()
    data class Error(val message: String) : PlannerUiState()
}

@HiltViewModel
class PlannerViewModel @Inject constructor(
    private val getWeeklyMealPlanUseCase: GetWeeklyMealPlanUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow<PlannerUiState>(PlannerUiState.Loading)
    val uiState: StateFlow<PlannerUiState> = _uiState

    fun getWeeklyPlan(startDate: LocalDate) {
        viewModelScope.launch {
            _uiState.value = PlannerUiState.Loading
            getWeeklyMealPlanUseCase(startDate.toString())
                .onSuccess {
                    _uiState.value = PlannerUiState.Success(it)
                }
                .onFailure {
                    _uiState.value = PlannerUiState.Error(handleError(it))
                }
        }
    }
}
