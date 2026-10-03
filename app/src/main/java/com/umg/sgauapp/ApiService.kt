package com.umg.sgauapp

import com.umg.sgauapp.model.Carrera
import com.umg.sgauapp.model.Curso
import com.umg.sgauapp.model.Estudiante
import com.umg.sgauapp.model.Usuario
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // --- USUARIOS ---
    @GET("api/usuarios")
    fun obtenerUsuarios(): Call<List<Usuario>>

    @GET("api/usuarios/{id}")
    fun obtenerUsuarioPorId(@Path("id") id: String): Call<Usuario>

    // --- ESTUDIANTES ---
    @GET("api/estudiantes")
    fun obtenerEstudiantes(): Call<List<Estudiante>>

    @GET("api/estudiantes/{carne}")
    fun obtenerEstudiantePorCarne(@Path("carne") carne: String): Call<Estudiante>

    @GET("api/estudiantes/buscar")
    fun buscarEstudiantesPorNombre(@Query("nombre") nombre: String): Call<List<Estudiante>>

    // --- CARRERAS ---
    @GET("api/carreras")
    fun obtenerCarreras(): Call<List<Carrera>>

    // --- CURSOS ---
    @GET("api/cursos")
    fun obtenerCursos(): Call<List<Curso>>
}