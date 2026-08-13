package com.example.aimealplanners.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OnboardingRequest(
    val goals: List<String>,
    val activityLevel: String,
    val sex: String?,
    val age: Int,
    val heightCm: Double?,
    val weightKg: Double?,
    val cuisines: List<String>,
    val dietaryApproach: String,
    val allergies: List<String>,
    val budget: String,
    val sameLunchDinner: Boolean,
    val pantryFirst: Boolean,
    val mealVariety: String,
    val calorieGoal: Int,
    val specialRequests: String,
    val attribution: String
)
