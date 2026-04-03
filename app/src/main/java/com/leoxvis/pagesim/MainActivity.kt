package com.leoxvis.pagesim

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.leoxvis.pagesim.ui.theme.MyApplicationTheme
import com.leoxvis.pagesim.ui.components.PageFlip
import com.leoxvis.pagesim.ui.data.setting.SettingsManager
import com.leoxvis.pagesim.ui.data.setting.ThemeMode
import com.leoxvis.pagesim.ui.system.SetStatusBarStyle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val settingsManager = remember { SettingsManager(context) }

            val themeState by settingsManager.themeMode.collectAsState(initial = ThemeMode.SYSTEM)
            val dynamicColor by settingsManager.dynamicColor.collectAsState(initial = true)

            val darkTheme = when (themeState) {
                ThemeMode.SYSTEM -> isSystemInDarkTheme()
                ThemeMode.DARK -> true
                ThemeMode.LIGHT -> false
            }

            MyApplicationTheme(
                dynamicColor = dynamicColor,
                darkTheme = darkTheme
            ) {
                SetStatusBarStyle(darkTheme)

                PageFlip(
                    themeState = themeState,
                    dynamicColor = dynamicColor
                )
            }
        }
    }
}

