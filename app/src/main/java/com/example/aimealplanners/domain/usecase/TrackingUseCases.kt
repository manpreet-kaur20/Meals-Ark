package com.example.aimealplanners.domain.usecase

import com.example.aimealplanners.data.remote.dto.*
import com.example.aimealplanners.domain.repository.AppRepository
import javax.inject.Inject

class TrackMealUseCase @Inject constructor(private val repository: AppRepository) {
    suspend operator fun invoke(request: TrackMealRequest) = repository.trackMeal(request)
}

class GetDailySummaryUseCase @Inject constructor(private val repository: AppRepository) {
    suspend operator fun invoke(date: String) = repository.getDailySummary(date)
}

class UpdateWeightUseCase @Inject constructor(private val repository: AppRepository) {
    suspend operator fun invoke(weight: Double, unit: String, date: String) = repository.updateWeight(weight, unit, date)
}

class GetSavedMealsUseCase @Inject constructor(private val repository: AppRepository) {
    suspend operator fun invoke() = repository.getSavedMeals()
}

class SaveMealRemoteUseCase @Inject constructor(private val repository: AppRepository) {
    suspend operator fun invoke(request: SaveMealRequest) = repository.saveMeal(request)
}

class DeleteSavedMealUseCase @Inject constructor(private val repository: AppRepository) {
    suspend operator fun invoke(id: Long) = repository.deleteSavedMeal(id)
}
