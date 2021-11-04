package org.abubaker.projemanag.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.abubaker.projemanag.R
import org.abubaker.projemanag.databinding.ItemBoardBinding
import org.abubaker.projemanag.models.Board

open class BoardItemsAdapter(
    private val context: Context,
    private var list: ArrayList<Board>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onClickListener: OnClickListener? = null

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.onBindViewHolder
     */
    inner class ViewHolder(val binding: ItemBoardBinding) : RecyclerView.ViewHolder(binding.root)


    /**
     * Inflates the item views which is designed in xml layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        // Inflate Layout (XML)
        val binding = ItemBoardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

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

        // Get Current position of the list item
        val model = list[position]

        // Validate first
        if (holder is ViewHolder) {

            // Thumbnail
            Glide
                .with(context)
                .load(model.image)
                .centerCrop()
                .placeholder(R.drawable.ic_board_place_holder)
                .into(holder.binding.ivBoardImage)


            // Username
            holder.binding.tvName.text = model.name

            // Created by?
            holder.binding.tvCreatedBy.text = "Created By : ${model.createdBy}"

            // Event: On clicking the row (item)
            holder.itemView.setOnClickListener {

                if (onClickListener != null) {
                    onClickListener!!.onClick(position, model)
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
     * A function for OnClickListener where the Interface is the expected parameter..
     */
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    /**
     * An interface for onclick items.
     */
    interface OnClickListener {

        fun onClick(
            position: Int,
            model: Board
        )

    }

}