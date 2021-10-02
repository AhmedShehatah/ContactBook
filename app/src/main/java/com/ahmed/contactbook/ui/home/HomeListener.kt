package com.ahmed.contactbook.ui.home

interface HomeListener {
    fun onStarted()
    fun onSuccess()
    fun onDeleted()
    fun isConnection(): Boolean
    fun onFailure(msg: String)
}