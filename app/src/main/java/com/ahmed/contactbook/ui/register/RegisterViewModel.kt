package com.ahmed.contactbook.ui.register

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.contactbook.data.model.UserRegister
import com.ahmed.contactbook.data.repo.Repo
import com.ahmed.contactbook.ui.login.AuthListener
import com.ahmed.contactbook.utils.ContactBookPreferences
import com.ahmed.contactbook.utils.SharedKeyEnum
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    var authListener: AuthListener? = null

    fun sendRegisterRequest(name: String, email: String, password: String) {

        authListener!!.onStarted()

        if (authListener!!.isConnection()) {
            if (name.isEmpty()) {
                authListener!!.onFailure("Please Enter Your Name")
                return
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                authListener!!.onFailure("Please Enter Valid Email Address")
                return
            }

            if (password.length < 6) {
                authListener!!.onFailure("password must be more than 6 characters")
                return
            }

            try {
                val userRegister = UserRegister(name, email, password)
                viewModelScope.launch {

                    val request = Repo().registerUser(userRegister)
                    if (request.isSuccessful) {
                        val token = request.body()!!.token
                        ContactBookPreferences().setString(SharedKeyEnum.TOKEN.toString(), token)
                        ContactBookPreferences().setBoolean(
                            SharedKeyEnum.FIRST_LOGIN.toString(),
                            true
                        )
                        authListener!!.onSuccess()
                    } else authListener!!.onFailure("Email Already in Use")

                }


            } catch (ex: Exception) {
                Log.d("viewmodel", ex.message.toString())
                authListener!!.onFailure("Failed To Register")
            }


        } else {
            authListener!!.onFailure("No Internet Connection")
            return
        }
    }
}