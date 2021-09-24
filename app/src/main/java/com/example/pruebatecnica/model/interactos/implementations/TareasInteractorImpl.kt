package com.example.pruebatecnica.model.interactos.implementations

import android.content.Context
import com.example.pruebatecnica.entitites.Tarea
import com.example.pruebatecnica.model.dao.tareas.TareasDAO
import com.example.pruebatecnica.model.interactos.interfaces.TareasInteractor
import com.example.pruebatecnica.presenter.presenters.interfaces.TareasPresenter

class TareasInteractorImpl(_tareasPresenter: TareasPresenter) : TareasInteractor{

    private var tareasPresenter : TareasPresenter = _tareasPresenter
    private var tareasDAO : TareasDAO = TareasDAO(this)

    override fun getTareasFromDAO(context: Context) {
        if (tareasDAO != null){
            tareasDAO.getTareas(context)
        }
    }

    override fun returnResulFromDAO(tareas: ArrayList<Tarea>) {
        if (tareasPresenter != null){
            tareasPresenter.showResult(tareas)
        }
    }

    override fun insertTareaWithDAO(context: Context, tarea: Tarea) {
        if (tareasDAO != null){
            tareasDAO.createTarea(context, tarea)
        }
    }

    override fun deleteTareaWithDAO(context: Context, id: Int) {
        if (tareasDAO != null){
            tareasDAO.deleteTareaById(context, id)
        }
    }

    override fun updateTareaWithDAO(context: Context, tarea: Tarea) {
        if (tareasDAO != null){
            tareasDAO.updateTarea(context, tarea)
        }
    }

}