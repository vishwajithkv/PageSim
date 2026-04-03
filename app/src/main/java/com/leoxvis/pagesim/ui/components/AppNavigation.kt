package com.leoxvis.pagesim.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.leoxvis.pagesim.ui.data.setting.ThemeMode
import com.leoxvis.pagesim.ui.run.RunScreen
import com.leoxvis.pagesim.ui.info.InfoScreen
import com.leoxvis.pagesim.ui.setting.SettingsScreen

data class BottomNavItem(
    val label: String,
    val icon: ImageVector
)

@Composable
fun PageFlip(
    themeState: ThemeMode,
    dynamicColor: Boolean
) {

    val items = listOf(
        BottomNavItem("Info", Icons.Default.Info),
        BottomNavItem("Simulate", Icons.Default.PlayArrow),
        BottomNavItem("Settings", Icons.Default.Settings)
    )

    var selectedItem by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = { selectedItem = index },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label
                            )
                        },
                        label = { Text(item.label) }
                    )
                }
            }
        },
        content = { innerPadding ->
            when (selectedItem) {
                0 -> InfoScreen(Modifier.padding(innerPadding))
                1 -> RunScreen(Modifier.padding(innerPadding))
                2 -> SettingsScreen(
                    themeState = themeState,
                    dynamicColor = dynamicColor,
                    Modifier.padding(innerPadding)
                )
            }
        }
    )
}