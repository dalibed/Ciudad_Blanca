package com.ciudadblanca.ui.screens.rooms

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ciudadblanca.ui.components.AmenityChip
import com.ciudadblanca.ui.components.OrangeButton
import com.ciudadblanca.ui.components.OrangeTopBar
import com.ciudadblanca.ui.theme.*

// ── Data model ────────────────────────────────────────────────────────────────
data class Room(
    val id: Int,
    val name: String,
    val category: String,
    val description: String,
    val pricePerNight: Int,
    val maxGuests: Int,
    val areaSqm: Int,
    val status: String,          // "disponible" | "ocupada"
    val amenities: List<Pair<ImageVector, String>>
)

val sampleRooms = listOf(
    Room(1, "Habitación Deluxe",    "Deluxe", "Habitación elegante con vista a la ciudad",   150, 2, 35, "disponible",
        listOf(Icons.Filled.Wifi to "WiFi", Icons.Filled.Tv to "TV", Icons.Filled.AcUnit to "Aire acondicionado", Icons.Filled.LocalBar to "Minibar", Icons.Filled.Security to "Caja fuerte")),
    Room(2, "Suite Junior",         "Suite",  "Suite junior con sala de estar",               250, 3, 50, "disponible",
        listOf(Icons.Filled.Wifi to "WiFi", Icons.Filled.Tv to "TV", Icons.Filled.AcUnit to "Aire acondicionado", Icons.Filled.LocalBar to "Minibar")),
    Room(3, "Habitación Estándar",  "Estándar","Habitación cómoda y acogedora",              100, 2, 25, "ocupada",
        listOf(Icons.Filled.Wifi to "WiFi", Icons.Filled.Tv to "TV", Icons.Filled.AcUnit to "Aire acondicionado")),
    Room(4, "Suite Presidencial",   "Suite",  "La suite más exclusiva del hotel",             400, 4, 80, "disponible",
        listOf(Icons.Filled.Wifi to "WiFi", Icons.Filled.Tv to "TV", Icons.Filled.AcUnit to "Aire acondicionado", Icons.Filled.LocalBar to "Minibar", Icons.Filled.Security to "Caja fuerte")),
)

// ── Rooms List Screen ─────────────────────────────────────────────────────────
@Composable
fun RoomsScreen(
    onBack: () -> Unit,
    onRoomClick: (Room) -> Unit
) {
    var showFilters by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { OrangeTopBar(title = "Habitaciones", onBack = onBack) }
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(
                start  = 16.dp,
                end    = 16.dp,
                top    = padding.calculateTopPadding() + 8.dp,
                bottom = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                // Header stat
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(OrangeAmber)
                        .padding(16.dp)
                ) {
                    Column {
                        Text("Habitaciones", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("${sampleRooms.count { it.status == "disponible" }} habitaciones disponibles",
                            color = Color.White.copy(alpha = 0.9f), fontSize = 13.sp)
                    }
                }
                Spacer(Modifier.height(4.dp))
                // Filter row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { showFilters = !showFilters }) {
                        Icon(Icons.Filled.FilterList, null, tint = OrangeAmber, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Filtros", color = OrangeAmber)
                    }
                }
            }

            items(sampleRooms) { room ->
                RoomListCard(room = room, onClick = { onRoomClick(room) })
            }
        }
    }
}

// ── Room List Card ────────────────────────────────────────────────────────────
@Composable
fun RoomListCard(room: Room, onClick: () -> Unit) {
    Card(
        modifier  = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape     = RoundedCornerShape(12.dp),
        colors    = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column {
            // Image placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
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
                    Text("\$${room.pricePerNight}/noche", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
                // Occupied badge
                if (room.status == "ocupada") {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(10.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFFE53935))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text("Ocupada", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Column(modifier = Modifier.padding(14.dp)) {
                Text(room.name, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Text(room.description, fontSize = 13.sp, color = TextSecondary)
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Person,    null, tint = TextSecondary, modifier = Modifier.size(15.dp))
                    Text(" ${room.maxGuests} huéspedes", fontSize = 12.sp, color = TextSecondary)
                    Spacer(Modifier.width(12.dp))
                    Icon(Icons.Filled.SquareFoot, null, tint = TextSecondary, modifier = Modifier.size(15.dp))
                    Text(" ${room.areaSqm}m²", fontSize = 12.sp, color = TextSecondary)
                }
                if (room.status == "disponible") {
                    Spacer(Modifier.height(12.dp))
                    Button(
                        onClick = onClick,
                        modifier = Modifier.fillMaxWidth().height(44.dp),
                        shape  = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = OrangeAmber)
                    ) {
                        Text("Ver Detalles", color = Color.White, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

// ── Room Detail Screen ────────────────────────────────────────────────────────
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RoomDetailScreen(
    room: Room,
    onBack: () -> Unit,
    onReserve: (Room) -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = { OrangeTopBar(title = room.name, onBack = onBack) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SurfaceWhite)
                .verticalScroll(scrollState)
                .padding(top = padding.calculateTopPadding())
        ) {
            // Room image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(Brush.linearGradient(listOf(Color(0xFF7B5E3A), Color(0xFF4A3520))))
            )

            Column(modifier = Modifier.padding(20.dp)) {
                // Title + price row
                Row(
                    modifier            = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment   = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(room.name, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        Text(room.category, fontSize = 13.sp, color = TextSecondary)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("\$${room.pricePerNight}", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = OrangeAmber)
                        Text("por noche", fontSize = 12.sp, color = TextSecondary)
                    }
                }

                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Person,    null, tint = TextSecondary, modifier = Modifier.size(16.dp))
                    Text(" ${room.maxGuests} huéspedes", fontSize = 13.sp, color = TextSecondary)
                    Spacer(Modifier.width(16.dp))
                    Icon(Icons.Filled.SquareFoot, null, tint = TextSecondary, modifier = Modifier.size(16.dp))
                    Text(" ${room.areaSqm}m²", fontSize = 13.sp, color = TextSecondary)
                }

                Divider(modifier = Modifier.padding(vertical = 16.dp), color = Color(0xFFEEEEEE))

                // Description
                Text("Descripción", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Spacer(Modifier.height(6.dp))
                Text(room.description, fontSize = 14.sp, color = TextSecondary, lineHeight = 20.sp)

                Divider(modifier = Modifier.padding(vertical = 16.dp), color = Color(0xFFEEEEEE))

                // Amenities
                Text("Amenities", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Spacer(Modifier.height(10.dp))
                FlowRow(
                    modifier            = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    room.amenities.forEach { (icon, label) ->
                        AmenityChip(icon = icon, label = label)
                    }
                }

                Divider(modifier = Modifier.padding(vertical = 16.dp), color = Color(0xFFEEEEEE))

                // Availability
                if (room.status == "disponible") {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.CheckCircle, null, tint = Color(0xFF4CAF50), modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Disponible para reservar", color = Color(0xFF4CAF50), fontWeight = FontWeight.Medium)
                    }
                    Spacer(Modifier.height(16.dp))
                    OrangeButton(text = "Reservar ahora", onClick = { onReserve(room) })
                } else {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Cancel, null, tint = Color(0xFFE53935), modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Habitación ocupada", color = Color(0xFFE53935), fontWeight = FontWeight.Medium)
                    }
                }

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}
