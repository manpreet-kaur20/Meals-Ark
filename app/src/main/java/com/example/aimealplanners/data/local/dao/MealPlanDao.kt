package com.example.aimealplanners.data.local.dao

import androidx.room.*
import com.example.aimealplanners.data.local.entity.MealPlanEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface MealPlanDao {
    @Query("SELECT * FROM meal_plans WHERE date BETWEEN :startDate AND :endDate")
    fun getMealPlans(startDate: LocalDate, endDate: LocalDate): Flow<List<MealPlanEntity>>

    @Query("SELECT * FROM meal_plans WHERE date = :date")
    suspend fun getMealPlanByDate(date: LocalDate): MealPlanEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMealPlan(mealPlan: MealPlanEntity)
}
