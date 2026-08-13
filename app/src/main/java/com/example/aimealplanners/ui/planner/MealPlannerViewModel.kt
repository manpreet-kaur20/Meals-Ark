package com.example.aimealplanners.ui.planner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aimealplanners.domain.model.MealPlan
import com.example.aimealplanners.domain.usecase.GetMealPlansUseCase
import com.example.aimealplanners.domain.usecase.SaveMealPlanUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import java.time.DayOfWeek
import javax.inject.Inject

@HiltViewModel
class MealPlannerViewModel @Inject constructor(
    private val getMealPlansUseCase: GetMealPlansUseCase,
    private val saveMealPlanUseCase: SaveMealPlanUseCase
) : ViewModel() {

    private val today = LocalDate.now()
    private val startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    private val endOfWeek = startOfWeek.plusDays(6)

    val mealPlans: StateFlow<List<MealPlan>> = getMealPlansUseCase(startOfWeek, endOfWeek)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun updateMealPlan(date: LocalDate, breakfastId: Long? = null, lunchId: Long? = null, dinnerId: Long? = null) {
        viewModelScope.launch {
            val currentPlan = mealPlans.value.find { it.date == date } ?: MealPlan(date)
            val updatedPlan = currentPlan.copy(
                breakfastDishId = breakfastId ?: currentPlan.breakfastDishId,
                lunchDishId = lunchId ?: currentPlan.lunchDishId,
                dinnerDishId = dinnerId ?: currentPlan.dinnerDishId
            )
            saveMealPlanUseCase(updatedPlan)
        }
    }
}
