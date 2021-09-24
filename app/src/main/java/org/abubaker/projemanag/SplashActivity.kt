package org.abubaker.projemanag

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import org.abubaker.projemanag.databinding.ActivityMainBinding
import org.abubaker.projemanag.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    // Binding Object
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        //This call the parent constructor
        super.onCreate(savedInstanceState)

        // Inflate Layout (XML)
        binding = DataBindingUtil.setContentView(this@SplashActivity, R.layout.activity_splash)

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
        binding.tvAppName.typeface = typeface


        /**
         * Here we will launch the Intro Screen after the splash screen using the handler.
         * As using handler the splash screen will disappear after what we give to the handler.
         */
        // Adding the handler to after the a task after some delay.
        Handler().postDelayed(
            {

                // Start the Intro Activity
                startActivity(Intent(this@SplashActivity, IntroActivity::class.java))

                // Call this when your activity is done and should be closed.
                finish()

            },
            2500
        ) // Here we pass the delay time in milliSeconds after which the splash activity will disappear.

    }
}