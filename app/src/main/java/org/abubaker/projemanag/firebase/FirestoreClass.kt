package org.abubaker.projemanag.firebase

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import org.abubaker.projemanag.activities.*
import org.abubaker.projemanag.models.Board
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
    fun loadUserData(
        activity: Activity,
        isToReadBoardsList: Boolean = false
    ) {

        // Here we pass the collection name from which we wants the data.
        mFireStore.collection(Constants.USERS)

            // The document id to get the Fields of user.
            .document(getCurrentUserID())

            // In registerUser() we are using .set() to write  values, but here we need to get data
            .get()

            // Success Action
            .addOnSuccessListener { document ->

                // Print Log Message
                // Log.e(activity.javaClass.simpleName, document.toString())

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
                        activity.updateNavigationUserDetails(loggedInUser, isToReadBoardsList)

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

                // Log.e(activity.javaClass.simpleName, "Error while getting loggedIn user details", e)

            }
    }

    /**
     * A function to update the user profile data into the database.
     *
     * HashMap<String, Any>
     * Key: String
     * Value: Any (kotlin), Object (Java)
     *
     * We will pass the user, as a HashMap
     */
    fun updateUserProfileData(activity: MyProfileActivity, userHashMap: HashMap<String, Any>) {

        // Collection Name
        mFireStore.collection(Constants.USERS)

            // Document ID
            .document(getCurrentUserID())

            // A hashmap of fields which are to be updated.
            .update(userHashMap)

            // On: Success
            .addOnSuccessListener {

                // Log + Toast: Profile data is updated successfully.
                Log.e(activity.javaClass.simpleName, "Profile Data updated successfully!")
                Toast.makeText(activity, "Profile updated successfully!", Toast.LENGTH_SHORT).show()

                // Notify the success result.
                activity.profileUpdateSuccess()
            }

            // On: Failure
            .addOnFailureListener { e ->

                // hideProgressDialog
                activity.hideProgressDialog()

                // Log
                Log.e(activity.javaClass.simpleName, "Error while creating a board.", e)
                Toast.makeText(activity, "Error when updating the profile!", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    /**
     * For creating a board and making an entry in the database
     */
    fun createBoard(activity: CreateBoardActivity, board: Board) {

        mFireStore.collection(Constants.BOARDS)

            // Returns a DocumentReference pointing to a new document with an auto-generated ID.
            .document()

            // We are asking that instead of overwriting, just  merge the existing data
            .set(board, SetOptions.merge())

            // Success
            .addOnSuccessListener {

                // Log + Toast: Board created successfully.
                Log.e(activity.javaClass.simpleName, "Board created successfully.")
                Toast.makeText(activity, "Board created successfully.", Toast.LENGTH_SHORT).show()

                // Hides the Progress Dialog and finish() the task.
                activity.boardCreatedSuccessfully()
            }

            // Failure
            .addOnFailureListener { e ->

                // Hide the Progress Dialog
                activity.hideProgressDialog()

                // Log: Error
                Log.e(activity.javaClass.simpleName, "Error while creating a board.", e)
            }
    }

    /**
     * A function to get the list of created boards from the database.
     */
    fun getBoardsList(activity: MainActivity) {

        // The collection name for BOARDS
        mFireStore.collection(Constants.BOARDS)

            // Array query as we want the list of the board in which the user is assigned. So here you can pass the current user id.
            .whereArrayContains(Constants.ASSIGNED_TO, getCurrentUserID())

            // Will get the documents snapshots.
            .get()

            // On: Success
            .addOnSuccessListener { document ->

                // Here we get the list of boards in the form of documents.
                Log.e(activity.javaClass.simpleName, document.documents.toString())

                // Here we have created a new instance for Boards ArrayList.
                val boardsList: ArrayList<Board> = ArrayList()

                // A for loop as per the list of documents to convert them into Boards ArrayList.
                for (i in document.documents) {

                    // We are saving the instance of the "OBJECT" to our board variable
                    val board = i.toObject(Board::class.java)!!

                    // Fetch the documentId
                    board.documentId = i.id

                    // Add record in the boardsList
                    boardsList.add(board)
                }

                // Populate the Activity by passing the result to the base activity.
                activity.populateBoardsListToUI(boardsList)
            }

            // On: Failure
            .addOnFailureListener { e ->

                // Hide the Progress Dialog
                activity.hideProgressDialog()

                // Log: Error
                Log.e(activity.javaClass.simpleName, "Error while creating a board.", e)

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
