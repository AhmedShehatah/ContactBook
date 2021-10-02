package com.ahmed.contactbook.ui.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ahmed.contactbook.BaseFragment
import com.ahmed.contactbook.R
import com.ahmed.contactbook.databinding.LoginFragmentBinding
import com.ahmed.contactbook.utils.ChechInternetConnection
import com.ahmed.contactbook.utils.toast

class LoginFragment : BaseFragment<LoginFragmentBinding>(), AuthListener {
    private lateinit var viewModel: LoginViewModel
    private lateinit var navController: NavController

    override fun setupOnViewCreated(view: View) {
        viewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        navController = Navigation.findNavController(view)
        viewModel.authListener = this

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            viewModel.sendLoginRequest(email, password)
        }
        binding.btnRegister.setOnClickListener { navController.navigate(R.id.action_loginFragment_to_registerFragment) }

    }

    override fun onStarted() {
        showProgressDialog(requireContext())
    }

    override fun onSuccess() {
        hideProgressDialog()
        navController.navigate(R.id.action_loginFragment_to_homeFragment)
    }

    override fun onFailure(msg: String) {
        requireContext().toast(msg)
        hideProgressDialog()
    }

    override fun isConnection(): Boolean {
        return ChechInternetConnection(requireContext()).isConnection()
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> LoginFragmentBinding
        get() = LoginFragmentBinding::inflate


}