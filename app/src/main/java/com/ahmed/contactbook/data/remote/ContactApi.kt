package com.ahmed.contactbook.data.remote

import com.ahmed.contactbook.data.model.LoginResponse
import com.ahmed.contactbook.data.model.UserData
import com.ahmed.contactbook.data.model.UserRegister
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ContactApi {

    @POST("login")
    suspend fun loginUser(
        @Body user: UserData
    ): Response<LoginResponse>

    @POST("register")
    suspend fun registerUser(
        @Body user: UserRegister
    ): Response<LoginResponse>
}