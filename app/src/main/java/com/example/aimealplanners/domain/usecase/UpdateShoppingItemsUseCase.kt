package com.example.aimealplanners.domain.usecase

import com.example.aimealplanners.domain.model.ShoppingItem
import com.example.aimealplanners.domain.repository.ShoppingRepository

class UpdateShoppingItemsUseCase(
    private val repository: ShoppingRepository
) {
    suspend operator fun invoke(items: List<ShoppingItem>) {
        repository.updateShoppingItems(items)
    }
}
