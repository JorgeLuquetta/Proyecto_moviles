package me.jorgeluquetta.proyecto_moviles

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BottomBar(navController: NavController, currentRoute: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomIconItem(
            icon = Icons.Default.Map,
            label = "Mapa",
            color = if (currentRoute == "map") Color(0xFFE1131F) else Color.Gray
        ) {
            navController.navigate("map") {
                popUpTo("map") { inclusive = false }
                launchSingleTop = true
            }
        }

        BottomIconItem(
            icon = Icons.Default.Route,
            label = "Rutas",
            color = if (currentRoute == "route") Color(0xFFFFD700) else Color.Gray
        ) {
            navController.navigate("route") {
                popUpTo("map") { inclusive = false }
                launchSingleTop = true
            }
        }

        BottomIconItem(
            icon = Icons.Default.Settings,
            label = "Configuración",
            color = if (currentRoute == "settings") Color(0xFF0033CC) else Color.Gray
        ) {
            navController.navigate("settings") {
                popUpTo("map") { inclusive = false }
                launchSingleTop = true
            }
        }
    }
}

@Composable
fun BottomIconItem(
    icon: ImageVector,
    label: String,
    color: Color,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = color,
            modifier = Modifier.size(28.dp)
        )
        Text(
            text = label,
            color = Color.Black,
            style = MaterialTheme.typography.labelSmall
        )
    }
}
