package com.leoxvis.pagesim.ui.data.setting

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

enum class ThemeMode { SYSTEM, DARK, LIGHT }

val THEME_MODE = stringPreferencesKey("theme_mode")
val DYNAMIC_COLOR = booleanPreferencesKey("dynamic_color")