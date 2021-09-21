package com.ahmed.contactbook.data.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ahmed.contactbook.data.model.LoginResponse
import com.ahmed.contactbook.data.model.UserData
import com.ahmed.contactbook.data.remote.ApiSettings
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repo {
    val liveData: MutableLiveData<String> = MutableLiveData()
     fun loginUser(email: String, password: String): LiveData<String> {
        val user = UserData(email, password)
        ApiSettings.apiInstance.loginUser(user)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {

                    try {
                        if (response.isSuccessful)
                            liveData.value = response.body()!!.toString() + "01097203910"
                        else
                            liveData.value = response.message()
                    } catch (t: Exception) {
                        Log.d("error", response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    liveData.value = t.message
                    Log.d("Error", t.message.toString())
                }

            })
        return liveData
    }
}