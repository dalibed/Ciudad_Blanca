package com.ciudadblanca.ui.screens.guide

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
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
import com.ciudadblanca.ui.components.OrangeButton
import com.ciudadblanca.ui.components.OrangeTopBar
import com.ciudadblanca.ui.theme.*

// ── Data model ────────────────────────────────────────────────────────────────
data class Attraction(
    val id: Int,
    val name: String,
    val category: String,    // Iglesia | Histórico | Arquitectura | Gastronomía
    val description: String,
    val address: String,
    val schedule: String,
    val rating: Float,
    val distanceKm: Float,
    val walkMinutes: Int,
    val gradientColors: List<Color> = listOf(Color(0xFF5C7A5A), Color(0xFF3A5A38))
)

val attractionCategories = listOf("Todos", "Iglesia", "Histórico", "Arquitectura", "Gastronomía")

val sampleAttractions = listOf(
    Attraction(1, "Catedral Basílica Nuestra Señora de la Asunción", "Iglesia",
        "Hermosa catedral colonial en el corazón de Popayán",
        "Parque Caldas, Centro, Popayán", "Acceso libre las 24 horas", 4.8f, 0.5f, 5,
        listOf(Color(0xFF8B7355), Color(0xFF5C4A2A))),
    Attraction(2, "Centro Histórico de Popayán", "Arquitectura",
        "El Centro Histórico de Popayán, conocido como 'La Ciudad Blanca', es Patrimonio de la Humanidad por la UNESCO. Sus calles empedradas y casas coloniales te transportan al pasado.",
        "Parque Caldas, Centro, Popayán", "Acceso libre las 24 horas", 4.9f, 0.8f, 10,
        listOf(Color(0xFF5C7A5A), Color(0xFF3A5A38))),
    Attraction(3, "Puente del Humilladero", "Histórico",
        "Icónico puente colonial de arcos, símbolo de la ciudad blanca",
        "Calle 5 con Carrera 9, Centro", "Acceso libre", 4.6f, 1.0f, 12,
        listOf(Color(0xFF6B7A8D), Color(0xFF3A4A5A))),
    Attraction(4, "Gastronomía Caucana", "Gastronomía",
        "Descubre los sabores únicos de la región: pipián, champús y sopa de maní",
        "Mercado Central, Popayán", "Lunes a sábado 7am-6pm", 4.7f, 1.2f, 15,
        listOf(Color(0xFF8B5E3A), Color(0xFF5A3A20))),
)

// ── Guide Screen ──────────────────────────────────────────────────────────────
@Composable
fun GuideScreen(onAttractionClick: (Attraction) -> Unit) {
    var selectedCategory by remember { mutableStateOf("Todos") }

    val filtered = if (selectedCategory == "Todos") sampleAttractions
                   else sampleAttractions.filter { it.category == selectedCategory }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceWhite)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(OrangeAmber)
                .padding(horizontal = 20.dp, vertical = 18.dp)
        ) {
            Column {
                Text("Guía de Popayán", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("Descubre la ciudad blanca",  color = Color.White.copy(alpha = 0.85f), fontSize = 13.sp)
            }
        }

        // Category chips
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(attractionCategories) { cat ->
                val selected = cat == selectedCategory
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (selected) OrangeAmber else Color(0xFFEEEEEE))
                        .clickable { selectedCategory = cat }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text  = cat,
                        color = if (selected) Color.White else TextSecondary,
                        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                        fontSize = 13.sp
                    )
                }
            }
        }

        // Attractions list
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            filtered.forEach { attraction ->
                AttractionCard(attraction = attraction, onClick = { onAttractionClick(attraction) })
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

// ── Attraction Card ───────────────────────────────────────────────────────────
@Composable
fun AttractionCard(attraction: Attraction, onClick: () -> Unit) {
    Card(
        modifier  = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape     = RoundedCornerShape(12.dp),
        colors    = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column {
            // Image area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .background(Brush.linearGradient(attraction.gradientColors))
            ) {
                // Category tag
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(OrangeAmber.copy(alpha = 0.9f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(attraction.category, color = Color.White, fontSize = 11.sp)
                }
            }

            Column(modifier = Modifier.padding(14.dp)) {
                Text(attraction.name, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextPrimary, lineHeight = 18.sp)
                Spacer(Modifier.height(4.dp))
                Text(attraction.description.take(70) + "…", fontSize = 12.sp, color = TextSecondary, lineHeight = 16.sp)
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Star, null, tint = Color(0xFFFFC107), modifier = Modifier.size(14.dp))
                    Text(" ${attraction.rating}", fontSize = 12.sp, color = TextSecondary)
                    Spacer(Modifier.width(12.dp))
                    Icon(Icons.Filled.LocationOn, null, tint = TextSecondary, modifier = Modifier.size(14.dp))
                    Text(" ${attraction.distanceKm}km", fontSize = 12.sp, color = TextSecondary)
                    Spacer(Modifier.width(12.dp))
                    Icon(Icons.Filled.DirectionsWalk, null, tint = TextSecondary, modifier = Modifier.size(14.dp))
                    Text(" ${attraction.walkMinutes} min caminando", fontSize = 12.sp, color = TextSecondary)
                }
            }
        }
    }
}

