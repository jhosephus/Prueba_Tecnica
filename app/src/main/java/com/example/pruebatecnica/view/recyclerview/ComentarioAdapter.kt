package com.example.pruebatecnica.view.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebatecnica.R
import com.example.pruebatecnica.entitites.Comentario

class ComentarioAdapter(private val data : ArrayList<Comentario>) : RecyclerView.Adapter<ComentarioAdapter.ComentarioViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComentarioViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_comentario, parent, false)
        return ComentarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComentarioViewHolder, position: Int) {
        val comentario : Comentario = data[position]
        holder.textViewDescription.text = comentario.description
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ComentarioViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textViewDescription : TextView

        init {
            textViewDescription = view.findViewById(R.id.itemComentario_description)
        }

    }

}