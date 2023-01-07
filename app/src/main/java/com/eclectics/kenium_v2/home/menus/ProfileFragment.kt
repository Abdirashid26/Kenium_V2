package com.eclectics.kenium_v2.home.menus

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.eclectics.kenium_v2.databinding.FragmentProfileBinding
import com.eclectics.kenium_v2.model.UserSession
import com.eclectics.kenium_v2.repo.FirebaseRepo
import java.util.*


class ProfileFragment : Fragment() {


    lateinit var binding : FragmentProfileBinding



    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentProfileBinding.inflate(layoutInflater)

        val displayNames = UserSession.userName.split(" ")

        binding.userInitials.text = displayNames[0].get(0).toString() + displayNames[1].get(0).toString()
        FirebaseRepo().firebaseAuth.currentUser?.let {
            binding.emailDisplay.text = "Email : " + it.email.toString()
        }

        displayGreeting()

        binding.logOutBtn.setOnClickListener {
            logOut()
        }

        return binding.root
    }


    private fun logOut(){
        FirebaseRepo().firebaseAuth.signOut()
        activity?.finish()
    }



    private fun displayGreeting(){
        val c: Calendar = Calendar.getInstance()
        val timeOfDay: Int = c.get(Calendar.HOUR_OF_DAY)

        if (timeOfDay >= 0 && timeOfDay < 12) {
            binding.greeting.text = "Good Morning , ${UserSession.userName}"
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            binding.greeting.text = "Good Afternoon , ${UserSession.userName}"
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            binding.greeting.text = "Good Evening , ${UserSession.userName}"
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            binding.greeting.text = "Good Night , ${UserSession.userName}"
        }
    }

}