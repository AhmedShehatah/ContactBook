package com.ahmed.contactbook.ui.contactProfile

interface ProfileListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(msg:String)
    fun isConnection():Boolean
}