package com.example.cuadradosmedios

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MiAdaptador(private val listaDeDatos: MutableList<String>) : RecyclerView.Adapter<MiAdaptador.MiViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ri, parent, false)
        return MiViewHolder(view)
    }

    override fun onBindViewHolder(holder: MiViewHolder, position: Int) {
        val item = listaDeDatos[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return listaDeDatos.size
    }

    inner class MiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.tv_ri)

        fun bind(item: String) {
            textView.text = item
        }
    }
}