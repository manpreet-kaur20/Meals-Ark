package com.example.aimealplanners.domain.usecase

import com.example.aimealplanners.domain.model.ShoppingItem
import com.example.aimealplanners.domain.repository.ShoppingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetShoppingItemsUseCase @Inject constructor(
    private val repository: ShoppingRepository
) {
    operator fun invoke(): Flow<List<ShoppingItem>> {
        return repository.getShoppingItems()
    }
}
