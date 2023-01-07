package com.eclectics.kenium_v2.auth

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import com.eclectics.kenium_v2.KeniumActivity
import com.eclectics.kenium_v2.R
import com.eclectics.kenium_v2.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    lateinit var binding : FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(layoutInflater)


        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        //Normal Handler is deprecated , so we have to change the code little bit

        // Handler().postDelayed({
//        Handler(Looper.getMainLooper()).postDelayed({
////            navigate to login fragment
//           val keniumActivity = Intent(activity, KeniumActivity::class.java)
//            startActivity(keniumActivity)
//            activity?.finish()
//        }, 3000)
        return binding.root
    }

}