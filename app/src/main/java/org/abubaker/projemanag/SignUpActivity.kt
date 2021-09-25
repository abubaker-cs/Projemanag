package org.abubaker.projemanag

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import org.abubaker.projemanag.databinding.ActivityIntroBinding
import org.abubaker.projemanag.databinding.ActivitySignUpBinding
import org.abubaker.projemanag.databinding.ActivitySplashBinding

class SignUpActivity : AppCompatActivity() {

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

        //
        binding.toolbarSignUpActivity.setNavigationOnClickListener {
            onBackPressed()
        }

    }

}