package org.abubaker.projemanag.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
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
        setSupportActionBar(binding.mainAppBarLayout.appBarMainTb)
        binding.mainAppBarLayout.appBarMainTb.setNavigationIcon(R.drawable.ic_black_color_back_24dp)

        // Click event for navigation in the action bar and call the toggleDrawer function
        binding.mainAppBarLayout.appBarMainTb.setNavigationOnClickListener {
            toggleDrawer()
        }
    }

    /**
     * A function for opening and closing the Navigation Drawer.
     */
    private fun toggleDrawer() {

        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }
    // END
}