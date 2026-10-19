package com.entregafood.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object AppColors {
    val Primary = Color(0xFFD62839) // vermelho pimenta
    val PrimaryDark = Color(0xFFA81E2D)
    val Background = Color(0xFFF6F7F9)
    val Surface = Color(0xFFFFFFFF)
    val Text = Color(0xFF1B2430)
    val Muted = Color(0xFF6B7685)
    val Border = Color(0xFFE1E5EB)
    val Danger = Color(0xFFB3261E)
}

private val LightScheme = lightColorScheme(
    primary = AppColors.Primary,
    onPrimary = Color.White,
    background = AppColors.Background,
    onBackground = AppColors.Text,
    surface = AppColors.Surface,
    onSurface = AppColors.Text,
    onSurfaceVariant = AppColors.Muted,
    outline = AppColors.Border,
    error = AppColors.Danger,
)

@Composable
fun EntregaFoodTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = LightScheme, content = content)
}
