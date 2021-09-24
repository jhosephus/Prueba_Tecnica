package com.example.pruebatecnica.model.interactos.interfaces

import android.content.Context
import com.example.pruebatecnica.entitites.Tarea

interface TareasInteractor {
    fun getTareasFromDAO(context: Context)
    fun returnResulFromDAO(tareas: ArrayList<Tarea>)
    fun insertTareaWithDAO(context: Context, tarea: Tarea)
    fun deleteTareaWithDAO(context: Context, id: Int)
    fun updateTareaWithDAO(context: Context, tarea: Tarea)
}