package com.example.pruebatecnica.presenter.presenters.interfaces

import android.content.Context
import com.example.pruebatecnica.entitites.Comentario

interface ComentariosPresenter {

    fun showResult(comentarios: ArrayList<Comentario>)
    fun getComentariosByTareaId(context: Context, tareaId: Int)
    fun createComentario(context: Context, comentario: Comentario)
    fun deleteComentariosByTareaId(context: Context, tareaId: Int)
}