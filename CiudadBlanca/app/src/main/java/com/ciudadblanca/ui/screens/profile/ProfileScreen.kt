package com.ciudadblanca.ui.screens.profile

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

// ── Profile Screen (authenticated) ───────────────────────────────────────────
@Composable
fun ProfileScreen(
    userName: String,
    userEmail: String,
    onEditProfile: () -> Unit,
    onMyReservations: () -> Unit,
    onMyRequests: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceGray)
            .verticalScroll(rememberScrollState())
    ) {
        // Orange header with avatar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(OrangeAmber)
                .padding(bottom = 24.dp, top = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.25f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Person, null, tint = Color.White, modifier = Modifier.size(44.dp))
                }
                Spacer(Modifier.height(10.dp))
                Text(userName,  color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(userEmail, color = Color.White.copy(alpha = 0.85f), fontSize = 13.sp)
            }
        }

        Spacer(Modifier.height(12.dp))

        // Mi Actividad section
        ProfileSection(title = "Mi Actividad") {
            ProfileMenuItem(Icons.Filled.CalendarToday, "Mis Reservas",    onClick = onMyReservations)
            ProfileMenuItem(Icons.Filled.Assignment,    "Mis Solicitudes", onClick = onMyRequests)
        }

        Spacer(Modifier.height(10.dp))

        // Información Personal section
        ProfileSection(title = "Información Personal") {
            ProfileInfoRow(Icons.Filled.Email, "Email", userEmail)
            Divider(color = Color(0xFFEEEEEE))
            ProfileInfoRow(Icons.Filled.Phone, "Teléfono", "No especificado")
            Divider(color = Color(0xFFEEEEEE))
            ProfileInfoRow(Icons.Filled.LocationOn, "Dirección", "No especificado")
            Spacer(Modifier.height(12.dp))
            OrangeButton(
                text     = "Editar Datos",
                onClick  = onEditProfile,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(12.dp))
        }

        Spacer(Modifier.height(10.dp))

        // Preferences section
        ProfileSection(title = "Preferencias") {
            PreferenceRow(Icons.Filled.Notifications, "Notificaciones", "Activadas")
            Divider(color = Color(0xFFEEEEEE))
            PreferenceRow(Icons.Filled.Language,      "Idioma",         "Español")
        }

        Spacer(Modifier.height(10.dp))

        // Logout
        Card(
            modifier  = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
            shape     = RoundedCornerShape(12.dp),
            colors    = CardDefaults.cardColors(containerColor = SurfaceWhite),
            elevation = CardDefaults.cardElevation(1.dp)
        ) {
            TextButton(
                onClick  = onLogout,
                modifier = Modifier.fillMaxWidth().padding(4.dp)
            ) {
                Icon(Icons.Filled.Logout, null, tint = Color(0xFFE53935))
                Spacer(Modifier.width(8.dp))
                Text("Cerrar Sesión", color = Color(0xFFE53935), fontWeight = FontWeight.SemiBold)
            }
        }

        Spacer(Modifier.height(24.dp))
    }
}

// ── Guest Profile Screen ──────────────────────────────────────────────────────
@Composable
fun GuestProfileScreen(onLogin: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceWhite)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(OrangeAmber)
                .padding(horizontal = 20.dp, vertical = 18.dp)
        ) {
            Column {
                Text("Mi Perfil", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("Invitado",  color = Color.White.copy(alpha = 0.85f), fontSize = 13.sp)
            }
        }

        Column(
            modifier            = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(SurfaceGray),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Person, null, tint = TextSecondary, modifier = Modifier.size(50.dp))
            }
            Spacer(Modifier.height(16.dp))
            Text("Modo Invitado", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            Spacer(Modifier.height(6.dp))
            Text(
                "Inicia sesión para acceder a todas las funcionalidades",
                fontSize  = 14.sp,
                color     = TextSecondary,
                textAlign = TextAlign.Center,
                modifier  = Modifier.padding(horizontal = 40.dp)
            )
            Spacer(Modifier.height(24.dp))
            OrangeButton(
                text     = "Iniciar Sesión",
                onClick  = onLogin,
                modifier = Modifier.padding(horizontal = 48.dp)
            )
        }
    }
}

// ── Edit Profile Screen ───────────────────────────────────────────────────────
@Composable
fun EditProfileScreen(
    initialName: String,
    initialEmail: String,
    onBack: () -> Unit,
    onSave: () -> Unit
) {
    var name    by remember { mutableStateOf(initialName) }
    var email   by remember { mutableStateOf(initialEmail) }
    var phone   by remember { mutableStateOf("+1 (555) 123-4567") }
    var address by remember { mutableStateOf("") }

    Scaffold(
        topBar = { OrangeTopBar(title = "Editar Perfil", onBack = onBack) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SurfaceWhite)
                .padding(top = padding.calculateTopPadding())
                .verticalScroll(rememberScrollState())
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                EditField("Nombre Completo", name,    { name = it },    Icons.Filled.Person)
                Spacer(Modifier.height(16.dp))
                EditField("Email",           email,   { email = it },   Icons.Filled.Email,    readOnly = true)
                Spacer(Modifier.height(16.dp))
                EditField("Teléfono",        phone,   { phone = it },   Icons.Filled.Phone)
                Spacer(Modifier.height(16.dp))
                EditField("Dirección",       address, { address = it }, Icons.Filled.LocationOn, placeholder = "Tu dirección")
                Spacer(Modifier.height(28.dp))
                OrangeButton(text = "Guardar Cambios", onClick = onSave)
                Spacer(Modifier.height(12.dp))
                OutlinedButton(
                    onClick  = onBack,
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape    = RoundedCornerShape(8.dp)
                ) {
                    Text("Cancelar", color = TextPrimary)
                }
            }
        }
    }
}

