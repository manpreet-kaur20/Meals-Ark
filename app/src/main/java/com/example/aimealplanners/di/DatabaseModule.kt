package com.example.aimealplanners.di

import android.content.Context
import androidx.room.Room
import com.example.aimealplanners.data.local.dao.*
import com.example.aimealplanners.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "meal_planner_db"
        ).build()
    }

    @Provides
    fun provideDishDao(database: AppDatabase): DishDao = database.dishDao()

    @Provides
    fun provideMealPlanDao(database: AppDatabase): MealPlanDao = database.mealPlanDao()

    @Provides
    fun provideShoppingItemDao(database: AppDatabase): ShoppingItemDao = database.shoppingItemDao()
}
