package me.jorgeluquetta.proyecto_moviles

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

// 🔹 Modelo de datos
data class RouteItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val description: String,
    val color: Color
)

// 🔹 Pantalla de Rutas
@Composable
fun RouteScreen(navController: NavHostController, busViewModel: BusViewModel ) {
    val currentRoute = "route"
    var searchQuery by remember { mutableStateOf("") }

    // Lista de rutas
    val routes = listOf(
        RouteItem(
            id = "hamacas",
            title = "HAMACAS",
            subtitle = "CARRERA 33 • C.C. CACIQUE",
            description = "CAMPANAZO • REPOSO",
            color = Color(0xFFCF1A17) // rojo
        ),
        RouteItem(
            id = "caracoli",
            title = "CARACOLÍ",
            subtitle = "CARRETERA ANTIGUA",
            description = "CARRERA 33 • CENTRO",
            color = Color(0xFFEDE430) // amarillo
        ),
        RouteItem(
            id = "cumbre",
            title = "CUMBRE",
            subtitle = "CARRERA 33 • MEGAMALL",
            description = "ESTADIO • SAN FRANCISCO • SANTOTO",
            color = Color(0xFFCF1A17) // rojo
        )
    )

    // Filtrar rutas según la búsqueda
    val filteredRoutes = routes.filter {
        it.title.contains(searchQuery, ignoreCase = true) ||
                it.subtitle.contains(searchQuery, ignoreCase = true) ||
                it.description.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        // --- Título ---
        Text(
            text = "Route",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp)
        )

        // --- Barra de búsqueda ---
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search route") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Buscar")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // --- Lista de rutas ---
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        ) {
            items(filteredRoutes) { route ->
                RouteItemCard(route = route) { routeId ->
                    navController.navigate("route_map/$routeId")



                }
            }
        }

        // --- Barra inferior ---
        BottomBar(navController = navController, currentRoute = currentRoute)
    }
}

// 🔹 Tarjeta individual de ruta
@Composable
fun RouteItemCard(
    route: RouteItem,
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick(route.id) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.background(Color.White)) {
            // --- Encabezado del cartel ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(route.color)
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "CARTEL DE RUTA IDA",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "MoveBGA",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            // --- Detalle de la ruta ---
            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.DirectionsBus,
                    contentDescription = "Bus",
                    tint = route.color,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 12.dp)
                )

                Column {
                    Text(
                        text = route.title,
                        color = route.color,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = route.subtitle,
                        color = route.color,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = route.description,
                        color = route.color,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
