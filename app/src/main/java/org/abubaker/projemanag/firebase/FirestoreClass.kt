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
     * A function for getting the user id of current logged user.
     */
    fun getCurrentUserID(): String {

        // It will return current user's UID
        // UID is actually defined in the "Authentication" page for individual users, i.e.:
        // user xyz@yahoo.com has a UID of HVTXS5nsTyYH6cj1GzRNlIrpLgR5
        return FirebaseAuth
            .getInstance()
            .currentUser!!
            .uid
    }

}