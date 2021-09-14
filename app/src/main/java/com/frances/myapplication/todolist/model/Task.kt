package com.frances.myapplication.todolist.model

data class Task(
    val title:String,
    val hour:String,
    val date:String,
    val id: Int = 0

){ //here it must press Alt+Insert to implement this functions below
    //we must do it because we did list.remove(task) but it's just the ID we can
    //guarantee that it'll be the same, because our user can change the date, hour and title


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Task

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}
