package com.ciudadblanca.ui.screens.services

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ciudadblanca.ui.components.OrangeButton
import com.ciudadblanca.ui.components.OrangeTopBar
import com.ciudadblanca.ui.components.StatusBadge
import com.ciudadblanca.ui.theme.*

// ── Data model ────────────────────────────────────────────────────────────────
data class HotelService(
    val id: Int,
    val name: String,
    val description: String,
    val icon: ImageVector,
    val status: String   // "disponible"
)

val hotelServices = listOf(
    HotelService(1, "Spa & Piscina",  "Relájate en nuestro spa de lujo con piscina climatizada", Icons.Filled.Waves,      "disponible"),
    HotelService(2, "Restaurante",    "Cocina gourmet internacional y local",                     Icons.Filled.Restaurant,  "disponible"),
    HotelService(3, "Gimnasio",       "Centro fitness 24/7 con equipamiento profesional",          Icons.Filled.FitnessCenter,"disponible"),
    HotelService(4, "Bar Lounge",     "Cócteles premium y vinos selectos",                        Icons.Filled.LocalBar,    "disponible"),
    HotelService(5, "Transporte",     "Traslado privado al aeropuerto",                           Icons.Filled.DirectionsCar,"disponible"),
)

// ── Services List Screen ──────────────────────────────────────────────────────
@Composable
fun ServicesScreen(
    currentRoute: String,
    onServiceClick: (HotelService) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceWhite)
    ) {
        // Orange header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(OrangeAmber)
                .padding(horizontal = 20.dp, vertical = 18.dp)
        ) {
            Column {
                Text("Servicios", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("Disfruta de nuestras instalaciones", color = Color.White.copy(alpha = 0.85f), fontSize = 13.sp)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            hotelServices.forEach { service ->
                ServiceCard(service = service, onClick = { onServiceClick(service) })
            }
        }
    }
}

// ── Service Card ──────────────────────────────────────────────────────────────
@Composable
private fun ServiceCard(service: HotelService, onClick: () -> Unit) {
    Card(
        modifier  = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape     = RoundedCornerShape(12.dp),
        colors    = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier          = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(OrangeAmber.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(service.icon, null, tint = OrangeAmber, modifier = Modifier.size(26.dp))
            }
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(service.name, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextPrimary)
                    StatusBadge(service.status)
                }
                Spacer(Modifier.height(3.dp))
                Text(service.description, fontSize = 12.sp, color = TextSecondary, lineHeight = 16.sp)
            }
        }
    }
}

// ── Service Request Form ──────────────────────────────────────────────────────
@Composable
fun ServiceRequestScreen(
    service: HotelService,
    onBack: () -> Unit,
    onSubmit: () -> Unit
) {
    var date    by remember { mutableStateOf("11/03/2026") }
    var time    by remember { mutableStateOf("08:30 a.m.") }
    var details by remember { mutableStateOf("") }

    Scaffold(
        topBar = { OrangeTopBar(title = service.name, onBack = onBack) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SurfaceWhite)
                .padding(top = padding.calculateTopPadding())
                .verticalScroll(rememberScrollState())
        ) {
            // Service status banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(OrangeAmber.copy(alpha = 0.08f))
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(service.icon, null, tint = OrangeAmber, modifier = Modifier.size(22.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(service.name, fontWeight = FontWeight.Bold, color = OrangeAmber)
                    Spacer(Modifier.width(8.dp))
                    StatusBadge(service.status)
                }
            }

            Column(modifier = Modifier.padding(24.dp)) {
                ServiceFormField(label = "Fecha *", value = date, onValueChange = { date = it },
                    icon = Icons.Filled.CalendarToday)
                Spacer(Modifier.height(16.dp))
                ServiceFormField(label = "Hora *", value = time, onValueChange = { time = it },
                    icon = Icons.Filled.AccessTime)
                Spacer(Modifier.height(16.dp))

                Text("Detalles Adicionales", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = TextPrimary)
                Spacer(Modifier.height(4.dp))
                OutlinedTextField(
                    value         = details,
                    onValueChange = { details = it },
                    placeholder   = { Text("solicito este servicio", color = TextSecondary) },
                    modifier      = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    shape         = RoundedCornerShape(8.dp),
                    maxLines      = 5,
                    colors        = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor   = OrangeAmber,
                        unfocusedBorderColor = Color(0xFFDDDDDD)
                    )
                )

                Spacer(Modifier.height(28.dp))
                OrangeButton(text = "Enviar Solicitud", onClick = onSubmit)
            }
        }
    }
}

// ── Service Success Screen ────────────────────────────────────────────────────
@Composable
fun ServiceSuccessScreen(
    serviceName: String,
    date: String,
    time: String,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceWhite)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Success icon
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color(0xFF4CAF50)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.Check, null, tint = Color.White, modifier = Modifier.size(44.dp))
        }
        Spacer(Modifier.height(20.dp))
        Text("¡Solicitud Enviada!", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Spacer(Modifier.height(6.dp))
        Text(
            "Tu solicitud de servicio ha sido recibida. Te contactaremos pronto.",
            fontSize = 14.sp,
            color    = TextSecondary,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(28.dp))

        Card(
            modifier  = Modifier.fillMaxWidth(),
            shape     = RoundedCornerShape(12.dp),
            colors    = CardDefaults.cardColors(containerColor = SurfaceGray),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Detalles de tu solicitud", fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                Spacer(Modifier.height(10.dp))
                SuccessDetailRow("Servicio:", serviceName)
                SuccessDetailRow("Fecha:", date)
                SuccessDetailRow("Hora:", time)
            }
        }

        Spacer(Modifier.height(28.dp))
        OrangeButton(text = "Volver a Servicios", onClick = onBack)
    }
}

@Composable
private fun SuccessDetailRow(label: String, value: String) {
    Row(modifier = Modifier.padding(vertical = 3.dp)) {
        Text(label, color = TextSecondary, fontSize = 13.sp, modifier = Modifier.width(80.dp))
        Text(value, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun ServiceFormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: ImageVector
) {
    Column {
        Text(label, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = TextPrimary)
        Spacer(Modifier.height(4.dp))
        OutlinedTextField(
            value         = value,
            onValueChange = onValueChange,
            leadingIcon   = { Icon(icon, null, tint = TextSecondary, modifier = Modifier.size(20.dp)) },
            modifier      = Modifier.fillMaxWidth(),
            shape         = RoundedCornerShape(8.dp),
            singleLine    = true,
            colors        = OutlinedTextFieldDefaults.colors(
                focusedBorderColor   = OrangeAmber,
                unfocusedBorderColor = Color(0xFFDDDDDD)
            )
        )
    }
}
