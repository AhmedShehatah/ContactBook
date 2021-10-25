package com.ahmed.contactbook.ui.login

interface AuthListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(msg: String)
    fun isConnection(): Boolean
}