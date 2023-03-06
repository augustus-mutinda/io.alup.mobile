package io.alup.insurance.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.alup.insurance.model.AlupState
import io.alup.insurance.model.ApiRepository
import io.alup.insurance.model.Constants
import io.alup.insurance.model.Util.Companion.fullMsisdn
import io.alup.insurance.model.custom.AlupViewModel
import io.alup.insurance.model.data.CreateUserResponse
import io.alup.insurance.model.data.User
import io.alup.insurance.model.data.UserDto
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Augustus, augustusmutinda16@gmail.com
 * Authentication view model, handles all authentication related tasks
 * such as login, register, verify otp, resend otp, etc
 * Basically all tasks before the user is logged in
 */
@HiltViewModel
class AuthViewModel @Inject constructor(_application: Application, var _repository: ApiRepository) :
    AlupViewModel(_application) {

    var authState = MutableLiveData<AlupState<Boolean>>(AlupState.Default)
    var signInState = MutableLiveData<AlupState<Boolean>>(AlupState.Default)
    var registerState = MutableLiveData<AlupState<CreateUserResponse>>(AlupState.Default)
    var verifyOtpState = MutableLiveData<AlupState<Boolean>>(AlupState.Default)
    var currentUserState = MutableLiveData<AlupState<User>>(AlupState.Default)
    var resendCounterState = MutableLiveData<AlupState<Int>>(AlupState.Default)

    var phone = MutableLiveData("")
    var enrollmentId = MutableLiveData("")
    var otp = ""
    var email = MutableLiveData("")
    var firstName = MutableLiveData("")
    var lastName = MutableLiveData("")

    fun initiate() {
        authState.value = AlupState.Resolved(_repository.hasAuthToken())
    }

    fun getOtp() {
        signInState.value = AlupState.Loading
        if (signInState.checkOnlineStatus())
            viewModelScope.launch {
                _repository
                    .getOtp(phone.value!!.fullMsisdn())
                    .also {
                        signInState.value = AlupState.Verify(it.isSuccessful)
                        if (it.isSuccessful) {
                            enrollmentId.postValue(it.body()?.body?.code ?: "")
                            startResendCounter()
                        }
                    }
            }
    }

    fun verifyOtp(t: String?) {
        verifyOtpState.value = AlupState.Loading
        if (verifyOtpState.checkOnlineStatus())
            viewModelScope.launch {
                _repository
                    .verifyOtp(t ?: otp, enrollmentId.value!!)
                    .also {
                        if (it.isSuccessful && it.body()?.body?.verified == true)
                            _repository.saveString(
                                Constants.TKN,
                                (it.body()?.body?.token?.code ?: "").reversed()
                            )
                        verifyOtpState.value =
                            if (it.body()?.body?.verified == true) AlupState.Success(it.isSuccessful)
                            else AlupState.Failed("Verification failed, please try again")
                    }
            }
    }

    fun getCurrent() {
        currentUserState.value = AlupState.Loading
        if (currentUserState.checkOnlineStatus())
            viewModelScope.launch {
                _repository
                    .getCurrentUser()
                    .also {
                        if (it.isSuccessful && it.body()?.body != null)
                            if (it.body()?.retry() == true) getCurrent()
                            else currentUserState.value = AlupState.Success(it.body()?.body!!)
                        else
                            currentUserState.value =
                                AlupState.Failed("Something went wrong, some features may not work")
                    }
            }
    }

    fun register() {
        registerState.value = AlupState.Loading
        if (registerState.checkOnlineStatus())
            viewModelScope.launch {
                _repository
                    .signUp(getUser())
                    .also {
                        if (it.isSuccessful && it.body()?.body != null)
                            registerState.value = AlupState.Success(it.body()?.body!!)
                        else
                            registerState.value = AlupState.Failed("Registration failed")
                    }
            }
    }

    fun checkPreFlight() = when {
        phone.value == null || phone.value!!.isEmpty() -> "Please enter a phone number!"
        phone.value!!.length < 10 -> "Phone number is should look similar to 0712345678"
        else -> null
    }

    fun checkOtpPreFlight() = when {
        otp == null || otp.isEmpty() -> "Please enter a otp code"
        otp.length < 6 -> "OTP is should look similar to 123456"
        else -> null
    }

    fun checkRegisterPreFlight() = when {
        phone.value == null || phone.value!!.isEmpty() -> "Please enter a phone number!"
        phone.value!!.length < 10 -> "Phone number is should look similar to 0712345678"
        email.value == null || email.value!!.isEmpty() -> "Please enter an email address!"
        firstName.value == null || firstName.value!!.isEmpty() -> "Please enter Firstname!"
        lastName.value == null || lastName.value!!.isEmpty() -> "Please enter an Lastname!"
        else -> null
    }

    private fun getUser() = UserDto(
        email = email.value!!,
        firstName = firstName.value!!,
        lastName = lastName.value!!,
        userName = firstName.value!!.lowercase(),
        phone = phone.value!!.fullMsisdn()
    )

    private fun startResendCounter() {
        resendCounterState.value = AlupState.Loading
        viewModelScope.launch {
            for (i in 60 downTo 0) {
                resendCounterState.value = AlupState.Success(i)
                delay(1000)
            }
        }
    }

    fun signOut() {
        _repository.saveString(Constants.TKN, "")
    }

    fun resetAuthState() {
        signInState.value = AlupState.Default
        verifyOtpState.value = AlupState.Default
    }
}