package com.ahmed.contactbook.ui.splash

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        val token = ContactBookPreferences().getString(SharedKeyEnum.TOKEN.toString())
        if (token.isNullOrEmpty()) {
            binding.btnGetStarted.setOnClickListener {
                navController.navigate(R.id.action_splashScreen_to_loginFragment)

            }
        } else
            navController.navigate(R.id.action_splashScreen_to_homeFragment)


    }

}


