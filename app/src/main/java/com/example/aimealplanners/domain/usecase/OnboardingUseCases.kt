package com.example.aimealplanners.domain.usecase

import com.example.aimealplanners.data.remote.dto.OnboardingRequest
import com.example.aimealplanners.domain.repository.AppRepository
import javax.inject.Inject

class SaveOnboardingDataUseCase @Inject constructor(private val repository: AppRepository) {
    suspend operator fun invoke(request: OnboardingRequest) = repository.saveOnboardingData(request)
}
