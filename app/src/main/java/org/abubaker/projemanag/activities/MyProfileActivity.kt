package org.abubaker.projemanag.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import org.abubaker.projemanag.R
import org.abubaker.projemanag.databinding.ActivityMyProfileBinding
import org.abubaker.projemanag.firebase.FirestoreClass
import org.abubaker.projemanag.models.User
import java.io.IOException

class MyProfileActivity : BaseActivity() {

    // Binding Object
    private lateinit var binding: ActivityMyProfileBinding

    // Add a global variable for URI of a selected image from phone storage.
    private var mSelectedImageFileUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // Inflate Layout (XML)
        binding =
            DataBindingUtil.setContentView(this@MyProfileActivity, R.layout.activity_my_profile)

        // Call a function to setup action bar.
        setupActionBar()

        // Call a function to get the current logged in user details
        FirestoreClass().loadUserData(this@MyProfileActivity)

        // A click event for iv_profile_user_image.)
        binding.ivProfileUserImage.setOnClickListener {

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {

                // Call the image chooser function if the permission was already granted
                showImageChooser()

            } else {

                /* CAUTION:

                    Requests permissions to be granted to this application. These permissions
                    must be requested in our manifest, they should not be granted to your app,
                    and they should have protection level.

                 */

                // Ask for Permission: READ_EXTERNAL_STORAGE
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), READ_STORAGE_PERMISSION_CODE
                )

            }
        }

    }

    /**
     * Get the result of the image selection based on the constant code.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        //
        if (resultCode == Activity.RESULT_OK
            && requestCode == PICK_IMAGE_REQUEST_CODE
            && data!!.data != null
        ) {

            // The uri of selection image from phone storage.
            mSelectedImageFileUri = data.data

            try {

                // Load the user image in the ImageView.
                Glide
                    .with(this@MyProfileActivity)
                    .load(Uri.parse(mSelectedImageFileUri.toString())) // URI of the image
                    .centerCrop() // Scale type of the image.
                    .placeholder(R.drawable.ic_user_place_holder) // A default place holder
                    .into(binding.ivProfileUserImage) // the view in which the image will be loaded.


            } catch (e: IOException) {

                // Display Error
                e.printStackTrace()

            }
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
        if (requestCode == READ_STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // Call the image chooser function.
                showImageChooser()

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
     * A function to setup action bar
     */
    private fun setupActionBar() {

        // Set support or the ActionBar
        setSupportActionBar(binding.toolbarMyProfileActivity)
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
        binding.toolbarMyProfileActivity.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    /**
     * A function to set the existing details in UI.
     */
    fun setUserDataInUI(user: User) {

        // Thumbnail
        Glide
            .with(this@MyProfileActivity)
            .load(user.image)
            .centerCrop()
            .placeholder(R.drawable.ic_user_place_holder)
            .into(binding.ivProfileUserImage)

        // Username
        binding.etName.setText(user.name)

        // Email
        binding.etEmail.setText(user.email)

        // Mobile
        if (user.mobile != 0L) {
            binding.etMobile.setText(user.mobile.toString())
        }

    }

    /**
     * A function for user profile image selection from phone storage.
     */
    private fun showImageChooser() {

        // An intent for launching the image selection of phone storage.
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        // Launches the image selection of phone storage using the constant code.
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }

    /**
     * A companion object to declare the constants.
     */
    companion object {
        //A unique code for asking the Read Storage Permission using this we will be check and identify in the method onRequestPermissionsResult
        private const val READ_STORAGE_PERMISSION_CODE = 1

        // A constant for image selection from phone storage
        private const val PICK_IMAGE_REQUEST_CODE = 2
    }


}