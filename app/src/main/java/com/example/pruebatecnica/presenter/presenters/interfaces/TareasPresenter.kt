package com.example.pruebatecnica.presenter.presenters.interfaces

import android.content.Context
import com.example.pruebatecnica.entitites.Tarea

interface TareasPresenter {

    fun showResult(tareas: ArrayList<Tarea>)
    fun getTareas(context: Context)
    fun createTarea(context: Context, tarea: Tarea)
    fun deleteTareaById(context: Context, id: Int)
    fun updateTarea(context: Context, tarea: Tarea)
}