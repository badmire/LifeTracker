package com.example.lifetracker.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifetracker.data.TaskRecord
import com.example.lifetracker.data.TaskTemplate
import com.example.lifetracker.utils.milisecondToString
import com.example.myapplication.R

class TaskAdapter(private val onClick: (TaskTemplate) -> Unit)
    : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    var taskTemplates: List<TaskTemplate> = listOf()
    var taskRecords: List<TaskRecord?> = listOf(null)

    fun updateTaskTemplates(taskTemplates: List<TaskTemplate>) {
        this.taskTemplates = taskTemplates
        notifyDataSetChanged()
    }

    fun updateTaskRecords(taskRecords: List<TaskRecord?>) {
        this.taskRecords = taskRecords
        notifyDataSetChanged()
    }

    override fun getItemCount() = this.taskTemplates.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_list_item, parent, false)
        return ViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.taskTemplates[position])
        Log.d("TaskAdapter : onBindViewHolder","Size of incoming task records: ${this.taskRecords.size}")
        for(record in this.taskRecords) {
            if (record!!.template == this.taskTemplates[position].name) {
                holder.bind(this.taskTemplates[position], record)
                break
            }
        }
    }

    class ViewHolder(itemView: View, val onClick: (TaskTemplate) -> Unit)
        : RecyclerView.ViewHolder(itemView) {
        private val nameTV: TextView = itemView.findViewById(R.id.tv_name)
        private val iconTV: TextView = itemView.findViewById(R.id.tv_icon)
        private val lastStampTV: TextView = itemView.findViewById(R.id.tv_time_stamp)

        private val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(itemView.context)

        private lateinit var currentTaskTemplate: TaskTemplate

        /*
         * Set up a click listener on this individual ViewHolder.  Call the provided onClick
         * function, passing the forecast item represented by this ViewHolder as an argument.
         */
        init {
            itemView.setOnClickListener {
                currentTaskTemplate.let(onClick)
            }
        }

        fun bind(taskTemplate: TaskTemplate, taskRecord: TaskRecord?=null) {
            currentTaskTemplate = taskTemplate

            val ctx = itemView.context

            // val units = sharedPrefs.getString(ctx.getString(R.string.pref_units_key), null)

            nameTV.text = taskTemplate.name
            iconTV.text = "+"
            if (taskRecord?.stamp != null) {
                lastStampTV.text = milisecondToString(taskRecord.stamp)
            }


            Log.d("TaskAdapter : Bind","Name: ${taskTemplate.name} Last Stamp: ${taskRecord?.stamp.toString()}")
            //dateTV.text = ctx.getString(R.string.forecast_date, date)
            //timeTV.text = ctx.getString(R.string.forecast_time, date)
            //highTempTV.text = ctx.getString(
            //    R.string.forecast_temp,
            //    forecastPeriod.highTemp,
            //    tempUnitsDisplay
            //)
        }
    }
}