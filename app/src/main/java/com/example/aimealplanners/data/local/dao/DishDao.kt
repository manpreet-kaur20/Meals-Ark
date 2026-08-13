package com.example.aimealplanners.data.local.dao

import androidx.room.*
import com.example.aimealplanners.data.local.entity.DishEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DishDao {
    @Query("SELECT * FROM dishes")
    fun getDishes(): Flow<List<DishEntity>>

    @Query("SELECT * FROM dishes WHERE id = :id")
    suspend fun getDishById(id: Long): DishEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDish(dish: DishEntity)

    @Delete
    suspend fun deleteDish(dish: DishEntity)
}
