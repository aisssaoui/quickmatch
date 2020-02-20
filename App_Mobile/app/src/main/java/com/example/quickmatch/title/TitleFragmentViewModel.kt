package com.example.quickmatch.title

import android.util.Log
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quickmatch.network.DatabaseApi
import com.example.quickmatch.network.PlayerObject
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber
import javax.security.auth.callback.Callback

enum class ConnectionStatus { DONE, ERROR, LOADING, RESET }

class TitleFragmentViewModel : ViewModel() {

    // Create coroutine job and scope
    private var viewModelJob = Job()

    // Uses main thread coz retrofit works itself on background threads
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    // Connection status property (private mutable and public)
    private val _status = MutableLiveData<ConnectionStatus>()
    val status : LiveData<ConnectionStatus>
        get() = _status

    private val _listPlayers = MutableLiveData<List<PlayerObject>>()
    val listPlayers : LiveData<List<PlayerObject>>
        get() = _listPlayers

    init {
        tryToConnect()
    }

    fun tryToConnect() {

        coroutineScope.launch {
            var resultDeferred = DatabaseApi.retrofitService.testConnection() // work on a background thread

            Timber.i("request sent")

            try {

                _status.value = ConnectionStatus.LOADING
                Timber.i("changed to loading")
                var result = resultDeferred.await() // waiting result without blocking main thread
                Timber.i(result.message)
                _status.value = ConnectionStatus.DONE

            } catch (t: Throwable) {

                _status.value = ConnectionStatus.ERROR

            }

            Timber.i(_status.value.toString())

        }
    }

    fun getPlayersList() {

        coroutineScope.launch {

            val listResultDeferred = DatabaseApi.retrofitService.getAllPlayers()

            try {

                val result = listResultDeferred.await()
                _listPlayers.value = result

            } catch (t: Throwable) {

                Timber.i(t.message)

            }

        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun resetConnectionStatus() {
        _status.value = ConnectionStatus.RESET
    }
}
