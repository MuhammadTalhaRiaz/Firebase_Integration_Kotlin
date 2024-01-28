package com.task.kotlintask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.janbark.kotlintask.R
import com.janbark.kotlintask.databinding.ActivityMainBinding
import com.janbark.kotlintask.databinding.ActivitySignupBinding
import com.task.kotlintask.firebase.FirebaseClass
import com.task.kotlintask.utils.LoadingDialogFragment

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseManager: FirebaseClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    fun init(){
        firebaseManager = FirebaseClass(this)
        val username = binding.etUsername
        val mobile = binding.etMobile
        val email = binding.etEmail
        val password = binding.etPassowrd
        val signup = binding.btnregister
        val back = binding.btnback

        signup.setOnClickListener {
            val stremail = email.text.toString()
            val strpassword = password.text.toString()
            val strmobile = mobile.text.toString()
            val strusername = username.text.toString()

            val loadingDialog = LoadingDialogFragment()
            loadingDialog.show(supportFragmentManager, "Please Wait!")
            signup.isEnabled = false

            firebaseManager.signUpUser(
                stremail,  strpassword,strmobile, strusername,
                {userData ->
                    // Authentication and data added to Firestore successful
                    Toast.makeText(this, "User Register Successfully!", Toast.LENGTH_SHORT).show()
                    loadingDialog.dismiss()
                    val username = userData["username"] as String
                    val email = userData["email"] as String
                    val mobile = userData["mobile"] as String
                    val passwrod = userData["password"] as String

                    val intent = Intent(this,MainActivity:: class.java)
                    intent.putExtra("username", username)
                    intent.putExtra("mobile", mobile)
                    intent.putExtra("passwrod", passwrod)
                    intent.putExtra("email", email)
                    startActivity(intent)
                },
                { errorMessage ->
                    // Handle registration or Firestore data addition failure
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                    loadingDialog.dismiss()
                    signup.isEnabled = true
                }
            )
        }
        back.setOnClickListener {
            onBackButtonClick()
        }
    }
    fun onBackButtonClick() {
        finish()
    }
}