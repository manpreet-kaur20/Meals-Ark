package com.example.aimealplanners.data.repository

import com.example.aimealplanners.data.remote.api.ApiService
import com.example.aimealplanners.data.remote.dto.*
import com.example.aimealplanners.domain.model.*
import com.example.aimealplanners.domain.repository.AppRepository
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AppRepository {

    // ── Auth ──────────────────────────────────────────────────

    override suspend fun login(request: LoginRequest): Result<AuthResponse> {
        return try {
            Result.success(apiService.login(request))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signup(request: SignupRequest): Result<AuthResponse> {
        return try {
            Result.success(apiService.signup(request))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun verifyEmail(request: VerifyEmailRequest): Result<BaseResponse> {
        return try {
            Result.success(apiService.verifyEmail(request))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun resendOtp(email: String): Result<BaseResponse> {
        return try {
            Result.success(apiService.resendOtp(email))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout(): Result<BaseResponse> {
        return try {
            Result.success(apiService.logout())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteAccount(): Result<BaseResponse> {
        return try {
            Result.success(apiService.deleteAccount())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun generateQrCode(): Result<QrCodeResponse> {
        return try {
            Result.success(apiService.generateQrCode())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun forgotPassword(email: String): Result<BaseResponse> {
        return try {
            Result.success(apiService.forgotPassword(ForgotPasswordRequest(email)))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun googleSignIn(idToken: String): Result<AuthResponse> {
        return try {
            Result.success(apiService.googleSignIn(GoogleSignInRequest(idToken)))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ── Profile ──────────────────────────────────────────────

    override suspend fun updateProfile(request: UpdateProfileRequest): Result<UserProfileResponse> {
        return try {
            Result.success(apiService.updateProfile(request))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun changePassword(request: ChangePasswordRequest): Result<BaseResponse> {
        return try {
            Result.success(apiService.changePassword(request))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ── Onboarding ───────────────────────────────────────────

    override suspend fun saveOnboardingData(request: OnboardingRequest): Result<BaseResponse> {
        return try {
            Result.success(apiService.saveOnboardingData(request))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ── Products & Meals ─────────────────────────────────────

    override suspend fun getProducts(): Result<List<Product>> {
        return try {
            val response = apiService.getProducts()
            Result.success(response.products.map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getDailyMealPlan(date: String): Result<MealPlan> {
        return try {
            val response = apiService.getDailyMealPlan(date)
            Result.success(response.mealPlan.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getWeeklyMealPlan(startDate: String): Result<List<MealPlan>> {
        return try {
            val response = apiService.getWeeklyMealPlan(startDate)
            Result.success(response.weeklyPlan.map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getDishes(): Result<List<Dish>> {
        return try {
            val response = apiService.getDishes()
            Result.success(response.dishes.map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getShoppingList(): Result<List<ShoppingItem>> {
        return try {
            val response = apiService.getShoppingList()
            Result.success(response.items.map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun generateAiMeal(prompt: String): Result<MealPlan> {
        return try {
            val response = apiService.generateAiMeal(AiMealRequest(prompt))
            Result.success(response.mealPlan.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ── Tracking ─────────────────────────────────────────────

    override suspend fun trackMeal(request: TrackMealRequest): Result<TrackMealResponse> {
        return try {
            Result.success(apiService.trackMeal(request))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getDailySummary(date: String): Result<DailySummaryResponse> {
        return try {
            Result.success(apiService.getDailySummary(date))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateWeight(weight: Double, unit: String, date: String): Result<BaseResponse> {
        return try {
            Result.success(apiService.updateWeight(UpdateWeightRequest(weight, unit, date)))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ── Saved Meals ──────────────────────────────────────────

    override suspend fun getSavedMeals(): Result<SavedMealsResponse> {
        return try {
            Result.success(apiService.getSavedMeals())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun saveMeal(request: SaveMealRequest): Result<BaseResponse> {
        return try {
            Result.success(apiService.saveMeal(request))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteSavedMeal(id: Long): Result<BaseResponse> {
        return try {
            Result.success(apiService.deleteSavedMeal(id))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ── Analytics ────────────────────────────────────────────

    override suspend fun getAnalyticsSummary(startDate: String, endDate: String): Result<AnalyticsSummaryResponse> {
        return try {
            Result.success(apiService.getAnalyticsSummary(startDate, endDate))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ── Streak ───────────────────────────────────────────────

    override suspend fun getUserStreak(): Result<StreakResponse> {
        return try {
            Result.success(apiService.getUserStreak())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ── Subscription ─────────────────────────────────────────

    override suspend fun purchaseSubscription(request: PurchaseSubscriptionRequest): Result<SubscriptionResponse> {
        return try {
            Result.success(apiService.purchaseSubscription(request))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getSubscriptionStatus(): Result<SubscriptionStatusResponse> {
        return try {
            Result.success(apiService.getSubscriptionStatus())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
