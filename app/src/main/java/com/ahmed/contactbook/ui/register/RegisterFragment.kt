package com.ahmed.contactbook.ui.register

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ahmed.contactbook.BaseFragment
import com.ahmed.contactbook.R
import com.ahmed.contactbook.databinding.RegisterFragmentBinding
import com.ahmed.contactbook.ui.login.AuthListener
import com.ahmed.contactbook.utils.ChechInternetConnection

class RegisterFragment : BaseFragment<RegisterFragmentBinding>(), AuthListener {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var navController: NavController
    override fun onStarted() {
        showProgressDialog(requireContext())
    }

    override fun onSuccess() {
        hideProgressDialog()
        navController.navigate(R.id.action_registerFragment_to_homeFragment)
    }

    override fun onFailure(msg: String) {
        hideProgressDialog()
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    override fun isConnection(): Boolean {
        return ChechInternetConnection(requireContext()).isConnection()
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> RegisterFragmentBinding
        get() = RegisterFragmentBinding::inflate

    override fun setupOnViewCreated(view: View) {
        viewModel = ViewModelProvider(
            this
        ).get(RegisterViewModel::class.java)
        viewModel.authListener = this
        navController = Navigation.findNavController(view)
        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            viewModel.sendRegisterRequest(name, email, password)

        }

        binding.btnLogin.setOnClickListener {
            navController.navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

}