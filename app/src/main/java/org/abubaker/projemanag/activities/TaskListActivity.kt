package org.abubaker.projemanag.activities

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import org.abubaker.projemanag.R
import org.abubaker.projemanag.adapters.TaskListItemsAdapter
import org.abubaker.projemanag.databinding.ActivityTaskListBinding
import org.abubaker.projemanag.firebase.FirestoreClass
import org.abubaker.projemanag.models.Board
import org.abubaker.projemanag.models.Task
import org.abubaker.projemanag.utils.Constants

class TaskListActivity : BaseActivity() {

    // Binding Object
    private lateinit var binding: ActivityTaskListBinding

    // A global variable for Board Details.
    private lateinit var mBoardDetails: Board

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        // Inflate Layout (XML)
        binding = DataBindingUtil.setContentView(this@TaskListActivity, R.layout.activity_task_list)

        // Get the board documentId through intent.
        var boardDocumentId = ""

        if (intent.hasExtra(Constants.DOCUMENT_ID)) {
            boardDocumentId = intent.getStringExtra(Constants.DOCUMENT_ID)!!
        }

        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))

        // Get the Board Details
        FirestoreClass().getBoardDetails(this@TaskListActivity, boardDocumentId)

    }

    /**
     * A function to setup action bar
     * title: It will be used to fetch the title for the active board
     */
    private fun setupActionBar() {

        // Pick the ActionBar to change
        setSupportActionBar(binding.toolbarTaskListActivity)

        val actionBar = supportActionBar

        if (actionBar != null) {

            // Activate the functionality for the < Back Button
            actionBar.setDisplayHomeAsUpEnabled(true)

            // Enable < icon
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)

            // Updated the Board's Title in the ActionBar using mBoardDetails.name
            actionBar.title = mBoardDetails.name
        }

        // Action for the < Button
        binding.toolbarTaskListActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    /**
     * A function to get the result of Board Detail.
     */
    fun boardDetails(board: Board) {

        // Initialize and Assign the value to the global variable for Board Details.
        // After replace the parameter variable with global so from onwards the global variable will be used.
        mBoardDetails = board

        // Hide Progress Dialog
        hideProgressDialog()

        // Call the function to setup action bar.
        setupActionBar()

        // Here we are appending an item view for adding a list task list for the board.
        val addTaskList = Task(resources.getString(R.string.add_list))
        board.taskList.add(addTaskList)

        binding.rvTaskList.layoutManager =
            LinearLayoutManager(this@TaskListActivity, LinearLayoutManager.HORIZONTAL, false)

        binding.rvTaskList.setHasFixedSize(true)

        // Create an instance of TaskListItemsAdapter and pass the task list to it.
        val adapter = TaskListItemsAdapter(this@TaskListActivity, board.taskList)
        binding.rvTaskList.adapter = adapter // Attach the adapter to the recyclerView.

    }

    /**
     * A function to get the task list name from the adapter class which we will be using to create a new task list in the database.
     */
    fun createTaskList(taskListName: String) {

        Log.e("Task List Name", taskListName)

        // Create and Assign the task details
        val task = Task(taskListName, FirestoreClass().getCurrentUserID())

        mBoardDetails.taskList.add(0, task) // Add task to the first position of ArrayList
        mBoardDetails.taskList.removeAt(mBoardDetails.taskList.size - 1) // Remove the last position as we have added the item manually for adding the TaskList.

        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().addUpdateTaskList(this@TaskListActivity, mBoardDetails)
    }
    // END

    // TODO (Step 7: Create a function to get the result of add or updating the task list.)
    // START
    /**
     * A function to get the result of add or updating the task list.
     */
    fun addUpdateTaskListSuccess() {

        hideProgressDialog()

        // Here get the updated board details.
        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getBoardDetails(this@TaskListActivity, mBoardDetails.documentId)
    }


}