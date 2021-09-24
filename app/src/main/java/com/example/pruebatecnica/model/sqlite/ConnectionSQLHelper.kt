package com.example.pruebatecnica.model.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ConnectionSQLHelper(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(sqLiteDatabase: SQLiteDatabase?) {
        sqLiteDatabase?.execSQL(Utilities.CREAR_TABLA_TAREAS)
        sqLiteDatabase?.execSQL(Utilities.CREAR_TABLA_COMENTARIOS)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        sqLiteDatabase?.execSQL("DROP TABLE IF EXISTS tareas")
        sqLiteDatabase?.execSQL("DROP TABLE IF EXISTS comentarios")
        onCreate(sqLiteDatabase)
    }
}