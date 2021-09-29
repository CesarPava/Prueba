package com.edu.eam.myapplication.servicios

import com.edu.eam.myapplication.model.Estudiante
import java.text.SimpleDateFormat

object EstudianteData {

    var listaEstudiantes: ArrayList<Estudiante> = ArrayList()

    init{
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        listaEstudiantes.add(Estudiante("1", sdf.parse("12/06/1999"), "Pedro", floatArrayOf(3.4F, 5.0F, 2.5F)))
        listaEstudiantes.add(Estudiante("2", sdf.parse("19/12/2000"), "Lucia", floatArrayOf(3.4F, 5.0F, 2.5F)))
        listaEstudiantes.add(Estudiante("3", sdf.parse("05/01/1993"), "Juanita", floatArrayOf(3.4F, 5.0F, 2.5F)))
        listaEstudiantes.add(Estudiante("4", sdf.parse("21/09/2008"), "Carlos", floatArrayOf(3.4F, 5.0F, 2.5F)))
        listaEstudiantes.add(Estudiante("5", sdf.parse("21/09/2008"), "María", floatArrayOf(3.4F, 5.0F, 2.5F)))
        listaEstudiantes.add(Estudiante("6", sdf.parse("21/09/2008"), "Luis", floatArrayOf(3.4F, 5.0F, 2.5F)))
        listaEstudiantes.add(Estudiante("7", sdf.parse("21/09/2008"), "Pepe", floatArrayOf(3.4F, 5.0F, 2.5F)))

    }

    fun agregar(estudiante: Estudiante) {
        listaEstudiantes.add(estudiante)
    }

    fun agregarAmigo(estudiante: Estudiante, amigo: Estudiante) {
        estudiante.amigos.add(amigo)
    }

    fun obtener(codigo: String): Estudiante? {
        for ( e in listaEstudiantes ) {
            if (e.codigo == codigo) {
                return e
            }
        }
        return null
    }


    fun eliminar(codigo: String) {
        for ( (i,e) in listaEstudiantes.withIndex() ) {
            if (e.codigo == codigo) {
                listaEstudiantes.remove(e)
                break
            }
        }

    }

    fun modificar( estudiante: Estudiante) {
        for ( (i,e) in listaEstudiantes.withIndex() ) {
            if (e.codigo ==estudiante.codigo) {
                listaEstudiantes[i] = estudiante
                break
            }
        }
    }
}