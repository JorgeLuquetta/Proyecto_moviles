package me.jorgeluquetta.proyecto_moviles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun SettingsScreen(navController: NavController) {
    val currentRoute = "settings"

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        BottomBar(navController = navController, currentRoute = currentRoute)
    }
}
