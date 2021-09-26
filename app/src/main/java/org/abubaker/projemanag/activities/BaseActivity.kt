package org.abubaker.projemanag.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import org.abubaker.projemanag.R
import org.abubaker.projemanag.databinding.ActivityIntroBinding
import org.abubaker.projemanag.databinding.DialogProgressBinding

/**
 * Here we have created a BaseActivity Class in which we have added the progress dialog and SnackBar.
 * Now all the activity will extend the BaseActivity instead of AppCompatActivity.
 */
open class BaseActivity : AppCompatActivity() {

    // Binding Object
    private lateinit var binding: DialogProgressBinding

    //  It will be used in the Double Click to Exit function:
    private var doubleBackToExitPressedOnce = false

    /**
     * This is a progress dialog instance which we will initialize later on.
     */
    private lateinit var mProgressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate Layout (XML)
        binding = DataBindingUtil.setContentView(this@BaseActivity, R.layout.dialog_progress)
    }

    /**
     * This function is used to show the progress dialog with the title and message to user.
     */
    fun showProgressDialog(text: String) {
        mProgressDialog = Dialog(this)

        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        mProgressDialog.setContentView(R.layout.dialog_progress)

        // mProgressDialog.tv_progress_text.text = text

        mProgressDialog.findViewById<TextView>(R.id.tv_progress_text).text = text


        //Start the dialog and display it on screen.
        mProgressDialog.show()
    }

    /**
     * This function is used to dismiss the progress dialog if it is visible to user.
     */
    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }

    /**
     * Firebase: Get Current User's ID
     */
    fun getCurrentUserID(): String {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }

    /**
     * Double Click to Exit
     */
    fun doubleBackToExit() {

        // If it was clicked twice
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        // Alert: If it was clicked only once
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(
            this,
            resources.getString(R.string.please_click_back_again_to_exit),
            Toast.LENGTH_SHORT
        ).show()

        // Precaution: If the user did not pressed twice within 2 seconds then
        Handler(Looper.getMainLooper()).postDelayed(
            {
                doubleBackToExitPressedOnce = false
            },
            2000
        )
    }

    /**
     * Global Function: If there is any error, then display RELATED message
     */
    fun showErrorSnackBar(message: String) {

        // Generic Template
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)

        //
        val snackBarView = snackBar.view

        // Set background color
        snackBarView.setBackgroundColor(

            //
            ContextCompat.getColor(
                this@BaseActivity,
                R.color.snackbar_error_color
            )

        )

        // Display snackbar
        snackBar.show()
    }

}