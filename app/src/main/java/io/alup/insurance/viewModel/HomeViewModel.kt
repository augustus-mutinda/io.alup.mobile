package io.alup.insurance.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.alup.insurance.model.AlupState
import io.alup.insurance.model.ApiRepository
import io.alup.insurance.model.custom.AlupViewModel
import io.alup.insurance.model.data.AlupUI
import io.alup.insurance.model.data.Note
import io.alup.insurance.model.data.Payout
import io.alup.insurance.model.data.Policy
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Augustus, augustusmutinda16@gmail.com
 * Main view model, handles all other tasks not covered by AuthViewModel,
 * Basically all tasks after the user is logged in
 */
@HiltViewModel
class HomeViewModel @Inject constructor(_application: Application, var _repository: ApiRepository) :
    AlupViewModel(_application) {
    var ui = MutableLiveData(AlupUI())
    var notificationsState = MutableLiveData<AlupState<List<Note>>>(AlupState.Default)
    var policiesState = MutableLiveData<AlupState<List<Policy>>>(AlupState.Default)
    var payOutsState = MutableLiveData<AlupState<List<Payout>>>(AlupState.Default)

    fun getNotifications() {
        notificationsState.value = AlupState.Loading
        if (notificationsState.checkOnlineStatus())
            viewModelScope.launch {
                _repository.getNotifications()
                    .also {
                        if (it.isSuccessful && it.body() != null)
                            if (it.body()?.retry() == true) getNotifications()
                            else it.body().let { page ->
                                notificationsState.value =
                                    if (page?.body == null || page.body.results.isEmpty())
                                        AlupState.Empty
                                    else
                                        AlupState.Success(page.body.results)
                            }
                        else notificationsState.value = AlupState.Failed("")
                    }
            }
    }

    fun getPayouts() {
        payOutsState.value = AlupState.Loading
        if (payOutsState.checkOnlineStatus())
            viewModelScope.launch {
                _repository.getPayouts()
                    .also {
                        if (it.isSuccessful && it.body() != null)
                            if (it.body()?.retry() == true) getNotifications()
                            else it.body().let { page ->
                                payOutsState.value =
                                    if (page?.body == null || page.body.results.isEmpty())
                                        AlupState.Empty
                                    else
                                        AlupState.Success(page.body.results)
                            }
                        else payOutsState.value = AlupState.Failed("")
                    }
            }
    }

    fun getPolicies() {
        policiesState.value = AlupState.Loading
        if (policiesState.checkOnlineStatus())
            viewModelScope.launch {
                _repository.getPolicies()
                    .also {
                        if (it.isSuccessful && it.body() != null)
                            if (it.body()?.retry() == true) getNotifications()
                            else it.body().let { page ->
                                policiesState.value =
                                    if (page?.body == null || page.body.results.isEmpty())
                                        AlupState.Empty
                                    else
                                        AlupState.Success(page.body.results)
                            }
                        else policiesState.value = AlupState.Failed("")
                    }
            }
    }
}