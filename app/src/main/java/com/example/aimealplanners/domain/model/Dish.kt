package com.example.aimealplanners.domain.model

data class Dish(
    val id: Long = 0,
    val name: String,
    val category: String,
    val memo: String,
    val url: String,
    val photoUri: String? = null
)
