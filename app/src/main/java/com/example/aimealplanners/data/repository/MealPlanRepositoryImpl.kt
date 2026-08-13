package com.example.aimealplanners.data.repository

import com.example.aimealplanners.data.local.dao.MealPlanDao
import com.example.aimealplanners.data.mapper.toDomain
import com.example.aimealplanners.data.mapper.toEntity
import com.example.aimealplanners.domain.model.MealPlan
import com.example.aimealplanners.domain.repository.MealPlanRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class MealPlanRepositoryImpl @Inject constructor(
    private val mealPlanDao: MealPlanDao
) : MealPlanRepository {
    override fun getMealPlans(startDate: LocalDate, endDate: LocalDate): Flow<List<MealPlan>> {
        return mealPlanDao.getMealPlans(startDate, endDate).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getMealPlanByDate(date: LocalDate): MealPlan? {
        return mealPlanDao.getMealPlanByDate(date)?.toDomain()
    }

    override suspend fun saveMealPlan(mealPlan: MealPlan) {
        mealPlanDao.insertMealPlan(mealPlan.toEntity())
    }
}
