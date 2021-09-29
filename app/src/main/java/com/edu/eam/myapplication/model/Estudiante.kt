package com.edu.eam.myapplication.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*
import kotlin.collections.ArrayList

class Estudiante(var codigo: String?, var fechaNacimiento: Date, var nombre: String?, var notas: FloatArray?): Parcelable{
    var amigos: ArrayList<Estudiante> = ArrayList()

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readSerializable()  as Date,
        parcel.readString(),
        parcel.createFloatArray()
    ) {
        amigos = parcel.createTypedArrayList(CREATOR) as ArrayList<Estudiante>
    }



    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel?.writeString(codigo)
        parcel.writeSerializable(fechaNacimiento)
        parcel.writeString(nombre)
        parcel.writeFloatArray(notas)
        parcel.writeTypedList(amigos)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "Estudiante(codigo=$codigo, fechaNacimiento=$fechaNacimiento, nombre=$nombre, notas=${notas?.contentToString()}, amigos=$amigos)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Estudiante

        if (codigo != other.codigo) return false

        return true
    }

    override fun hashCode(): Int {
        return codigo?.hashCode() ?: 0
    }


    companion object CREATOR : Parcelable.Creator<Estudiante> {
        override fun createFromParcel(parcel: Parcel): Estudiante {
            return Estudiante(parcel)
        }

        override fun newArray(size: Int): Array<Estudiante?> {
            return arrayOfNulls(size)
        }
    }



}