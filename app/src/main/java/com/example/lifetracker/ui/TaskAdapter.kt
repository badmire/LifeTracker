package com.example.lifetracker.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifetracker.data.TaskRecord
import com.example.lifetracker.data.TaskTemplate
import com.example.myapplication.R

class TaskAdapter(private val onClick: (TaskTemplate) -> Unit)
    : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    var taskTemplates: List<TaskTemplate> = listOf()
    var taskRecords: List<TaskRecord?> = listOf(null)

    fun updateTasks(taskTemplates: List<TaskTemplate>, taskRecords: List<TaskRecord?>) {
        this.taskTemplates = taskTemplates
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
        holder.bind(this.taskTemplates[position], this.taskRecords[position])
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

        fun bind(taskTemplate: TaskTemplate, taskRecord: TaskRecord?) {
            currentTaskTemplate = taskTemplate

            val ctx = itemView.context
            // val date = openWeatherEpochToDate(forecastPeriod.epoch, forecastCity?.tzOffsetSec ?: 0)

            /*
             * Figure out the correct temperature and wind units to display for the current
             * setting of the units preference.
             */
            // val units = sharedPrefs.getString(ctx.getString(R.string.pref_units_key), null)

            nameTV.text = taskTemplate.name
            lastStampTV.text = taskRecord?.stamp.toString()
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