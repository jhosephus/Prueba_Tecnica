package com.example.pruebatecnica.presenter.presenters.implementations

import android.content.Context
import com.example.pruebatecnica.entitites.Comentario
import com.example.pruebatecnica.model.interactos.implementations.ComentariosInteractorImpl
import com.example.pruebatecnica.model.interactos.interfaces.ComentariosInteractor
import com.example.pruebatecnica.presenter.presenters.interfaces.ComentariosPresenter
import com.example.pruebatecnica.presenter.views.ComentariosView

class ComentariosPresenterImpl(_comentariosView : ComentariosView) : ComentariosPresenter {

    val comentariosView : ComentariosView = _comentariosView
    val comentariosInteractor : ComentariosInteractor = ComentariosInteractorImpl(this)

    override fun showResult(comentarios: ArrayList<Comentario>) {
        if(comentariosView != null){
            comentariosView.showComentariosResult(comentarios)
        }
    }

    override fun getComentariosByTareaId(context: Context, tareaId: Int) {
        if(comentariosInteractor != null){
            comentariosInteractor.getComentariosByTareaIdFromDAO(context, tareaId)
        }
    }

    override fun createComentario(context: Context, comentario: Comentario) {
        if(comentariosInteractor != null){
            comentariosInteractor.createComentarioWithDAO(context, comentario)
        }
    }

    override fun deleteComentariosByTareaId(context: Context, tareaId: Int) {
        if(comentariosInteractor != null) {
            comentariosInteractor.deleteComentariosByTareaIdWithDAO(context, tareaId)
        }
    }
}