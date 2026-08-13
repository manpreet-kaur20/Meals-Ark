package com.example.aimealplanners.domain.usecase

import com.example.aimealplanners.domain.model.MealPlan
import com.example.aimealplanners.domain.repository.AppRepository
import javax.inject.Inject

class GenerateAiMealUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(prompt: String): Result<MealPlan> {
        return repository.generateAiMeal(prompt)
    }
}
