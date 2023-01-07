package com.eclectics.kenium_v2.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.eclectics.kenium_v2.R
import com.eclectics.kenium_v2.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding : FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)


        val navHostFragment = childFragmentManager.findFragmentById(R.id.fragmentContainerView3)
        val navController = navHostFragment?.findNavController()

        Log.i("NAV_CONTROLLER",navController?.graph.toString())

       if(navController != null ){
           binding.bottomNavigation.setupWithNavController(navController)
       }




        return binding.root
    }


}