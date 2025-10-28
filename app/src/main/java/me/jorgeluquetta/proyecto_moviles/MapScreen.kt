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
import androidx.navigation.NavHostController
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@SuppressLint("MissingPermission")
@Composable
fun MapScreen(navController: NavHostController, routeId: String? = null) {
    val context = LocalContext.current
    val currentRoute = "map"

    // Inicializar Google Maps
    LaunchedEffect(Unit) {
        MapsInitializer.initialize(context)
    }

    // Estado de permisos
    var hasLocationPermission by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasLocationPermission = isGranted
    }

    // Solicitar permiso
    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            hasLocationPermission = true
        } else {
            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    // Obtener ubicación actual
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    var userLocation by remember { mutableStateOf<LatLng?>(null) }

    LaunchedEffect(hasLocationPermission) {
        if (hasLocationPermission) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    userLocation = LatLng(it.latitude, it.longitude)
                }
            }
        }
    }

    // Posición inicial de la cámara
    val defaultPosition = LatLng(7.119349, -73.1227416)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultPosition, 13f)
    }

    //  Datos de rutas predefinidas
    val routes = mapOf(
        "hamacas" to listOf(
            LatLng(7.156686020741695, -73.13736979214158),
            LatLng(7.155006809906785, -73.13732322882667),
            LatLng(7.1518685185555055, -73.13561404259177),
            LatLng(7.151032350133802, -73.13538891799752),
            LatLng(7.149708518346934, -73.13488387880643),
            LatLng(7.14817934544773, -73.1345649286672),
            LatLng(7.144934338757362, -73.132967220194),
            LatLng(7.1462132579083555, -73.13610108901369),
            LatLng(7.131699141796661, -73.12567457406922),
            LatLng(7.1076325827179625, -73.12001334596565)
        ),
        "caracoli" to listOf(
            LatLng(7.119, -73.1227),
            LatLng(7.113, -73.128),
            LatLng(7.109, -73.134)
        ),
        "cumbre" to listOf(
            LatLng(7.119, -73.1227),
            LatLng(7.123, -73.118),
            LatLng(7.130, -73.112)
        )
    )

    // Ruta seleccionada
    val selectedRoute = routes[routeId]

    // Mover cámara a la ruta seleccionada
    LaunchedEffect(selectedRoute) {
        selectedRoute?.let {
            if (it.isNotEmpty()) {
                cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(it.first(), 14f))
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        // --- Encabezado y buscador ---
        Text(
            text = "Map",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(16.dp)
                .align(Alignment.CenterHorizontally)
        )

        TextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("¿A dónde quieres ir?") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        // --- Google Map ---
        Box(modifier = Modifier.weight(1f)) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(
                    isMyLocationEnabled = hasLocationPermission,
                    mapType = MapType.NORMAL
                ),
                uiSettings = MapUiSettings(
                    myLocationButtonEnabled = true,
                    zoomControlsEnabled = false
                )
            ) {
                // ✅ Dibujar la ruta seleccionada si existe
                selectedRoute?.let { points ->
                    if (points.size >= 2) {
                        Polyline(
                            points = points,
                            color = Color(0xFFE1131F),
                            width = 8f
                        )
                        Marker(
                            state = MarkerState(position = points.first()),
                            title = "Inicio de ruta"
                        )
                        Marker(
                            state = MarkerState(position = points.last()),
                            title = "Destino"
                        )
                    }
                }
            }
        }

        // --- Barra inferior ---
        BottomBar(navController = navController, currentRoute = currentRoute)
    }
}
