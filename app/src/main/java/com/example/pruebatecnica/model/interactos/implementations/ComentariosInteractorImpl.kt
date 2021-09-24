package com.example.pruebatecnica.model.interactos.implementations

import android.content.Context
import com.example.pruebatecnica.entitites.Comentario
import com.example.pruebatecnica.model.dao.tareas.ComentariosDAO
import com.example.pruebatecnica.model.interactos.interfaces.ComentariosInteractor
import com.example.pruebatecnica.presenter.presenters.interfaces.ComentariosPresenter

class ComentariosInteractorImpl(_comentariosPresenter : ComentariosPresenter) : ComentariosInteractor {

    val comentariosPresenter : ComentariosPresenter = _comentariosPresenter
    val comentariosDAO : ComentariosDAO = ComentariosDAO(this)

    override fun getComentariosByTareaIdFromDAO(context: Context, tareaId: Int) {
        if (comentariosDAO != null){
            comentariosDAO.getComentariosByTareaId(context, tareaId)
        }
    }

    override fun createComentarioWithDAO(context: Context, comentario: Comentario) {
        if(comentariosDAO != null){
            comentariosDAO.createComentario(context, comentario)
        }
    }

    override fun deleteComentariosByTareaIdWithDAO(context: Context, tareaId: Int) {
        if(comentariosDAO != null){
            comentariosDAO.deleteComentariosByTareaId(context, tareaId)
        }
    }

    override fun returnResultFromDAO(comentarios: ArrayList<Comentario>) {
        if(comentariosPresenter != null){
            comentariosPresenter.showResult(comentarios)
        }
    }
}