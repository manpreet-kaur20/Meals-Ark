package com.example.aimealplanners.domain.model

data class ShoppingItem(
    val id: Long = 0,
    val name: String,
    val isChecked: Boolean = false,
    val order: Int = 0
)
