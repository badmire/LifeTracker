package com.example.lifetracker.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifetracker.data.TaskRecord
import com.example.lifetracker.data.TaskTemplate
import com.example.myapplication.R

class RecordAdapter(private val onClick: (TaskRecord) -> Unit)
    : RecyclerView.Adapter<RecordAdapter.ViewHolder>() {
    var taskRecords: List<TaskRecord> = listOf()

    fun updateTaskRecords(taskRecords: List<TaskRecord>) {
        this.taskRecords = taskRecords
        notifyDataSetChanged()
    }

    override fun getItemCount() = this.taskRecords.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.record_card_item,parent,false)
        return ViewHolder(view,onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.taskRecords[position])
    }

    class ViewHolder(itemView: View, val onClick: (TaskRecord) -> Unit)
        : RecyclerView.ViewHolder(itemView) {

        //Grab hooks for views
        private val stampTV: TextView = itemView.findViewById<TextView>(R.id.record_card_stamp)
        private val valueTV: TextView = itemView.findViewById<TextView>(R.id.record_card_value)
        private val noteBoolTV: TextView = itemView.findViewById<TextView>(R.id.record_card_note_bool)

        // Get preferences incase they are needed
        private val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(itemView.context)

        // Set the contents of the card
        private lateinit var currentRecord: TaskRecord

        init {
            itemView.setOnClickListener {
                currentRecord.let(onClick)
            }
        }

        fun bind(taskRecord: TaskRecord) {
            currentRecord = taskRecord

            val ctx = itemView.context

            //Set values here
            stampTV.text = currentRecord.stamp.toString()
            valueTV.text = currentRecord.value.toString()
            if (currentRecord.note != null) {
                noteBoolTV.text = "X"
            } else {
                noteBoolTV.text = " "
            }
        }
    }
}