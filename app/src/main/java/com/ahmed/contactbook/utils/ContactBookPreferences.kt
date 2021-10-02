package com.ahmed.contactbook.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.ahmed.contactbook.MyApp

class ContactBookPreferences {

    var contactBookPreferences: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null

    @SuppressLint("CommitPrefEdits")
    private fun openConnection() {
        contactBookPreferences = MyApp.appContext.getSharedPreferences(
            APP_PREFERENCES,
            Context.MODE_PRIVATE
        )
        editor = contactBookPreferences!!.edit()
    }

    private fun closeConnection() {
        contactBookPreferences = null
        editor = null
    }

    fun setString(key: String?, value: String?) {
        openConnection()
        editor!!.putString(key, value)
        editor!!.commit()
        closeConnection()
    }

    fun getString(key: String?): String? {
        var result: String? = ""
        openConnection()
        if (contactBookPreferences?.contains(key) == true) {
            result = contactBookPreferences?.getString(key, "")

        }
        closeConnection()
        return result
    }

    fun setBoolean(key: String, defValue: Boolean) {
        openConnection()
        editor!!.putBoolean(key, defValue)
        editor!!.commit()
        closeConnection()
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        var result = defValue
        openConnection()
        if (contactBookPreferences!!.contains(key)) {
            result = contactBookPreferences!!.getBoolean(key, defValue)
        }
        closeConnection()
        return result
    }

    fun removeString(key: String) {
        openConnection()
        editor!!.remove(key)
        editor!!.commit()
        closeConnection()
    }

    companion object {
        const val APP_PREFERENCES = "ContactBookPreferences"
    }
}