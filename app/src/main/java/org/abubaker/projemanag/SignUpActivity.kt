package org.abubaker.projemanag

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import org.abubaker.projemanag.databinding.ActivityIntroBinding
import org.abubaker.projemanag.databinding.ActivitySignUpBinding
import org.abubaker.projemanag.databinding.ActivitySplashBinding

class SignUpActivity : AppCompatActivity() {

    // Binding Object
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate Layout (XML)
        binding = DataBindingUtil.setContentView(this@SignUpActivity, R.layout.activity_sign_up)

        // Enabling Support for the Toolbar
        setSupportActionBar(binding.toolbarSignUpActivity)

        // Activating the Toolbar
        val actionbar = supportActionBar

        //
        if (actionbar != null) {
            actionbar!!.setDisplayHomeAsUpEnabled(true)
            // actionbar!!.title = happyPlaceDetailModel.title
        }

    }

    private fun setupActionBar() {





    }

}