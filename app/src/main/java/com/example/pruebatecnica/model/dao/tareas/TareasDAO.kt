package com.example.pruebatecnica.model.dao.tareas

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.pruebatecnica.entitites.Tarea
import com.example.pruebatecnica.model.interactos.interfaces.TareasInteractor
import com.example.pruebatecnica.model.sqlite.ConnectionSQLHelper
import com.example.pruebatecnica.model.sqlite.Utilities
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

class TareasDAO(_tareasInteractor : TareasInteractor) {

    private var tareasInteractor : TareasInteractor = _tareasInteractor

    fun getTareas(context: Context){
        val connectionSQLHelper : ConnectionSQLHelper = ConnectionSQLHelper(context, "db_tareas", null, 1)
        val db : SQLiteDatabase = connectionSQLHelper.readableDatabase

        val parameters = arrayOf("0")
        val fields = arrayOf(Utilities.TAREAS_CAMPO_ID,
            Utilities.TAREAS_CAMPO_DESCRIPTION,
            Utilities.TAREAS_CAMPO_STARTDATE,
            Utilities.TAREAS_CAMPO_ENDDATE,
            Utilities.TAREAS_CAMPO_FINISHEDSTATUS)

        //val cursor: Cursor = db.rawQuery("SELECT * FROM ${Utilities.TABLA_TAREAS}", null)

        var tareas : ArrayList<Tarea> = ArrayList()

        try {
            val cursor: Cursor = db.rawQuery("SELECT * FROM ${Utilities.TABLA_TAREAS} ORDER BY ${Utilities.TAREAS_CAMPO_STARTDATE} DESC, ${Utilities.TAREAS_CAMPO_ID} DESC", null)
            with(cursor) {
                while (moveToNext()) {
                    val itemId = getLong(getColumnIndex(Utilities.TAREAS_CAMPO_ID))
                    val itemTitle = getString(getColumnIndex(Utilities.TAREAS_CAMPO_TITLE))
                    val itemDescription = getString(getColumnIndex(Utilities.TAREAS_CAMPO_DESCRIPTION))
                    val itemStartDate = getString(getColumnIndex(Utilities.TAREAS_CAMPO_STARTDATE))
                    val itemEndDate = getString(getColumnIndex(Utilities.TAREAS_CAMPO_ENDDATE))
                    val itemFinishStatus = getInt(getColumnIndex(Utilities.TAREAS_CAMPO_FINISHEDSTATUS))

                    val simpleDate : SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd")

                    var tarea : Tarea = Tarea(itemId.toInt(), itemTitle, itemDescription,
                        simpleDate.parse(itemStartDate), simpleDate.parse(itemEndDate), if(itemFinishStatus == 1) true else false)

                    tareas.add(tarea)
                    Log.i("My Tag", itemDescription)
                }
            }
        } catch (e: Exception){
            Log.i("My Tag", "Documento vacio")
        }

        if (tareasInteractor != null){
            tareasInteractor.returnResulFromDAO(tareas)
        }

    }

    fun createTarea(context: Context, tarea: Tarea){
        val connectionSQLHelper : ConnectionSQLHelper = ConnectionSQLHelper(context, "db_tareas", null, 1)
        val db : SQLiteDatabase = connectionSQLHelper.writableDatabase

        val simpleDate : SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
        val fStatus : Int = if (tarea.finishStatus) 1 else 0
        //val fStatus : Int = 1

        val queryString : String = "INSERT INTO ${Utilities.TABLA_TAREAS} " +
                "(${Utilities.TAREAS_CAMPO_TITLE}, ${Utilities.TAREAS_CAMPO_DESCRIPTION}, ${Utilities.TAREAS_CAMPO_STARTDATE}, ${Utilities.TAREAS_CAMPO_ENDDATE}, ${Utilities.TAREAS_CAMPO_FINISHEDSTATUS}) " +
                "values ('${tarea.title}', '${tarea.description}', '${simpleDate.format(tarea.startDate)}', '${simpleDate.format(tarea.endDate)}', ${fStatus.toString()})"

        db.execSQL(queryString)
        db.close()

        getTareas(context)

    }

    fun deleteTareaById(context: Context, id: Int){
        val connectionSQLHelper : ConnectionSQLHelper = ConnectionSQLHelper(context, "db_tareas", null, 1)
        val db : SQLiteDatabase = connectionSQLHelper.writableDatabase

        val queryString : String = "DELETE FROM ${Utilities.TABLA_TAREAS} " +
                "WHERE ${Utilities.TAREAS_CAMPO_ID} = $id"

        db.execSQL(queryString)
        db.close()
    }

    fun updateTarea(context: Context, tarea: Tarea){
        val connectionSQLHelper : ConnectionSQLHelper = ConnectionSQLHelper(context, "db_tareas", null, 1)
        val db : SQLiteDatabase = connectionSQLHelper.writableDatabase

        val simpleDate : SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
        val fStatus : Int = if (tarea.finishStatus) 1 else 0
        //val fStatus : Int = 1

        val queryString : String = "UPDATE ${Utilities.TABLA_TAREAS} " +
                "SET ${Utilities.TAREAS_CAMPO_TITLE} = '${tarea.title}', ${Utilities.TAREAS_CAMPO_DESCRIPTION} = '${tarea.description}', ${Utilities.TAREAS_CAMPO_STARTDATE} = '${simpleDate.format(tarea.startDate)}', ${Utilities.TAREAS_CAMPO_ENDDATE} = '${simpleDate.format(tarea.endDate)}', ${Utilities.TAREAS_CAMPO_FINISHEDSTATUS} = $fStatus " +
                "WHERE ${Utilities.TAREAS_CAMPO_ID} = ${tarea.id}"

        db.execSQL(queryString)
        db.close()

        Log.i("My Tag", "updateTarea")
    }

}