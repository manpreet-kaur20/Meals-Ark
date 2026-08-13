package com.example.aimealplanners.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.aimealplanners.data.local.dao.DishDao
import com.example.aimealplanners.data.local.dao.MealPlanDao
import com.example.aimealplanners.data.local.dao.ShoppingItemDao
import com.example.aimealplanners.data.local.entity.DishEntity
import com.example.aimealplanners.data.local.entity.MealPlanEntity
import com.example.aimealplanners.data.local.entity.ShoppingItemEntity

@Database(
    entities = [
        DishEntity::class,
        MealPlanEntity::class,
        ShoppingItemEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dishDao(): DishDao
    abstract fun mealPlanDao(): MealPlanDao
    abstract fun shoppingItemDao(): ShoppingItemDao
}
