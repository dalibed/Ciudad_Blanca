package com.ciudadblanca.ui.screens.reservation

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ciudadblanca.ui.components.OrangeButton
import com.ciudadblanca.ui.components.OrangeTopBar
import com.ciudadblanca.ui.theme.*
import com.ciudadblanca.ui.screens.rooms.Room
import com.ciudadblanca.ui.screens.rooms.sampleRooms

// ── Reservation State ─────────────────────────────────────────────────────────
data class ReservationState(
    val checkIn: String  = "",
    val checkOut: String = "",
    val adults: Int      = 1,
    val children: Int    = 0,
    val selectedRoom: Room? = null
)

// ── Stepper indicator ─────────────────────────────────────────────────────────
@Composable
fun ReservationStepper(currentStep: Int) {
    val steps = listOf("Fechas", "Huéspedes", "Habitación", "Resumen")
    Row(
        modifier            = Modifier
            .fillMaxWidth()
            .background(OrangeAmber)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment   = Alignment.CenterVertically
    ) {
        steps.forEachIndexed { index, label ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(if (index <= currentStep) Color.White else Color.White.copy(alpha = 0.35f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text  = "${index + 1}",
                        color = if (index <= currentStep) OrangeAmber else Color.White.copy(alpha = 0.6f),
                        fontSize   = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                if (index < steps.lastIndex) {
                    Box(
                        modifier = Modifier
                            .width(20.dp)
                            .height(2.dp)
                            .background(Color.White.copy(alpha = 0.4f))
                    )
                }
            }
        }
    }
}

// ── Step 1 – Dates ────────────────────────────────────────────────────────────
@Composable
fun ReservationDatesScreen(
    state: ReservationState,
    onStateChange: (ReservationState) -> Unit,
    onBack: () -> Unit,
    onContinue: () -> Unit
) {
    Scaffold(
        topBar = { OrangeTopBar(title = "Nueva Reserva", onBack = onBack) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SurfaceWhite)
                .padding(top = padding.calculateTopPadding())
        ) {
            ReservationStepper(currentStep = 0)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                Text("Selecciona las fechas", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Spacer(Modifier.height(20.dp))

                ReservationTextField(
                    label = "Fecha de entrada",
                    value = state.checkIn,
                    placeholder = "dd/mm/aaaa",
                    icon = Icons.Filled.CalendarToday,
                    onValueChange = { onStateChange(state.copy(checkIn = it)) }
                )
                Spacer(Modifier.height(16.dp))
                ReservationTextField(
                    label = "Fecha de salida",
                    value = state.checkOut,
                    placeholder = "dd/mm/aaaa",
                    icon = Icons.Filled.CalendarToday,
                    onValueChange = { onStateChange(state.copy(checkOut = it)) }
                )

                Spacer(Modifier.weight(1f))
                OrangeButton(
                    text    = "Continuar",
                    onClick = onContinue,
                    enabled = state.checkIn.isNotBlank() && state.checkOut.isNotBlank()
                )
            }
        }
    }
}

// ── Step 2 – Guests ───────────────────────────────────────────────────────────
@Composable
fun ReservationGuestsScreen(
    state: ReservationState,
    onStateChange: (ReservationState) -> Unit,
    onBack: () -> Unit,
    onContinue: () -> Unit
) {
    Scaffold(
        topBar = { OrangeTopBar(title = "Nueva Reserva", onBack = onBack) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SurfaceWhite)
                .padding(top = padding.calculateTopPadding())
        ) {
            ReservationStepper(currentStep = 1)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                Text("¿Cuántos huéspedes?", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Spacer(Modifier.height(24.dp))

                GuestCounter(
                    label    = "Adultos",
                    value    = state.adults,
                    onMinus  = { if (state.adults > 1) onStateChange(state.copy(adults = state.adults - 1)) },
                    onPlus   = { onStateChange(state.copy(adults = state.adults + 1)) }
                )
                Spacer(Modifier.height(16.dp))
                GuestCounter(
                    label    = "Niños",
                    value    = state.children,
                    onMinus  = { if (state.children > 0) onStateChange(state.copy(children = state.children - 1)) },
                    onPlus   = { onStateChange(state.copy(children = state.children + 1)) }
                )

                Spacer(Modifier.weight(1f))
                OrangeButton(text = "Continuar", onClick = onContinue)
            }
        }
    }
}

