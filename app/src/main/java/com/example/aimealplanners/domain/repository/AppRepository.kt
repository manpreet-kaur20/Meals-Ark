package com.example.aimealplanners.domain.repository

import com.example.aimealplanners.data.remote.dto.*
import com.example.aimealplanners.domain.model.*

interface AppRepository {
    // ── Auth ──────────────────────────────────────────────────
    suspend fun login(request: LoginRequest): Result<AuthResponse>
    suspend fun signup(request: SignupRequest): Result<AuthResponse>
    suspend fun verifyEmail(request: VerifyEmailRequest): Result<BaseResponse>
    suspend fun resendOtp(email: String): Result<BaseResponse>
    suspend fun logout(): Result<BaseResponse>
    suspend fun deleteAccount(): Result<BaseResponse>
    suspend fun generateQrCode(): Result<QrCodeResponse>
    suspend fun forgotPassword(email: String): Result<BaseResponse>
    suspend fun googleSignIn(idToken: String): Result<AuthResponse>

    // ── Profile ──────────────────────────────────────────────
    suspend fun updateProfile(request: UpdateProfileRequest): Result<UserProfileResponse>
    suspend fun changePassword(request: ChangePasswordRequest): Result<BaseResponse>

    // ── Onboarding ───────────────────────────────────────────
    suspend fun saveOnboardingData(request: OnboardingRequest): Result<BaseResponse>

    // ── Products & Meals ─────────────────────────────────────
    suspend fun getProducts(): Result<List<Product>>
    suspend fun getDailyMealPlan(date: String): Result<MealPlan>
    suspend fun getWeeklyMealPlan(startDate: String): Result<List<MealPlan>>
    suspend fun getDishes(): Result<List<Dish>>
    suspend fun getShoppingList(): Result<List<ShoppingItem>>
    suspend fun generateAiMeal(prompt: String): Result<MealPlan>

    // ── Tracking ─────────────────────────────────────────────
    suspend fun trackMeal(request: TrackMealRequest): Result<TrackMealResponse>
    suspend fun getDailySummary(date: String): Result<DailySummaryResponse>
    suspend fun updateWeight(weight: Double, unit: String, date: String): Result<BaseResponse>

    // ── Saved Meals ──────────────────────────────────────────
    suspend fun getSavedMeals(): Result<SavedMealsResponse>
    suspend fun saveMeal(request: SaveMealRequest): Result<BaseResponse>
    suspend fun deleteSavedMeal(id: Long): Result<BaseResponse>

    // ── Analytics ────────────────────────────────────────────
    suspend fun getAnalyticsSummary(startDate: String, endDate: String): Result<AnalyticsSummaryResponse>

    // ── Streak ───────────────────────────────────────────────
    suspend fun getUserStreak(): Result<StreakResponse>

    // ── Subscription ─────────────────────────────────────────
    suspend fun purchaseSubscription(request: PurchaseSubscriptionRequest): Result<SubscriptionResponse>
    suspend fun getSubscriptionStatus(): Result<SubscriptionStatusResponse>
}

