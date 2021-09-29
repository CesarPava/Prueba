package com.edu.eam.myapplication.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewParent
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.edu.eam.myapplication.R
import com.edu.eam.myapplication.databinding.ActivityPantalla3Binding
import com.edu.eam.myapplication.model.Estudiante
import com.edu.eam.myapplication.utils.Constantes

class Pantalla3Activity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityPantalla3Binding
    var estado: Boolean = true
    var pokemonSeleccionado:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPantalla3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val estudiante: Estudiante? = intent.getParcelableExtra("estudiante")
        if (estudiante != null) {
            Log.e("ACTIVIDAD3", estudiante.toString())
        }

        binding.imagen.setOnClickListener (this)

        binding.leerDatos.setOnClickListener(this)

        binding.txtNombre.isErrorEnabled = true
        binding.txtEdad.isErrorEnabled = true

        val lista = arrayOf("Pikachu", "Charmander", "Snorlax", "Gengar", "Mew", "Chikorita")

        //val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, lista)

        val adapter = ArrayAdapter.createFromResource(this, R.array.pokemon, android.R.layout.simple_spinner_item)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                pokemonSeleccionado = parent?.getItemAtPosition(position).toString()
                Constantes.mostrarMensaje(baseContext, "La opcion seleccionada es: ${parent?.getItemAtPosition(position)}")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    override fun onBackPressed() {
        val intent = intent
        intent.putExtra("RESPUESTA", "Mensaje enviado desde la pantalla 3")
        setResult(Activity.RESULT_OK, intent)
        finish()
        super.onBackPressed()
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            binding.imagen.id -> {
                if(estado) {
                    estado = false
                    binding.imagen.setImageResource(R.drawable.img2)
                } else {
                    estado = true
                    binding.imagen.setImageResource(R.drawable.img1)
                }

            }

            binding.leerDatos.id -> {
                val nombre = binding.nombre.text
                val edad = binding.edad.text
                if( nombre.length > 10) {
                    binding.txtNombre.error = "Máximo 10 caracteres"
                } else {
                    binding.txtNombre.error = null
                }
                Constantes.mostrarMensaje(this, "${nombre} y ${edad} y ${pokemonSeleccionado}")
            }
        }
    }
}