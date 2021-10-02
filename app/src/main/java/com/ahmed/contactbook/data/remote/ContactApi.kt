package com.ahmed.contactbook.data.remote

import com.ahmed.contactbook.data.model.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ContactApi {

    @POST("login")
    suspend fun loginUser(
        @Body user: UserData
    ): Response<LoginResponse>

    @POST("register")
    suspend fun registerUser(
        @Body user: UserRegister
    ): Response<LoginResponse>

    @POST("contacts")
    suspend fun addContact(
        @Header("Authorization")
        token: String,
        @Body
        contact: SaveContact
    ): Response<SaveContact>

    @GET("contacts")
    suspend fun getContacts(
        @Header("Authorization")
        token: String
    ): Response<AllContacts>

    @PATCH("contacts/{id}")
    suspend fun updateContact(
        @Header("Authorization")
        token: String,
        @Path("id") id: String,
        @Body
        user: UpdateContact
    ): Response<ResponseBody>

    @DELETE("contacts/{id}")
    suspend fun deleteContact(
        @Header("Authorization")
        token: String,
        @Path("id") id: String
    ): Response<ResponseBody>

    @PATCH("phones/{id}")
    suspend fun updatePhone(
        @Header("Authorization")
        token: String,
        @Path("id")
        id: String,
        @Body
        phone: Phone
    ): Response<ResponseBody>
}