package com.ahmed.contactbook.data.remote

import com.ahmed.contactbook.data.model.LoginResponse
import com.ahmed.contactbook.data.model.UserData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ContactApi {

    @POST("login")
    fun loginUser(
        @Body user: UserData
    ): Call<LoginResponse>
}