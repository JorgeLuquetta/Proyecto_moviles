package me.jorgeluquetta.proyecto_moviles


import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteMapScreen(
    navController: NavController,
    routeId: String,
    busViewModel: BusViewModel
) {
    val context = LocalContext.current
    val route = busViewModel.getRoute(routeId)
    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(route) {
        route?.firstOrNull()?.let { start ->
            cameraPositionState.position = CameraPosition.fromLatLngZoom(start, 13f)
        }
    }

    Box(Modifier.fillMaxSize()) {
        if (route != null) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                // 🔹 Línea de la ruta
                Polyline(
                    points = route,
                    color = Color.Blue,
                    width = 10f
                )

                // 🔹 Marcadores genéricos de Android
                Marker(
                    state = MarkerState(position = route.first()),
                    title = "Inicio (Punto A)",
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
                Marker(
                    state = MarkerState(position = route.last()),
                    title = "Final (Punto B)",
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                )
            }
        } else {
            Text(
                text = "Ruta no encontrada.",
                modifier = Modifier.align(Alignment.Center),
                color = Color.Gray
            )
        }

        // 🔙 Botón para volver
        FloatingActionButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp),
            containerColor = Color.White
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Atrás", tint = Color.Black)
        }
    }
}



fun normalizeRouteId(raw: String): String {
    return raw.trim().lowercase()
}


fun BusInfo(routeName: String): BusInfo? {
    return when (routeName.lowercase()) {
        "hamacas" -> BusInfo("Hamacas", "Carlos Pérez", "TMB-101", 40)
        "caracoli" -> BusInfo("Caracolí", "María Gómez", "TMB-202", 35)
        "cumbre" -> BusInfo("Cumbre", "Luis Díaz", "TMB-303", 30)
        else -> null
    }
}











