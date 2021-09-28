package org.abubaker.projemanag.activities

import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.abubaker.projemanag.R
import org.abubaker.projemanag.databinding.ActivitySignUpBinding
import org.abubaker.projemanag.firebase.FirestoreClass
import org.abubaker.projemanag.models.User

class SignUpActivity : BaseActivity() {

    // Binding Object
    private lateinit var binding: ActivitySignUpBinding

    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        //This call the parent constructor
        super.onCreate(savedInstanceState)

        // Inflate Layout (XML)
        binding = DataBindingUtil.setContentView(this@SignUpActivity, R.layout.activity_sign_up)

        // SDK 30 > This is used to hide the status bar and make the splash screen as a full screen activity.
        // window.insetsController?.hide(WindowInsets.Type.statusBars())
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // Actionbar
        setupActionBar()

    }

    /**
     * A function for actionBar Setup.
     */
    private fun setupActionBar() {

        // Enabling Support for the Toolbar
        setSupportActionBar(binding.toolbarSignUpActivity)

        // Activating the Toolbar
        val actionbar = supportActionBar

        // If Actionbar is available then:
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }

        // This will enable BACK Arrow (button) to return back to the previous screen
        binding.toolbarSignUpActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        // Click event for sign-up button.
        binding.btnSignUp.setOnClickListener {
            registerUser()
        }

    }

    /**
     * A function to register a user to our app using the Firebase.
     * For more details visit: https://firebase.google.com/docs/auth/android/custom-auth
     */
    private fun registerUser() {

        // "Trim Extra Spaces" and Store text in the variables: Name, Email and Password
        val name: String = binding.etName.text.toString().trim { it <= ' ' }
        val email: String = binding.etEmail.text.toString().trim { it <= ' ' }
        val password: String = binding.etPassword.text.toString().trim { it <= ' ' }

        // Send data for verification to check if any field is left Empty
        if (validateForm(name, email, password)) {

            // Show the progress dialog
            showProgressDialog(
                resources.getString(
                    R.string.please_wait
                )
            )

            // Store data in Firestore
            FirebaseAuth

                // 01 Get Ready
                .getInstance()

                // 02 We will be using Email + Password to create a new user account
                .createUserWithEmailAndPassword(email, password)

                // 03 Execute Task
                .addOnCompleteListener(OnCompleteListener<AuthResult> { task ->

                    // If the registration is successfully done
                    if (task.isSuccessful) {

                        // Firebase registered user
                        val firebaseUser: FirebaseUser = task.result!!.user!!

                        // Registered Email
                        val registeredEmail = firebaseUser.email!!

                        val user = User(firebaseUser.uid, name, registeredEmail)
                        FirestoreClass().registerUser(this, user)

                        // Finish the Sign-Up Screen
                        finish()

                    } else {

                        // Display Error Message
                        Toast.makeText(
                            this@SignUpActivity,
                            task.exception!!.message,
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                })
        }

    }

    /**
     * A function to validate the entries of a new user.
     */
    private fun validateForm(name: String, email: String, password: String): Boolean {

        // Loop: It will be used to check if any field is Empty
        return when {

            // Name
            TextUtils.isEmpty(name) -> {
                showErrorSnackBar("Please enter name.")
                false
            }

            // Email
            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("Please enter email.")
                false
            }

            // Password
            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("Please enter password.")
                false
            }

            // Default
            else -> {
                true
            }
        }
    }

    /**
     * A function to be called the user is registered successfully and entry is made in the firestore database.
     */
    fun userRegisteredSuccess() {

        Toast.makeText(
            this@SignUpActivity,
            "You have successfully registered.",
            Toast.LENGTH_SHORT
        ).show()

        // Hide the progress dialog
        hideProgressDialog()

        /**
         * Here the new user registered is automatically signed-in so we just sign-out the user from firebase
         * and send him to Intro Screen for Sign-In
         */
        FirebaseAuth.getInstance().signOut()

        // Finish the Sign-Up Screen
        finish()
    }

}