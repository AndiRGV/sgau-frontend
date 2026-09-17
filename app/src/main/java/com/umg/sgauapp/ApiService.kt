package com.umg.sgauapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // --- USUARIOS ---
    @GET("api/usuarios")
    fun obtenerUsuarios(): Call<List<Any>> // Cambia List<Any> por tu modelo DTO de usuario cuando lo crees

    @GET("api/usuarios/{id}")
    fun obtenerUsuarioPorId(@Path("id") id: Long): Call<Any>

    // --- ESTUDIANTES ---
    @GET("api/estudiantes")
    fun obtenerEstudiantes(): Call<List<Any>>

    @GET("api/estudiantes/{carne}")
    fun obtenerEstudiantePorCarne(@Path("carne") carne: String): Call<Any>

    @GET("api/estudiantes/buscar")
    fun buscarEstudiantesPorNombre(@Query("nombre") nombre: String): Call<List<Any>>

    // --- CARRERAS ---
    @GET("api/carreras")
    fun obtenerCarreras(): Call<List<Any>>

    // --- CURSOS ---
    @GET("api/cursos")
    fun obtenerCursos(): Call<List<Any>>
}