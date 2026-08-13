package com.example.aimealplanners.data.remote.dto

import com.example.aimealplanners.domain.model.Product

data class ProductResponse(
    val products: List<ProductDto>
)

data class ProductDto(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double
)

fun ProductDto.toDomain(): Product {
    return Product(
        id = id,
        title = title,
        description = description,
        price = price
    )
}
