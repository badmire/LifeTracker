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

class TaskAdapter(private val onClick: (List<Task>) -> Unit)
    : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    var tasks: List<TaskTemplate> = listOf()

    fun updateTasks(tasks: List<Task?>) {
        tasks = tasks
        notifyDataSetChanged()
    }

    override fun getItemCount() = this.tasks.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_list_item, parent, false)
        return ViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.tasks[position])
    }

    class ViewHolder(itemView: View, val onClick: (Task) -> Unit)
        : RecyclerView.ViewHolder(itemView) {
        // TODO: Fix these to be what we want them to be, based on the XML
        private val nameTV: TextView = itemView.findViewById(R.id.tv_name)
        private val iconTV: TextView = itemView.findViewById(R.id.tv_icon)
        private val lastStampTV: TextView = itemView.findViewById(R.id.tv_time_stamp)

        private val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(itemView.context)

        private lateinit var currentTask: Task

        /*
         * Set up a click listener on this individual ViewHolder.  Call the provided onClick
         * function, passing the forecast item represented by this ViewHolder as an argument.
         */
        init {
            itemView.setOnClickListener {
                currentTask.let(onClick)
            }
        }

        fun bind(task: Task) {
            currentTask = task

            val ctx = itemView.context
            // val date = openWeatherEpochToDate(forecastPeriod.epoch, forecastCity?.tzOffsetSec ?: 0)

            /*
             * Figure out the correct temperature and wind units to display for the current
             * setting of the units preference.
             */
            val units = sharedPrefs.getString(ctx.getString(R.string.pref_units_key), null)

            dateTV.text = ctx.getString(R.string.forecast_date, date)
            timeTV.text = ctx.getString(R.string.forecast_time, date)
            highTempTV.text = ctx.getString(
                R.string.forecast_temp,
                forecastPeriod.highTemp,
                tempUnitsDisplay
            )
            lowTempTV.text = ctx.getString(
                R.string.forecast_temp,
                forecastPeriod.lowTemp,
                tempUnitsDisplay
            )
            popTV.text = ctx.getString(R.string.forecast_pop, forecastPeriod.pop)

            /*
             * Load forecast icon into ImageView using Glide: https://bumptech.github.io/glide/
             */
            Glide.with(ctx)
                .load(forecastPeriod.iconUrl)
                .into(iconIV)
        }
    }
}