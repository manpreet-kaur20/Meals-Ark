package com.example.aimealplanners.ui

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.aimealplanners.ui.navigation.*
import com.example.aimealplanners.ui.dish.DishViewModel
import com.example.aimealplanners.ui.planner.MealPlannerViewModel
import com.example.aimealplanners.ui.calendar.CalendarViewModel

import com.example.aimealplanners.ui.splash.SplashScreen
import com.example.aimealplanners.ui.onboarding.OnboardingQuestionnaireScreen
import com.example.aimealplanners.ui.auth.SignInScreen
import com.example.aimealplanners.ui.auth.SignUpScreen
import com.example.aimealplanners.ui.auth.ForgotPasswordScreen
import com.example.aimealplanners.ui.paywall.PaywallScreen
import com.example.aimealplanners.ui.home.MainAppContainerScreen

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun MainScaffold() {
    val context = LocalContext.current
    val sharedPrefs = remember { context.getSharedPreferences("meal_planner_prefs", Context.MODE_PRIVATE) }
    val isOnboarded = remember { sharedPrefs.getBoolean("is_onboarded", false) }

    val initialRoute: NavKey = if (isOnboarded) Dishes else Splash
    val backStack = rememberNavBackStack(initialRoute)

    fun markUserOnboarded() {
        sharedPrefs.edit().putBoolean("is_onboarded", true).apply()
    }

    fun clearUserOnboarded() {
        sharedPrefs.edit().putBoolean("is_onboarded", false).apply()
    }

    val dishViewModel: DishViewModel = hiltViewModel()
    val plannerViewModel: MealPlannerViewModel = hiltViewModel()
    val calendarViewModel: CalendarViewModel = hiltViewModel()

    Box(modifier = Modifier.fillMaxSize()) {
        AppNavigation(
            backStack = backStack,
            dishViewModel = dishViewModel,
            plannerViewModel = plannerViewModel,
            calendarViewModel = calendarViewModel,
            onUserLoggedIn = { markUserOnboarded() },
            onUserLoggedOut = { clearUserOnboarded() }
        )
    }
}

@Composable
fun AppNavigation(
    backStack: NavBackStack<NavKey>,
    dishViewModel: DishViewModel,
    plannerViewModel: MealPlannerViewModel,
    calendarViewModel: CalendarViewModel,
    onUserLoggedIn: () -> Unit,
    onUserLoggedOut: () -> Unit
) {
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Splash> {
                SplashScreen(
                    onGetStarted = {
                        backStack.add(OnboardingQuestionnaire)
                    },
                    onSignIn = {
                        backStack.add(SignIn)
                    }
                )
            }
            entry<OnboardingQuestionnaire> {
                OnboardingQuestionnaireScreen(
                    onBackToSplash = { backStack.removeLastOrNull() },
                    onQuestionnaireComplete = { backStack.add(SignUp) }
                )
            }
            entry<SignIn> {
                SignInScreen(
                    onBackClick = { backStack.removeLastOrNull() },
                    onSignInSuccess = {
                        onUserLoggedIn()
                        backStack.add(Paywall)
                    },
                    onForgotPasswordClick = { backStack.add(ForgotPassword) },
                    onSignUpClick = { backStack.add(SignUp) }
                )
            }
            entry<SignUp> {
                SignUpScreen(
                    onBackClick = { backStack.removeLastOrNull() },
                    onSignUpSuccess = {
                        onUserLoggedIn()
                        backStack.add(Paywall)
                    },
                    onSignInClick = { backStack.add(SignIn) }
                )
            }
            entry<ForgotPassword> {
                ForgotPasswordScreen(
                    onBackClick = { backStack.removeLastOrNull() },
                    onResetSuccess = { backStack.add(SignIn) }
                )
            }
            entry<Paywall> {
                PaywallScreen(
                    onClosePaywall = {
                        onUserLoggedIn()
                        backStack.add(Dishes)
                    },
                    onSubscribeSuccess = {
                        onUserLoggedIn()
                        backStack.add(Dishes)
                    }
                )
            }
            entry<Dishes> {
                MainAppContainerScreen(
                    onOpenSettings = {},
                    onGenerateDailyPlan = {},
                    onLogOut = {
                        onUserLoggedOut()
                        backStack.clear()
                        backStack.add(Splash)
                    }
                )
            }
        }
    )
}
