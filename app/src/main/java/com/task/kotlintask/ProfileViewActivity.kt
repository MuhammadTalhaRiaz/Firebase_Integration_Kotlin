package com.task.kotlintask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.janbark.kotlintask.R
import com.janbark.kotlintask.databinding.ActivityLoginBinding
import com.janbark.kotlintask.databinding.ActivityProfileViewBinding
import com.task.kotlintask.firebase.FirebaseClass

class ProfileViewActivity : AppCompatActivity() {
    private lateinit var profileBinding: ActivityProfileViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileBinding = ActivityProfileViewBinding.inflate(layoutInflater)
        setContentView(profileBinding.root)
         init()
    }

    fun init(){
        var username = intent.getStringExtra("username").toString()
        var email = intent.getStringExtra("email").toString()
        var mobile = intent.getStringExtra("mobile").toString()
        var password = intent.getStringExtra("passwrod").toString()
        val (firstName, lastName) = splitName(username)

        val profileFName = profileBinding.edEditProfile
        val profileLName = profileBinding.edLastName
        var profileEmail = profileBinding.edEmail
        val profileMobile = profileBinding.edMobile
        val profilePassword = profileBinding.edPassword

        profileEmail.text= email
        profileMobile.text= mobile
        profilePassword.text= password
        profileFName.text= firstName
        profileLName.text= lastName

    }
    fun splitName(fullName: String): Pair<String, String> {
        val names = fullName.split(" ")

        // Check if there are at least two names
        if (names.size >= 2) {
            val firstName = names[0]
            val lastName = names.subList(1, names.size).joinToString(" ")
            return Pair(firstName, lastName)
        } else {
            // If there is only one name or none, set lastName to an empty string
            return Pair(fullName, "")
        }
    }

}