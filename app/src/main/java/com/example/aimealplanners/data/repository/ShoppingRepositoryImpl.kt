package com.example.aimealplanners.data.repository

import com.example.aimealplanners.data.local.dao.ShoppingItemDao
import com.example.aimealplanners.data.mapper.toDomain
import com.example.aimealplanners.data.mapper.toEntity
import com.example.aimealplanners.domain.model.ShoppingItem
import com.example.aimealplanners.domain.repository.ShoppingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ShoppingRepositoryImpl @Inject constructor(
    private val shoppingItemDao: ShoppingItemDao
) : ShoppingRepository {
    override fun getShoppingItems(): Flow<List<ShoppingItem>> {
        return shoppingItemDao.getShoppingItems().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun saveShoppingItem(item: ShoppingItem) {
        shoppingItemDao.insertShoppingItem(item.toEntity())
    }

    override suspend fun deleteShoppingItem(item: ShoppingItem) {
        shoppingItemDao.deleteShoppingItem(item.toEntity())
    }

    override suspend fun updateShoppingItems(items: List<ShoppingItem>) {
        shoppingItemDao.updateShoppingItems(items.map { it.toEntity() })
    }
}
