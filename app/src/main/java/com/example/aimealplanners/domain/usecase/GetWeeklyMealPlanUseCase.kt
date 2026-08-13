package com.example.aimealplanners.domain.usecase

import com.example.aimealplanners.domain.model.MealPlan
import com.example.aimealplanners.domain.repository.AppRepository
import javax.inject.Inject

class GetWeeklyMealPlanUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(startDate: String): Result<List<MealPlan>> {
        return repository.getWeeklyMealPlan(startDate)
    }
}
