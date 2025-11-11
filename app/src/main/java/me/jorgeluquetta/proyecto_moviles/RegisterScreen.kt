package me.jorgeluquetta.proyecto_moviles

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.auth

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onClickBack: () -> Unit = {},
    onSuccessfulRegister: () -> Unit = {}
) {

    val auth = Firebase.auth
    val activity = LocalView.current.context as Activity

    // Estados de los inputs
    var inputName by remember { mutableStateOf("") }
    var inputEmail by remember { mutableStateOf("") }
    var inputPassword by remember { mutableStateOf("") }
    var inputPasswordConfirmation by remember { mutableStateOf("") }

    // Errores
    var nameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }
    var registerError by remember { mutableStateOf("") }

    // Visibilidad de contraseñas
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onClickBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.Black
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 32.dp)
                .imePadding()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Logo
            Image(
                painter = painterResource(id = R.drawable.img_logo_movebga),
                contentDescription = "Logo",
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Registro de Usuario",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A2E)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Nombre
            OutlinedTextField(
                value = inputName,
                onValueChange = {
                    inputName = it
                    nameError = ""
                },
                label = { Text("Nombre") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "Nombre")
                },
                supportingText = {
                    if (nameError.isNotEmpty()) Text(nameError, color = Color.Red)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Correo
            OutlinedTextField(
                value = inputEmail,
                onValueChange = {
                    inputEmail = it
                    emailError = ""
                },
                label = { Text("Correo Electrónico") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Email, contentDescription = "Email")
                },
                supportingText = {
                    if (emailError.isNotEmpty()) Text(emailError, color = Color.Red)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Contraseña
            OutlinedTextField(
                value = inputPassword,
                onValueChange = {
                    inputPassword = it
                    passwordError = ""
                },
                label = { Text("Contraseña") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = "Contraseña")
                },
                trailingIcon = {
                    val icon = if (passwordVisible)
                        Icons.Filled.Visibility else Icons.Filled.VisibilityOff

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = icon,
                            contentDescription = if (passwordVisible)
                                "Ocultar contraseña" else "Mostrar contraseña"
                        )
                    }
                },
                supportingText = {
                    if (passwordError.isNotEmpty()) Text(passwordError, color = Color.Red)
                },
                visualTransformation = if (passwordVisible)
                    VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Confirmar contraseña
            OutlinedTextField(
                value = inputPasswordConfirmation,
                onValueChange = {
                    inputPasswordConfirmation = it
                    confirmPasswordError = ""
                },
                label = { Text("Confirmar Contraseña") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = "Confirmar Contraseña")
                },
                trailingIcon = {
                    val icon = if (confirmPasswordVisible)
                        Icons.Filled.Visibility else Icons.Filled.VisibilityOff

                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(
                            imageVector = icon,
                            contentDescription = if (confirmPasswordVisible)
                                "Ocultar contraseña" else "Mostrar contraseña"
                        )
                    }
                },
                supportingText = {
                    if (confirmPasswordError.isNotEmpty()) Text(confirmPasswordError, color = Color.Red)
                },
                visualTransformation = if (confirmPasswordVisible)
                    VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            if (registerError.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(registerError, color = Color.Red)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón de registro
            Button(
                onClick = {
                    val (isValidName, nameMsg) = validateName(inputName)
                    val (isValidEmail, emailMsg) = validateEmail(inputEmail)
                    val (isValidPassword, passMsg) = validatePassword(inputPassword)
                    val (isValidConfirm, confirmMsg) =
                        validateConfirmPassword(inputPassword, inputPasswordConfirmation)

                    nameError = nameMsg
                    emailError = emailMsg
                    passwordError = passMsg
                    confirmPasswordError = confirmMsg

                    if (isValidName && isValidEmail && isValidPassword && isValidConfirm) {
                        auth.createUserWithEmailAndPassword(inputEmail, inputPassword)
                            .addOnCompleteListener(activity) { task ->
                                if (task.isSuccessful) {
                                    onSuccessfulRegister()
                                } else {
                                    val ex = task.exception
                                    registerError = when (ex) {
                                        is FirebaseAuthInvalidCredentialsException -> "Correo inválido"
                                        is FirebaseAuthUserCollisionException -> "Correo ya registrado"
                                        else -> ex?.localizedMessage ?: "Error al registrar. Intenta de nuevo"
                                    }
                                }
                            }
                    } else {
                        registerError = "Por favor, corrige los errores antes de continuar"
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE1131F))
            ) {
                Text("Registrarse", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}