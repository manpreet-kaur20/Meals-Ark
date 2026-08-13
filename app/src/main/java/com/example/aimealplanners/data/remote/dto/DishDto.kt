package com.example.aimealplanners.data.remote.dto

import com.example.aimealplanners.domain.model.Dish

data class DishResponse(
    val dishes: List<DishDto>
)

data class DishDto(
    val id: Long,
    val name: String,
    val category: String,
    val memo: String,
    val url: String,
    val photoUri: String?
)

fun DishDto.toDomain(): Dish {
    return Dish(
        id = id,
        name = name,
        category = category,
        memo = memo,
        url = url,
        photoUri = photoUri
    )
}
