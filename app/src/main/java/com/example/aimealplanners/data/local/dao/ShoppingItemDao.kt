package com.example.aimealplanners.data.local.dao

import androidx.room.*
import com.example.aimealplanners.data.local.entity.ShoppingItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingItemDao {
    @Query("SELECT * FROM shopping_items ORDER BY `order` ASC")
    fun getShoppingItems(): Flow<List<ShoppingItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(item: ShoppingItemEntity)

    @Delete
    suspend fun deleteShoppingItem(item: ShoppingItemEntity)

    @Update
    suspend fun updateShoppingItems(items: List<ShoppingItemEntity>)
}
