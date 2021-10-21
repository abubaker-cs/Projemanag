package org.abubaker.projemanag.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import org.abubaker.projemanag.R
import org.abubaker.projemanag.databinding.ActivityIntroBinding
import org.abubaker.projemanag.databinding.ActivityMyProfileBinding

class MyProfileActivity : BaseActivity() {

    // Binding Object
    private lateinit var binding: ActivityMyProfileBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // Inflate Layout (XML)
        binding =
            DataBindingUtil.setContentView(this@MyProfileActivity, R.layout.activity_my_profile)

        // Call a function to setup action bar.
        setupActionBar()

    }

    /**
     * A function to setup action bar
     */
    private fun setupActionBar() {

        setSupportActionBar(binding.toolbarMyProfileActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
            actionBar.title = resources.getString(R.string.my_profile)
        }

        binding.toolbarMyProfileActivity.setNavigationOnClickListener { onBackPressed() }
    }


}