package com.edu.eam.myapplication.utils

import android.content.Context
import android.widget.Toast

object Constantes {
    //Constantes del main activity
    val KEY_NOMBRE = "nombre_text_view"
    val KEY_ESTUDIANTE = "estudiante"
    val KEY_EDITAR = "estudiante_editar"
    val KEY_POSICION = "estudiante_posicion"

    fun mostrarMensaje(context: Context, mensaje: String){
        Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show()
    }
}