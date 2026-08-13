package com.example.aimealplanners.domain.usecase

import com.example.aimealplanners.domain.repository.AppRepository
import javax.inject.Inject

class GetAnalyticsSummaryUseCase @Inject constructor(private val repository: AppRepository) {
    suspend operator fun invoke(startDate: String, endDate: String) = repository.getAnalyticsSummary(startDate, endDate)
}

class GetUserStreakUseCase @Inject constructor(private val repository: AppRepository) {
    suspend operator fun invoke() = repository.getUserStreak()
}
