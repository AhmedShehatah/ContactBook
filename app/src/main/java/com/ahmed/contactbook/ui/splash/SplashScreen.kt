package com.ahmed.contactbook.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ahmed.contactbook.R
import com.ahmed.contactbook.databinding.FragmentSplashScreenBinding


class SplashScreen : Fragment(R.layout.fragment_splash_screen) {
    lateinit var binding: FragmentSplashScreenBinding
    lateinit var navController: NavController


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSplashScreenBinding.bind(view)
        navController = Navigation.findNavController(view)

        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.bottom_animation)
        binding.ivAppIcon.animation = animation

        Handler().postDelayed({
            navController.navigate(R.id.action_splashScreen_to_loginFragment)
        }, 1000)


    }


}