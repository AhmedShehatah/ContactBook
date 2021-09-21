package com.ahmed.contactbook.utils

import android.content.Context
import android.net.ConnectivityManager


class ChechInternetConnection(private var requireContext: Context) {


    fun CheckInternetConn(context: Context?) {
        this.requireContext = context!!
    }

    fun isConnection(): Boolean {
        val conn = requireContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (conn != null) {
            val info = conn.activeNetworkInfo
            return info != null && info.isConnected
        }
        return false
    }
}