package com.example.habittracker.core.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

data class DrawerItem(
    val label: String,
    val route: String,
    val icon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawerContent(
    items: List<DrawerItem>,
    selectedRoute: String?,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    headerContent: @Composable ColumnScope.() -> Unit = {},
    footerContent: @Composable ColumnScope.() -> Unit = {}
) {
    ModalDrawerSheet(modifier = modifier) {
        // Optional Header (e.g., User info, Logo)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            headerContent()
        }
        Spacer(Modifier.height(12.dp))
        HorizontalDivider()
        Spacer(Modifier.height(12.dp))

        // Navigation Items
        items.forEach { item ->
            NavigationDrawerItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = item.route == selectedRoute,
                onClick = { onItemSelected(item.route) },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Optional Footer (e.g., Settings, Logout)
        HorizontalDivider() // Separator
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 16.dp)
        ) {
            footerContent()
        }
    }
}