package com.umg.sgauapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.umg.sgauapp.model.Usuario
import com.umg.sgauapp.ui.theme.SgauAppTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SgauAppTheme {
                var isLoggedIn by remember { mutableStateOf(false) }

                if (!isLoggedIn) {
                    LoginScreen(
                        onLoginClick = { emailIngresado, passwordIngresada ->
                            val correoLimpio = emailIngresado.trim()

                            if (correoLimpio.isBlank()) {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Por favor ingresa un correo válido",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Verificando credenciales...",
                                    Toast.LENGTH_SHORT
                                ).show()

                                // Consulta a la API de Neon DB
                                RetrofitClient.apiService.obtenerUsuarios().enqueue(object : Callback<List<Usuario>> {
                                    override fun onResponse(
                                        call: Call<List<Usuario>>,
                                        response: Response<List<Usuario>>
                                    ) {
                                        if (response.isSuccessful) {
                                            val listaUsuarios = response.body() ?: emptyList()

                                            // Compara limpiando espacios en blanco e ignorando mayúsculas/minúsculas
                                            val usuarioValido = listaUsuarios.find { usuario ->
                                                usuario.email.trim().equals(correoLimpio, ignoreCase = true)
                                            }

                                            if (usuarioValido != null) {
                                                Toast.makeText(
                                                    this@MainActivity,
                                                    "¡Bienvenido ${usuarioValido.nombre}!",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                isLoggedIn = true
                                            } else {
                                                Toast.makeText(
                                                    this@MainActivity,
                                                    "El correo $correoLimpio no está registrado",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                        } else {
                                            Toast.makeText(
                                                this@MainActivity,
                                                "Error de servidor HTTP ${response.code()}",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    }

                                    override fun onFailure(call: Call<List<Usuario>>, t: Throwable) {
                                        Toast.makeText(
                                            this@MainActivity,
                                            "Error de conexión: ${t.message}",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                })
                            }
                        }
                    )
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "SgauApp - Gestión Académica")
                        Spacer(modifier = Modifier.height(32.dp))

                        Button(onClick = {
                            val intent = Intent(this@MainActivity, ListaDatosActivity::class.java)
                            intent.putExtra("TIPO", "USUARIOS")
                            startActivity(intent)
                        }) {
                            Text(text = "Ver Usuarios (RecyclerView)")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(onClick = {
                            val intent = Intent(this@MainActivity, ListaDatosActivity::class.java)
                            intent.putExtra("TIPO", "ESTUDIANTES")
                            startActivity(intent)
                        }) {
                            Text(text = "Ver Estudiantes (RecyclerView)")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(onClick = {
                            val intent = Intent(this@MainActivity, ListaDatosActivity::class.java)
                            intent.putExtra("TIPO", "CARRERAS")
                            startActivity(intent)
                        }) {
                            Text(text = "Ver Carreras (RecyclerView)")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(onClick = {
                            val intent = Intent(this@MainActivity, ListaDatosActivity::class.java)
                            intent.putExtra("TIPO", "CURSOS")
                            startActivity(intent)
                        }) {
                            Text(text = "Ver Cursos (RecyclerView)")
                        }
                    }
                }
            }
        }
    }
}