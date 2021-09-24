package com.example.pruebatecnica.model.interactos.interfaces

import android.content.Context
import com.example.pruebatecnica.entitites.Comentario

interface ComentariosInteractor {

    fun getComentariosByTareaIdFromDAO(context: Context, tareaId: Int)
    fun createComentarioWithDAO(context: Context, comentario: Comentario)
    fun deleteComentariosByTareaIdWithDAO(context: Context, tareaId: Int)
    fun returnResultFromDAO(comentarios : ArrayList<Comentario>)

}