package com.example.pruebatecnica.view.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebatecnica.R
import com.example.pruebatecnica.entitites.Tarea

class TareaAdapter(private val data: ArrayList<Tarea>,
                   val numerationList: ArrayList<Int>,
                   val mListener: OnItemClickListener) : RecyclerView.Adapter<TareaAdapter.TareaViewHolder>() {

    interface OnItemClickListener {
        fun OnItemClick(position: Int)
        fun OnCheckBoxClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TareaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tarea, parent, false)

        return TareaViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: TareaViewHolder, position: Int) {
        val tarea: Tarea = data[position]
        holder.title.text = tarea.title
        holder.description.text = tarea.description
        val startDateDay: String = String.format("%02d", tarea.startDate.date)
        val startDateMonth: String= String.format("%02d", tarea.startDate.month + 1)
        holder.startDate.text = "Creada: $startDateDay/$startDateMonth"
        val endDateDay: String = String.format("%02d", tarea.endDate.date)
        val endDateMonth: String= String.format("%02d", tarea.endDate.month + 1)
        holder.endDate.text = "Finaliza: $endDateDay/$endDateMonth"
        holder.numerator.text = String.format("%02d", numerationList[position])
        holder.checkBox.isChecked = tarea.finishStatus
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class TareaViewHolder(view: View, mListener: OnItemClickListener) : RecyclerView.ViewHolder(view) {

        val numerator: TextView
        val title: TextView
        val description: TextView
        val startDate: TextView
        val endDate: TextView
        val checkBox: CheckBox

        init {
            numerator = view.findViewById(R.id.itemTarea_numerator)
            title = view.findViewById(R.id.itemTarea_title)
            description = view.findViewById(R.id.itemTarea_description)
            startDate = view.findViewById(R.id.itemTarea_startDate)
            endDate = view.findViewById(R.id.itemTarea_endDate)
            checkBox = view.findViewById(R.id.itemTarea_checkbox)

            view.setOnClickListener {
                if (mListener != null) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.OnItemClick(position)
                    }
                }
            }
/*
            checkBox.setOnCheckedChangeListener( object : CompoundButton.OnCheckedChangeListener{
                override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                    if (mListener != null) {
                        val position = adapterPosition
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.OnCheckBoxClick(position)
                        }
                    }
                }
            })
  */
            checkBox.setOnClickListener(object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    if (mListener != null) {
                        val position = adapterPosition
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.OnCheckBoxClick(position)
                        }
                    }
                }

            })


        }

    }
}