// ── Step 3 – Room Selection ───────────────────────────────────────────────────
@Composable
fun ReservationRoomScreen(
    state: ReservationState,
    onStateChange: (ReservationState) -> Unit,
    onBack: () -> Unit,
    onContinue: () -> Unit
) {
    val availableRooms = sampleRooms.filter { it.status == "disponible" }

    Scaffold(
        topBar = { OrangeTopBar(title = "Nueva Reserva", onBack = onBack) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SurfaceWhite)
                .padding(top = padding.calculateTopPadding())
        ) {
            ReservationStepper(currentStep = 2)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(Modifier.height(16.dp))
                Text("Selecciona una habitación", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Spacer(Modifier.height(12.dp))

                availableRooms.forEach { room ->
                    val isSelected = state.selectedRoom?.id == room.id
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable { onStateChange(state.copy(selectedRoom = room)) }
                            .then(
                                if (isSelected) Modifier.border(2.dp, OrangeAmber, RoundedCornerShape(size = 12.dp))
                                else Modifier
                            ),
                        shape     = RoundedCornerShape(12.dp),
                        colors    = CardDefaults.cardColors(containerColor = if (isSelected) OrangeAmber.copy(alpha = 0.06f) else SurfaceWhite),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Row(
                            modifier            = Modifier.padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment   = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(room.name, fontWeight = FontWeight.Bold, color = TextPrimary)
                                Text(room.category, fontSize = 12.sp, color = TextSecondary)
                                Spacer(Modifier.height(4.dp))
                                Row {
                                    Icon(Icons.Filled.Person, null, tint = TextSecondary, modifier = Modifier.size(14.dp))
                                    Text(" ${room.maxGuests} personas", fontSize = 12.sp, color = TextSecondary)
                                    Spacer(Modifier.width(8.dp))
                                    Icon(Icons.Filled.SquareFoot, null, tint = TextSecondary, modifier = Modifier.size(14.dp))
                                    Text(" ${room.areaSqm}m²", fontSize = 12.sp, color = TextSecondary)
                                }
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("\$${room.pricePerNight}", fontWeight = FontWeight.Bold, color = OrangeAmber, fontSize = 18.sp)
                                Text("por noche", fontSize = 11.sp, color = TextSecondary)
                            }
                        }
                    }
                }

                Spacer(Modifier.weight(1f))
                Spacer(Modifier.height(16.dp))
                OrangeButton(
                    text    = "Continuar",
                    onClick = onContinue,
                    enabled = state.selectedRoom != null
                )
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

// ── Step 4 – Summary & Confirm ────────────────────────────────────────────────
@Composable
fun ReservationSummaryScreen(
    state: ReservationState,
    onBack: () -> Unit,
    onConfirm: () -> Unit
) {
    val room  = state.selectedRoom ?: return
    val nights = 2 // TODO: compute from dates
    val total  = room.pricePerNight * nights

    Scaffold(
        topBar = { OrangeTopBar(title = "Nueva Reserva", onBack = onBack) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SurfaceWhite)
                .padding(top = padding.calculateTopPadding())
        ) {
            ReservationStepper(currentStep = 3)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                Text("Resumen de reserva", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Spacer(Modifier.height(16.dp))

                Card(
                    modifier  = Modifier.fillMaxWidth(),
                    shape     = RoundedCornerShape(12.dp),
                    colors    = CardDefaults.cardColors(containerColor = SurfaceGray),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        SummaryRow(Icons.Filled.CalendarToday, "Fechas", "${state.checkIn} - ${state.checkOut}")
                        Spacer(Modifier.height(10.dp))
                        SummaryRow(Icons.Filled.People, "Huéspedes",
                            "${state.adults + state.children} personas (${state.adults} adultos, ${state.children} niños)")
                        Spacer(Modifier.height(10.dp))
                        SummaryRow(Icons.Filled.Bed, "Habitación", room.name)
                    }
                }

                Spacer(Modifier.height(16.dp))
                Divider(color = Color(0xFFEEEEEE))
                Spacer(Modifier.height(12.dp))

                Row(
                    modifier            = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Precio por noche", color = TextSecondary, fontSize = 14.sp)
                    Text("\$${room.pricePerNight}", color = TextPrimary, fontWeight = FontWeight.Medium)
                }
                Spacer(Modifier.height(6.dp))
                Row(
                    modifier            = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("\$$total", fontWeight = FontWeight.Bold, color = OrangeAmber, fontSize = 18.sp)
                }

                Spacer(Modifier.weight(1f))
                OrangeButton(text = "✓  Confirmar Reserva", onClick = onConfirm)
            }
        }
    }
}

// ── Helper composables ────────────────────────────────────────────────────────
@Composable
private fun ReservationTextField(
    label: String,
    value: String,
    placeholder: String,
    icon: ImageVector,
    onValueChange: (String) -> Unit
) {
    Column {
        Text(label, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = TextPrimary)
        Spacer(Modifier.height(4.dp))
        OutlinedTextField(
            value         = value,
            onValueChange = onValueChange,
            placeholder   = { Text(placeholder, color = TextSecondary) },
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

@Composable
private fun GuestCounter(
    label: String,
    value: Int,
    onMinus: () -> Unit,
    onPlus: () -> Unit
) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        shape    = RoundedCornerShape(10.dp),
        border   = BorderStroke(1.dp, Color(0xFFDDDDDD))
    ) {
        Row(
            modifier            = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment   = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label, fontSize = 16.sp, color = TextPrimary, fontWeight = FontWeight.Medium)
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick  = onMinus,
                    modifier = Modifier
                        .size(34.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(SurfaceGray)
                ) {
                    Icon(Icons.Filled.Remove, null, tint = TextPrimary)
                }
                Text(
                    text     = "$value",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick  = onPlus,
                    modifier = Modifier
                        .size(34.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(OrangeAmber)
                ) {
                    Icon(Icons.Filled.Add, null, tint = Color.White)
                }
            }
        }
    }
}

@Composable
private fun SummaryRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(verticalAlignment = Alignment.Top) {
        Icon(icon, null, tint = TextSecondary, modifier = Modifier.size(18.dp))
        Spacer(Modifier.width(8.dp))
        Column {
            Text(label, fontSize = 11.sp, color = TextSecondary)
            Text(value, fontSize = 14.sp, color = TextPrimary, fontWeight = FontWeight.Medium)
        }
    }
}
