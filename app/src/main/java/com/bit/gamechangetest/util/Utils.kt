package com.bit.gamechangetest.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.bit.gamechangetest.AppObjectController
import com.bit.gamechangetest.R
import java.util.*
import java.util.concurrent.TimeUnit

fun isTimeIsGreaterThen24Hours(time: Long): Boolean {
    val oTime = Date(time)
    val cTime = Date()
    val diff = cTime.time - oTime.time
    val daysDiff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
    if (daysDiff >= 1) {
        return true
    }
    return false
}
/*
fun isInternetAvailable(): Boolean {
    val connectivityManager =
        AppObjectController.joshApplication.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            //for other device how are able to connect with Ethernet
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        val nwInfo = connectivityManager.activeNetworkInfo ?: return false
        return nwInfo.isConnected
    }
}*/

fun showInternetNotAvailableMessage(context: Context) {
    Handler(Looper.getMainLooper()).post {
        Toast.makeText(
            context,
            context.getString(R.string.internet_not_available_msz),
            Toast.LENGTH_LONG
        ).show()
    }
}