package com.umg.sgauapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Interfaz visual básica con Jetpack Compose
        setContent {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "SgauApp - Conectando a Cloud Run...")
            }
        }

        // Llamada a tu backend usando Retrofit
        val apiService = RetrofitClient.instance.create(ApiService::class.java)

        apiService.obtenerUsuarios().enqueue(object : Callback<List<Any>> {
            override fun onResponse(call: Call<List<Any>>, response: Response<List<Any>>) {
                if (response.isSuccessful) {
                    Log.d("API_SUCCESS", "Datos recibidos: ${response.body()}")
                } else {
                    Log.e("API_ERROR", "Código de error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Any>>, t: Throwable) {
                Log.e("API_FAILURE", "Fallo de conexión: ${t.message}")
            }
        })
    }
}