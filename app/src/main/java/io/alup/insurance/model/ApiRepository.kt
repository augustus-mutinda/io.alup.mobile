package io.alup.insurance.model

import android.content.SharedPreferences
import io.alup.insurance.model.Constants.Companion.TKN
import io.alup.insurance.model.data.UserDto
import javax.inject.Inject

/**
 * @author Augustus, augustusmutinda16@gmail.com
 * A class to implement all api calls
 * @see Api
 * @see SharedPreferences
 */
class ApiRepository @Inject constructor(
    private val api: Api,
    private val prefs: SharedPreferences
) {
    suspend fun getOtp(msisdn: String) = api.getOtp(msisdn)

    suspend fun verifyOtp(otp: String, code: String) = api.verifyOtp(otp, code)

    suspend fun signUp(body: UserDto) = api.signUp()

    suspend fun getCurrentUser() = api.getCurrentUser()

    suspend fun getNotifications() = api.getNotifications()

    suspend fun getPayouts() = api.getPayouts()

    suspend fun getPolicies() = api.getPolicies()

    fun hasAuthToken(): Boolean {
        return prefs.getString(TKN, "").let {
            it != null && it.isNotEmpty()
        }
    }

    fun saveString(tkn: String, reversed: String) {
        prefs.edit().putString(tkn, reversed).apply()
    }
}