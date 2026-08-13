package com.example.aimealplanners.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.aimealplanners.data.remote.dto.AnalyticsSummaryResponse
import com.example.aimealplanners.data.remote.dto.StreakResponse
import com.example.aimealplanners.domain.usecase.GetAnalyticsSummaryUseCase
import com.example.aimealplanners.domain.usecase.GetUserStreakUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AnalyticsUiState {
    data object Idle : AnalyticsUiState()
    data object Loading : AnalyticsUiState()
    data object Success : AnalyticsUiState()
    data class Error(val message: String) : AnalyticsUiState()
}

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val getAnalyticsSummaryUseCase: GetAnalyticsSummaryUseCase,
    private val getUserStreakUseCase: GetUserStreakUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow<AnalyticsUiState>(AnalyticsUiState.Idle)
    val uiState: StateFlow<AnalyticsUiState> = _uiState

    private val _analyticsSummary = MutableStateFlow<AnalyticsSummaryResponse?>(null)
    val analyticsSummary: StateFlow<AnalyticsSummaryResponse?> = _analyticsSummary

    private val _streak = MutableStateFlow<StreakResponse?>(null)
    val streak: StateFlow<StreakResponse?> = _streak

    fun loadAnalytics(startDate: String, endDate: String) {
        viewModelScope.launch {
            _uiState.value = AnalyticsUiState.Loading
            getAnalyticsSummaryUseCase(startDate, endDate)
                .onSuccess { summary ->
                    _analyticsSummary.value = summary
                    _uiState.value = AnalyticsUiState.Success
                }
                .onFailure {
                    _uiState.value = AnalyticsUiState.Error(handleError(it))
                }
        }
    }

    fun loadStreak() {
        viewModelScope.launch {
            getUserStreakUseCase()
                .onSuccess { streakData ->
                    _streak.value = streakData
                }
                .onFailure {
                    // Silently handle streak load failure
                }
        }
    }
}
