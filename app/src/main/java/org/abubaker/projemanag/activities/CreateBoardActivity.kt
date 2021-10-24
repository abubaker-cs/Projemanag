package org.abubaker.projemanag.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import org.abubaker.projemanag.R
import org.abubaker.projemanag.databinding.ActivityCreateBoardBinding

class CreateBoardActivity : BaseActivity() {

    // Binding Object
    private lateinit var binding: ActivityCreateBoardBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate Layout (XML)
        binding =
            DataBindingUtil.setContentView(this@CreateBoardActivity, R.layout.activity_create_board)

        // Call the setup action bar function
        setupActionBar()
    }


    /**
     * A function to setup action bar
     */
    private fun setupActionBar() {

        // Set support or the ActionBar
        setSupportActionBar(binding.toolbarCreateBoardActivity)
        val actionBar = supportActionBar

        // If Actionbar Exists
        if (actionBar != null) {

            // Enable the ActionBar
            actionBar.setDisplayHomeAsUpEnabled(true)

            // Icon
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)

            // Update the Title
            actionBar.title = resources.getString(R.string.my_profile)
        }

        // On BackPress take the user to the previous screen
        binding.toolbarCreateBoardActivity.setNavigationOnClickListener {
            onBackPressed()
        }

    }


}