package io.alup.insurance.model

import android.os.Build

/**
 * @author Augustus, augustusmutinda16@gmail.com
 * A class to hold all constants
 */
class Constants {
    companion object {
        var BASE = "https://run.mocky.io/v3/"
        var TKN = "7VURe4cl9FgSxZqj5_TAWJMjrbV71ALdk3md6PdC1cuiH2XHXcGgg4vlu"
        fun sourceApplication() = "io-pula-insurance-android"
        fun sourceVersion() = Build.BRAND + " " + Build.MODEL
        fun sourceType() = Build.VERSION.RELEASE ?: ""
    }
}