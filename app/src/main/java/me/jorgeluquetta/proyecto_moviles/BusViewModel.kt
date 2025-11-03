package me.jorgeluquetta.proyecto_moviles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


data class BusInfo(
    val nombreRuta: String,
    val conductor: String,
    val placa: String,
    val pasajeros: Int
)

class BusViewModel : ViewModel() {

    // Información de los buses
    private val busInfoMap = mapOf(
        "hamacas" to BusInfo(
            nombreRuta = "Hamacas",
            conductor = "Juan Gómez",
            placa = "ABC-123",
            pasajeros = 30
        ),
        "caracoli" to BusInfo(
            nombreRuta = "Caracolí",
            conductor = "María Rojas",
            placa = "DEF-456",
            pasajeros = 25
        ),
        "cumbre" to BusInfo(
            nombreRuta = "Cumbre",
            conductor = "Carlos Díaz",
            placa = "GHI-789",
            pasajeros = 20
        )
    )

    // Posiciones actuales de los buses
    private val _busPositions = MutableStateFlow<Map<String, LatLng>>(emptyMap())
    val busPositions: StateFlow<Map<String, LatLng>> = _busPositions

    // Rutas predefinidas
    private val routes = mapOf(
        "hamacas" to listOf(
            LatLng(7.156686020741695, -73.13736979214158),
            LatLng(7.151032350133802, -73.13538891799752),
            LatLng(7.144934338757362, -73.132967220194),
            LatLng(7.131699141796661, -73.12567457406922)
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

    init {
        // Simulación del movimiento de los buses
        routes.forEach { (name, points) ->
            if (points.size >= 2) startRouteSimulation(name, points)
        }
    }

    private fun startRouteSimulation(routeName: String, points: List<LatLng>) {
        viewModelScope.launch {
            var idx = 0
            while (true) {
                val start = points[idx]
                val end = points[(idx + 1) % points.size]

                // 🔹 10 pasos intermedios por tramo (movimiento más fluido)
                val steps = 10
                val stepDelay = 10000L // 1 segundo por paso → 10 seg por tramo total

                for (i in 0..steps) {
                    val t = i / steps.toFloat()
                    val lat = start.latitude + (end.latitude - start.latitude) * t
                    val lng = start.longitude + (end.longitude - start.longitude) * t

                    val current = _busPositions.value.toMutableMap()
                    current[routeName] = LatLng(lat, lng)
                    _busPositions.value = current

                    delay(stepDelay)
                }

                idx = (idx + 1) % points.size
            }
        }
    }


    fun getRoute(routeId: String?): List<LatLng>? = routes[routeId]
    // Devuelve la información del bus
    fun getBusInfo(routeId: String): BusInfo? = busInfoMap[routeId]



}



