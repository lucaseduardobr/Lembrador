package com.frances.myapplication.todolist.ui



import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.frances.myapplication.todolist.R
import com.frances.myapplication.todolist.databinding.ItemTaskBinding
import com.frances.myapplication.todolist.model.Task

//here list adapter is <T,VH>
class TaskListAdapter:ListAdapter<Task,TaskListAdapter.TaskViewHolder>(DiffCallback()){
    var listenerEdit : (Task) -> Unit = {

    }

    var listenerDelete : (Task) -> Unit = {

    }



//aqui ele pega a activity que tem as views
    inner class TaskViewHolder(
        private val binding:ItemTaskBinding
        ): RecyclerView.ViewHolder(binding.root){


    fun bind(item: Task) {
        binding.tvTitle.text = item.title
        binding.tvDate.text = "${item.date} ${item.hour}"
        //building our menu, (3 points)
        binding.ivMore.setOnClickListener {
            showPopup(item)
        }

    }
    //building our menu
    private fun showPopup(item: Task) {
        val ivMore = binding.ivMore
        val popupMenu = PopupMenu(ivMore.context,ivMore)
        popupMenu.menuInflater.inflate(R.menu.popupmenu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.action_edit -> listenerEdit(item)
                R.id.action_delete -> listenerDelete(item)
            }
            return@setOnMenuItemClickListener true
        }
        //to show pop up
        popupMenu.show()
    }

}
    //it needs to extends this classes because of abstract class
    //Recycler.View.Adapter
    //it already knows we're gonna returns a TaskViewHolder
    //because of this on ListAdapter TaskListAdapter.TaskViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        //I don t have layoutInflater here, so I'll need to get it on
        //my mainactivity as follow
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater,parent,false)
        return TaskViewHolder(binding)

    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int)  = holder.bind(getItem(position))
}

class DiffCallback: DiffUtil.ItemCallback<Task>(){
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean = oldItem == newItem


    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean = oldItem.id==newItem.id


}