// ── Attraction Detail Screen ──────────────────────────────────────────────────
@Composable
fun AttractionDetailScreen(
    attraction: Attraction,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = { OrangeTopBar(title = "", onBack = onBack) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SurfaceWhite)
                .verticalScroll(scrollState)
                .padding(top = padding.calculateTopPadding())
        ) {
            // Hero image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(Brush.linearGradient(attraction.gradientColors))
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(OrangeAmber.copy(alpha = 0.9f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(attraction.category, color = Color.White, fontSize = 12.sp)
                }
            }

            Column(modifier = Modifier.padding(20.dp)) {
                Text(attraction.name, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary, lineHeight = 24.sp)
                Spacer(Modifier.height(6.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Star, null, tint = Color(0xFFFFC107), modifier = Modifier.size(16.dp))
                    Text(" ${attraction.rating}", fontSize = 13.sp, color = TextSecondary)
                    Spacer(Modifier.width(14.dp))
                    Icon(Icons.Filled.LocationOn, null, tint = TextSecondary, modifier = Modifier.size(14.dp))
                    Text(" ${attraction.distanceKm}km", fontSize = 13.sp, color = TextSecondary)
                    Spacer(Modifier.width(14.dp))
                    Icon(Icons.Filled.DirectionsWalk, null, tint = TextSecondary, modifier = Modifier.size(14.dp))
                    Text(" ${attraction.walkMinutes} min caminando", fontSize = 13.sp, color = TextSecondary)
                }

                Divider(modifier = Modifier.padding(vertical = 16.dp), color = Color(0xFFEEEEEE))

                Text(attraction.description, fontSize = 14.sp, color = TextSecondary, lineHeight = 21.sp)

                Spacer(Modifier.height(20.dp))

                // Address
                DetailInfoRow(Icons.Filled.LocationOn, "Dirección", attraction.address)
                Spacer(Modifier.height(12.dp))
                DetailInfoRow(Icons.Filled.AccessTime,  "Horarios",   attraction.schedule)

                Spacer(Modifier.height(28.dp))
                OrangeButton(
                    text    = "📍  Cómo Llegar",
                    onClick = { /* TODO: open maps */ }
                )
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun DetailInfoRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(verticalAlignment = Alignment.Top) {
        Icon(icon, null, tint = OrangeAmber, modifier = Modifier.size(18.dp))
        Spacer(Modifier.width(8.dp))
        Column {
            Text(label, fontSize = 11.sp, color = TextSecondary)
            Text(value, fontSize = 14.sp, color = TextPrimary, fontWeight = FontWeight.Medium)
        }
    }
}
