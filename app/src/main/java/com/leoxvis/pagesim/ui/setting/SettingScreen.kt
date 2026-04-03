package com.leoxvis.pagesim.ui.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.leoxvis.pagesim.ui.components.ThemeSelectionDialog
import com.leoxvis.pagesim.ui.data.setting.SettingsManager
import com.leoxvis.pagesim.ui.data.setting.ThemeMode
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    themeState: ThemeMode,
    dynamicColor: Boolean,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val settingsManager = remember {
        SettingsManager(context.applicationContext)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Settings",
            modifier = Modifier
                .padding(top = 55.dp, start = 30.dp, bottom = 18.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Left
        )

        Text(
            "Appearance",
            modifier = Modifier.padding(start = 26.dp, top = 12.dp, bottom = 12.dp),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        var showThemeDialog by remember { mutableStateOf(false) }

        Card(
            shape = CardDefaults.elevatedShape,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 6.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Row(modifier = Modifier.padding(2.dp)) {
                Text(
                    "App Theme",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(12.dp)
                )
                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = { showThemeDialog = true },
                    content = {
                        Text(
                            text = when (themeState) {
                                ThemeMode.SYSTEM -> "System"
                                ThemeMode.DARK -> "Dark"
                                ThemeMode.LIGHT -> "Light"
                            }
                        )
                    }
                )

                if (showThemeDialog) {
                    ThemeSelectionDialog(
                        currentTheme = themeState,
                        onThemeSelected = { selected ->
                            coroutineScope.launch {
                                settingsManager.setThemeMode(selected)
                            }
                        },
                        onDismiss = { showThemeDialog = false }
                    )
                }

            }

        }

        Card(
            shape = CardDefaults.elevatedShape,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 6.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Row() {
                Text(
                    "Use Dynamic Color",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(12.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = dynamicColor,
                    onCheckedChange = { enabled ->
                        coroutineScope.launch {
                            settingsManager.setDynamicColor(enabled)
                        }

                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
        }



        Text(
            "Developer",
            modifier = Modifier.padding(top = 12.dp, bottom = 4.dp, start = 26.dp),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Card(
            shape = CardDefaults.elevatedShape,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {

            val uriHandler = LocalUriHandler.current

            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .clickable {
                            uriHandler.openUri("https://github.com/vishwajithkv")
                        }) {
                    Text(
                        "Github Profile", style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.25f)
                )

                Row(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .clickable {
                            uriHandler.openUri("https://github.com/vishwajithkv/PageSim")
                        },
                ) {
                    Text(
                        "Source Code",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Text(
            "About",
            modifier = Modifier.padding(start = 26.dp, top = 12.dp, bottom = 4.dp),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Card(
            shape = CardDefaults.elevatedShape,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {

            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.padding(vertical = 4.dp)) {
                    Text(
                        "Version",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        "1.0.0",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.25f)
                )
                Row(modifier = Modifier.padding(vertical = 4.dp)) {
                    Text(
                        "Built With",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        "Compose",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}