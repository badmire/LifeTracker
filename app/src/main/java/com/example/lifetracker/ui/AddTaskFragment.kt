package com.example.lifetracker.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifetracker.data.TaskTemplate
import com.example.lifetracker.utils.showToast
import com.example.myapplication.R
import com.google.android.material.switchmaterial.SwitchMaterial

class AddTaskFragment : Fragment(R.layout.activity_add_task){
    // Instantiate viewModel and adapter
    private val viewModel: TaskViewModel by viewModels()

    // Grab hooks for views
    // Universal
    private lateinit var nameTV : TextView
    private lateinit var nameET : EditText
    private lateinit var submitBtn : Button
    private lateinit var task_spinner : Spinner
    private lateinit var period_spinner : Spinner
    private lateinit var taskSpecificContainer : LinearLayout

    // Task specific
    private lateinit var goalTV : TextView
    private lateinit var goalET : EditText
    private lateinit var directionToggle : SwitchMaterial
    private lateinit var optionsBtn : Button
    private lateinit var qualRV : RecyclerView
    private val qualAdapter = QualitativeOptionsAdapter()

    // Containers for task creation
    private var optionsList : MutableList<String> = mutableListOf()
    private var taskTypeCode = 1 //Task code for later, default to spinner default value
    private var periodInDays = 1 //Window for calculating success later for individual tasks

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Required Magic
        super.onViewCreated(view, savedInstanceState)

        // Find views
        nameTV = view.findViewById(R.id.add_task_name_title)
        nameET = view.findViewById(R.id.add_task_name_edit)
        submitBtn = view.findViewById(R.id.add_task_submit_button)
        task_spinner = view.findViewById(R.id.task_type_spinner)
        period_spinner = view.findViewById(R.id.task_period_spinner)
        taskSpecificContainer = view.findViewById(R.id.task_type_container)


        // type specific values TODO: Move into the onselected listener
//        goalTV = view.findViewById(R.id.add_task_goal_title)
//        goalET = view.findViewById(R.id.add_task_goal_edit)

        // Options for type spinner
        var task_types = arrayOf("Count to Goal", "Qualitative", "Range", "boolean (Need better name)")

        // Options for period spinner
        var periods = arrayOf("Daily","Weekly","Monthly","Yearly")

        // Build dropdown, https://www.digitalocean.com/community/tutorials/android-spinner-using-kotlin
        // Initialize adapters
        var task_aa = ArrayAdapter(
            requireContext(), // Pass in main activity context
            R.layout.add_task_spinner_text_item, // Layout to be inflated for each item
            task_types // List of items
        )

        var period_aa = ArrayAdapter(
            requireContext(),
            R.layout.add_task_spinner_text_item,
            periods
        )

        // Fill in spinners view and define behavior
        with(task_spinner){
            adapter = task_aa
            setSelection(0,false)
            prompt = "Select task type"
        }

        with(period_spinner){
            adapter = period_aa
            setSelection(0,false)
            prompt = "Select time period"
        }

        // Inflate task spinner with default task type
        taskSpecificContainer.addView(
            LayoutInflater
                .from(requireContext())
                .inflate(
                    R.layout.add_task_layout_countup,
                    null
                )
        )
        goalET = view.findViewById(R.id.add_task_goal_edit)
        directionToggle = view.findViewById(R.id.add_task_direction_toggle)

