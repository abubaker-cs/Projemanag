package org.abubaker.projemanag.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import org.abubaker.projemanag.R
import org.abubaker.projemanag.databinding.ActivityMainBinding
import org.abubaker.projemanag.databinding.NavHeaderMainBinding
import org.abubaker.projemanag.firebase.FirestoreClass
import org.abubaker.projemanag.models.User

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    // Binding Object
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        //This call the parent constructor
        super.onCreate(savedInstanceState)

        // Inflate Layout (XML)
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)

        // Setup ActionBar
        setupActionBar()

        // Assign the NavigationView.OnNavigationItemSelectedListener to navigation view.
        binding.navView.setNavigationItemSelectedListener(this)

        // Now depending on the type of Activity, i.e. MainActivity or SignInActivity
        FirestoreClass().signInUser(this)


    }

    /**
     * A function to setup action bar
     */
    private fun setupActionBar() {

        // Enabling Support for the Toolbar
        setSupportActionBar(binding.mainAppBarLayout.appBarMainTb)

        // Icon
        binding.mainAppBarLayout.appBarMainTb.setNavigationIcon(R.drawable.ic_action_navigation_menu)

        // Click event for navigation in the action bar and call the toggleDrawer function
        binding.mainAppBarLayout.appBarMainTb.setNavigationOnClickListener {
            toggleDrawer()
        }

    }

    /**
     * A function for opening and closing the Navigation Drawer.
     */
    private fun toggleDrawer() {

        // If the Drawer is Open
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {

            // We will close the drawable, since it was already opened
            binding.drawerLayout.closeDrawer(GravityCompat.START)

        } else {

            // Since, the Drawer was closed so now we will open it
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

    }

    // Add a onBackPressed function and check if the navigation drawer is open or closed.
    override fun onBackPressed() {

        // If the Drawer is Open
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {

            // We will close the drawable, since it was already opened
            binding.drawerLayout.closeDrawer(GravityCompat.START)


        } else {

            // A double back press function is added in Base Activity.
            doubleBackToExit()
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

        when (menuItem.itemId) {

            // My Profile
            R.id.nav_my_profile -> {

                // Start MyProfileActivity
                startActivity(Intent(this@MainActivity, MyProfileActivity::class.java))

            }

            // Sign Out
            R.id.nav_sign_out -> {

                // Here sign outs the user from firebase in this device.
                FirebaseAuth.getInstance().signOut()

                // Send the user to the intro screen of the application.
                val intent = Intent(this, IntroActivity::class.java)

                // Flags: Reset the priority
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)

                // Initialize
                startActivity(intent)


                finish()
            }

        }

        binding.drawerLayout.closeDrawer(GravityCompat.START)

        return true
    }

    // We want to load image from the User Object (downloaded from Firebase)
    // defined as image variable in the models/User.kt file
    // val image: String = "",
    fun updateNavigationUserDetails(user: User) {

        // Based on solution provided by Helder
        // URL: https://www.udemy.com/course/android-kotlin-developer/learn/lecture/18301726#questions/14389880
        val headerView = binding.navView.getHeaderView(0)
        val headerBinding = NavHeaderMainBinding.bind(headerView)

        // Profile Image
        Glide
            .with(this)
            .load(user.image)
            .centerCrop()
            .placeholder(R.drawable.ic_user_place_holder)
            .into(headerBinding.ivUserImage)

        // Username
        headerBinding.tvUsername.text = user.name

    }
}