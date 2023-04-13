package com.example.lifetracker.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class qualitativeOptionsAdapter : RecyclerView.Adapter<qualitativeOptionsAdapter.ViewHolder>() {
    // Holder for options
    var curOptions: MutableList<String> = mutableListOf()

    override fun getItemCount() = this.curOptions.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.qual_option_card,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.curOptions[position])
        holder.itemView.findViewById<Button>(R.id.option_del_btn)
            .setOnClickListener {
                curOptions.remove(this.curOptions[position])
            }
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private lateinit var curOption: String

        init {
        }

        fun bind(curOption: String) {
            this.curOption = curOption
        }
    }
}