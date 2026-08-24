package com.app.aimeals.data.local

import javax.inject.Inject
import javax.inject.Singleton

data class OnboardingRegistrationPayload(
    var sex: String? = "Female",
    var age: Int = 25,
    var height: Double? = 165.5,
    var weight: Double? = 60.0,
    var activityLevelId: Int? = 2,
    var goals: List<Int> = listOf(1),
    var cuisines: List<Int> = listOf(1),
    var dietId: Int? = 1,
    var budgetId: Int? = 2,
    var dailyCalorieGoal: Int = 2100,
    var allergies: List<Int> = emptyList(),
    var marketingSourceId: Int? = 2,
    var specialRequests: String = ""
)

@Singleton
class OnboardingDataManager @Inject constructor() {
    var payload: OnboardingRegistrationPayload = OnboardingRegistrationPayload()

    fun updatePayload(newPayload: OnboardingRegistrationPayload) {
        this.payload = newPayload
    }
}
