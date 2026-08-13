package com.example.aimealplanners.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.EventNote
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppRoute : NavKey {
    val title: String
    val icon: ImageVector
}

@Serializable
data object Dishes : AppRoute {
    override val title: String = "Dishes"
    override val icon: ImageVector = Icons.Rounded.RestaurantMenu
}

@Serializable
data object WeeklyPlanner : AppRoute {
    override val title: String = "Weekly Planner"
    override val icon: ImageVector = Icons.AutoMirrored.Rounded.EventNote
}

@Serializable
data object Calendar : AppRoute {
    override val title: String = "Calendar"
    override val icon: ImageVector = Icons.Rounded.CalendarMonth
}

@Serializable
data object ShoppingList : AppRoute {
    override val title: String = "Shopping List"
    override val icon: ImageVector = Icons.Rounded.ShoppingBag
}

@Serializable
data object Splash : NavKey

@Serializable
data object SignIn : NavKey

@Serializable
data object SignUp : NavKey

@Serializable
data object ForgotPassword : NavKey

@Serializable
data object OnboardingQuestionnaire : NavKey

@Serializable
data object Paywall : NavKey

val MainDestinations = listOf(Dishes, WeeklyPlanner, Calendar, ShoppingList)
