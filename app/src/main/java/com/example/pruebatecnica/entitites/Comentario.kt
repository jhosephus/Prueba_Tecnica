package com.example.pruebatecnica.entitites

import java.io.Serializable

class Comentario(
        val id: Int,
        val description: String,
        val tareaId: Int
) : Serializable {
}