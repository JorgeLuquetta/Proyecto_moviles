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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun TextSizeScreen(navController: NavController) {
    var selectedSize by remember { mutableStateOf("Mediano") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Tamaño del texto",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = Color(0xFF222222)
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextSizeOption("Pequeño", selectedSize) { selectedSize = "Pequeño" }
        TextSizeOption("Mediano", selectedSize) { selectedSize = "Mediano" }
        TextSizeOption("Grande", selectedSize) { selectedSize = "Grande" }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Vista previa del texto",
            fontSize = when (selectedSize) {
                "Pequeño" -> 12.sp
                "Mediano" -> 16.sp
                "Grande" -> 20.sp
                else -> 16.sp
            },
            color = Color.DarkGray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        androidx.compose.material3.Button(
            onClick = { navController.popBackStack() },
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Guardar", fontSize = 16.sp)
        }
    }
}

@Composable
fun TextSizeOption(
    label: String,
    selected: String,
    onClick: () -> Unit
) {
    val isSelected = label == selected
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(
                color = if (isSelected) Color(0xFFEDE430).copy(alpha = 0.2f) else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = if (isSelected) Color(0xFF222222) else Color.Gray,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.weight(1f)
        )
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color(0xFFEDE430)
            )
        }
    }
}