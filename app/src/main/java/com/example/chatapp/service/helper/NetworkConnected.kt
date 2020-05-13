package com.example.chatapp.service.helper

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkConnected(con: Application) {
    private val cm = con.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
    private val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

    fun isInternetConnected(): Boolean {
        return isConnected
    }
}