// ── My Reservations Screen ────────────────────────────────────────────────────
@Composable
fun MyReservationsScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = { OrangeTopBar(title = "Mis Reservas", onBack = onBack) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SurfaceGray)
                .padding(top = padding.calculateTopPadding())
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(OrangeAmber.copy(alpha = 0.12f))
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Text("1 reservas registradas", color = OrangeAmberDark, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }

            // Sample reservation card
            ReservationCard(
                reservationId = "RES-1773246115955",
                roomName      = "Suite Junior",
                checkIn       = "2026-03-11",
                checkOut      = "2026-03-13",
                guests        = 4,
                total         = 500,
                status        = "Pendiente"
            )

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ReservationCard(
    reservationId: String,
    roomName: String,
    checkIn: String,
    checkOut: String,
    guests: Int,
    total: Int,
    status: String
) {
    Card(
        modifier  = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        shape     = RoundedCornerShape(12.dp),
        colors    = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier            = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment   = Alignment.CenterVertically
            ) {
                Text("Reserva $reservationId", fontSize = 11.sp, color = TextSecondary)
                StatusBadge(status)
            }
            Spacer(Modifier.height(8.dp))
            Text(roomName, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary)
            Spacer(Modifier.height(6.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.CalendarToday, null, tint = TextSecondary, modifier = Modifier.size(14.dp))
                Text(" $checkIn - $checkOut", fontSize = 13.sp, color = TextSecondary)
            }
            Spacer(Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.People, null, tint = TextSecondary, modifier = Modifier.size(14.dp))
                Text(" $guests huéspedes", fontSize = 13.sp, color = TextSecondary)
            }
            Divider(modifier = Modifier.padding(vertical = 10.dp), color = Color(0xFFEEEEEE))
            Row(
                modifier            = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Text("\$$total", fontWeight = FontWeight.Bold, color = OrangeAmber, fontSize = 17.sp)
            }
        }
    }
}

// ── My Requests Screen ────────────────────────────────────────────────────────
@Composable
fun MyRequestsScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = { OrangeTopBar(title = "Mis Solicitudes", onBack = onBack) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SurfaceGray)
                .padding(top = padding.calculateTopPadding())
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(OrangeAmber.copy(alpha = 0.12f))
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Text("1 solicitudes registradas", color = OrangeAmberDark, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }

            Card(
                modifier  = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                shape     = RoundedCornerShape(12.dp),
                colors    = CardDefaults.cardColors(containerColor = SurfaceWhite),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier            = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment   = Alignment.CenterVertically
                    ) {
                        Text("Solicitud SR-1773247008624", fontSize = 11.sp, color = TextSecondary)
                        StatusBadge("creada")
                    }
                    Spacer(Modifier.height(8.dp))
                    Text("Spa & Piscina", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary)
                    Spacer(Modifier.height(4.dp))
                    Text("quiero el servicio", fontSize = 13.sp, color = TextSecondary)
                    Spacer(Modifier.height(6.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.AccessTime, null, tint = TextSecondary, modifier = Modifier.size(14.dp))
                        Text(" 11/3/2026, 11:36:48", fontSize = 12.sp, color = TextSecondary)
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

// ── Reusable helpers ──────────────────────────────────────────────────────────
@Composable
private fun ProfileSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Card(
            modifier  = Modifier.fillMaxWidth(),
            shape     = RoundedCornerShape(12.dp),
            colors    = CardDefaults.cardColors(containerColor = SurfaceWhite),
            elevation = CardDefaults.cardElevation(1.dp)
        ) {
            Column(modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)) {
                Text(
                    text     = title,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize   = 15.sp,
                    color      = TextPrimary
                )
                content()
            }
        }
    }
}

@Composable
private fun ProfileMenuItem(icon: ImageVector, label: String, onClick: () -> Unit) {
    Row(
        modifier          = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, tint = OrangeAmber, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(10.dp))
            Text(label, fontSize = 14.sp, color = TextPrimary)
        }
        Icon(Icons.Filled.ChevronRight, null, tint = TextSecondary)
    }
}

@Composable
private fun ProfileInfoRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier          = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = TextSecondary, modifier = Modifier.size(18.dp))
        Spacer(Modifier.width(8.dp))
        Column {
            Text(label, fontSize = 10.sp, color = TextSecondary)
            Text(value, fontSize = 13.sp, color = TextPrimary)
        }
    }
}

@Composable
private fun PreferenceRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier            = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment   = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, tint = TextSecondary, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(10.dp))
            Text(label, fontSize = 14.sp, color = TextPrimary)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(value, fontSize = 13.sp, color = TextSecondary)
            Icon(Icons.Filled.ChevronRight, null, tint = TextSecondary)
        }
    }
}

@Composable
private fun EditField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: ImageVector,
    placeholder: String = label,
    readOnly: Boolean = false
) {
    Column {
        Text(label, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = TextPrimary)
        Spacer(Modifier.height(4.dp))
        OutlinedTextField(
            value         = value,
            onValueChange = onValueChange,
            placeholder   = { Text(placeholder, color = TextSecondary) },
            leadingIcon   = { Icon(icon, null, tint = TextSecondary, modifier = Modifier.size(20.dp)) },
            readOnly      = readOnly,
            modifier      = Modifier.fillMaxWidth(),
            shape         = RoundedCornerShape(8.dp),
            singleLine    = true,
            colors        = OutlinedTextFieldDefaults.colors(
                focusedBorderColor    = OrangeAmber,
                unfocusedBorderColor  = Color(0xFFDDDDDD),
                disabledBorderColor   = Color(0xFFDDDDDD)
            )
        )
    }
}
