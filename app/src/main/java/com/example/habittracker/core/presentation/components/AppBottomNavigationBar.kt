package com.example.habittracker.core.presentation.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val label: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Composable
fun AppBottomNavigationBar(
    items: List<BottomNavItem>,
    selectedRoute: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        items.forEach { item ->
            val isSelected = selectedRoute == item.route
            NavigationBarItem(
                selected = isSelected,
                onClick = { onItemSelected(item.route) },
                label = { Text(item.label) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label
                    )
                }
            )
        }
    }
}