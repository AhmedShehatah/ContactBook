package com.ahmed.contactbook.ui.login

import androidx.lifecycle.LiveData

interface AuthListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(msg:String)
    fun isConnection():Boolean
}