package com.ahmed.contactbook.ui.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.contactbook.data.repo.Repo
import com.ahmed.contactbook.utils.ContactBookPreferences
import com.ahmed.contactbook.utils.SharedKeyEnum
import kotlinx.coroutines.launch


class LoginViewModel : ViewModel() {

    var authListener: AuthListener? = null

    fun sendLoginRequest(email: String, password: String) {
        authListener!!.onStarted()
        if (authListener!!.isConnection()) {

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                authListener!!.onFailure("Please Enter Valid Email Address")
                return
            }

            if (password.length < 6) {
                authListener!!.onFailure("password must be more than 6 characters")
                return
            }


            try {

                val user = com.ahmed.contactbook.data.model.UserData(email, password)
                viewModelScope.launch {

                    val request = Repo().loginUser(user)
                    if (request.isSuccessful) {

                        val token = request.body()!!.token
                        ContactBookPreferences().setString(SharedKeyEnum.TOKEN.toString(), token)
                        authListener!!.onSuccess()
                    } else authListener!!.onFailure("email or password isn't correct")

                }


            } catch (ex: Exception) {
                Log.d("viewmodel", ex.message.toString())
                authListener!!.onFailure("Failed To login")
            }


        } else
            authListener!!.onFailure("No Internet Connection")

    }
}