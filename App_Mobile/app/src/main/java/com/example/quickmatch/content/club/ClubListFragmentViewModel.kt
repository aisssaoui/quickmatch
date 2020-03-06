package com.example.quickmatch.content.club

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quickmatch.network.ClubObject
import com.example.quickmatch.network.DatabaseApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

enum class RequestStatus { DONE, ERROR, LOADING }

class ClubListFragmentViewModel : ViewModel() {

    private val _getAllStatus = MutableLiveData<RequestStatus>()
    val getAllStatus : LiveData<RequestStatus>
        get() = _getAllStatus

   val clubs = MutableLiveData<List<ClubObject>>()

    // Create coroutine job and scope
    private var viewModelJob = Job()

    // Uses main thread coz retrofit works itself on background threads
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    init {
        getClubs()
    }

    private fun getClubs() {

        _getAllStatus.value = RequestStatus.LOADING

        coroutineScope.launch {

            try {

                clubs.value = DatabaseApi.retrofitService.getAllClubs()
                _getAllStatus.value = RequestStatus.DONE

            } catch (t: Throwable) {

                Timber.i(t.message + " / getClubs()")
                _getAllStatus.value = RequestStatus.ERROR

            }
        }
    }

}