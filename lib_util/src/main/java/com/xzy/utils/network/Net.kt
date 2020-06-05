@file:Suppress("unused")

package com.xzy.utils.network

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.xzy.utils.os.osVerCode

enum class NetworkType {
    NONE,
    WIFI,
    MOBILE,
    ETHERNET,
    OTHER
}

@Suppress("DEPRECATION")
@SuppressLint("NewApi")
private fun Context.networkType(): NetworkType {
    val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

    if (osVerCode >= Build.VERSION_CODES.M) {
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
        if (capabilities == null ||
                !capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        ) return NetworkType.NONE
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> NetworkType.WIFI
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> NetworkType.MOBILE
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ->
                NetworkType.ETHERNET
            else -> NetworkType.OTHER
        }
    } else {
        val info = cm.activeNetworkInfo
        if (info == null || !info.isConnected) {
            return NetworkType.NONE
        }
        @Suppress("DEPRECATION")
        return when (info.type) {
            ConnectivityManager.TYPE_WIFI -> NetworkType.WIFI
            ConnectivityManager.TYPE_MOBILE -> NetworkType.MOBILE
            ConnectivityManager.TYPE_ETHERNET -> NetworkType.ETHERNET
            else -> NetworkType.OTHER
        }
    }
}

/** Check if the network is connected. */
fun Context.isConnected(): Boolean {
    return networkType() != NetworkType.NONE
}

/** Check if it is connected to WiFi. */
fun Context.isWiFi(): Boolean {
    return networkType() == NetworkType.WIFI
}

/** Check if it is connected to mobile. */
fun Context.isMobile(): Boolean {
    return networkType() == NetworkType.MOBILE
}

/** Check if it is connected to ethernet. */
fun Context.isEthernet(): Boolean {
    return networkType() == NetworkType.ETHERNET
}
