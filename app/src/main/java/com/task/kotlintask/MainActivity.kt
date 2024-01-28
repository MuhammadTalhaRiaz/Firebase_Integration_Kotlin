package com.task.kotlintask

import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.janbark.kotlintask.R
import com.janbark.kotlintask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dataBundle = getData()
        init(dataBundle)


    }
    fun getData() : Bundle{
        val username = intent.getStringExtra("username")
        val email = intent.getStringExtra("email")
        val mobile = intent.getStringExtra("mobile")
        val password = intent.getStringExtra("passwrod")
        val bundle = Bundle().apply {
            putString("username", username)
            putString("email", email)
            putString("mobile", mobile)
            putString("password", password)
        }

        return bundle
    }
    private fun init(dataBundle: Bundle) {
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation_view)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Set data bundle as arguments for all fragments
        val topLevelDestinations = setOf(
            R.id.homeFragment,
            R.id.chat,
            R.id.order,
            R.id.profile,
        )

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in topLevelDestinations) {
                // Set data bundle as arguments for the top-level fragment destinations
                destination.label?.let {
                    val navBackStackEntry = navController.currentBackStackEntry
                    navBackStackEntry?.savedStateHandle?.set("userData", dataBundle)
                }
            }
        }
        // Setup BottomNavigationView with NavController
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}