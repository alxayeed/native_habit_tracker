package com.example.habittracker.features.auth.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.habittracker.core.presentation.components.BasicTextField
import com.example.habittracker.core.presentation.components.PasswordTextField
import com.example.habittracker.core.presentation.components.PrimaryButton
import com.example.habittracker.features.auth.presentation.viewmodel.AuthViewModel

@Composable
fun SignUpScreen(
    navController: NavHostController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordsMatch by remember { mutableStateOf(true) } // State for password match validation

    // Handle errors with Snackbar
    LaunchedEffect(key1 = uiState.error) {
        uiState.error?.let { message ->
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
            viewModel.dismissError()
        }
    }

    // Basic check when confirm password changes
    LaunchedEffect(password, confirmPassword) {
        passwordsMatch = password == confirmPassword
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
                text = "Create Account",
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
                imeAction = ImeAction.Next
            )

            Spacer(modifier = Modifier.height(16.dp))

            PasswordTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                labelText = "Confirm Password",
                imeAction = ImeAction.Done, // TODO: Add keyboard action to trigger sign up maybe
                isError = !passwordsMatch && confirmPassword.isNotEmpty(), // Show error if passwords don't match
                supportingText = if (!passwordsMatch && confirmPassword.isNotEmpty()) "Passwords do not match" else null
            )

            Spacer(modifier = Modifier.height(24.dp))

            PrimaryButton(
                text = "Sign Up",
                onClick = {
                    if (email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()) {
                        if (passwordsMatch) {
                            viewModel.signUp(email, password)
                            // Consider navigation after successful signup or showing a message
                            // depending on whether email verification is enabled.
                            // For now, errors will show via Snackbar. Success might show a message too.
                            // e.g., if (viewModel.uiState.value.error == null && !viewModel.uiState.value.isLoading) {
                            //          navController.popBackStack() // Go back to Login after trying?
                            //       }
                        } else {
                            // Error state is already handled by the TextField isError/supportingText
                            println("Passwords don't match")
                        }
                    } else {
                        // TODO: Show local validation error
                        println("Fields cannot be blank")
                    }
                },
                isLoading = uiState.isLoading,
                modifier = Modifier.fillMaxWidth(),
                enabled = passwordsMatch // Disable button if passwords don't match
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Already have an account? Log in",
                modifier = Modifier
                    .clickable {
                        navController.popBackStack() // Just pop back if coming from Login
                        // Or: navController.navigate(Routes.LOGIN_SCREEN) { popUpTo(Routes.LOGIN_SCREEN) { inclusive = true } }
                    }
                    .padding(vertical = 8.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

// Basic Preview
@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    com.example.habittracker.ui.theme.HabitTrackerTheme { // Use your theme path
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Sign Up Screen Preview Area")
        }
    }
}