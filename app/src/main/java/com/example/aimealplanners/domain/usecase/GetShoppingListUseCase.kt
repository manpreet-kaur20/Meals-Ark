package com.example.aimealplanners.domain.usecase

import com.example.aimealplanners.domain.model.ShoppingItem
import com.example.aimealplanners.domain.repository.AppRepository
import javax.inject.Inject

class GetShoppingListUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(): Result<List<ShoppingItem>> {
        return repository.getShoppingList()
    }
}
