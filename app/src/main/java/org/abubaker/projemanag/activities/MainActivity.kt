package org.abubaker.projemanag.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import org.abubaker.projemanag.R
import org.abubaker.projemanag.adapters.BoardItemsAdapter
import org.abubaker.projemanag.databinding.ActivityMainBinding
import org.abubaker.projemanag.databinding.ContentMainBinding
import org.abubaker.projemanag.databinding.ItemBoardBinding
import org.abubaker.projemanag.firebase.FirestoreClass
import org.abubaker.projemanag.models.Board
import org.abubaker.projemanag.models.User
import org.abubaker.projemanag.utils.Constants

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    /**
     * A companion object to declare the constants.
     */
    companion object {

        //A unique code for starting the activity for result
        val MY_PROFILE_REQUEST_CODE: Int = 11

    }

    // Binding Object
    private lateinit var binding: ActivityMainBinding

    // Username
    private lateinit var mUserName: String

    override fun onCreate(savedInstanceState: Bundle?) {

        //This call the parent constructor
        super.onCreate(savedInstanceState)

        // Inflate Layout (XML)
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)

        // Setup ActionBar
        setupActionBar()

        // Assign the NavigationView.OnNavigationItemSelectedListener to navigation view.
        binding.navView.setNavigationItemSelectedListener(this)

        // Load user's data from the database
        // >>> Now depending on the type of Activity, i.e. MainActivity or SignInActivity
        FirestoreClass().loadUserData(this)

        // FAB: Launch the Create Board screen on a fab button click.
        binding.mainAppBarLayout.fabCreateBoard.setOnClickListener {

            // Setting parameters for the intent, i.e our objective is to open CreateBoardActivity
            val intent = Intent(this@MainActivity, CreateBoardActivity::class.java)

            // We are sending the USERNAME to the CreateBoardActivity which we retrieved
            // in the updateNavigationUserDetails() function
            intent.putExtra(Constants.NAME, mUserName)

            // Initialize the intent
            startActivity(intent)
        }

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
                startActivityForResult(
                    Intent(this@MainActivity, MyProfileActivity::class.java),
                    MY_PROFILE_REQUEST_CODE
                )

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
        val headerBinding = headerView.findViewById<ImageView>(R.id.iv_profile_user_image)

        // Username
        mUserName = user.name

        // Profile Image
        Glide
            .with(this@MainActivity)
            .load(user.image)
            .centerCrop()
            .placeholder(R.drawable.ic_user_place_holder)
            .into(headerBinding)

        // Username
        headerView.findViewById<TextView>(R.id.tv_username).text = user.name
    }

    /**
     * A function to populate the result of BOARDS list in the UI i.e in the recyclerView.
     */
    fun populateBoardsListToUI(boardsList: ArrayList<Board>) {

        // Binding the content_main.xml file
        val contentMainBinding = ContentMainBinding.inflate(layoutInflater)

        //
        hideProgressDialog()

        if (boardsList.size > 0) {

            // Toggling the views: Board List + No Boards Available
            contentMainBinding.rvBoardsList.visibility = View.VISIBLE
            contentMainBinding.tvNoBoardsAvailable.visibility = View.GONE

            //
            contentMainBinding.rvBoardsList.layoutManager = LinearLayoutManager(this@MainActivity)

            //
            contentMainBinding.rvBoardsList.setHasFixedSize(true)

            // Create an instance of BoardItemsAdapter and pass the boardList to it.
            val adapter = BoardItemsAdapter(this@MainActivity, boardsList)

            // Attach the adapter to the recyclerView.
            contentMainBinding.rvBoardsList.adapter = adapter

        } else {

            // Toggling the views: Board List + No Boards Available
            contentMainBinding.rvBoardsList.visibility = View.GONE
            contentMainBinding.tvNoBoardsAvailable.visibility = View.VISIBLE

        }
    }

    /**
     * Receives the result's status from the MyProfileActivity.kt
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        //
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == MY_PROFILE_REQUEST_CODE) {

            // Get the user updated details, so the Thumbnail can be refreshed in the Drawer
            FirestoreClass().loadUserData(this@MainActivity)

        } else {

            // Log
            Log.e("Cancelled", "Cancelled")

        }
    }

}

