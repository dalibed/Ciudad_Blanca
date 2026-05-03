package com.ciudadblanca.ui.screens.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ciudadblanca.ui.theme.*

// ── Home Screen ───────────────────────────────────────────────────────────────
@Composable
fun HomeScreen(
    userName: String = "Invitado",
    isGuest: Boolean = true,
    onNavigateRooms: () -> Unit,
    onNavigateReservation: () -> Unit,
    onNavigateServices: () -> Unit,
    onNavigateGuide: () -> Unit,
    onRegisterPrompt: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceGray)
            .verticalScroll(scrollState)
    ) {
        // ── Hero Banner ──────────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            // Gradient background (replace with real image via Coil/AsyncImage)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(Color(0xFF8B7355), Color(0xFF5C4A2A))
                        )
                    )
            )
            // Gradient overlay for text readability
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.55f))
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(20.dp)
            ) {
                Icon(
                    Icons.Filled.Star,
                    contentDescription = null,
                    tint     = OrangeLight,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text       = "Hola, $userName",
                    color      = Color.White,
                    fontSize   = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text     = "Bienvenido a Ciudad Blanca Popayán",
                    color    = Color.White.copy(alpha = 0.85f),
                    fontSize = 13.sp
                )
            }
        }

        // ── Quick Access Grid ────────────────────────────────────────────────
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier            = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickAccessCard(
                    icon    = Icons.Filled.Bed,
                    label   = "Habitaciones",
                    subtext = "Explora nuestras suites",
                    modifier = Modifier.weight(1f),
                    onClick = onNavigateRooms
                )
                QuickAccessCard(
                    icon    = Icons.Filled.CalendarToday,
                    label   = "Reservar",
                    subtext = "Haz tu reserva ahora",
                    modifier = Modifier.weight(1f),
                    onClick = onNavigateReservation
                )
            }
            Spacer(Modifier.height(12.dp))
            Row(
                modifier            = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickAccessCard(
                    icon    = Icons.Filled.RoomService,
                    label   = "Servicios",
                    subtext = "Disfruta de nuestras instalaciones",
                    modifier = Modifier.weight(1f),
                    onClick = onNavigateServices
                )
                QuickAccessCard(
                    icon    = Icons.Filled.Map,
                    label   = "Guía Popayán",
                    subtext = "Descubre la ciudad blanca",
                    modifier = Modifier.weight(1f),
                    onClick = onNavigateGuide
                )
            }
        }

        // ── Featured Rooms Section ───────────────────────────────────────────
        Row(
            modifier            = Modifier.padding(horizontal = 20.dp),
            verticalAlignment   = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text       = "Habitaciones Destacadas",
                fontSize   = 17.sp,
                fontWeight = FontWeight.Bold,
                color      = TextPrimary
            )
            TextButton(onClick = onNavigateRooms) {
                Text("Ver todas", color = OrangeAmber, fontSize = 13.sp)
            }
        }

        // Sample featured room card
        FeaturedRoomCard(
            name        = "Habitación Deluxe",
            description = "Habitación elegante con vista a la ciudad",
            price       = "\$150",
            guests      = 2,
            area        = 35,
            modifier    = Modifier.padding(horizontal = 20.dp),
            onClick     = onNavigateRooms
        )

        // ── Guest Registration Prompt ────────────────────────────────────────
        if (isGuest) {
            Spacer(Modifier.height(16.dp))
            Card(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                shape  = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = OrangeAmber)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text       = "¡Regístrate para más beneficios!",
                        color      = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize   = 16.sp
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text     = "Crea una cuenta para hacer reservas y solicitar servicios",
                        color    = Color.White.copy(alpha = 0.9f),
                        fontSize = 13.sp
                    )
                    Spacer(Modifier.height(14.dp))
                    Button(
                        onClick = onRegisterPrompt,
                        colors  = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor   = OrangeAmber
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Registrarme ahora", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))
    }
}

// ── Quick Access Card ─────────────────────────────────────────────────────────
@Composable
private fun QuickAccessCard(
    icon: ImageVector,
    label: String,
    subtext: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape    = RoundedCornerShape(12.dp),
        colors   = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(
                imageVector        = icon,
                contentDescription = label,
                tint               = OrangeAmber,
                modifier           = Modifier.size(28.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(text = label,   fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
            Text(text = subtext, fontSize = 11.sp, color = TextSecondary, lineHeight = 14.sp)
        }
    }
}

// ── Featured Room Card ────────────────────────────────────────────────────────
@Composable
fun FeaturedRoomCard(
    name: String,
    description: String,
    price: String,
    guests: Int,
    area: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier  = modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(12.dp),
        colors    = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column {
            // Room image placeholder (replace with AsyncImage + painter)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(Brush.linearGradient(listOf(Color(0xFF7B5E3A), Color(0xFF4A3520))))
            ) {
                // Price badge
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(OrangeAmber)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text("$price/noche", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }

            Column(modifier = Modifier.padding(14.dp)) {
                Text(text = name, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Text(text = description, fontSize = 13.sp, color = TextSecondary)
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Person, null, tint = TextSecondary, modifier = Modifier.size(16.dp))
                    Text(" $guests huéspedes", fontSize = 12.sp, color = TextSecondary)
                    Spacer(Modifier.width(12.dp))
                    Icon(Icons.Filled.SquareFoot, null, tint = TextSecondary, modifier = Modifier.size(16.dp))
                    Text(" ${area}m²", fontSize = 12.sp, color = TextSecondary)
                }
            }
        }
    }
}
