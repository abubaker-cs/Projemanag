package org.abubaker.projemanag.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import org.abubaker.projemanag.R
import org.abubaker.projemanag.databinding.ActivityCreateBoardBinding
import org.abubaker.projemanag.utils.Constants
import java.io.IOException

class CreateBoardActivity : BaseActivity() {

    // Binding Object
    private lateinit var binding: ActivityCreateBoardBinding

    // Add a global variable for URI of a selected image from phone storage.
    private var mSelectedImageFileUri: Uri? = null

    // Catch the data sent by MainActivity
    private lateinit var mUserName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate Layout (XML)
        binding =
            DataBindingUtil.setContentView(this@CreateBoardActivity, R.layout.activity_create_board)

        // Call the setup action bar function
        setupActionBar()

        /**
         * Capturing the username
         */
        if (intent.hasExtra(Constants.NAME)) {
            mUserName = intent.getStringExtra(Constants.NAME)!!
        }

        /**
         * Select Image
         */
        // A click event for iv_profile_user_image.)
        binding.ivBoardImage.setOnClickListener {

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {

                // Call the image chooser function if the permission was already granted
                Constants.showImageChooser(this@CreateBoardActivity)

            } else {

                // Ask for Permission: READ_EXTERNAL_STORAGE
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    Constants.READ_STORAGE_PERMISSION_CODE
                )

            }
        }
    }


    /**
     * A function to setup action bar
     */
    private fun setupActionBar() {

        // Set support or the ActionBar
        setSupportActionBar(binding.toolbarCreateBoardActivity)
        val actionBar = supportActionBar

        // If Actionbar Exists
        if (actionBar != null) {

            // Enable the ActionBar
            actionBar.setDisplayHomeAsUpEnabled(true)

            // Icon
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)

            // Update the Title
            actionBar.title = resources.getString(R.string.my_profile)
        }

        // On BackPress take the user to the previous screen
        binding.toolbarCreateBoardActivity.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    /**
     * This function will identify the result of runtime permission after the user allows or deny permission based on the unique code.
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // Call the image chooser function.
                Constants.showImageChooser(this@CreateBoardActivity)

            } else {

                //Displaying another toast if permission is not granted
                Toast.makeText(
                    this,
                    "Oops, you just denied the permission for storage. You can also allow it from settings.",
                    Toast.LENGTH_LONG
                ).show()
            }

        }
    }

    /**
     * Get the result of the image selection based on the constant code.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        // Check if the result code is OK, returned with requestCode and retrieved data is not null
        if (resultCode == Activity.RESULT_OK
            && requestCode == Constants.PICK_IMAGE_REQUEST_CODE
            && data!!.data != null
        ) {

            // The uri of selection image from phone storage.
            mSelectedImageFileUri = data.data

            try {

                // Load the user image in the ImageView.
                Glide
                    .with(this)
                    .load(Uri.parse(mSelectedImageFileUri.toString())) // URI of the image
                    .centerCrop() // Scale type of the image.
                    .placeholder(R.drawable.ic_board_place_holder) // A default place holder
                    .into(binding.ivBoardImage) // the view in which the image will be loaded.


            } catch (e: IOException) {

                // Print error on the StackTrace in case if something will wrong
                e.printStackTrace()

            }
        }
    }


}