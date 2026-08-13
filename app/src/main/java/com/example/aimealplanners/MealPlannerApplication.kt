package com.example.aimealplanners

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MealPlannerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
