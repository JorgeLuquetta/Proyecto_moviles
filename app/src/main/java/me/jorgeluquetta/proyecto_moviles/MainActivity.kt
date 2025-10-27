package me.jorgeluquetta.proyecto_moviles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import me.jorgeluquetta.proyecto_moviles.ui.theme.Proyecto_movilesTheme

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
                composable(route = "map") {
                    MapScreen()
                }
            }
        }
    }
}
