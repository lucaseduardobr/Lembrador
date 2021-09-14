package com.frances.myapplication.todolist.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.frances.myapplication.todolist.databinding.ActivityMainBinding
import com.frances.myapplication.todolist.datasource.TaskDataSource
import com.frances.myapplication.todolist.model.Task

//Conteudo para a hora e a data pode ser encontrado em
//https://material.io/design mtbom


class MainActivity : AppCompatActivity() {

    //using binding to refer our MainActivity
    private lateinit var binding:ActivityMainBinding
    //wait adapter be called to instance TaskListAdapter()
    private val adapter by lazy {TaskListAdapter()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //adapter of our recycler view get the adapter that
        //we just create:  private val adapter by lazy {TaskListAdapter()}
        binding.rvTask.adapter = adapter
        updateList()
        insertListeners()

    }

    private fun insertListeners() {
        binding.fbtn.setOnClickListener {
            startActivityForResult(Intent(this,AddTaskActivity::class.java),CREATE_NEW_TASK)
        }

        adapter.listenerEdit = {
            //basicaly I go to AddTaskActivity then I edit my
            //task
            val intent = Intent(this,AddTaskActivity::class.java)
            //here we pass the name of putExtra and its value
            intent.putExtra(AddTaskActivity.TASK_ID,it.id)
            startActivityForResult(intent, CREATE_NEW_TASK)

        }
        adapter.listenerDelete = {
            TaskDataSource.deleteTask(it)
            updateList()


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== CREATE_NEW_TASK && resultCode == Activity.RESULT_OK) {
            updateList()
        }

    }

    private fun updateList(){
        var lista  = TaskDataSource.getList()
        if(lista.isEmpty()) {
            binding.includeEmpty.emptyState.visibility = View.VISIBLE
        }else{
            binding.includeEmpty.emptyState.visibility = View.GONE
        }
        adapter.submitList(null)
        adapter.submitList(lista)

    }


    companion object {
        private const val CREATE_NEW_TASK = 1000
    }
}

