package com.edu.eam.myapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.edu.eam.myapplication.R
import com.edu.eam.myapplication.databinding.ActivityDetalleBinding
import com.edu.eam.myapplication.model.Estudiante
import com.edu.eam.myapplication.servicios.EstudianteData


class DetalleActivity : AppCompatActivity() {

    lateinit var binding:ActivityDetalleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val codigoEstudiante = intent.getStringExtra("item_e")

        if (codigoEstudiante != null) {
            binding.infoEstudiante.text = EstudianteData.obtener(codigoEstudiante).toString()
            
        }

    }
}