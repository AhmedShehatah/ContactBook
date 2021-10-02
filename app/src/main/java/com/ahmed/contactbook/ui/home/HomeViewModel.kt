package com.ahmed.contactbook.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.contactbook.data.model.GetContact
import com.ahmed.contactbook.data.repo.Repo
import com.ahmed.contactbook.utils.ContactBookPreferences
import com.ahmed.contactbook.utils.SharedKeyEnum
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    var homeListener: HomeListener? = null
    val contacts = MutableLiveData<List<GetContact>>()
    fun getContact() {
        homeListener!!.onStarted()
        val token = "Bearer " + ContactBookPreferences().getString(SharedKeyEnum.TOKEN.toString())
        viewModelScope.launch {
            try {
                val response = Repo().getAllContact(token)
                if (response.isSuccessful) {
                    contacts.value = response.body()!!.data
                    homeListener!!.onSuccess()
                } else homeListener!!.onFailure(response.message())
            } catch (ex: Exception) {
                homeListener!!.onFailure(ex.message!!)
            }

        }
    }

    fun deleteContact(id: String) {
        val token = "Bearer " + ContactBookPreferences().getString(SharedKeyEnum.TOKEN.toString())
        homeListener!!.onStarted()
        viewModelScope.launch {
            val response = Repo().deleteContact(token, id)
            if (response.isSuccessful) homeListener!!.onDeleted()
            else homeListener!!.onFailure("Couldn't delete contact")
        }
    }

}