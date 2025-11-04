package me.jorgeluquetta.proyecto_moviles


import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.delay


@SuppressLint("MissingPermission")
@Composable
fun MapScreen(navController: NavController, busViewModel: BusViewModel) {
    val context = LocalContext.current

    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val cameraPositionState = rememberCameraPositionState()
    val busIcon = remember { bitmapDescriptorFromVector(context, R.drawable.ic_bus) }

    // Estado del ViewModel
    val busPositions by busViewModel.busPositions.collectAsState()

    // Estado de ubicación del usuario
    var userLocation by remember { mutableStateOf<LatLng?>(null) }

    // Mostrar info del bus seleccionado
    var selectedBusInfo by remember { mutableStateOf<BusInfo?>(null) }

    // Obtener ubicación actual una sola vez
    LaunchedEffect(Unit) {
        try {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    val latLng = LatLng(it.latitude, it.longitude)
                    userLocation = latLng
                    // Centrar cámara en el usuario
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(latLng, 15f)
                }
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    Scaffold(
        bottomBar = { BottomBar(navController = navController, currentRoute = "map") }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(
                    mapType = MapType.NORMAL,
                    isMyLocationEnabled = userLocation != null // Muestra el punto azul del usuario
                ),
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = false,
                    myLocationButtonEnabled = true,
                    compassEnabled = true
                )
            ) {
                // Mostrar buses
                busPositions.forEach { (routeKey, position) ->
                    val info = busViewModel.getBusInfo(routeKey)
                    if (busIcon != null && info != null) {
                        Marker(
                            state = MarkerState(position = position),
                            title = info.nombreRuta,
                            snippet = "Conductor: ${info.conductor}\nPlaca: ${info.placa}\nCapacidad: ${info.pasajeros} pasajeros",
                            icon = busIcon,
                            onClick = {
                                selectedBusInfo = info
                                false
                            }
                        )
                    }
                }
            }

            // Información del bus seleccionado
            selectedBusInfo?.let { info ->
                AlertDialog(
                    onDismissRequest = { selectedBusInfo = null },
                    title = { Text(text = "Ruta: ${info.nombreRuta}") },
                    text = {
                        Text(
                            "Conductor: ${info.conductor}\n" +
                                    "Placa: ${info.placa}\n" +
                                    "Capacidad: ${info.pasajeros} pasajeros"
                        )
                    },
                    confirmButton = {
                        TextButton(onClick = { selectedBusInfo = null }) {
                            Text("Cerrar")
                        }
                    }
                )
            }
        }
    }
}




