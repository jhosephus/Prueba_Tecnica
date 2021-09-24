package com.example.pruebatecnica.view

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
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
import com.example.pruebatecnica.view.recyclerview.ComentarioAdapter
import com.example.pruebatecnica.view.recyclerview.TareaAdapter
import java.text.DateFormat
import java.text.SimpleDateFormat

class TareaDetailsActivity : AppCompatActivity(), TareasView, ComentariosView {

    val tareasPresenter : TareasPresenter = TareasPresenterImpl(this)
    val comentariosPresenter : ComentariosPresenter = ComentariosPresenterImpl(this)

    lateinit var creationTextView: TextView
    lateinit var endTextView: TextView
    lateinit var contentTextView: TextView
    lateinit var commentsTitleTextView: TextView
    lateinit var inputText: EditText
    lateinit var recyclerView: RecyclerView

    lateinit var tarea: Tarea

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarea_details)

        val intent: Intent = intent
        tarea = intent.getSerializableExtra("tarea") as Tarea

        recyclerView = findViewById(R.id.tareaDetailsActivity_recyclerView)

        creationTextView = findViewById(R.id.tareaDetailsActivity_creationDate)
        endTextView = findViewById(R.id.tareaDetailsActivity_endDate)
        contentTextView = findViewById(R.id.tareaDetailsActivity_content)
        commentsTitleTextView = findViewById(R.id.tareaDetailsActivity_commentsTitle)
        inputText = findViewById(R.id.tareaDetailsActivity_input)
        inputText.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                val comentario : Comentario = Comentario(0, inputText.text.toString(), tarea.id)
                comentariosPresenter.createComentario(this@TareaDetailsActivity, comentario)
                inputText.setText("")
                comentariosPresenter.getComentariosByTareaId(this@TareaDetailsActivity, tarea.id)
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(inputText.applicationWindowToken, 0)
                return true
            }
        })

        fillForm(tarea)

        comentariosPresenter.getComentariosByTareaId(this, tarea.id)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.actionbar_tarea_details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.actionbarTareaDetails_item1 -> {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this@TareaDetailsActivity)
                builder.setMessage("Recuerda: Se van a borrar todos los comentarios")
                        .setTitle("¿Seguro quieres eliminar ${tarea.title}?")
                        .setPositiveButton("Aceptar", object : DialogInterface.OnClickListener{
                            override fun onClick(p0: DialogInterface?, p1: Int) {
                                var intent: Intent = Intent()
                                intent.putExtra("id", tarea.id)
                                setResult(RESULT_CANCELED, intent)
                                finish()
                            }
                        })
                        .setNegativeButton("Cancelar", object : DialogInterface.OnClickListener{
                            override fun onClick(p0: DialogInterface?, p1: Int) {
                            }
                        })

                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
            R.id.actionbarTareaDetails_item2 -> {
                openEditarTareaDialog()
            }
        }

        return true
    }

    fun fillForm(tarea: Tarea){

        val simpleDate : SimpleDateFormat = SimpleDateFormat("dd/MM")
        val creationDateString: String = simpleDate.format(tarea.startDate)
        creationTextView.text = "Creada: $creationDateString"
        val endDateString: String = simpleDate.format(tarea.endDate)
        endTextView.text = "Finaliza: $endDateString"
        contentTextView.text = tarea.description

    }

    fun showComentarios(comentarios: ArrayList<Comentario>){
        commentsTitleTextView.text = "Comentarios (${comentarios.size})"

        val mLayoutManager: LinearLayoutManager = LinearLayoutManager(this)
        val mAdapter : ComentarioAdapter = ComentarioAdapter(comentarios)
        recyclerView.setLayoutManager(mLayoutManager)
        recyclerView.setAdapter(mAdapter)
    }

    fun openEditarTareaDialog(){
        val dialog: NuevaTareaDialog = NuevaTareaDialog(tarea,false, object : NuevaTareaDialog.onDialogResultListener{
            override fun onPositiveResult(tarea: Tarea) {
                tareasPresenter.updateTarea(this@TareaDetailsActivity, tarea)
                fillForm(tarea)
                setResult(RESULT_OK)
            }
        })
        dialog.show(supportFragmentManager, "EditarTareaDialog")
    }

    //TareasView implementation
    override fun showResult(tareas: ArrayList<Tarea>) {

    }

    //ComentariosView implementation
    override fun showComentariosResult(comentarios: ArrayList<Comentario>) {
        showComentarios(comentarios)
    }

}