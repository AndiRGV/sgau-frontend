package com.umg.sgauapp.model

data class Usuario(
    val id: Long,
    val nombre: String,
    val email: String,
    val rol: String? = null
)
