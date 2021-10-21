package com.ahmed.contactbook.ui.addContact

import com.ahmed.contactbook.data.model.Phone

interface AddListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(msg: String)
    fun isConnection(): Boolean
    fun addView()
    fun getPhones(): List<Phone>
    fun removeView()

}