package com.example.lifetracker.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lifetracker.data.Task
import com.example.lifetracker.data.TaskTemplate
import com.example.lifetracker.utils.showToast
import com.example.myapplication.R

class AddTaskFragment : Fragment(R.layout.add_task_fragment){
    // Instantiate viewModel and adapter
    private val viewModel: TaskViewModel by viewModels()

    // Grab hooks for views
    private lateinit var nameTV : TextView
    private lateinit var nameET : EditText
    private lateinit var goalTV : TextView
    private lateinit var goalET : EditText
    private lateinit var submitBtn : Button
    private lateinit var spinner : Spinner
    private lateinit var taskSpecificContainer : LinearLayout
    private lateinit var optionsList : MutableList<String>

    private var taskTypeCode = 1 //Task code for later, default to spinner default value

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Required Magic
        super.onViewCreated(view, savedInstanceState)

        // Find views
        nameTV = view.findViewById(R.id.add_task_name_title)
        nameET = view.findViewById(R.id.add_task_name_edit)
        submitBtn = view.findViewById(R.id.add_task_submit_button)
        spinner = view.findViewById(R.id.task_type_spinner)
        taskSpecificContainer = view.findViewById(R.id.task_type_container)

        // type specific values TODO: Move into the onselected listener
//        goalTV = view.findViewById(R.id.add_task_goal_title)
//        goalET = view.findViewById(R.id.add_task_goal_edit)

        // Options for type spinner
        var task_types = arrayOf("Count up", "Count down", "Qualitative", "Range", "boolean (Need better name)")

        // Build dropdown, https://www.digitalocean.com/community/tutorials/android-spinner-using-kotlin
        // Initialize adapter
        var aa = ArrayAdapter(
            requireContext(), // Pass in main activity context
            R.layout.spinner_text_item, // Layout to be inflated for each item
            task_types // List of items
        )

        // Fill in spinner view and define behavior
        with(spinner){
            adapter = aa
            setSelection(0,false)
            prompt = "Select task type"
        }

        // Inflate with default task type
        taskSpecificContainer.addView(
            LayoutInflater
                .from(requireContext())
                .inflate(
                    R.layout.add_task_countup,
                    null
                )
        )
        goalET = view.findViewById(R.id.add_task_goal_edit)


        // Set behavior on spinner switch
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                showToast(requireContext(),"Nothing Selected")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Set typecode for later
                taskTypeCode = when (spinner.selectedItem.toString()) {
                    "Count up" -> 1
                    "Count down" -> 2
                    "Qualitative" -> 3
                    "Range" -> 4
                    "boolean (Need better name)" -> 5
                    else -> 0
                }
                // Inflate layout for different task types
                when (taskTypeCode) {
                    1 -> {
                        taskSpecificContainer.removeAllViews()
                        taskSpecificContainer.addView(
                            LayoutInflater
                                .from(requireContext())
                                .inflate(
                                    R.layout.add_task_countup,
                                    null
                                )
                            )
                        goalET = view!!.findViewById(R.id.add_task_goal_edit)
                    }
                    2 -> {
                        taskSpecificContainer.removeAllViews()
                        taskSpecificContainer.addView(
                            LayoutInflater
                                .from(requireContext())
                                .inflate(
                                    R.layout.add_task_countdown,
                                    null
                                )
                        )
                        goalET = view!!.findViewById(R.id.add_task_goal_edit)
                    }
                    3 -> {
                        taskSpecificContainer.removeAllViews()
                        taskSpecificContainer.addView(
                            LayoutInflater
                                .from(requireContext())
                                .inflate(
                                    R.layout.add_task_qualitative,
                                    null
                                )
                        )
                        goalET = view!!.findViewById(R.id.add_task_goal_edit)
                    }
                    4 -> {
                        taskSpecificContainer.removeAllViews()
                        taskSpecificContainer.addView(
                            LayoutInflater
                                .from(requireContext())
                                .inflate(
                                    R.layout.add_task_range,
                                    null
                                )
                        )
                        goalET = view!!.findViewById(R.id.add_task_goal_edit)
                    }
                    5 -> {
                        taskSpecificContainer.removeAllViews()
                        taskSpecificContainer.addView(
                            LayoutInflater
                                .from(requireContext())
                                .inflate(
                                    R.layout.add_task_boolean,
                                    null
                                )
                        )
                        goalET = view!!.findViewById(R.id.add_task_goal_edit)
                    }
                    else -> {

                    }
                }
                showToast(requireContext(),"${spinner.selectedItem.toString()} Selected")
            }
        }

        submitBtn.setOnClickListener {

            if ( // Validate all fields before task creation
                goalET.text.toString() != "" && // Check for no goal set
                taskTypeCode != 0 // Check task type is valid
            ) {
                Log.d("AddTaskFragment","submit button : value in goalET: ${goalET.text.toString().toInt()}")

                // Build new task based on form
                val new_task = TaskTemplate(
                    nameET.text.toString(),
                    taskTypeCode,
                    1,
                    true,
                    goalET.text.toString().toInt(),
//                    optionsList.toList(),
                )

                // Give feedback
                showToast(requireContext(),"Task type is: ${spinner.selectedItem.toString()}")
                // Add task to database
                viewModel.addTaskTemplate(new_task)
                // Construct directions back to overview and navigate
                val directions = AddTaskFragmentDirections.navigateToOverview()
                findNavController().navigate(directions)

            } else { // Give feedback on error, do nothing

            }



        }
    }

}