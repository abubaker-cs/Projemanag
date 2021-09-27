package org.abubaker.projemanag.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import org.abubaker.projemanag.R
import org.abubaker.projemanag.databinding.ActivitySignInBinding

class SignInActivity : BaseActivity() {

    // Binding Object
    private lateinit var binding: ActivitySignInBinding

    //
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {

        //This call the parent constructor
        super.onCreate(savedInstanceState)

        // Inflate Layout (XML)
        binding = DataBindingUtil.setContentView(this@SignInActivity, R.layout.activity_sign_in)

        //
        auth = FirebaseAuth.getInstance()

        // SDK 30 > This is used to hide the status bar and make the splash screen as a full screen activity.
        // window.insetsController?.hide(WindowInsets.Type.statusBars())
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // Actionbar
        setupActionBar()

        // Click event for sign-in button and call the function to sign in.
        binding.btnSignIn.setOnClickListener {
            signInRegisteredUser()
        }
    }

    /**
     * A function for actionBar Setup.
     */
    private fun setupActionBar() {

        // Enabling Support for the Toolbar
        setSupportActionBar(binding.toolbarSignInActivity)

        // Activating the Toolbar
        val actionbar = supportActionBar

        // If Actionbar is available then:
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }

        // This will enable BACK Arrow (button) to return back to the previous screen
        binding.toolbarSignInActivity.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    /**
     * A function for Sign-In using the registered user using the email and password.
     */
    private fun signInRegisteredUser() {

        // Here we get the text from editText and trim the space
        val email: String = binding.etEmail.text.toString().trim { it <= ' ' }
        val password: String = binding.etPassword.text.toString().trim { it <= ' ' }

        // After validating fields:
        if (validateForm(email, password)) {

            // Show the progress dialog.
            showProgressDialog(resources.getString(R.string.please_wait))

            // Sign-In using FirebaseAuth
            FirebaseAuth
                .getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    // Hide the progress dialog
                    hideProgressDialog()

                    // If task was successful
                    if (task.isSuccessful) {

                        // 1. Display Success Message
                        Toast.makeText(
                            this@SignInActivity,
                            "You have successfully signed in.",
                            Toast.LENGTH_LONG
                        ).show()

                        // 2. Open: MainActivity.kt
                        startActivity(
                            Intent(this@SignInActivity, MainActivity::class.java)
                        )

                    } else {

                        // Display failure message
                        Toast.makeText(
                            this@SignInActivity,
                            task.exception!!.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }
        }
    }

    /**
     * A function to validate the entries of a user.
     */
    private fun validateForm(email: String, password: String): Boolean {

        return if (TextUtils.isEmpty(email)) {
            showErrorSnackBar("Please enter email.")
            false
        } else if (TextUtils.isEmpty(password)) {
            showErrorSnackBar("Please enter password.")
            false
        } else {
            true
        }

    }

}