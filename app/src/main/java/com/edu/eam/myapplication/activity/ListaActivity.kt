package com.edu.eam.myapplication.activity

import android.content.Intent
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edu.eam.myapplication.R
import com.edu.eam.myapplication.adapter.EstudianteAdapter
import com.edu.eam.myapplication.databinding.ActivityListaBinding
import com.edu.eam.myapplication.model.Estudiante
import com.edu.eam.myapplication.servicios.EstudianteData
import com.edu.eam.myapplication.utils.Constantes
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ListaActivity : AppCompatActivity() {

    lateinit var binding: ActivityListaBinding

    lateinit var listaEstudiantes: ArrayList<Estudiante>

    lateinit var adapter:EstudianteAdapter

    lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            onActivityResult(it.resultCode, it)
        }

        listaEstudiantes = EstudianteData.listaEstudiantes



        adapter = EstudianteAdapter(listaEstudiantes, this)
        binding.listaEstudiante.adapter = adapter
        binding.listaEstudiante.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val simpleCallback: ItemTouchHelper.SimpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                val estudiante = EstudianteData.listaEstudiantes[pos]
                when(direction) {
                    ItemTouchHelper.LEFT -> {
                        EstudianteData.eliminar(estudiante.codigo!!)
                        adapter.notifyItemRemoved((pos))
                        Snackbar.make(binding.listaEstudiante, "Estudiante: ${estudiante.nombre}", Snackbar.LENGTH_LONG).setAction("Deshacer", View.OnClickListener {
                            EstudianteData.listaEstudiantes.add(pos, estudiante)
                            adapter.notifyItemInserted(pos)
                        }).show()
                    }
                    ItemTouchHelper.RIGHT ->editarEstudiantes(estudiante)


                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(baseContext, R.color.rojo))
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(baseContext, R.color.azul))
                    .addSwipeLeftActionIcon(android.R.drawable.ic_menu_delete)
                    .addSwipeRightActionIcon(android.R.drawable.ic_menu_edit)
                    .create().decorate()
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }

        }

        ItemTouchHelper(simpleCallback).attachToRecyclerView( binding.listaEstudiante )

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_estudiante, menu)

        val itemBusqueda = menu.findItem(R.id.menu_buscar)
        val searchView:androidx.appcompat.widget.SearchView = itemBusqueda.actionView as androidx.appcompat.widget.SearchView

        searchView.queryHint = "Nombre del estudiante..."

        searchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    adapter.filter.filter(newText)
                }
                return false
            }

        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_agregar -> {
                val intent = Intent(this, CrearActivity::class.java)
                resultLauncher.launch(intent)
            }

            R.id.menu_eliminar -> {
                EstudianteData.eliminar("1")
                adapter.notifyItemRemoved(0)
            }

            R.id.menu_modificar -> {
                val aux = listaEstudiantes[1]
                listaEstudiantes[1] = listaEstudiantes[2]
                listaEstudiantes[2] = aux
                adapter.notifyItemMoved(1, 2)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onActivityResult(resultCode: Int, result: ActivityResult){
        val data = result.data?.extras
        if (resultCode == 200) {
            val estudiante = data?.getParcelable<Estudiante>("estudianteNuevo")
            if (estudiante != null) {
                listaEstudiantes.add(estudiante)
                Constantes.mostrarMensaje(this, "Nuevo Estudiante!")
                adapter.notifyItemInserted(listaEstudiantes.size)
            }

        }
        if(resultCode==777){
            val estudiante=data?.getParcelable<Estudiante>("estudianteModificar")
            if(estudiante!=null){
                EstudianteData.modificar(estudiante)
                adapter.notifyDataSetChanged();
            }
        }
    }

    fun editarEstudiantes(estudiante: Estudiante){
        val intent= Intent(this,ModificarActivity::class.java)
        intent.putExtra(Constantes.KEY_EDITAR,estudiante)
        startActivity((intent))

    }

}