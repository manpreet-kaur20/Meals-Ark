package com.example.aimealplanners.domain.usecase

import com.example.aimealplanners.domain.model.Dish
import com.example.aimealplanners.domain.repository.DishRepository

class GetDishByIdUseCase(
    private val repository: DishRepository
) {
    suspend operator fun invoke(id: Long): Dish? {
        return repository.getDishById(id)
    }
}
