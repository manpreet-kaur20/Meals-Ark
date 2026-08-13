package com.example.aimealplanners.domain.repository

import com.example.aimealplanners.domain.model.ShoppingItem
import kotlinx.coroutines.flow.Flow

interface ShoppingRepository {
    fun getShoppingItems(): Flow<List<ShoppingItem>>
    suspend fun saveShoppingItem(item: ShoppingItem)
    suspend fun deleteShoppingItem(item: ShoppingItem)
    suspend fun updateShoppingItems(items: List<ShoppingItem>)
}
