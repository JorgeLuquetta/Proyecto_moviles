package me.jorgeluquetta.proyecto_moviles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "welcome",
                modifier = Modifier.fillMaxSize()
            ) {
                composable(route = "welcome") {
                    WelcomeScreen(
                        onLoginClick = {
                            navController.navigate("login")
                        },
                        onRegisterClick = {
                            navController.navigate("register")
                        }
                    )
                }

                composable(route = "login") {
                    LoginScreen(
                        onClickRegister = {
                            navController.navigate("register")
                        },
                        onSuccessfulLogin = {
                            navController.navigate("map") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    )
                }

                composable(route = "register") {
                    RegisterScreen(
                        onClickBack = {
                            navController.popBackStack()
                        },
                        onSuccessfulRegister = {
                            navController.navigate("welcome") {
                                popUpTo("register") { inclusive = true }
                            }
                        }
                    )
                }
                composable(
                    route = "map?routeId={routeId}",
                    arguments = listOf(
                        navArgument("routeId") { nullable = true; defaultValue = null }
                    )
                ) { backStackEntry ->
                    val routeId = backStackEntry.arguments?.getString("routeId")
                    MapScreen(navController, routeId)
                }
                composable(route = "map") {
                    MapScreen(navController)
                }
                composable(route = "route") {
                    RouteScreen(navController)
                }
                composable(route = "settings") {
                    SettingsScreen(navController)
                }
            }
        }
    }
}
