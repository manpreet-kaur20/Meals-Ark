package com.example.aimealplanners.domain.usecase

import com.example.aimealplanners.domain.model.MealPlan
import com.example.aimealplanners.domain.repository.MealPlanRepository
import java.time.LocalDate

class GetMealPlanByDateUseCase(
    private val repository: MealPlanRepository
) {
    suspend operator fun invoke(date: LocalDate): MealPlan? {
        return repository.getMealPlanByDate(date)
    }
}
