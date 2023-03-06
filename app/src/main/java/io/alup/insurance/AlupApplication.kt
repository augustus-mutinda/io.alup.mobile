package io.alup.insurance

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import dagger.hilt.android.HiltAndroidApp

/**
 * @author Augustus, augustusmutinda16@gmail.com
 * Application class
 * More functionality can be added here
 */
@HiltAndroidApp
class AlupApplication : Application(){

    fun isOnline() =
        (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
            .let { it != null && it.isConnected }
}