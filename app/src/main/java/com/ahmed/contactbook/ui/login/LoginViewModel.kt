package com.ahmed.contactbook.ui.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.contactbook.data.repo.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginViewModel : ViewModel() {

    var authListener: AuthListener? = null

    fun sendLoginRequest(email: String, password: String) {
        if (authListener!!.isConnection()) {
            if (email.isEmpty()) {
                authListener!!.onFailure("Please Enter Your Email Address")
                return
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                authListener!!.onFailure("Please add valid email")
                return
            }
            if (password.isEmpty()) {
                authListener!!.onFailure("Please add your password")
                return
            }


            if (password.length < 6) {
                authListener!!.onFailure("password must be 6 char")
                return
            }

            authListener!!.onStarted()

            try {

                    val repository: LiveData<String> =
                        Repo().loginUser(email, password)
                    authListener!!.onSuccess(repository)



            } catch (ex: Exception) {
                Log.d("viewmodel", ex.message.toString())
                authListener!!.onFailure("Failed To login")
            }


        } else
            authListener!!.onFailure("No Internet Connection")

    }
}