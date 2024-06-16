package com.example.lifetracker.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifetracker.data.TaskRecord
import com.example.lifetracker.utils.milisecondToString
import com.example.myapplication.R

class RecordAdapter(private val onClick: (TaskRecord) -> Unit)
    : RecyclerView.Adapter<RecordAdapter.ViewHolder>() {

    // Container for later use
    var taskRecords: List<TaskRecord> = listOf()

    fun updateRecords(taskRecords: List<TaskRecord>) {
        Log.d("RecordAdapter : updateTaskRecords", "Incoming taskRecords: ${taskRecords}")
        this.taskRecords = taskRecords
        notifyDataSetChanged()
    }

    override fun getItemCount() = this.taskRecords.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("RecordAdapter : onCreateViewHolder", "Size of taskRecords on creation: ${taskRecords.size}")
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.record_detail_record_card_item,parent,false)
        return ViewHolder(view,onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("RecordAdapter : onBindViewHolder", "Bound at position: $position")
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
            Log.d("RecordAdapter : ViewHolder", "")
            itemView.setOnClickListener {
                currentRecord.let(onClick)
            }
        }

        fun bind(taskRecord: TaskRecord) {
            currentRecord = taskRecord

//            val ctx = itemView.context

            Log.d("RecordAdapter : Bind", "Incoming TaskRecord: ${taskRecord}")
            //Set values here
            stampTV.text = milisecondToString(currentRecord.stamp)
//            valueTV.text = currentRecord.value.toString()
            if (currentRecord.note != null) {
                noteBoolTV.text = "📋"
            } else {
                noteBoolTV.text = " "
            }
        }
    }

}