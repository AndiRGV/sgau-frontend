package com.umg.sgauapp.model

data class Estudiante(
    val carne: String,
    val nombre: String,
    val apellido: String,
    val email: String,
    val carrera: String? = null
)
