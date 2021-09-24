package com.example.pruebatecnica.model.sqlite

class Utilities {

    companion object{
        val CREAR_TABLA_TAREAS: String = "CREATE TABLE tareas (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT, startDate TEXT, endDate TEXT, finishStatus INTEGER)"
        val CREAR_TABLA_COMENTARIOS: String = "CREATE TABLE comentarios (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, description TEXT, tareaId INTEGER)"

        //Constantes tabla tareas
        val TABLA_TAREAS : String = "tareas"
        val TAREAS_CAMPO_ID : String = "id"
        val TAREAS_CAMPO_TITLE : String = "title"
        val TAREAS_CAMPO_DESCRIPTION : String = "description"
        val TAREAS_CAMPO_STARTDATE : String = "startDate"
        val TAREAS_CAMPO_ENDDATE : String = "endDate"
        val TAREAS_CAMPO_FINISHEDSTATUS : String = "finishStatus"

        //Constantes tabla tareas
        val TABLA_COMENTARIOS : String = "comentarios"
        val COMENTARIOS_CAMPO_ID : String = "id"
        val COMENTARIOS_CAMPO_DESCRIPTION : String = "description"
        val COMENTARIOS_CAMPO_TAREAID : String = "tareaId"


    }

}