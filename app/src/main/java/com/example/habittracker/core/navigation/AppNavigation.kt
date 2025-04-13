package com.example.habittracker.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.habittracker.features.auth.presentation.screens.LoginScreen // Placeholder import
import com.example.habittracker.features.auth.presentation.screens.ResetPasswordScreen // Placeholder import
import com.example.habittracker.features.auth.presentation.screens.SignUpScreen // Placeholder import
import com.example.habittracker.features.auth.presentation.screens.SplashScreen
import com.example.habittracker.features.auth.presentation.screens.ProfileScreen // Placeholder import
import com.example.habittracker.features.auth.presentation.screens.HomeScreen // Placeholder import for home

@Composable
fun AppNavigation(
    navController: NavHostController
    // Potentially pass AppState or other global dependencies if needed
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH_SCREEN // Start at the Splash screen
    ) {
        composable(route = Routes.SPLASH_SCREEN) {
            // We'll pass navController and ViewModel here
            SplashScreen(navController = navController)
        }
        composable(route = Routes.LOGIN_SCREEN) {
            // Placeholder - We'll add ViewModel and navigation actions later
            LoginScreen(navController = navController)
        }
        composable(route = Routes.SIGNUP_SCREEN) {
            // Placeholder
            SignUpScreen(navController = navController)
        }
        composable(route = Routes.RESET_PASSWORD_SCREEN) {
            // Placeholder
            ResetPasswordScreen(navController = navController)
        }
        composable(route = Routes.HOME_SCREEN) {
            // Placeholder - This might eventually host its own nested navigation
            HomeScreen(navController = navController)
        }
        composable(route = Routes.PROFILE_SCREEN) {
            // Placeholder
            ProfileScreen(navController = navController)
        }
        // Add other destinations (e.g., Habit Details) later
    }
}