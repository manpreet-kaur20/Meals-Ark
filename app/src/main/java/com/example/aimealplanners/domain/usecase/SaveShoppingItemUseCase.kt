package com.example.aimealplanners.domain.usecase

import com.example.aimealplanners.domain.model.ShoppingItem
import com.example.aimealplanners.domain.repository.ShoppingRepository

class SaveShoppingItemUseCase(
    private val repository: ShoppingRepository
) {
    suspend operator fun invoke(item: ShoppingItem) {
        repository.saveShoppingItem(item)
    }
}
