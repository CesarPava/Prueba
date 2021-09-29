package com.edu.eam.myapplication.activity

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import com.edu.eam.myapplication.databinding.ActivityCrearBinding
import com.edu.eam.myapplication.model.Estudiante
import com.edu.eam.myapplication.utils.Constantes
import java.lang.Float
import java.util.*

class CrearActivity : AppCompatActivity(), View.OnClickListener, DatePickerDialog.OnDateSetListener{

    lateinit var binding:ActivityCrearBinding

    var day = 0
    var month = 0
    var year = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGuardarEstudiante.setOnClickListener(this)

        pickDate()
    }

    private fun getDateTimeCalendar() {
        val cal: Calendar = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
    }

    private fun pickDate() {
        binding.btnDate.setOnClickListener {
            getDateTimeCalendar()

            DatePickerDialog(this, this, year, month, day).show()
        }
    }


    override fun onClick(v: View?) {
        when(v?.id) {
            binding.btnGuardarEstudiante.id -> {
                val codigo = binding.codigo.text.toString()
                val nombre = binding.nombre.text.toString()
                val fecha = Date((savedYear - 1900), (savedMonth - 1), savedDay)

                val nota1 = Float.parseFloat(binding.nota1.text.toString())
                val nota2 = Float.parseFloat(binding.nota2.text.toString())
                val nota3 = Float.parseFloat(binding.nota3.text.toString())

                var estudiante: Estudiante = Estudiante(codigo, fecha, nombre, floatArrayOf(nota1, nota2, nota3))

                val intent = intent
                intent.putExtra("estudianteNuevo", estudiante)
                setResult(200, intent)
                finish()
            }
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month + 1
        savedYear = year

        getDateTimeCalendar()

        binding.fechaSeleccionada.text = "$savedDay - $savedMonth - $savedYear"

    }
}