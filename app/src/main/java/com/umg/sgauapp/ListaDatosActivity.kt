package com.umg.sgauapp

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umg.sgauapp.adapter.CarreraAdapter
import com.umg.sgauapp.adapter.CursoAdapter
import com.umg.sgauapp.adapter.EstudianteAdapter
import com.umg.sgauapp.adapter.UsuarioAdapter
import com.umg.sgauapp.model.Carrera
import com.umg.sgauapp.model.Curso
import com.umg.sgauapp.model.Estudiante
import com.umg.sgauapp.model.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaDatosActivity : AppCompatActivity() {

    private lateinit var rvDatos: RecyclerView
    private lateinit var tvTitulo: TextView
    private val apiService = RetrofitClient.instance.create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_datos)

        rvDatos = findViewById(R.id.rvDatos)
        tvTitulo = findViewById(R.id.tvTituloLista)
        rvDatos.layoutManager = LinearLayoutManager(this)

        val tipo = intent.getStringExtra("TIPO") ?: "USUARIOS"

        if (tipo == "USUARIOS") {
            cargarUsuarios()
        } else if (tipo == "ESTUDIANTES") {
            cargarEstudiantes()
        } else if (tipo == "CARRERAS") {
            cargarCarreras()
        } else if (tipo == "CURSOS") {
            cargarCursos()
        }
    }

    private fun cargarUsuarios() {
        tvTitulo.text = "Lista de Usuarios"
        apiService.obtenerUsuarios().enqueue(object : Callback<List<Usuario>> {
            override fun onResponse(call: Call<List<Usuario>>, response: Response<List<Usuario>>) {
                if (response.isSuccessful) {
                    val usuarios = response.body() ?: emptyList()
                    rvDatos.adapter = UsuarioAdapter(usuarios)
                } else {
                    Toast.makeText(this@ListaDatosActivity, "Error al cargar usuarios", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Usuario>>, t: Throwable) {
                Toast.makeText(this@ListaDatosActivity, "Fallo de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun cargarEstudiantes() {
        tvTitulo.text = "Lista de Estudiantes"
        apiService.obtenerEstudiantes().enqueue(object : Callback<List<Estudiante>> {
            override fun onResponse(call: Call<List<Estudiante>>, response: Response<List<Estudiante>>) {
                if (response.isSuccessful) {
                    val estudiantes = response.body() ?: emptyList()
                    rvDatos.adapter = EstudianteAdapter(estudiantes)
                } else {
                    Toast.makeText(this@ListaDatosActivity, "Error al cargar estudiantes", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Estudiante>>, t: Throwable) {
                Toast.makeText(this@ListaDatosActivity, "Fallo de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun cargarCarreras() {
        tvTitulo.text = "Lista de Carreras"
        apiService.obtenerCarreras().enqueue(object : Callback<List<Carrera>> {
            override fun onResponse(call: Call<List<Carrera>>, response: Response<List<Carrera>>) {
                if (response.isSuccessful) {
                    val carreras = response.body() ?: emptyList()
                    rvDatos.adapter = CarreraAdapter(carreras)
                } else {
                    Toast.makeText(this@ListaDatosActivity, "Error al cargar carreras", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Carrera>>, t: Throwable) {
                Toast.makeText(this@ListaDatosActivity, "Fallo de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun cargarCursos() {
        tvTitulo.text = "Lista de Cursos"
        apiService.obtenerCursos().enqueue(object : Callback<List<Curso>> {
            override fun onResponse(call: Call<List<Curso>>, response: Response<List<Curso>>) {
                if (response.isSuccessful) {
                    val cursos = response.body() ?: emptyList()
                    rvDatos.adapter = CursoAdapter(cursos)
                } else {
                    Toast.makeText(this@ListaDatosActivity, "Error al cargar cursos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Curso>>, t: Throwable) {
                Toast.makeText(this@ListaDatosActivity, "Fallo de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
