package com.example.aimealplanners.domain.usecase

import com.example.aimealplanners.domain.model.MealPlan
import com.example.aimealplanners.domain.repository.MealPlanRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GetMealPlansUseCase @Inject constructor(
    private val repository: MealPlanRepository
) {
    operator fun invoke(startDate: LocalDate, endDate: LocalDate): Flow<List<MealPlan>> {
        return repository.getMealPlans(startDate, endDate)
    }
}
