package com.task.kotlintask.firebase

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.task.kotlintask.MainActivity

class FirebaseClass (private val context: Context) {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun signUpUser(email: String, password: String, mobile: String, username: String,
                   onSuccess: (Map<String, Any>) -> Unit,
                   onFailure: (String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        // Registration successful, add data to Firestore
                        val userData = hashMapOf(
                            "username" to username,
                            "email" to email,
                            "mobile" to mobile,
                            "password" to password
                            // Add other user data fields as needed
                        )

                        addUserDataToFirestore(
                            user.uid,
                            userData,
                            {
                                getUserDataFromFirestore(
                                    user.uid,
                                    onSuccess,
                                    { onFailure.invoke("Failed to retrieve user data from Firestore.") }
                                )
                            },
                            {
                                // Handle failure
                                onFailure.invoke("Failed to add data to Firestore.")
                            }
                        )
                    }
                } else {
                    onFailure.invoke("Registration failed. ${task.exception?.message}")
                }
            }
    }

    fun loginUser(email: String, password: String, onLoginSuccess: (Map<String, Any>) -> Unit, onFailure: (String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        // Fetch user data from Firestore using the UID
                        getUserDataFromFirestore(user.uid,
                            { userData ->
                                // User data retrieved successfully
                                onLoginSuccess.invoke(userData)
                            },
                            {
                                // Handle failure in retrieving user data
                                onFailure.invoke("Failed to retrieve user data from Firestore.")
                            }
                        )
                    }
                } else {
                    onFailure.invoke("Authentication failed. ${task.exception?.message}")
                }
            }
    }

    private fun getUserDataFromFirestore(uid: String, onSuccess: (Map<String, Any>) -> Unit, onFailure: () -> Unit) {
        val usersCollection = db.collection("users")

        usersCollection.document(uid)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // User data exists, retrieve it
                    val userData = documentSnapshot.data as Map<String, Any>
                    onSuccess.invoke(userData)
                } else {
                    // User data not found
                    onFailure.invoke()
                }
            }
            .addOnFailureListener {
                // Handle failure in Firestore query
                onFailure.invoke()
            }
    }

    private fun addUserDataToFirestore(uid: String, userData: Map<String, Any>, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val usersCollection = db.collection("users")

        usersCollection.document(uid)
            .set(userData)
            .addOnSuccessListener {
                onSuccess.invoke()
            }
            .addOnFailureListener {
                onFailure.invoke("Failed to add data to Firestore.")
            }
    }
}