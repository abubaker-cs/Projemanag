package org.abubaker.projemanag.firebase

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import org.abubaker.projemanag.activities.MainActivity
import org.abubaker.projemanag.activities.MyProfileActivity
import org.abubaker.projemanag.activities.SignInActivity
import org.abubaker.projemanag.activities.SignUpActivity
import org.abubaker.projemanag.models.User
import org.abubaker.projemanag.utils.Constants

/**
 * A custom class where we will add the operation performed for the firestore database.
 */
class FirestoreClass {

    // Create an instance of Firebase Firestore
    private val mFireStore = FirebaseFirestore.getInstance()

    /**
     * Register details in the Firestore database
     * i.e at https://console.firebase.google.com/project/projemanag-3ef69/firestore/data/~2F
     */

    // We are passing:
    // 1 - activities/SignUpActivity.kt (data will be transferred through it)
    // 2 - models/User.kt (userInfo will be based on it)
    fun registerUser(activity: SignUpActivity, userInfo: User) {

        /**
         * What is a Collection in Firestore?
         * It is a SET of documents that contain documents, i.e. USERS will store unique data for
         * each user. ]
         *
         * We are importing USERS variable from utils/Constants.kt file
         * It is like we clicked on the "Start Collection" in:
         * https://console.firebase.google.com/project/projemanag-3ef69/firestore
         */
        mFireStore.collection(Constants.USERS)

            // Document ID for users fields. Here the document it is the User ID.
            // We have defined getCurrentUserID() below this class
            .document(getCurrentUserID())

            // Here the userInfo are Field and the SetOption is set to merge. It is for if we wants to merge
            // userInfo = Set of fields defined in the models/User.kt:
            // 1. id
            // 2. name
            // 3. email, etc
            .set(userInfo, SetOptions.merge())

            // Success Action
            .addOnSuccessListener {

                // This function inside the SignUpActivity will inform, that the user was registered
                // successfully and an entry is made in the Firestore database.
                activity.userRegisteredSuccess()

            }

            // Failure Action
            .addOnFailureListener { e ->

                Log.e(
                    activity.javaClass.simpleName,
                    "Error writing document",
                    e
                )

            }
    }

    /**
     * A function to SignIn using firebase and GET the user details from Firestore Database.
     */
    // fun signInUser(activity: SignInActivity) {
    fun loadUserData(activity: Activity) {

        // Here we pass the collection name from which we wants the data.
        mFireStore.collection(Constants.USERS)

            // The document id to get the Fields of user.
            .document(getCurrentUserID())

            // In registerUser() we are using .set() to write  values, but here we need to get data
            .get()

            // Success Action
            .addOnSuccessListener { document ->

                // Print Log Message
                Log.e(
                    activity.javaClass.simpleName, document.toString()
                )

                // Since our .document() has all the user details, we can get additional information form it below:
                // Here we have received the document snapshot which is converted into the User Data model object.
                val loggedInUser = document.toObject(User::class.java)!!

                // TODO Replace which() with Interfaces
                // https://www.udemy.com/course/android-kotlin-developer/learn/lecture/18301750#questions/10496002

                // We need to check, if the Activity is of a certain type
                when (activity) {

                    is SignInActivity -> {

                        // Here call a function of base activity for transferring the result to it.
                        // Note: signInSuccess() function is defined in the activities/SignUpActivity.kt file
                        activity.signInSuccess(loggedInUser)

                    }

                    is MainActivity -> {

                        // Initialize: updateNavigationUserDetails() in MainActivity.kt
                        activity.updateNavigationUserDetails(loggedInUser)

                    }

                    is MyProfileActivity -> {

                        // Initialize: setUserDataInUI() defined in the MyProfileActivity.kt
                        activity.setUserDataInUI(loggedInUser)

                    }

                }


            }

            // Failure Action
            .addOnFailureListener { e ->

                when (activity) {

                    is SignInActivity -> {

                        /// We need to hide the hideProgressDialog
                        // This hideProgressDialog() is in our base activity
                        activity.hideProgressDialog()

                    }

                    is MainActivity -> {

                        /// We need to hide the hideProgressDialog
                        activity.hideProgressDialog()

                    }

                    is MyProfileActivity -> {
                        activity.hideProgressDialog()
                    }

                }

                Log.e(
                    activity.javaClass.simpleName,
                    "Error while getting loggedIn user details",
                    e
                )

            }
    }

    fun updateUserProfileData(activity: MyProfileActivity, userHashMap: HashMap<String, Any>) {
        mFireStore.collection(Constants.USERS) // Collection Name
            .document(getCurrentUserID()) // Document ID
            .update(userHashMap) // A hashmap of fields which are to be updated.
            .addOnSuccessListener {
                // Profile data is updated successfully.
                Log.e(activity.javaClass.simpleName, "Profile Data updated successfully!")

                Toast.makeText(activity, "Profile updated successfully!", Toast.LENGTH_SHORT).show()

                // Notify the success result.
                activity.profileUpdateSuccess()
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while creating a board.",
                    e
                )
            }
    }

    /**
     * A function for getting the user id of current logged user.
     */
    fun getCurrentUserID(): String {

        // An Instance of currentUser using FirebaseAuth
        val currentUser = FirebaseAuth.getInstance().currentUser

        // A variable to assign the currentUserId if it is not null or else it will be blank.
        var currentUserID = ""

        // If the user exists
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }

        return currentUserID

        // It will return current user's UID
        // UID is actually defined in the "Authentication" page for individual users, i.e.:
        // user xyz@yahoo.com has a UID of HVTXS5nsTyYH6cj1GzRNlIrpLgR5
        // return FirebaseAuth.getInstance().currentUser!!.uid
    }

}
