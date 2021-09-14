package com.frances.myapplication.todolist.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.frances.myapplication.todolist.databinding.ActivityAddTaskBinding
import com.frances.myapplication.todolist.datasource.TaskDataSource
import com.frances.myapplication.todolist.extensions.format
import com.frances.myapplication.todolist.extensions.text
import com.frances.myapplication.todolist.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class AddTaskActivity :AppCompatActivity(){

    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //ask if there are put extra in that intent which code is task_id
        if(intent.hasExtra(TASK_ID)) {
            //check out if there are any id
                //if not returns 0
            val taskId = intent.getIntExtra(TASK_ID,0)
            TaskDataSource.findById(taskId)?.let {
                binding.ivTitulo.text = it.title
                binding.tilDate.text = it.date
                binding.tilHour.text = it.hour
            }

        }
        insertListeners()
    }

    private fun insertListeners(){
        binding.tilDate.editText?.setOnClickListener {
            //add calender on the view
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                //para ser hora brasileira
                val timeZone = TimeZone.getDefault()
                val offSet =timeZone.getOffset(Date().time )*-1

                // uma maneira de se fazer
                // binding.tilDate.editText?.setText(Date(it).format())

                //usando extensions

                binding.tilDate.text = Date(it+offSet).format()
            }
            datePicker.show(supportFragmentManager,"Date_PICKER_TAG")

        }
        binding.ivBack.setOnClickListener {
            //it ends this activity
            finish()
        }

        binding.tilHour.editText?.setOnClickListener {
            val timerPicker = MaterialTimePicker.Builder()
                    //to put a 24h clock
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()

            timerPicker.addOnPositiveButtonClickListener {
                val minute = if(timerPicker.minute in 0..9) "0${timerPicker.minute}" else "${timerPicker.minute}"
                val hour = if(timerPicker.hour in 0..9) "0${timerPicker.hour}" else "${timerPicker.hour}"
                //eh um elemento TextInputLayout
                binding.tilHour.text = "${hour}:${minute}"

            }
            timerPicker.show(supportFragmentManager,null)

        }


        binding.mbCancel.setOnClickListener {
            //cancel button
            finish()

        }

        binding.mbCriarTarefa.setOnClickListener {
            val task = Task(
                title = binding.ivTitulo.text ,
                date = binding.tilDate.text,
                hour =binding.tilHour.text,
                //So i get the extra called TASK_ID which contain the id of our task
                //each task has a different ID
                id = intent.getIntExtra(TASK_ID,0)

            )
            TaskDataSource.insertTask(task)

            //result is ok, so I can show up our result on screen
            setResult(Activity.RESULT_OK)
            finish()

        }



    }

    companion object {
         const val TASK_ID = "task_id"
    }
}