package org.abubaker.projemanag

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import org.abubaker.projemanag.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    // Binding Object
    private lateinit var binding: ActivitySignInBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // Inflate Layout (XML)
        binding = DataBindingUtil.setContentView(this@SignInActivity, R.layout.activity_sign_in)

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
}