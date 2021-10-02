package com.ahmed.contactbook.ui.addContact

interface AddListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(msg: String)
    fun isConnection(): Boolean
}