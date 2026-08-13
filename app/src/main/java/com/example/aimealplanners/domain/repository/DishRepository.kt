package com.example.aimealplanners.domain.repository

import com.example.aimealplanners.domain.model.Dish
import kotlinx.coroutines.flow.Flow

interface DishRepository {
    fun getDishes(): Flow<List<Dish>>
    suspend fun getDishById(id: Long): Dish?
    suspend fun saveDish(dish: Dish)
    suspend fun deleteDish(dish: Dish)
}
