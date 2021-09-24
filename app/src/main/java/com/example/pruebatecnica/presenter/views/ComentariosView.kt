package com.example.pruebatecnica.presenter.views

import com.example.pruebatecnica.entitites.Comentario

interface ComentariosView {

    fun showComentariosResult(comentarios : ArrayList<Comentario>)
}