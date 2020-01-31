package com.example.quickmatch.title

import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quickmatch.network.DatabaseApi
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*

enum class ConnectionStatus { DONE, ERROR, LOADING }

class TitleFragmentViewModel : ViewModel() {

    // Create coroutine job and scope
    private var viewModelJob = Job()

    // Uses main thread coz retrofit works itself on background threads
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    // Connection status property (private mutable and public)
    private val _status = MutableLiveData<ConnectionStatus>()
    val status : LiveData<ConnectionStatus>
        get() = _status

    init {
        tryToConnect()
    }

    fun tryToConnect() {

        coroutineScope.launch {
            var resultDeferred = DatabaseApi.retrofitService.testConnection() // work on a background thread

            try {

                _status.value = ConnectionStatus.LOADING
                var result = resultDeferred.await() // waiting result without blocking main thread
                _status.value = ConnectionStatus.DONE

            } catch (t: Throwable) {

                _status.value = ConnectionStatus.ERROR

            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
