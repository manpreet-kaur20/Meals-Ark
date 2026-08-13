package com.example.aimealplanners.domain.usecase

import com.example.aimealplanners.domain.model.Dish
import com.example.aimealplanners.domain.repository.DishRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDishesUseCase @Inject constructor(
    private val repository: DishRepository
) {
    operator fun invoke(): Flow<List<Dish>> {
        return repository.getDishes()
    }
}
