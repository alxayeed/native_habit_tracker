package com.example.habittracker.features.auth.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.outlined.CheckCircleOutline
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Timeline
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.habittracker.core.navigation.Routes
import com.example.habittracker.core.presentation.components.AppBottomNavigationBar
import com.example.habittracker.core.presentation.components.AppDrawerContent
import com.example.habittracker.core.presentation.components.AppTopAppBar
import com.example.habittracker.core.presentation.components.BottomNavItem
import com.example.habittracker.core.presentation.components.DrawerItem
import com.example.habittracker.features.auth.presentation.viewmodel.AuthViewModel
import com.example.habittracker.ui.theme.HabitTrackerTheme
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel() // Inject AuthViewModel for logout etc.
) {
    // --- State ---
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    // State to track selected bottom nav item - start with Habits route
    var selectedBottomRoute by remember { mutableStateOf(Routes.HOME_HABITS_ROUTE) } // Needs defining
    // State to track selected drawer item (optional if drawer only navigates)
    var selectedDrawerRoute by remember { mutableStateOf<String?>(null) }

    // --- Data for Navigation Items ---
    val bottomNavItems = listOf(
        BottomNavItem("Habits", Routes.HOME_HABITS_ROUTE, Icons.Filled.CheckCircle, Icons.Outlined.CheckCircleOutline),
        BottomNavItem("Progress", Routes.HOME_PROGRESS_ROUTE, Icons.Filled.Timeline, Icons.Outlined.Timeline),
        BottomNavItem("Settings", Routes.HOME_SETTINGS_ROUTE, Icons.Filled.Settings, Icons.Outlined.Settings) // Example
    )

    val drawerItems = listOf(
        DrawerItem("Home", Routes.HOME_SCREEN, Icons.Filled.Home), // Navigate to base home?
        DrawerItem("Profile", Routes.PROFILE_SCREEN, Icons.Filled.Person)
        // Add other drawer destinations later
    )

    // Determine current screen title based on bottom nav selection
    val currentScreenTitle = bottomNavItems.find { it.route == selectedBottomRoute }?.label ?: "Habit Tracker"

    // --- UI Structure ---
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen, // Only allow swipe gesture when open
        drawerContent = {
            AppDrawerContent(
                items = drawerItems,
                selectedRoute = selectedDrawerRoute,
                onItemSelected = { route ->
                    selectedDrawerRoute = route // Update selection state
                    scope.launch { drawerState.close() } // Close drawer after selection
                    // Handle Navigation based on drawer item route
                    when (route) {
                        Routes.PROFILE_SCREEN -> navController.navigate(Routes.PROFILE_SCREEN)
                        Routes.HOME_SCREEN -> { /* Stay here or navigate to default bottom tab */
                            selectedBottomRoute = Routes.HOME_HABITS_ROUTE // Example: Reset to habits
                        }
                        // Add other drawer navigation cases
                    }
                },
                headerContent = {
                    // Example Header
                    Text("Habit Tracker", style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(8.dp))
                    Text("Welcome!", style = MaterialTheme.typography.bodyMedium) // TODO: Add user name
                },
                footerContent = {
                    // Example Footer
                    TextButton(onClick = {
                        scope.launch { drawerState.close() }
                        authViewModel.logout()
                        // Navigation back to Login is handled by observing AuthStatus in SplashScreen/LoginScreen
                    }) {
                        Text("Logout")
                    }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                AppTopAppBar(
                    title = currentScreenTitle,
                    onNavigationIconClick = {
                        scope.launch { drawerState.open() }
                    }
                )
            },
            bottomBar = {
                AppBottomNavigationBar(
                    items = bottomNavItems,
                    selectedRoute = selectedBottomRoute,
                    onItemSelected = { route -> selectedBottomRoute = route }
                )
            }
        ) { paddingValues ->
            // --- Main Screen Content Area ---
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues) // Apply padding from Scaffold
            ) {
                // Display content based on the selected bottom navigation item
                when (selectedBottomRoute) {
                    Routes.HOME_HABITS_ROUTE -> HabitsContent() // Replace with actual Habit list composable
                    Routes.HOME_PROGRESS_ROUTE -> ProgressContent() // Replace with actual Progress composable
                    Routes.HOME_SETTINGS_ROUTE -> SettingsContent() // Replace with actual Settings composable
                }
            }
        }
    }
}

// --- Placeholder Content Composables ---
// These would eventually be replaced by actual feature screens/composables

@Composable
fun HabitsContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Habits List Area")
    }
}

@Composable
fun ProgressContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Progress/Stats Area")
    }
}

@Composable
fun SettingsContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Settings Area")
    }
}


// --- Define new Routes constants ---
// Add these to your core/navigation/Routes.kt file
/*
object Routes {
    const val SPLASH_SCREEN = "splash_screen"
    const val LOGIN_SCREEN = "login_screen"
    // ... other auth routes ...
    const val HOME_SCREEN = "home_screen" // Represents the container screen
    const val PROFILE_SCREEN = "profile_screen"

    // Routes for content within HomeScreen's bottom nav
    const val HOME_HABITS_ROUTE = "home_habits"
    const val HOME_PROGRESS_ROUTE = "home_progress"
    const val HOME_SETTINGS_ROUTE = "home_settings"
}
*/

// --- Preview ---
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HabitTrackerTheme { // Use your theme path
        // Previewing complex screens with state and navigation is tricky.
        // You might preview individual components or a simplified static layout.
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Home Screen Preview Area")
        }
    }
}