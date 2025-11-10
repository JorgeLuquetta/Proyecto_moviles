package me.jorgeluquetta.proyecto_moviles

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth


@Composable
fun SettingsScreen(navController: NavController) {
    val currentRoute = "settings"
    var darkMode by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9F9))
            .padding(horizontal = 16.dp)
    ) {
        // Título
        Text(
            text = "Configuración",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF222222)
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, bottom = 24.dp)
        )

        // Contenedor principal de opciones
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(vertical = 4.dp)
        ) {
            SettingItem(
                icon = Icons.Default.Key,
                iconColor = Color(0xFFEDE430),
                text = "Cambiar contraseña",
                onClick = {
                    navController.navigate("change_password")
                }
            )
            Divider(thickness = 0.5.dp, color = Color(0xFFE0E0E0))

            SettingItem(
                icon = Icons.Default.TextFields,
                iconColor = Color(0xFFEDE430),
                text = "Tamaño de texto"
            )
            Divider(thickness = 0.5.dp, color = Color(0xFFE0E0E0))

            SettingSwitchItem(
                icon = Icons.Default.DarkMode,
                iconColor = Color(0xFFEDE430),
                text = "Modo oscuro",
                checked = darkMode,
                onCheckedChange = { darkMode = it }
            )
            Divider(thickness = 0.5.dp, color = Color(0xFFE0E0E0))

            SettingItem(
                icon = Icons.Default.Notifications,
                iconColor = Color(0xFFEDE430),
                text = "Notificaciones"
            )
            Divider(thickness = 0.5.dp, color = Color(0xFFE0E0E0))

            SettingItem(
                icon = Icons.Default.Security,
                iconColor = Color(0xFFEDE430),
                text = "Privacidad"
            )
            Divider(thickness = 0.5.dp, color = Color(0xFFE0E0E0))

            // NUEVA OPCIÓN: Cerrar sesión
            SettingItem(
                icon = Icons.Default.ExitToApp,
                iconColor = Color(0xFFE74C3C),
                text = "Cerrar sesión",
                onClick = {

                    // Aquí va tu lógica para cerrar sesión
                    // Por ejemplo, limpiar FirebaseAuth o SharedPreferences
                    navController.navigate("login") {
                        popUpTo(0) // Limpia el stack de navegación
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Barra inferior
        BottomBar(navController = navController, currentRoute = currentRoute)
    }
}

@Composable
fun SettingItem(
    icon: ImageVector,
    iconColor: Color,
    text: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 14.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = iconColor,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = "Go",
            tint = Color.Gray
        )
    }
}

@Composable
fun SettingSwitchItem(
    icon: ImageVector,
    iconColor: Color,
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = iconColor,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF1E1E1E)
            )
        )
    }
}
