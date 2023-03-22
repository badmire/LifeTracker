package com.example.lifetracker.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifetracker.data.TaskRecord
import com.example.lifetracker.data.TaskTemplate
import com.example.lifetracker.utils.milisecondToString
import com.example.myapplication.R

class TaskAdapter(
    private val navigateToRecordDetailOnClick: (TaskTemplate) -> Unit,
    private val quickAddOnClick: (TaskTemplate) -> Unit
)
    : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    var taskTemplates: List<TaskTemplate> = listOf()
    var taskRecords: List<TaskRecord?> = listOf(null)
    var latestRecords: Map<String,TaskRecord?> = mapOf()

    fun updateTaskTemplates(taskTemplates: List<TaskTemplate>) {
        this.taskTemplates = taskTemplates
        notifyDataSetChanged()
    }

    fun updateLatestRecords(latestRecords: Map<String,TaskRecord?>) {
        this.latestRecords = latestRecords
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
        return ViewHolder(
            view,
            navigateToRecordDetailOnClick,
            quickAddOnClick
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            this.taskTemplates[position],
            taskRecords,
            latestRecords.get(taskTemplates[position].name)
        )
    }

    class ViewHolder(
        itemView: View,
        private val navigateToRecordDetailOnClick: (TaskTemplate) -> Unit,
        private val quickAddOnClick: (TaskTemplate) -> Unit
    )
        : RecyclerView.ViewHolder(itemView) {
        private val nameTV: TextView = itemView.findViewById(R.id.tv_name)
        private val iconTV: ImageButton = itemView.findViewById(R.id.tv_icon)
        private val lastStampTV: TextView = itemView.findViewById(R.id.tv_time_stamp)
        private val statusTV: TextView = itemView.findViewById(R.id.tv_status)

        private val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(itemView.context)

        private lateinit var currentTaskTemplate: TaskTemplate

        /*
         * Set up a click listener on this individual ViewHolder.  Call the provided onClick
         * function, passing the forecast item represented by this ViewHolder as an argument.
         */
        init {
            itemView.setOnClickListener {
                currentTaskTemplate.let(navigateToRecordDetailOnClick)
            }
            iconTV.setOnClickListener {
                currentTaskTemplate.let(quickAddOnClick)
            }
        }

        fun bind(
            taskTemplate: TaskTemplate,
            taskRecords: List<TaskRecord?>,
            taskRecord: TaskRecord?=null
        ) {
            currentTaskTemplate = taskTemplate

            val ctx = itemView.context

            // val units = sharedPrefs.getString(ctx.getString(R.string.pref_units_key), null)

            nameTV.text = taskTemplate.name
            if (taskRecord?.stamp != null) {
                lastStampTV.text = milisecondToString(taskRecord.stamp)
            }

            val currentStatus = taskRecords.filter {
                it?.template == currentTaskTemplate.name
            }.size

            Log.d("TaskAdapter : bind","Task: ${currentTaskTemplate.name}, Status: ${currentStatus.toString()}")

            statusTV.text =
                currentStatus.toString() +
                " / " +
                currentTaskTemplate.goal.toString()

            if (currentStatus >= currentTaskTemplate!!.goal!!) {
                statusTV.setTextColor(ctx.getColor(R.color.figma_teal))
            } else {
                statusTV.setTextColor(ctx.getColor(R.color.figma_red))
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