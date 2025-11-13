    package me.jorgeluquetta.proyecto_moviles


    import android.Manifest
    import android.annotation.SuppressLint
    import android.content.pm.PackageManager
    import androidx.activity.compose.rememberLauncherForActivityResult
    import androidx.activity.result.contract.ActivityResultContracts
    import androidx.compose.foundation.layout.*
    import androidx.compose.material3.*
    import androidx.compose.runtime.*
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.platform.LocalContext
    import androidx.core.content.ContextCompat
    import androidx.navigation.NavController
    import com.google.android.gms.location.LocationServices
    import com.google.android.gms.maps.model.CameraPosition
    import com.google.android.gms.maps.model.LatLng
    import com.google.maps.android.compose.*

    @SuppressLint("MissingPermission")
    @Composable
    fun MapScreen(navController: NavController, busViewModel: BusViewModel) {
        val context = LocalContext.current
        val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

        val defaultLocation = LatLng(7.119349, -73.122741)

        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(defaultLocation, 13f)
        }

        val busIcon = remember { bitmapDescriptorFromVector(context, R.drawable.ic_bus) }
        val busPositions by busViewModel.busPositions.collectAsState()

        var userLocation by remember { mutableStateOf<LatLng?>(null) }
        var selectedBusInfo by remember { mutableStateOf<BusInfo?>(null) }
        var hasLocationPermission by remember { mutableStateOf(false) }

        // Launcher para pedir permiso
        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { granted ->
            hasLocationPermission = granted
        }

        LaunchedEffect(Unit) {
            val granted = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            if (granted) {
                hasLocationPermission = true
            } else {
                // Pedir permiso solo si no está concedido
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }

        LaunchedEffect(hasLocationPermission) {
            if (hasLocationPermission) {
                try {
                    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                        location?.let {
                            val latLng = LatLng(it.latitude, it.longitude)
                            userLocation = latLng
                            cameraPositionState.position = CameraPosition.fromLatLngZoom(latLng, 15f)
                        }
                    }
                } catch (e: SecurityException) {
                    e.printStackTrace()
                }
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
                        isMyLocationEnabled = hasLocationPermission // Evita cierre
                    ),
                    uiSettings = MapUiSettings(
                        zoomControlsEnabled = false,
                        myLocationButtonEnabled = hasLocationPermission, // Solo visible si se tiene permiso
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

                // Dialogo con info del bus seleccionado
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
