package com.ahmed.contactbook.data.repo

import com.ahmed.contactbook.data.model.LoginResponse
import com.ahmed.contactbook.data.model.UserData
import com.ahmed.contactbook.data.model.UserRegister
import com.ahmed.contactbook.data.remote.ApiSettings
import retrofit2.Response

class Repo {

    suspend fun loginUser(user: UserData): Response<LoginResponse> =
        ApiSettings.apiInstance.loginUser(user)

    suspend fun registerUser(user: UserRegister): Response<LoginResponse> =
        ApiSettings.apiInstance.registerUser(user)
}