package com.example.habittracker.features.auth.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.habittracker.core.navigation.Routes
import com.example.habittracker.features.auth.domain.entity.AuthStatus
import com.example.habittracker.features.auth.presentation.viewmodel.AuthViewModel

@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: AuthViewModel = hiltViewModel() // Get ViewModel via Hilt
) {
    val authState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(authState.authStatus) { // Re-run when authStatus changes
        when (authState.authStatus) {
            is AuthStatus.Authenticated -> {
                navController.navigate(Routes.HOME_SCREEN) {
                    popUpTo(Routes.SPLASH_SCREEN) { inclusive = true } // Clear back stack
                }
            }
            is AuthStatus.Unauthenticated -> {
                navController.navigate(Routes.LOGIN_SCREEN) {
                    popUpTo(Routes.SPLASH_SCREEN) { inclusive = true } // Clear back stack
                }
            }
            is AuthStatus.Loading -> {
                // Stay on splash screen while loading
            }
        }
    }

    // Simple UI for Splash Screen
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Show loading indicator while status is Loading
        if (authState.authStatus == AuthStatus.Loading) {
            CircularProgressIndicator()
        } else {
            // Optionally show logo or text briefly before navigation happens
            Text("Habit Tracker")
        }
    }
}