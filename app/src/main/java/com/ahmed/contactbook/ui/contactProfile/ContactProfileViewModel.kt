package com.ahmed.contactbook.ui.contactProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.contactbook.data.model.Phone
import com.ahmed.contactbook.data.model.UpdateContact
import com.ahmed.contactbook.data.repo.Repo
import com.ahmed.contactbook.ui.addContact.AddListener
import kotlinx.coroutines.launch

class ContactProfileViewModel : ViewModel() {
    var addListener: AddListener? = null
    fun updateUser(token: String, id: String, user: UpdateContact) {
        addListener!!.onStarted()
        if (addListener!!.isConnection()) {

            viewModelScope.launch {

                val response = Repo().updateContact(token, id, user)
                if (response.isSuccessful) addListener!!.onSuccess()
                else addListener!!.onFailure("failed to save data")
            }
        } else addListener!!.onFailure("No Internet Connection")
    }

    fun updatePhone(token: String, id: String, phone: Phone) {
        if (addListener!!.isConnection()) {
            viewModelScope.launch {
                val response = Repo().updatePhone(token, id, phone)
            }
        } else
            addListener!!.onFailure("No Internet Connection")
    }
}