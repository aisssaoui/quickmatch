package com.example.quickmatch.title

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quickmatch.network.DatabaseApi
import com.example.quickmatch.network.TestObject
import kotlinx.coroutines.*
import java.lang.Exception

enum class ConnectionStatus { DONE, LOADING, ERROR }

class TitleFragmentViewModel : ViewModel() {

    // Connection status property (private mutable and public)
    private val _connectionEstablished = MutableLiveData<ConnectionStatus>()
    val connectionEstablished : LiveData<ConnectionStatus>
        get() = _connectionEstablished

    // Create coroutine job and scope
    val viewModelJob = Job()
    val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        tryToConnect()
    }

    private fun tryToConnect() {

        coroutineScope.launch {

            Log.i("TitleFragmentViewModel", "Before call")
            val futureResult = DatabaseApi.retrofitService.testConnection()
            Log.i("TitleFragmentViewModel", "After call")

            /* For some reasons it doesn't make it inside the try catch */
            try {
                _connectionEstablished.value = ConnectionStatus.LOADING
                Log.i("TitleFragmentViewModel", "Loading")
                var result = futureResult.await()
                Log.i("TitleFragmentViewModel", "After await")
                _connectionEstablished.value = ConnectionStatus.DONE
                Log.i("TitleFragmentViewModel", result.message)

            } catch (e: Exception) {
                _connectionEstablished.value = ConnectionStatus.ERROR
                Log.i("TitleFragmentViewModel", e.message!!)
            }
        }

    }


    fun doneTryingToConnect() {
        _connectionEstablished.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
