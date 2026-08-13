package com.example.aimealplanners.domain.model

import java.time.LocalDate

data class MealPlan(
    val date: LocalDate,
    val breakfastDishId: Long? = null,
    val lunchDishId: Long? = null,
    val dinnerDishId: Long? = null
)
