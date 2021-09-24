package org.abubaker.projemanag

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import org.abubaker.projemanag.databinding.ActivityMainBinding
import org.abubaker.projemanag.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    // Binding Object
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_splash)

        // Inflate Layout (XML)
        binding = DataBindingUtil.setContentView(this@SplashActivity, R.layout.activity_splash)

    }
}