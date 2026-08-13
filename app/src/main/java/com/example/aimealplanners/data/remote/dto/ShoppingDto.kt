package com.example.aimealplanners.data.remote.dto

import com.example.aimealplanners.domain.model.ShoppingItem

data class ShoppingResponse(
    val items: List<ShoppingDto>
)

data class ShoppingDto(
    val id: Long,
    val name: String,
    val isChecked: Boolean,
    val order: Int
)

fun ShoppingDto.toDomain(): ShoppingItem {
    return ShoppingItem(
        id = id,
        name = name,
        isChecked = isChecked,
        order = order
    )
}
