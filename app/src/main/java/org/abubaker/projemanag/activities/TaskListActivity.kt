package org.abubaker.projemanag.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import org.abubaker.projemanag.R
import org.abubaker.projemanag.databinding.ActivityTaskListBinding
import org.abubaker.projemanag.firebase.FirestoreClass
import org.abubaker.projemanag.models.Board
import org.abubaker.projemanag.utils.Constants

class TaskListActivity : BaseActivity() {

    // Binding Object
    private lateinit var binding: ActivityTaskListBinding

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

        //
        FirestoreClass().getBoardDetails(this@TaskListActivity, boardDocumentId)

    }

    /**
     * A function to setup action bar
     */
    private fun setupActionBar(title: String) {

        setSupportActionBar(binding.toolbarTaskListActivity)

        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
            actionBar.title = title
        }

        binding.toolbarTaskListActivity.setNavigationOnClickListener { onBackPressed() }
    }

    /**
     * A function to get the result of Board Detail.
     */
    fun boardDetails(board: Board) {

        // Hide Progress Dialog
        hideProgressDialog()

        // Call the function to setup action bar.
        setupActionBar(board.name)

    }


}