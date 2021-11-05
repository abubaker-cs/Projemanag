package org.abubaker.projemanag.adapters

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import org.abubaker.projemanag.R
import org.abubaker.projemanag.databinding.ItemTaskBinding
import org.abubaker.projemanag.models.Task

open class TaskListItemsAdapter(
    private val context: Context,
    private var list: ArrayList<Task>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    // class MyViewHolder(view: @NonNull ItemTaskBinding) : RecyclerView.ViewHolder(view)
    inner class MyViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

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

        // Inflate Layout (XML)
        // val binding2 = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)


        // val view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false)

        // Here the layout params are converted dynamically according to the screen size as width is 70% and height is wrap_content.
        val layoutParams = LinearLayout.LayoutParams(
            (parent.width * 0.7).toInt(),
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        // Here the dynamic margins are applied to the view.
        layoutParams.setMargins((15.toDp()).toPx(), 0, (40.toDp()).toPx(), 0)

        binding.root.layoutParams = layoutParams

        // view.layoutParams = layoutParams

        return MyViewHolder(binding)
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

        // val model = list[position]
        holder.bind(items[position])

        if (holder is MyViewHolder) {

            if (position == list.size - 1) {

                holder.itemView.tv_add_task_list.visibility = View.VISIBLE
                holder.itemView.ll_task_item.visibility = View.GONE
            } else {
                holder.itemView.tv_add_task_list.visibility = View.GONE
                holder.itemView.ll_task_item.visibility = View.VISIBLE
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