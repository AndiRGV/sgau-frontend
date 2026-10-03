package com.umg.sgauapp.model

import com.google.gson.annotations.SerializedName

data class Usuario(
    val id: String, // String para soportar los UUIDs
    val username: String? = null,
    val email: String,
    @SerializedName("rolId") val rolId: String? = null,
    @SerializedName("estadoId") val estadoId: String? = null
) {
    val nombre: String
        get() = username ?: email.substringBefore("@")
}