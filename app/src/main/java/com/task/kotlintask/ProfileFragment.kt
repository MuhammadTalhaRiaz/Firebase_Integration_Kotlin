package com.task.kotlintask

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import com.google.android.material.card.MaterialCardView
import com.janbark.kotlintask.R
import com.janbark.kotlintask.databinding.FragmentProfileBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve data from savedStateHandle
        val userDataBundle = findNavController().currentBackStackEntry?.savedStateHandle?.get<Bundle>("userData")

        // Use the retrieved data as needed
        userDataBundle?.let {
            val username = it.getString("username")
            val useremail = it.getString("email")
            val usermobile = it.getString("mobile")
            val userpassword = it.getString("password")

            binding.prifileName.text = username
            binding.prifileEmail.text = useremail
            val cardView: CardView = binding.editProfileCard
            cardView.setOnClickListener {
                val intent = Intent(context,ProfileViewActivity:: class.java)
                intent.putExtra("username", username)
                intent.putExtra("mobile", usermobile)
                intent.putExtra("passwrod", userpassword)
                intent.putExtra("email", useremail)
                startActivity(intent)
            }
            binding.logout.setOnClickListener {
                logout(view)
            }
        }
    }

    fun logout(view: View) {
        // Create an Intent to navigate to the LoginActivity
        val intent = Intent(context, LoginActivit::class.java)

        // Set flags to clear the task and create a new task
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

        // Start the LoginActivity and finish the current activity
        startActivity(intent)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}