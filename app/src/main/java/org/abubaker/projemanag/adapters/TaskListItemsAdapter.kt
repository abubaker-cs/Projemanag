package org.abubaker.projemanag.adapters

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import org.abubaker.projemanag.R
import org.abubaker.projemanag.activities.TaskListActivity
import org.abubaker.projemanag.databinding.ItemTaskBinding
import org.abubaker.projemanag.models.Task

open class TaskListItemsAdapter(
    private val context: Context,
    private var list: ArrayList<Task>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    inner class ViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

    /**
     * Inflates the item views which is designed in xml layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val layoutInflater = LayoutInflater.from(context)

        val binding: ItemTaskBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_task, parent, false)

        // Here the layout params are converted dynamically according to the screen size as width is 70% and height is wrap_content.
        val layoutParams = LinearLayout.LayoutParams(
            (parent.width * 0.7).toInt(),
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        // Here the dynamic margins are applied to the view.
        // Order: Left, Top, Right, Bottom
        layoutParams.setMargins((15.toDp()).toPx(), 0, (40.toDp()).toPx(), 0)

        // Assign changes to the RecyclerView in the UI
        binding.root.layoutParams = layoutParams

        return ViewHolder(binding)
    }

    /**
     * Binds each item in the ArrayList to a view
     *
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val model = list[position]

        if (holder is ViewHolder) {

            // If no records exist
            if (position == list.size - 1) {

                holder.binding.tvAddTaskList.visibility = View.VISIBLE
                holder.binding.llTaskItem.visibility = View.GONE

            } else {

                // If we have entries in the list
                holder.binding.tvAddTaskList.visibility = View.GONE
                holder.binding.llTaskItem.visibility = View.VISIBLE

            }

            // Add a click event for showing the view for adding the task list name.
            // And also set the task list title name.
            holder.binding.tvTaskListTitle.text = model.title

            holder.binding.tvAddTaskList.setOnClickListener {
                holder.binding.tvAddTaskList.visibility = View.GONE
                holder.binding.cvAddTaskListName.visibility = View.VISIBLE
            }

            // Add a click event for hiding the view for adding the task list name.
            holder.binding.ibCloseListName.setOnClickListener {
                holder.binding.tvAddTaskList.visibility = View.VISIBLE
                holder.binding.cvAddTaskListName.visibility = View.GONE
            }

            // Add a click event for passing the task list name to the base activity function. To create a task list.
            holder.binding.ibDoneListName.setOnClickListener {

                val listName = holder.binding.etTaskListName.text.toString()

                if (listName.isNotEmpty()) {

                    // Here we check the context is an instance of the TaskListActivity.
                    if (context is TaskListActivity) {
                        context.createTaskList(listName)
                    }

                } else {
                    Toast.makeText(context, "Please Enter List Name.", Toast.LENGTH_SHORT).show()
                }

            }

        }
    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return list.size
    }

    /**
     * A function to get density pixel from pixel
     */
    private fun Int.toDp(): Int =
        (this / Resources.getSystem().displayMetrics.density).toInt()

    /**
     * A function to get pixel from density pixel
     */
    private fun Int.toPx(): Int =
        (this * Resources.getSystem().displayMetrics.density).toInt()

}