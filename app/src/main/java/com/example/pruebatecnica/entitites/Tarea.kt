package com.example.pruebatecnica.entitites

import java.io.Serializable
import java.util.*

class Tarea(
    val id: Int,
    val title:String,
    val description:String,
    val startDate:Date,
    val endDate:Date,
    val finishStatus: Boolean) : Serializable {

}