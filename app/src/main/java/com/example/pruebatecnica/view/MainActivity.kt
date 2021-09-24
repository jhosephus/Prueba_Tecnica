package com.example.pruebatecnica.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebatecnica.R
import com.example.pruebatecnica.entitites.Comentario
import com.example.pruebatecnica.entitites.Tarea
import com.example.pruebatecnica.presenter.presenters.implementations.ComentariosPresenterImpl
import com.example.pruebatecnica.presenter.presenters.implementations.TareasPresenterImpl
import com.example.pruebatecnica.presenter.presenters.interfaces.ComentariosPresenter
import com.example.pruebatecnica.presenter.presenters.interfaces.TareasPresenter
import com.example.pruebatecnica.presenter.views.ComentariosView
import com.example.pruebatecnica.presenter.views.TareasView
import com.example.pruebatecnica.view.dialog.NuevaTareaDialog
import com.example.pruebatecnica.view.recyclerview.TareaAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), TareasView, ComentariosView {

    val tareasPresenter : TareasPresenter = TareasPresenterImpl(this)
    val comentariosPresenter : ComentariosPresenter = ComentariosPresenterImpl(this)

    lateinit var recyclerView: RecyclerView
    lateinit var floatingActionButton: FloatingActionButton
    var finishedVisible: Boolean = false
    var firstCallRecyclerView: Boolean = true

    var mData : ArrayList<Tarea> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recyclerView = findViewById(R.id.mainActivity_recyclerView)
        recyclerView.setHasFixedSize(true)

        //Presenter
        tareasPresenter.getTareas(this)

        floatingActionButton = findViewById(R.id.mainActivity_fab)
        floatingActionButton.setOnClickListener {
            openNuevaTareaDialog()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.actionbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionbar_item1 -> {

                Log.i("My Tag", "Actionbar item click")

                if (!finishedVisible){
                    Log.i("My Tag", "Actionbar item click: true")
                    finishedVisible = true
                    item.setIcon(R.drawable.ic_invisible)
                    item.setTitle("Completados")
                    updateRecyclerView(mData, true)
                } else {
                    Log.i("My Tag", "Actionbar item click: false")
                    finishedVisible = false
                    item.setIcon(R.drawable.ic_visible)
                    item.setTitle("Pendientes")
                    updateRecyclerView(mData, false)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    fun buildRecyclerView(mData: ArrayList<Tarea>, statusFilter: Boolean) {

        var numbers: ArrayList<Int> = ArrayList()
        var tareaList : ArrayList<Tarea> = ArrayList()
        var numerationList: ArrayList<Int> = ArrayList()

        for (index: Int in mData.indices){
            numbers.add(mData.size - index)
            if(mData[index].finishStatus == statusFilter){
                numerationList.add(numbers[index])
                tareaList.add(mData[index])
            }
        }

        val mLayoutManager: LinearLayoutManager = LinearLayoutManager(this)
        val mAdapter: TareaAdapter = TareaAdapter(tareaList, numerationList, object : TareaAdapter.OnItemClickListener {
            override fun OnItemClick(position: Int) {
                Log.i("My Tag", position.toString())
                val intent: Intent = Intent(this@MainActivity, TareaDetailsActivity::class.java)
                intent.putExtra("tarea", tareaList[position])
                startActivityForResult(intent, 1)
            }

            override fun OnCheckBoxClick(position: Int) {
                val selectedTarea : Tarea = tareaList[position]
                val updatedTarea : Tarea = Tarea(selectedTarea.id, selectedTarea.title, selectedTarea.description, selectedTarea.startDate, selectedTarea.endDate, !selectedTarea.finishStatus)
                tareasPresenter.updateTarea(this@MainActivity, updatedTarea)
                tareasPresenter.getTareas(this@MainActivity)
            }

        })
        recyclerView.setLayoutManager(mLayoutManager)
        recyclerView.setAdapter(mAdapter)
    }

    fun updateRecyclerView(mData: ArrayList<Tarea>, statusFilter: Boolean){
        var numbers: ArrayList<Int> = ArrayList()
        var tareaList : ArrayList<Tarea> = ArrayList()
        var numerationList: ArrayList<Int> = ArrayList()

        for (index: Int in mData.indices){
            numbers.add(mData.size - index)
            if(mData[index].finishStatus == statusFilter){
                numerationList.add(numbers[index])
                tareaList.add(mData[index])
            }
        }

        val mAdapter: TareaAdapter = TareaAdapter(tareaList, numerationList, object : TareaAdapter.OnItemClickListener {
            override fun OnItemClick(position: Int) {
                val intent: Intent = Intent(this@MainActivity, TareaDetailsActivity::class.java)
                intent.putExtra("tarea", tareaList[position])
                startActivityForResult(intent, 1)
            }

            override fun OnCheckBoxClick(position: Int) {
                val selectedTarea : Tarea = tareaList[position]
                val updatedTarea : Tarea = Tarea(selectedTarea.id, selectedTarea.title, selectedTarea.description, selectedTarea.startDate, selectedTarea.endDate, !selectedTarea.finishStatus)
                tareasPresenter.updateTarea(this@MainActivity, updatedTarea)
                tareasPresenter.getTareas(this@MainActivity)
            }

        })
        recyclerView.setAdapter(mAdapter)

    }

    fun openNuevaTareaDialog(){
        val dialog: NuevaTareaDialog = NuevaTareaDialog(Tarea(0,"","", Date(0), Date(0), false),true, object : NuevaTareaDialog.onDialogResultListener{
            override fun onPositiveResult(tarea: Tarea) {
                tareasPresenter.createTarea(this@MainActivity, tarea)
            }
        })
        dialog.show(supportFragmentManager, "NuevaTareaDialog")
    }

    //Implementacion TareasView
    override fun showResult(tareas: ArrayList<Tarea>) {

        mData = tareas

        if (firstCallRecyclerView){
            buildRecyclerView(tareas, finishedVisible)
        } else {
            firstCallRecyclerView = false
            Log.i("My Tag", "showResult: ${firstCallRecyclerView.toString()}")
            updateRecyclerView(tareas, finishedVisible)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.i("My Tag", "OnActivityResult")

        when(resultCode){
            RESULT_CANCELED -> {
                val tareaId: Int = data?.getIntExtra("id", 99) ?: 88
                tareasPresenter.deleteTareaById(this@MainActivity, tareaId)
                tareasPresenter.getTareas(this@MainActivity)
                comentariosPresenter.deleteComentariosByTareaId(this@MainActivity, tareaId)
            }
            RESULT_OK -> {
                tareasPresenter.getTareas(this@MainActivity)
            }
        }

    }

    override fun showComentariosResult(comentarios: ArrayList<Comentario>) {

    }


}