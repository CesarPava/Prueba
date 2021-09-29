package com.edu.eam.myapplication.activity

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.DatePicker
import com.edu.eam.myapplication.databinding.ActivityModificarBinding
import com.edu.eam.myapplication.model.Estudiante
import com.edu.eam.myapplication.utils.Constantes
import java.lang.Float
import java.util.*

class ModificarActivity : AppCompatActivity(), View.OnClickListener, DatePickerDialog.OnDateSetListener {

    lateinit var binding: ActivityModificarBinding

    var day = 0
    var month = 0
    var year = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityModificarBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnModificarEstudiante.setOnClickListener(this)

        val estudiante: Estudiante? = intent.getParcelableExtra(Constantes.KEY_EDITAR)

       binding.codigo.setText(estudiante?.codigo.toString())

        binding.nombre.setText(estudiante!!.nombre.toString())

       binding.nota1.setText(estudiante?.notas?.get(0)!!.toString())

        binding.nota2.setText(estudiante?.notas?.get(1)!!.toString())

        binding.nota3.setText(estudiante?.notas?.get(2)!!.toString())

        Log.v("MODIFICAR","$estudiante")

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
            binding.btnModificarEstudiante.id -> {
                val codigo = binding.txtCodigo.toString()
                val nombre = binding.txtNombre.toString()
                val fecha = Date((savedYear - 1900), (savedMonth - 1), savedDay)

                val nota1 = Float.parseFloat(binding.nota1.text.toString())
                val nota2 = Float.parseFloat(binding.nota2.text.toString())
                val nota3 = Float.parseFloat(binding.nota3.text.toString())


                var estudiante: Estudiante = Estudiante(codigo, fecha, nombre, floatArrayOf(nota1, nota2, nota3))

                val intent = intent
                intent.putExtra("estudianteModificar", estudiante)
                setResult(777, intent)
                finish()
            }
        }
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        TODO("Not yet implemented")
    }
}