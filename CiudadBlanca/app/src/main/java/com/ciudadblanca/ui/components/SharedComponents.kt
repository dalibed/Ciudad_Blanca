package com.ciudadblanca.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ciudadblanca.ui.theme.*

// ── Bottom Navigation Bar ────────────────────────────────────────────────────
data class NavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val bottomNavItems = listOf(
    NavItem("Inicio",        Icons.Filled.Home,        "home"),
    NavItem("Habitaciones",  Icons.Filled.Bed,         "rooms"),
    NavItem("Servicios",     Icons.Filled.RoomService, "services"),
    NavItem("Guía",          Icons.Filled.Map,         "guide"),
    NavItem("Perfil",        Icons.Filled.Person,      "profile"),
)

@Composable
fun CiudadBlancaBottomBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    NavigationBar(
        containerColor = SurfaceWhite,
        tonalElevation = 8.dp
    ) {
        bottomNavItems.forEach { item ->
            val selected = currentRoute == item.route
            NavigationBarItem(
                selected = selected,
                onClick  = { onNavigate(item.route) },
                icon = {
                    Icon(
                        imageVector        = item.icon,
                        contentDescription = item.label,
                        tint = if (selected) OrangeAmber else TextSecondary
                    )
                },
                label = {
                    Text(
                        text     = item.label,
                        fontSize = 10.sp,
                        color    = if (selected) OrangeAmber else TextSecondary
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

// ── Orange Top App Bar ───────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrangeTopBar(
    title: String,
    onBack: (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            Text(
                text       = title,
                color      = TextOnOrange,
                fontWeight = FontWeight.Bold,
                fontSize   = 20.sp
            )
        },
        navigationIcon = {
            if (onBack != null) {
                IconButton(onClick = onBack) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = TextOnOrange
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = OrangeAmber
        )
    )
}

// ── Primary Button ────────────────────────────────────────────────────────────
@Composable
fun OrangeButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick  = onClick,
        enabled  = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        shape  = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = OrangeAmber,
            contentColor   = TextOnOrange
        )
    ) {
        Text(text = text, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
    }
}

// ── Outlined Secondary Button ─────────────────────────────────────────────────
@Composable
fun OutlinedGrayButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick  = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        shape  = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary)
    ) {
        Text(text = text, fontSize = 16.sp)
    }
}

// ── Status Badge ──────────────────────────────────────────────────────────────
@Composable
fun StatusBadge(status: String) {
    val (bg, label) = when (status.lowercase()) {
        "disponible" -> BadgeGreen  to "Disponible"
        "ocupada"    -> BadgeRed    to "Ocupada"
        "pendiente"  -> BadgePending to "Pendiente"
        "creada"     -> Color(0xFF2196F3) to "Creada"
        else         -> TextSecondary to status
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(bg)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(text = label, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}

// ── Amenity Chip ──────────────────────────────────────────────────────────────
@Composable
fun AmenityChip(icon: ImageVector, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(end = 16.dp, bottom = 8.dp)
    ) {
        Icon(icon, contentDescription = label, tint = TextSecondary, modifier = Modifier.size(18.dp))
        Spacer(Modifier.width(4.dp))
        Text(text = label, fontSize = 13.sp, color = TextSecondary)
    }
}
