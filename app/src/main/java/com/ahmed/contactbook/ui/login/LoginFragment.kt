package com.ahmed.contactbook.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.ahmed.contactbook.R
import com.ahmed.contactbook.databinding.LoginFragmentBinding
import com.ahmed.contactbook.utils.ChechInternetConnection

class LoginFragment : Fragment(R.layout.login_fragment), AuthListener {
    lateinit var binding: LoginFragmentBinding
    lateinit var viewModel: LoginViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = LoginFragmentBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)

        viewModel.authListener = this
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            viewModel.sendLoginRequest(email, password)
        }


    }

    override fun onStarted() {
        Toast.makeText(requireContext(), "started", Toast.LENGTH_SHORT).show()
        binding.progressBar.visibility = View.VISIBLE

    }

    override fun onSuccess(liveData: LiveData<String>) {
        liveData.observe(requireActivity(), { result ->
            Toast.makeText(requireContext(), result, Toast.LENGTH_SHORT).show()
        })
        binding.progressBar.visibility = View.GONE
    }

    override fun onFailure(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        binding.progressBar.visibility = View.GONE
    }

    override fun isConnection(): Boolean {
        return ChechInternetConnection(requireContext()).isConnection()
    }

}