package com.example.aimealplanners.data.remote.dto

import com.example.aimealplanners.domain.model.MealPlan
import java.time.LocalDate

data class MealPlanResponse(
    val mealPlan: MealPlanDto
)

data class WeeklyPlanResponse(
    val weeklyPlan: List<MealPlanDto>
)

data class MealPlanDto(
    val date: String,
    val breakfastDishId: Long?,
    val lunchDishId: Long?,
    val dinnerDishId: Long?
)

fun MealPlanDto.toDomain(): MealPlan {
    return MealPlan(
        date = LocalDate.parse(date),
        breakfastDishId = breakfastDishId,
        lunchDishId = lunchDishId,
        dinnerDishId = dinnerDishId
    )
}
