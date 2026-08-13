package com.example.aimealplanners.domain.repository

import com.example.aimealplanners.domain.model.MealPlan
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface MealPlanRepository {
    fun getMealPlans(startDate: LocalDate, endDate: LocalDate): Flow<List<MealPlan>>
    suspend fun getMealPlanByDate(date: LocalDate): MealPlan?
    suspend fun saveMealPlan(mealPlan: MealPlan)
}
