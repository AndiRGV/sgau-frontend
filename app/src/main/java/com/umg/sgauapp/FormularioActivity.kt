package com.umg.sgauapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FormularioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario)

        // Referencias a los elementos del XML
        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etCodigo = findViewById<EditText>(R.id.etCodigo)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        // Acción al hacer clic en el botón
        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val codigo = etCodigo.text.toString().trim()

            if (nombre.isNotEmpty() && codigo.isNotEmpty()) {
                // Aquí más adelante se conectará con el ApiService que tú creaste
                Toast.makeText(this, "Datos listos para enviar: $nombre", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}