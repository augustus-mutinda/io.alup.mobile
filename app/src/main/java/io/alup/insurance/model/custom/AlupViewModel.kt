package io.alup.insurance.model.custom

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.alup.insurance.AlupApplication
import io.alup.insurance.model.AlupState

/**
 * @author Augustus, augustusmutinda16@gmail.com
 * A base class for all view models
 * @param _application The application instance
 * @see AndroidViewModel
 * @see AlupApplication
 */
open class AlupViewModel(private var _application: Application) : AndroidViewModel(_application) {
    fun app() = _application as AlupApplication
    fun <T : Any> MutableLiveData<AlupState<T>>.checkOnlineStatus(): Boolean {
        if (!app().isOnline())
            value = AlupState.Failed("You are offline,\nPlease check your internet connection")
        return app().isOnline()
    }
}