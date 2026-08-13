package com.example.aimealplanners.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TrackMealRequest(
    val mealType: String,
    val foodName: String,
    val calories: Int,
    val protein: Double,
    val carbs: Double,
    val fat: Double,
    val servingSize: String,
    val date: String,
    val imageUrl: String? = null,
    val barcode: String? = null
)

@JsonClass(generateAdapter = true)
data class TrackMealResponse(
    val success: Boolean,
    val message: String,
    val trackedMeal: TrackedMealDto? = null
)

@JsonClass(generateAdapter = true)
data class TrackedMealDto(
    val id: Long,
    val mealType: String,
    val foodName: String,
    val calories: Int,
    val protein: Double,
    val carbs: Double,
    val fat: Double,
    val date: String
)

@JsonClass(generateAdapter = true)
data class DailySummaryResponse(
    val date: String,
    val totalCalories: Int,
    val totalProtein: Double,
    val totalCarbs: Double,
    val totalFat: Double,
    val meals: List<TrackedMealDto>,
    val calorieGoal: Int,
    val waterIntake: Int
)

@JsonClass(generateAdapter = true)
data class UpdateWeightRequest(
    val weight: Double,
    val unit: String = "kg",
    val date: String
)

@JsonClass(generateAdapter = true)
data class SavedMealsResponse(
    val savedMeals: List<SavedMealDto>
)

@JsonClass(generateAdapter = true)
data class SavedMealDto(
    val id: Long,
    val name: String,
    val calories: Int,
    val protein: Double,
    val carbs: Double,
    val fat: Double,
    val imageUrl: String? = null,
    val savedAt: String
)

@JsonClass(generateAdapter = true)
data class SaveMealRequest(
    val name: String,
    val calories: Int,
    val protein: Double,
    val carbs: Double,
    val fat: Double,
    val imageUrl: String? = null
)

@JsonClass(generateAdapter = true)
data class AnalyticsSummaryResponse(
    val averageCalories: Int,
    val averageProtein: Double,
    val averageCarbs: Double,
    val averageFat: Double,
    val totalMealsLogged: Int,
    val streakDays: Int,
    val weightHistory: List<WeightEntryDto>,
    val calorieHistory: List<CalorieEntryDto>
)

@JsonClass(generateAdapter = true)
data class WeightEntryDto(
    val date: String,
    val weight: Double
)

@JsonClass(generateAdapter = true)
data class CalorieEntryDto(
    val date: String,
    val calories: Int
)

@JsonClass(generateAdapter = true)
data class StreakResponse(
    val currentStreak: Int,
    val bestStreak: Int,
    val freezesLeft: Int,
    val mealsLoggedToday: Int,
    val timeLeftHours: Int
)
