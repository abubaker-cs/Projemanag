package org.abubaker.projemanag

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import org.abubaker.projemanag.databinding.ActivityIntroBinding
import org.abubaker.projemanag.databinding.ActivityMainBinding

class IntroActivity : AppCompatActivity() {

    // Binding Object
    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate Layout (XML)
        binding = DataBindingUtil.setContentView(this@IntroActivity, R.layout.activity_intro)

        /**
         * Full screen flags here.
         */
        // SDK 30 > This is used to hide the status bar and make the splash screen as a full screen activity.
        // window.insetsController?.hide(WindowInsets.Type.statusBars())

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        /**
         * Custom Font
         */
        // This is used to get the file from the assets folder and set it to the title textView.
        // Carbon Font Family: https://www.1001fonts.com/carbon-font.html
        val typeface: Typeface = Typeface.createFromAsset(assets, "carbon bl.ttf")
        binding.tvAppNameIntro.typeface = typeface


        /**
         * Launch the Sign up Activity
         */
        // Add a click event for Sign Up button and launch the Sign Up Screen.
        binding.btnSignUpIntro.setOnClickListener {

            // Launch the sign up screen.
            startActivity(Intent(this@IntroActivity, SignUpActivity::class.java))

        }

    }
}