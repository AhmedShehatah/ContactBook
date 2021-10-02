package com.ahmed.contactbook.ui.addContact

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.contactbook.data.model.Phone
import com.ahmed.contactbook.data.model.SaveContact
import com.ahmed.contactbook.data.repo.Repo
import com.ahmed.contactbook.utils.ContactBookPreferences
import com.ahmed.contactbook.utils.SharedKeyEnum
import kotlinx.coroutines.launch

class AddContactViewModel : ViewModel() {
    var addListener: AddListener? = null
    fun validateData(name: String, phone: String, note: String?, email: String?) {

        addListener!!.onStarted()
        if (addListener!!.isConnection()) {
            if (name.isEmpty()) {
                addListener!!.onFailure("Enter Name")
                return
            }
            if (!Patterns.PHONE.matcher(phone).matches()) {
                addListener!!.onFailure("Enter Valid Phone Number")
                return
            }
            if (email!!.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                addListener!!.onFailure("Enter Valid Email Address")
                return
            }

            val phones = ArrayList<Phone>()
            phones.add(
                Phone(1, phone)
            )


            val contact = SaveContact(
                if (email.isNullOrEmpty()) null else email, name,
                if(note.isNullOrEmpty())null else note, phones as List<Phone>
            )
            viewModelScope.launch {
                val token =
                    "Bearer " + ContactBookPreferences().getString(SharedKeyEnum.TOKEN.toString())
                try {

                    val request = Repo().addContact(token, contact)
                    if (request.isSuccessful) {
                        addListener!!.onSuccess()

                    } else {
                        addListener!!.onFailure("Failed")
                    }

                } catch (ex: Exception) {
                    addListener!!.onFailure(ex.message.toString())
                }
            }
        } else
            addListener!!.onFailure("No Internet Connection")

    }

}