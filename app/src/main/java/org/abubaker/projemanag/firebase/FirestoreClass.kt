package org.abubaker.projemanag.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import org.abubaker.projemanag.activities.SignUpActivity
import org.abubaker.projemanag.models.User
import org.abubaker.projemanag.utils.Constants

/**
 * A custom class where we will add the operation performed for the firestore database.
 */
class FirestoreClass {

    // 01 Create a instance of Firebase Firestore
    private val mFireStore = FirebaseFirestore.getInstance()

    /**
     * Register details in the Firestore database
     * i.e at https://console.firebase.google.com/project/projemanag-3ef69/firestore/data/~2F
     */

    // We are passing:
    // 1 - activities/SignUpActivity.kt (data will be transferred through it)
    // 2 - models/User.kt (userInfo will be based on it)
    fun registerUser(activity: SignUpActivity, userInfo: User) {

        // We are importing USERS variable from utils/Constants.kt file
        mFireStore.collection(Constants.USERS)

            // Document ID for users fields. Here the document it is the User ID.
            .document(getCurrentUserID())

            // Here the userInfo are Field and the SetOption is set to merge. It is for if we wants to merge
            .set(userInfo, SetOptions.merge())

            .addOnSuccessListener {

                // Here call a function of base activity for transferring the result to it.
                activity.userRegisteredSuccess()

            }
            .addOnFailureListener { e ->

                Log.e(
                    activity.javaClass.simpleName,
                    "Error writing document",
                    e
                )

            }
    }

    /**
     * A function for getting the user id of current logged user.
     */
    fun getCurrentUserID(): String {

        //
        return FirebaseAuth
            .getInstance()
            .currentUser!!
            .uid
    }

}