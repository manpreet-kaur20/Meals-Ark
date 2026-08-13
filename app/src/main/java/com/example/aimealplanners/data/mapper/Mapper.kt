package com.example.aimealplanners.data.mapper

import com.example.aimealplanners.data.local.entity.DishEntity
import com.example.aimealplanners.data.local.entity.MealPlanEntity
import com.example.aimealplanners.data.local.entity.ShoppingItemEntity
import com.example.aimealplanners.domain.model.Dish
import com.example.aimealplanners.domain.model.MealPlan
import com.example.aimealplanners.domain.model.ShoppingItem

fun DishEntity.toDomain(): Dish {
    return Dish(
        id = id,
        name = name,
        category = category,
        memo = memo,
        url = url,
        photoUri = photoUri
    )
}

fun Dish.toEntity(): DishEntity {
    return DishEntity(
        id = id,
        name = name,
        category = category,
        memo = memo,
        url = url,
        photoUri = photoUri
    )
}

fun MealPlanEntity.toDomain(): MealPlan {
    return MealPlan(
        date = date,
        breakfastDishId = breakfastDishId,
        lunchDishId = lunchDishId,
        dinnerDishId = dinnerDishId
    )
}

fun MealPlan.toEntity(): MealPlanEntity {
    return MealPlanEntity(
        date = date,
        breakfastDishId = breakfastDishId,
        lunchDishId = lunchDishId,
        dinnerDishId = dinnerDishId
    )
}

fun ShoppingItemEntity.toDomain(): ShoppingItem {
    return ShoppingItem(
        id = id,
        name = name,
        isChecked = isChecked,
        order = order
    )
}

fun ShoppingItem.toEntity(): ShoppingItemEntity {
    return ShoppingItemEntity(
        id = id,
        name = name,
        isChecked = isChecked,
        order = order
    )
}