        // Set change in period on spinner switch
        period_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                periodInDays = when (period_spinner.selectedItem.toString()) {
                    "Daily" -> 1
                    "Weekly" -> 7
                    "Monthly" -> 30
                    "Yearly" -> 365
                    else -> 1
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        // Set behavior on task spinner switch
        task_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                showToast(requireContext(),"Nothing Selected")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Set typecode for later
                taskTypeCode = when (task_spinner.selectedItem.toString()) {
                    "Count to Goal" -> 1
                    "Qualitative" -> 3
                    "Range" -> 4
                    "boolean (Need better name)" -> 5
                    else -> 0
                }
                // Remove previous view
                taskSpecificContainer.removeAllViews()

                // Inflate layout for different task types
                when (taskTypeCode) {
                    1 -> { // Count up
                        taskSpecificContainer.addView(
                            LayoutInflater
                                .from(requireContext())
                                .inflate(
                                    R.layout.add_task_layout_countup,
                                    null
                                )
                            )
                        // Wire up goal edit
                        goalET = view!!.findViewById(R.id.add_task_goal_edit)
                        // Wire up direction toggle
                        directionToggle = view.findViewById(R.id.add_task_direction_toggle)
                        // Wire submit button
                        submitBtn.setOnClickListener {addNewCountUp()}
                    }
                    3 -> { // Qualitative
                        Log.d("Add Task","The qualitative branch has been called")
                        taskSpecificContainer.addView(
                            LayoutInflater
                                .from(requireContext())
                                .inflate(
                                    R.layout.add_task_layout_qualitative,
                                    null
                                )
                        )

                        qualRV = taskSpecificContainer.findViewById(R.id.add_task_qual_rv)
                        qualRV.layoutManager = LinearLayoutManager(requireContext())
                        qualRV.adapter = qualAdapter



                        optionsBtn = taskSpecificContainer.findViewById(R.id.add_qual_item_btn)
                        optionsBtn.setOnClickListener {
                            qualAdapter.addOption()
                        }
                        submitBtn.setOnClickListener {addNewQualitative()}
                    }
                    4 -> { // Range
                        taskSpecificContainer.addView(
                            LayoutInflater
                                .from(requireContext())
                                .inflate(
                                    R.layout.add_task_layout_range,
                                    null
                                )
                        )
                        submitBtn.setOnClickListener {addNewRange()}
                    }
                    5 -> { // Boolean
                        taskSpecificContainer.addView(
                            LayoutInflater
                                .from(requireContext())
                                .inflate(
                                    R.layout.add_task_layout_boolean,
                                    null
                                )
                        )
                        submitBtn.setOnClickListener {addNewBoolean()}
                    }
                    else -> {

                    }
                }
                showToast(requireContext(),"${task_spinner.selectedItem.toString()} Selected")
            }
        }
    }


    fun addNewQualitative() {
        Log.d("Add Task Fragment","addNewQualitative called")
        Log.d("Add Task Fragment","Name: ${nameET.text.toString()}")
        Log.d("Add Task Fragment","List size: ${optionsList.size}")
        Log.d("Add Task Fragment","Type code: ${taskTypeCode}")
        if ( // Validate all fields before task creation
            nameET.text.toString() != "" && // Check for no name set
            qualAdapter.curOptions.size > 1 && // Check for at least 1 option
            taskTypeCode == 3 // Check task type is valid
        ) {
            Log.d("Add Task Fragment","Inside if block")
            // Harvest text values for options
            var finalOptions = mutableListOf<String>()
            for (i in qualAdapter.curOptions.indices) {
                val curView = qualRV.getChildAt(i)
                finalOptions.add(curView.findViewById<EditText>(R.id.add_option_et).text.toString())
            }


            // Build new task based on form
            val new_task = TaskTemplate(
                name = nameET.text.toString(),
                type = taskTypeCode,
                period = periodInDays,
                options = finalOptions
            )

            // Add task to database
            viewModel.addTaskTemplate(new_task)
            // Construct directions back to overview and navigate
            val directions = AddTaskFragmentDirections.navigateToOverview()
            findNavController().navigate(directions)

        } else { // Give feedback on error, do nothing

        }
    }

    fun addNewCountUp() {
        Log.d("AddTaskFragment","submit button : value of toggle: ${directionToggle.isChecked}")
        if ( // Validate all fields before task creation
            goalET.text.toString() != "" && // Check for no goal set
            nameET.text.toString() != "" && // Check for no name set
            taskTypeCode != 0 // Check task type is valid
        ) {
            Log.d("AddTaskFragment","submit button : value in goalET: ${goalET.text.toString().toInt()}")


            // Build new task based on form
            val new_task = TaskTemplate(
                nameET.text.toString(),
                taskTypeCode,
                periodInDays,
                directionToggle.isChecked,
                goalET.text.toString().toInt(),
            )

            // Give feedback
            showToast(requireContext(),"Task type is: ${task_spinner.selectedItem.toString()}")
            // Add task to database
            viewModel.addTaskTemplate(new_task)
            // Construct directions back to overview and navigate
            val directions = AddTaskFragmentDirections.navigateToOverview()
            findNavController().navigate(directions)

        } else { // Give feedback on error, do nothing

        }
    }

    fun addNewBoolean() {

    }

    fun addNewRange() {

    }
}