package com.example.aimealplanners.domain.usecase

import com.example.aimealplanners.domain.model.Dish
import com.example.aimealplanners.domain.repository.DishRepository

class DeleteDishUseCase(
    private val repository: DishRepository
) {
    suspend operator fun invoke(dish: Dish) {
        repository.deleteDish(dish)
    }
}
