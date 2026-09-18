package com.umg.sgauapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.umg.sgauapp.R
import com.umg.sgauapp.model.Estudiante

class EstudianteAdapter(private val estudiantes: List<Estudiante>) :
    RecyclerView.Adapter<EstudianteAdapter.EstudianteViewHolder>() {

    class EstudianteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tvNombre)
        val tvDetalle: TextView = view.findViewById(R.id.tvDetalle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstudianteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_generico, parent, false)
        return EstudianteViewHolder(view)
    }

    override fun onBindViewHolder(holder: EstudianteViewHolder, position: Int) {
        val estudiante = estudiantes[position]
        holder.tvNombre.text = "${estudiante.nombre} ${estudiante.apellido}"
        holder.tvDetalle.text = "Carné: ${estudiante.carne} - ${estudiante.email}"
    }

    override fun getItemCount(): Int = estudiantes.size
}
