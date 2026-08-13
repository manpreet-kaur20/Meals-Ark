package com.example.aimealplanners.domain.usecase

import com.example.aimealplanners.domain.model.MealPlan
import com.example.aimealplanners.domain.repository.MealPlanRepository
import javax.inject.Inject

class SaveMealPlanUseCase @Inject constructor(
    private val repository: MealPlanRepository
) {
    suspend operator fun invoke(mealPlan: MealPlan) {
        repository.saveMealPlan(mealPlan)
    }
}
