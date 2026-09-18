package com.umg.sgauapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.umg.sgauapp.R
import com.umg.sgauapp.model.Carrera

class CarreraAdapter(private val carreras: List<Carrera>) :
    RecyclerView.Adapter<CarreraAdapter.CarreraViewHolder>() {

    class CarreraViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tvNombre)
        val tvDetalle: TextView = view.findViewById(R.id.tvDetalle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarreraViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_generico, parent, false)
        return CarreraViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarreraViewHolder, position: Int) {
        val carrera = carreras[position]
        holder.tvNombre.text = carrera.nombre
        holder.tvDetalle.text = "Facultad: ${carrera.facultad ?: "N/A"}"
    }

    override fun getItemCount(): Int = carreras.size
}
