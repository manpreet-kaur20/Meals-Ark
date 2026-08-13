package com.example.aimealplanners.data.remote.api

import com.example.aimealplanners.data.remote.dto.*
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    // ── Auth ──────────────────────────────────────────────────
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @POST("auth/signup")
    suspend fun signup(@Body request: SignupRequest): AuthResponse

    @POST("auth/verify-email")
    suspend fun verifyEmail(@Body request: VerifyEmailRequest): BaseResponse

    @POST("auth/resend-otp")
    suspend fun resendOtp(@Query("email") email: String): BaseResponse

    @POST("auth/logout")
    suspend fun logout(): BaseResponse

    @DELETE("user/delete")
    suspend fun deleteAccount(): BaseResponse

    @GET("auth/qr-code")
    suspend fun generateQrCode(): QrCodeResponse

    @POST("auth/forgot-password")
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest): BaseResponse

    @POST("auth/google")
    suspend fun googleSignIn(@Body request: GoogleSignInRequest): AuthResponse

    // ── Profile ──────────────────────────────────────────────
    @PUT("user/profile")
    suspend fun updateProfile(@Body request: UpdateProfileRequest): UserProfileResponse

    @PUT("user/password")
    suspend fun changePassword(@Body request: ChangePasswordRequest): BaseResponse

    // ── Onboarding ───────────────────────────────────────────
    @POST("user/onboarding")
    suspend fun saveOnboardingData(@Body request: OnboardingRequest): BaseResponse

    // ── Products & Meals ─────────────────────────────────────
    @GET("products")
    suspend fun getProducts(): ProductResponse

    @GET("meal-plan/daily")
    suspend fun getDailyMealPlan(@Query("date") date: String): MealPlanResponse

    @GET("meal-plan/weekly")
    suspend fun getWeeklyMealPlan(@Query("startDate") startDate: String): WeeklyPlanResponse

    @GET("dishes")
    suspend fun getDishes(): DishResponse

    @GET("shopping-list")
    suspend fun getShoppingList(): ShoppingResponse

    @POST("ai/generate-meal")
    suspend fun generateAiMeal(@Body request: AiMealRequest): MealPlanResponse

    // ── Tracking ─────────────────────────────────────────────
    @POST("tracking/meal")
    suspend fun trackMeal(@Body request: TrackMealRequest): TrackMealResponse

    @GET("tracking/daily-summary")
    suspend fun getDailySummary(@Query("date") date: String): DailySummaryResponse

    @PUT("user/weight")
    suspend fun updateWeight(@Body request: UpdateWeightRequest): BaseResponse

    // ── Saved Meals ──────────────────────────────────────────
    @GET("meals/saved")
    suspend fun getSavedMeals(): SavedMealsResponse

    @POST("meals/save")
    suspend fun saveMeal(@Body request: SaveMealRequest): BaseResponse

    @DELETE("meals/saved/{id}")
    suspend fun deleteSavedMeal(@Path("id") id: Long): BaseResponse

    // ── Analytics ────────────────────────────────────────────
    @GET("analytics/summary")
    suspend fun getAnalyticsSummary(
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): AnalyticsSummaryResponse

    // ── Streak ───────────────────────────────────────────────
    @GET("user/streak")
    suspend fun getUserStreak(): StreakResponse

    // ── Subscription ─────────────────────────────────────────
    @POST("subscription/purchase")
    suspend fun purchaseSubscription(@Body request: PurchaseSubscriptionRequest): SubscriptionResponse

    @GET("subscription/status")
    suspend fun getSubscriptionStatus(): SubscriptionStatusResponse
}
