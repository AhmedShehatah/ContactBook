package com.ahmed.contactbook.ui.splash

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ahmed.contactbook.BaseFragment
import com.ahmed.contactbook.R
import com.ahmed.contactbook.databinding.FragmentSplashScreenBinding
import com.ahmed.contactbook.utils.ContactBookPreferences
import com.ahmed.contactbook.utils.SharedKeyEnum


class SplashScreen : BaseFragment<FragmentSplashScreenBinding>() {


    lateinit var navController: NavController

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSplashScreenBinding
        get() = FragmentSplashScreenBinding::inflate

    override fun setupOnViewCreated(view: View) {
        navController = Navigation.findNavController(view)

        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.bottom_animation)
        binding.ivAppIcon.animation = animation
        val token =
            ContactBookPreferences().getString(SharedKeyEnum.TOKEN.toString())
        Handler().postDelayed({

            if (!token.isNullOrEmpty())
                navController.navigate(R.id.action_splashScreen_to_homeFragment)
            else
                navController.navigate(R.id.action_splashScreen_to_loginFragment)

        }, 1000)

    }


}