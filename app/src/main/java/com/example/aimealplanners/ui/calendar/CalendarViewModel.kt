package com.example.aimealplanners.ui.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aimealplanners.domain.model.MealPlan
import com.example.aimealplanners.domain.usecase.GetMealPlansUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getMealPlansUseCase: GetMealPlansUseCase
) : ViewModel() {

    private val currentMonth = LocalDate.now().withDayOfMonth(1)
    private val startOfMonth = currentMonth
    private val endOfMonth = currentMonth.with(TemporalAdjusters.lastDayOfMonth())

    val monthMealPlans: StateFlow<List<MealPlan>> = getMealPlansUseCase(startOfMonth, endOfMonth)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
