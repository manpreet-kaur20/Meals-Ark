package com.example.aimealplanners.domain.usecase

import com.example.aimealplanners.domain.model.Product
import com.example.aimealplanners.domain.repository.AppRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(): Result<List<Product>> {
        return repository.getProducts()
    }
}
