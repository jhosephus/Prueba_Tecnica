package com.example.pruebatecnica.view.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import com.example.pruebatecnica.R
import com.example.pruebatecnica.entitites.Tarea
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class NuevaTareaDialog (_tarea: Tarea, _isCreating: Boolean, _onDialogResultListener: onDialogResultListener) : AppCompatDialogFragment(), DatePickerDialog.OnDateSetListener  {

    val mListener : onDialogResultListener = _onDialogResultListener
    val isCreating : Boolean = _isCreating
    val mTarea : Tarea = _tarea

    var dateWasPicked = false

    lateinit var editTextTitulo: EditText
    lateinit var editTextContenido: EditText
    lateinit var textView: TextView

    var date : Map<String,Int> = hashMapOf()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder: AlertDialog.Builder = AlertDialog.Builder(activity)

        val layoutInflater: LayoutInflater = requireActivity().layoutInflater
        val view: View = layoutInflater.inflate(R.layout.dialog_nuevatarea, null)

        builder.setView(view)
                .setTitle(if(isCreating) "Nueva Tarea" else "Editar Actividad")
                .setNegativeButton("Cancelar", object : DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        //
                    }
                })
                .setPositiveButton("Aceptar", object : DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        //
                        val calendar : Calendar = Calendar.getInstance()
                        val startDate : Date = Date(calendar.timeInMillis)
                        if(isCreating && dateWasPicked){
                            calendar.set(Calendar.YEAR, date.get("year")!!)
                            calendar.set(Calendar.MONTH, date.get("month")!!)
                            calendar.set(Calendar.DAY_OF_MONTH, date.get("day")!!)
                            val endDate: Date = Date(calendar.timeInMillis)

                            val tarea: Tarea = Tarea(0, editTextTitulo.text.toString(), editTextContenido.text.toString(), startDate, endDate, false)
                            mListener.onPositiveResult(tarea)
                        } else {
                            if (dateWasPicked){
                                calendar.set(Calendar.YEAR, date.get("year")!!)
                                calendar.set(Calendar.MONTH, date.get("month")!!)
                                calendar.set(Calendar.DAY_OF_MONTH, date.get("day")!!)
                            }
                            val endDate: Date = Date(calendar.timeInMillis)
                            val tarea: Tarea = Tarea(mTarea.id, editTextTitulo.text.toString(), editTextContenido.text.toString(), mTarea.startDate, if(dateWasPicked) endDate else mTarea.endDate, false)
                            mListener.onPositiveResult(tarea)
                        }

                    }
                })
        editTextTitulo = view.findViewById(R.id.nuevaTareaDialog_titulo)
        editTextContenido = view.findViewById(R.id.nuevaTareaDialog_contenido)
        textView = view.findViewById(R.id.nuevaTareaDialog_fecha)
        textView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                val datePicker: DialogFragment = DatePickerFragment()
                datePicker.show(childFragmentManager, "DatePickerDialog")
            }
        })

        val simpleDate : SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")

        if(!isCreating){
            editTextTitulo.setText(mTarea.title)
            editTextContenido.setText(mTarea.description)
            textView.text = "Finaliza: ${simpleDate.format(mTarea.endDate)}"
        }

        return builder.create()
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        dateWasPicked = true

        val dayString: String = String.format("%02d", p3)
        val monthString: String = String.format("%02d", p2)
        val yearString: String = String.format("%04d", p1)

        date = hashMapOf("day" to p3, "month" to p2, "year" to p1)

        textView.text = "Finaliza ($dayString/$monthString/$yearString)"
        //Log.i("My Tag", "Finaliza: $dayString/$monthString/$yearString")
    }

    interface onDialogResultListener {
        fun onPositiveResult(tarea: Tarea)
    }

}