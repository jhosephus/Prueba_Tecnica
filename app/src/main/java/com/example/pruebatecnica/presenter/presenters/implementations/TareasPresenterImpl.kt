package com.example.pruebatecnica.presenter.presenters.implementations

import android.content.Context
import com.example.pruebatecnica.entitites.Tarea
import com.example.pruebatecnica.model.interactos.implementations.TareasInteractorImpl
import com.example.pruebatecnica.model.interactos.interfaces.TareasInteractor
import com.example.pruebatecnica.presenter.presenters.interfaces.TareasPresenter
import com.example.pruebatecnica.presenter.views.TareasView

class TareasPresenterImpl(_tareasView: TareasView) : TareasPresenter {

    private var tareasView : TareasView = _tareasView
    private var tareasInteractor : TareasInteractor = TareasInteractorImpl(this)

    override fun showResult(tareas: ArrayList<Tarea>) {
        if(tareasView != null){
            tareasView.showResult(tareas)
        }
    }

    override fun getTareas(context: Context) {
        if(tareasInteractor != null){
            tareasInteractor.getTareasFromDAO(context)
        }
    }

    override fun createTarea(context: Context, tarea: Tarea) {
        if(tareasInteractor != null){
            tareasInteractor.insertTareaWithDAO(context, tarea)
        }
    }

    override fun deleteTareaById(context: Context, id: Int) {
        if(tareasInteractor != null){
            tareasInteractor.deleteTareaWithDAO(context, id)
        }
    }

    override fun updateTarea(context: Context, tarea: Tarea) {
        if(tareasInteractor != null){
            tareasInteractor.updateTareaWithDAO(context, tarea)
        }
    }

}