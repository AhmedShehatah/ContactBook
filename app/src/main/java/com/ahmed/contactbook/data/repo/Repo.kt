package com.ahmed.contactbook.data.repo

import com.ahmed.contactbook.data.model.*
import com.ahmed.contactbook.data.remote.ApiSettings
import okhttp3.ResponseBody
import retrofit2.Response

class Repo {

    suspend fun loginUser(user: UserData): Response<LoginResponse> =
        ApiSettings.apiInstance.loginUser(user)

    suspend fun registerUser(user: UserRegister): Response<LoginResponse> =
        ApiSettings.apiInstance.registerUser(user)

    suspend fun addContact(token: String, contact: SaveContact): Response<SaveContact> =
        ApiSettings.apiInstance.addContact(token, contact)

    suspend fun getAllContact(token: String): Response<AllContacts> =
        ApiSettings.apiInstance.getContacts(token)

    suspend fun updateContact(
        token: String,
        id: String,
        user: UpdateContact
    ): Response<ResponseBody> =
        ApiSettings.apiInstance.updateContact(token, id, user)

    suspend fun deleteContact(token: String, id: String): Response<ResponseBody> =
        ApiSettings.apiInstance.deleteContact(token, id)

    suspend fun updatePhone(token: String, id: String,phone:Phone): Response<ResponseBody> =
        ApiSettings.apiInstance.updatePhone(token, id,phone)
}