package org.abubaker.projemanag

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    }
}