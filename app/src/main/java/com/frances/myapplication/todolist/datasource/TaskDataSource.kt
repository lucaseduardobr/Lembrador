package com.frances.myapplication.todolist.datasource
import com.frances.myapplication.todolist.model.Task

object TaskDataSource{
    private val list = arrayListOf<Task>()

    fun getList()=list.toList()

    // even with val no changeable, it creates a copy of our task
    //object to alter the id
    //we're gonna use this id to edit our tasks
    fun insertTask(task:Task){
        if (task.id == 0) {
            list.add(task.copy(id = list.size + 1))
        }else{
            list.remove(task)
            list.add(task)


        }

    }
    //returns a list
    fun findById(taskId: Int) =list.find {it.id == taskId}

    fun deleteTask(task:Task){
        list.remove(task)
    }


}