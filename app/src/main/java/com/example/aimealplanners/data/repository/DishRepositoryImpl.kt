package com.example.aimealplanners.data.repository

import com.example.aimealplanners.data.local.dao.DishDao
import com.example.aimealplanners.data.mapper.toDomain
import com.example.aimealplanners.data.mapper.toEntity
import com.example.aimealplanners.domain.model.Dish
import com.example.aimealplanners.domain.repository.DishRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DishRepositoryImpl @Inject constructor(
    private val dishDao: DishDao
) : DishRepository {
    override fun getDishes(): Flow<List<Dish>> {
        return dishDao.getDishes().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getDishById(id: Long): Dish? {
        return dishDao.getDishById(id)?.toDomain()
    }

    override suspend fun saveDish(dish: Dish) {
        dishDao.insertDish(dish.toEntity())
    }

    override suspend fun deleteDish(dish: Dish) {
        dishDao.deleteDish(dish.toEntity())
    }
}
