package org.abubaker.projemanag.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import org.abubaker.projemanag.R
import org.abubaker.projemanag.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Binding Object
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        //This call the parent constructor
        super.onCreate(savedInstanceState)

        // Inflate Layout (XML)
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)

        // Setup ActionBar
        setupActionBar()


    }

    /**
     * A function to setup action bar
     */
    private fun setupActionBar() {

        // setSupportActionBar(toolbar_main_activity)

        // toolbar_main_activity.setNavigationIcon(R.drawable.ic_action_navigation_menu)


        // Enabling Support for the Toolbar
        setSupportActionBar(binding.toolbarMainActivity)

        // Activating the Toolbar
        val actionbar = supportActionBar

        //
        if (actionbar != null) {
            // actionbar!!.setDisplayHomeAsUpEnabled(true)
            // actionbar!!.title = happyPlaceDetailModel.title
        }

        // Click event for navigation in the action bar and call the toggleDrawer function
        // toolbar_main_activity.setNavigationOnClickListener {
        //     toggleDrawer()
        // }
    }

    /**
     * A function for opening and closing the Navigation Drawer.
     */
    private fun toggleDrawer() {

//        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
//            drawer_layout.closeDrawer(GravityCompat.START)
//        } else {
//            drawer_layout.openDrawer(GravityCompat.START)
//        }
    }
    // END
}