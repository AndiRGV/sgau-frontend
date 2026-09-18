package com.umg.sgauapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.umg.sgauapp.model.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
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
