package com.umg.sgauapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.umg.sgauapp.R
import com.umg.sgauapp.model.Curso

class CursoAdapter(private val cursos: List<Curso>) :
    RecyclerView.Adapter<CursoAdapter.CursoViewHolder>() {

    class CursoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tvNombre)
        val tvDetalle: TextView = view.findViewById(R.id.tvDetalle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CursoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_generico, parent, false)
        return CursoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CursoViewHolder, position: Int) {
        val curso = cursos[position]
        holder.tvNombre.text = curso.nombre
        holder.tvDetalle.text = "Créditos: ${curso.creditos ?: 0}"
    }

    override fun getItemCount(): Int = cursos.size
}
