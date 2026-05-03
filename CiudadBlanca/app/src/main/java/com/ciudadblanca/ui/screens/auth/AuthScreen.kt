package com.ciudadblanca.ui.screens.auth

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ciudadblanca.ui.components.OrangeButton
import com.ciudadblanca.ui.components.OutlinedGrayButton
import com.ciudadblanca.ui.theme.*

// ── Splash Screen ─────────────────────────────────────────────────────────────
@Composable
fun SplashScreen(onFinish: () -> Unit) {

    var alpha by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(Unit) {
        alpha = 1f
        kotlinx.coroutines.delay(1200)
        onFinish()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(OrangeLight, OrangeAmber, OrangeAmberDark)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.alpha(alpha)
        ) {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = "Ciudad Blanca",
                tint = Color.White,
                modifier = Modifier.size(80.dp)
            )

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Ciudad Blanca",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Popayán",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 18.sp
            )
        }
    }
}

// ── Auth Screen (Login + Register tabs) ──────────────────────────────────────
@Composable
fun AuthScreen(
    onLoginSuccess: () -> Unit,
    onGuestContinue: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(1) } // 0=Login 1=Register
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceWhite)
            .verticalScroll(scrollState)
    ) {
        // Orange header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(OrangeAmber)
                .padding(vertical = 28.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector        = Icons.Filled.Lock,
                    contentDescription = null,
                    tint               = Color.White,
                    modifier           = Modifier.size(40.dp)
                )
                Spacer(Modifier.height(8.dp))
                Text("Ciudad Blanca",  color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text("Bienvenido a Popayán", color = Color.White.copy(alpha = 0.85f), fontSize = 13.sp)
            }
        }

        // Tab switcher
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 20.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(SurfaceGray)
        ) {
            listOf("Iniciar Sesión", "Registrarse").forEachIndexed { index, label ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (selectedTab == index) OrangeAmber else Color.Transparent)
                        .clickable { selectedTab = index }
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text       = label,
                        color      = if (selectedTab == index) Color.White else TextSecondary,
                        fontWeight = if (selectedTab == index) FontWeight.SemiBold else FontWeight.Normal
                    )
                }
            }
        }

        // Form content
        Box(modifier = Modifier.padding(horizontal = 24.dp)) {
            if (selectedTab == 1) RegisterForm(onRegister = onLoginSuccess)
            else                  LoginForm(onLogin = onLoginSuccess)
        }

        Spacer(Modifier.height(16.dp))

        // Guest option
        OutlinedGrayButton(
            text     = "Continuar como Invitado",
            onClick  = onGuestContinue,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(Modifier.height(32.dp))
    }
}

// ── Register Form ─────────────────────────────────────────────────────────────
@Composable
private fun RegisterForm(onRegister: () -> Unit) {
    var name     by remember { mutableStateOf("") }
    var email    by remember { mutableStateOf("") }
    var phone    by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column {
        AuthTextField(value = name,     onValueChange = { name = it },     label = "Nombre Completo", placeholder = "Tu nombre",         icon = Icons.Filled.Person)
        Spacer(Modifier.height(12.dp))
        AuthTextField(value = email,    onValueChange = { email = it },    label = "Email",           placeholder = "tu@email.com",      icon = Icons.Filled.Mail, keyboardType = KeyboardType.Email)
        Spacer(Modifier.height(12.dp))
        AuthTextField(value = phone,    onValueChange = { phone = it },    label = "Teléfono (opcional)", placeholder = "+57 300 123 4567", icon = Icons.Filled.Phone, keyboardType = KeyboardType.Phone)
        Spacer(Modifier.height(12.dp))
        AuthTextField(value = password, onValueChange = { password = it }, label = "Contraseña",      placeholder = "••••••••",          icon = Icons.Filled.Lock, isPassword = true)
        Spacer(Modifier.height(24.dp))
        OrangeButton(text = "Registrarse", onClick = onRegister)
    }
}

// ── Login Form ────────────────────────────────────────────────────────────────
@Composable
private fun LoginForm(onLogin: () -> Unit) {
    var email    by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column {
        AuthTextField(value = email,    onValueChange = { email = it },    label = "Email",      placeholder = "tu@email.com", icon = Icons.Filled.Mail, keyboardType = KeyboardType.Email)
        Spacer(Modifier.height(12.dp))
        AuthTextField(value = password, onValueChange = { password = it }, label = "Contraseña", placeholder = "••••••••",     icon = Icons.Filled.Lock, isPassword = true)
        TextButton(
            onClick  = { /* TODO: forgot password */ },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("¿Olvidaste tu contraseña?", color = OrangeAmber, fontSize = 13.sp)
        }
        Spacer(Modifier.height(12.dp))
        OrangeButton(text = "Iniciar Sesión", onClick = onLogin)
    }
}

// ── Reusable Auth TextField ───────────────────────────────────────────────────
@Composable
private fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    icon: ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false
) {
    Column {
        Text(text = label, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = TextPrimary)
        Spacer(Modifier.height(4.dp))
        OutlinedTextField(
            value         = value,
            onValueChange = onValueChange,
            placeholder   = { Text(placeholder, color = TextSecondary) },
            leadingIcon   = { Icon(icon, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(20.dp)) },
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions      = KeyboardOptions(keyboardType = keyboardType),
            modifier             = Modifier.fillMaxWidth(),
            shape                = RoundedCornerShape(8.dp),
            singleLine           = true,
            colors               = OutlinedTextFieldDefaults.colors(
                focusedBorderColor   = OrangeAmber,
                unfocusedBorderColor = Color(0xFFDDDDDD)
            )
        )
    }
}
