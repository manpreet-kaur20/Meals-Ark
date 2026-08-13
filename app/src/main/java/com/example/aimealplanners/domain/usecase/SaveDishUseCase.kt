package com.example.aimealplanners.domain.usecase

import com.example.aimealplanners.domain.model.Dish
import com.example.aimealplanners.domain.repository.DishRepository
import javax.inject.Inject

class SaveDishUseCase @Inject constructor(
    private val repository: DishRepository
) {
    suspend operator fun invoke(dish: Dish) {
        repository.saveDish(dish)
    }
}
