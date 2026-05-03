package com.ciudadblanca.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// ── Paleta Ciudad Blanca ──────────────────────────────────────────────────────
val OrangeAmber     = Color(0xFFE07A20)
val OrangeAmberDark = Color(0xFFC46A10)
val OrangeLight     = Color(0xFFF5A04A)
val OrangeHeader    = Color(0xFFD4711A)

val SurfaceWhite    = Color(0xFFFFFFFF)
val SurfaceGray     = Color(0xFFF5F5F5)
val CardWhite       = Color(0xFFFFFFFF)

val TextPrimary     = Color(0xFF1A1A1A)
val TextSecondary   = Color(0xFF757575)
val TextOnOrange    = Color(0xFFFFFFFF)

val BadgeGreen      = Color(0xFF4CAF50)
val BadgeRed        = Color(0xFFE53935)
val BadgePending    = Color(0xFFFF9800)

private val LightColorScheme = lightColorScheme(
    primary          = OrangeAmber,
    onPrimary        = TextOnOrange,
    primaryContainer = OrangeLight,
    secondary        = OrangeAmberDark,
    background       = SurfaceGray,
    surface          = SurfaceWhite,
    onBackground     = TextPrimary,
    onSurface        = TextPrimary,
)

@Composable
fun CiudadBlancaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography  = Typography(),
        content     = content
    )
}
