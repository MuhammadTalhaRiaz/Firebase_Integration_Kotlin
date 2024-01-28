package com.task.kotlintask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.janbark.kotlintask.R
import com.janbark.kotlintask.databinding.ActivityLoginBinding
import com.janbark.kotlintask.databinding.ActivitySignupBinding
import com.task.kotlintask.firebase.FirebaseClass
import com.task.kotlintask.utils.LoadingDialogFragment

class LoginActivit : AppCompatActivity() {
    private lateinit var loginbinding: ActivityLoginBinding
    private lateinit var firebaseManager: FirebaseClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginbinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginbinding.root)
        init()
    }

    fun init(){
        firebaseManager = FirebaseClass(this)

        val etemail: EditText = findViewById(R.id.et_email)
        val etpassword: EditText = findViewById(R.id.et_passowrd)
        val etsignup: TextView = findViewById(R.id.txt_signin)
        val signin = loginbinding.btnlogin


        etsignup.setOnClickListener {
            val intent =Intent(this , SignupActivity :: class.java)
            startActivity(intent)
        }
        signin.setOnClickListener {
            val email = etemail.text.toString()
            val password = etpassword.text.toString()
            // Call the function to sign in with email and password
            val loadingDialog = LoadingDialogFragment()
            loadingDialog.show(supportFragmentManager, "Please Wait!")
            signin.isEnabled = false
            firebaseManager.loginUser(
                email,
                password,
                { userData ->
                    // User login and data retrieval successful
                    // Access user data here (e.g., userData["username"], userData["email"], etc.)
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
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
                    // Handle login or data retrieval failure
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                    loadingDialog.dismiss()
                    signin.isEnabled = true
                })
        }
    }
}