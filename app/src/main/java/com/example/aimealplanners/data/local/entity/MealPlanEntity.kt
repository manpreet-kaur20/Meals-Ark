package com.example.aimealplanners.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "meal_plans")
data class MealPlanEntity(
    @PrimaryKey
    val date: LocalDate,
    val breakfastDishId: Long?,
    val lunchDishId: Long?,
    val dinnerDishId: Long?
)
