package com.example.aimealplanners.domain.usecase

import com.example.aimealplanners.data.remote.dto.PurchaseSubscriptionRequest
import com.example.aimealplanners.domain.repository.AppRepository
import javax.inject.Inject

class PurchaseSubscriptionUseCase @Inject constructor(private val repository: AppRepository) {
    suspend operator fun invoke(request: PurchaseSubscriptionRequest) = repository.purchaseSubscription(request)
}

class GetSubscriptionStatusUseCase @Inject constructor(private val repository: AppRepository) {
    suspend operator fun invoke() = repository.getSubscriptionStatus()
}
