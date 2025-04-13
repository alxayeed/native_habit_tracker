package com.example.habittracker.features.auth.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController // Import KeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.habittracker.core.navigation.Routes
import com.example.habittracker.core.presentation.BasicTextField
import com.example.habittracker.core.presentation.PasswordTextField
import com.example.habittracker.core.presentation.PrimaryButton
import com.example.habittracker.features.auth.domain.entity.AuthStatus // Import AuthStatus
import com.example.habittracker.features.auth.presentation.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current // Get keyboard controller

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // --- React to Auth Status Changes for Navigation ---
    LaunchedEffect(key1 = uiState.authStatus) {
        if (uiState.authStatus is AuthStatus.Authenticated) {
            // Navigate to Home & Clear Login/Splash from back stack
            navController.navigate(Routes.HOME_SCREEN) {
                popUpTo(navController.graph.startDestinationId) { // Pop up to the start destination of the graph
                    inclusive = true
                }
                // Ensure only one copy of HomeScreen is launched
                launchSingleTop = true
            }
        }
    }
    // --------------------------------------------------

    // Handle errors with Snackbar
    LaunchedEffect(key1 = uiState.error) {
        uiState.error?.let { message ->
            keyboardController?.hide() // Hide keyboard on error
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
            viewModel.dismissError()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome Back!",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            BasicTextField(
                value = email,
                onValueChange = { email = it },
                labelText = "Email",
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )

            Spacer(modifier = Modifier.height(16.dp))

            PasswordTextField(
                value = password,
                onValueChange = { password = it },
                labelText = "Password",
                imeAction = ImeAction.Done
                // TODO: Add keyboard action to trigger login
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Forgot Password?",
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { navController.navigate(Routes.RESET_PASSWORD_SCREEN) }
                    .padding(vertical = 8.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            PrimaryButton(
                text = "Login",
                onClick = {
                    keyboardController?.hide()
                    if (email.isNotBlank() && password.isNotBlank()) {
                        viewModel.login(email, password)
                    } else {
                        viewModel.showError("Email and password cannot be blank.") // Show error via ViewModel/Snackbar
                    }
                },
                isLoading = uiState.isLoading,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Don't have an account? Sign up",
                modifier = Modifier
                    .clickable { navController.navigate(Routes.SIGNUP_SCREEN) }
                    .padding(vertical = 8.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

// Add a simple showError method to the ViewModel for local validation feedback
// Modify AuthViewModel.kt:
/*
@HiltViewModel
class AuthViewModel @Inject constructor(...) : ViewModel() {
    // ... existing code ...

    fun showError(message: String) {
        _uiState.update { it.copy(error = message, isLoading = false) }
    }

    // ... rest of ViewModel ...
}
*/