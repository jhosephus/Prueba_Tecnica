package com.example.pruebatecnica.model.dao.tareas

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.pruebatecnica.entitites.Comentario
import com.example.pruebatecnica.entitites.Tarea
import com.example.pruebatecnica.model.interactos.interfaces.ComentariosInteractor
import com.example.pruebatecnica.model.sqlite.ConnectionSQLHelper
import com.example.pruebatecnica.model.sqlite.Utilities
import java.lang.Exception
import java.text.SimpleDateFormat

class ComentariosDAO(_comentariosInteractor : ComentariosInteractor) {

    val comentariosInteractor : ComentariosInteractor = _comentariosInteractor

    fun getComentariosByTareaId(context: Context, tareaId: Int){
        val connectionSQLHelper : ConnectionSQLHelper = ConnectionSQLHelper(context, "db_tareas", null, 1)
        val db : SQLiteDatabase = connectionSQLHelper.readableDatabase

        var comentarios : ArrayList<Comentario> = ArrayList()

        try {
            val cursor: Cursor = db.rawQuery("SELECT * FROM ${Utilities.TABLA_COMENTARIOS} " +
                    "WHERE ${Utilities.COMENTARIOS_CAMPO_TAREAID} = $tareaId " +
                    "ORDER BY ${Utilities.COMENTARIOS_CAMPO_ID} DESC", null)
            with(cursor) {
                while (moveToNext()) {
                    val itemId = getLong(getColumnIndex(Utilities.COMENTARIOS_CAMPO_ID))
                    val itemDescription = getString(getColumnIndex(Utilities.COMENTARIOS_CAMPO_DESCRIPTION))
                    val itemTareaId = getLong(getColumnIndex(Utilities.COMENTARIOS_CAMPO_TAREAID))

                    var comentario : Comentario = Comentario(itemId.toInt(), itemDescription, itemTareaId.toInt())

                    comentarios.add(comentario)
                }
            }
        } catch (e: Exception){
            Log.i("My Tag", "Documento vacio")
        }

        if (comentariosInteractor != null){
            comentariosInteractor.returnResultFromDAO(comentarios)
        }
    }

    fun createComentario(context: Context, comentario: Comentario){
        val connectionSQLHelper : ConnectionSQLHelper = ConnectionSQLHelper(context, "db_tareas", null, 1)
        val db : SQLiteDatabase = connectionSQLHelper.writableDatabase

        val queryString : String = "INSERT INTO ${Utilities.TABLA_COMENTARIOS} " +
                "(${Utilities.COMENTARIOS_CAMPO_DESCRIPTION}, ${Utilities.COMENTARIOS_CAMPO_TAREAID}) " +
                "values ('${comentario.description}', ${comentario.tareaId})"

        db.execSQL(queryString)
        db.close()
    }

    fun deleteComentariosByTareaId(context: Context, tareaId: Int){
        val connectionSQLHelper : ConnectionSQLHelper = ConnectionSQLHelper(context, "db_tareas", null, 1)
        val db : SQLiteDatabase = connectionSQLHelper.writableDatabase

        val queryString : String = "DELETE FROM ${Utilities.TABLA_COMENTARIOS} " +
                "WHERE ${Utilities.COMENTARIOS_CAMPO_TAREAID} = $tareaId"

        db.execSQL(queryString)
        db.close()
    }

}