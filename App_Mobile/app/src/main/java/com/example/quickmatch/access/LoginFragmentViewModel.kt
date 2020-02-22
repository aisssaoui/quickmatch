package com.example.quickmatch.access

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quickmatch.network.DatabaseApi
import com.example.quickmatch.network.PlayerObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

enum class LoginStatus { UNKNOWN, GOOGLE, WRONG_PWD, NETWORK_ERROR, SUCCESS }

class LoginFragmentViewModel : ViewModel() {

    // Result of the login query
    private val _loginStatus = MutableLiveData<LoginStatus>()
    val loginStatus : LiveData<LoginStatus>
        get() = _loginStatus

    // Create coroutine job and scope
    private var viewModelJob = Job()

    // Uses main thread coz retrofit works itself on background threads
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    fun tryToLogin(mailAddress : String, password : String) {

        coroutineScope.launch {

            try {

                var result = DatabaseApi.retrofitService.getPlayerByMail(mailAddress)

                Timber.i(result.toString())

                checkPassword(result, password)

            } catch (t: Throwable) {

                Timber.i(t.message)
                _loginStatus.value = LoginStatus.NETWORK_ERROR

            }
        }
    }

    //TODO unhash password before check

    private fun checkPassword(player : PlayerObject, password: String) = when (player.password) {
                                                                                "stocked by Google" -> _loginStatus.value = LoginStatus.GOOGLE
                                                                                password ->  _loginStatus.value = LoginStatus.SUCCESS
                                                                                else -> _loginStatus.value = LoginStatus.WRONG_PWD
                                                                                }

}