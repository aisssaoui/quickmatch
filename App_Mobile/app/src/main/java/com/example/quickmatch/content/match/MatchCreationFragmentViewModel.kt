package com.example.quickmatch.content.match

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quickmatch.content.club.RequestStatus
import com.example.quickmatch.content.player
import com.example.quickmatch.network.ClubAndPlayerBelongClubObject
import com.example.quickmatch.network.DatabaseApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class MatchCreationFragmentViewModel : ViewModel() {

    private val _clubs = MutableLiveData<List<ClubAndPlayerBelongClubObject>>()
    val clubs : LiveData<List<ClubAndPlayerBelongClubObject>>
        get() = _clubs

    private val _clubsNames = MutableLiveData<List<String>>()
    val clubsNames : LiveData<List<String>>
        get() = _clubsNames

    private val _slots = MutableLiveData<List<Int>>()
    val slots : LiveData<List<Int>>
        get() = _slots

    private val _getClubsStatus = MutableLiveData<RequestStatus>()
    val getClubsStatus : LiveData<RequestStatus>
        get() =_getClubsStatus

    private val _getClubsNamesStatus = MutableLiveData<RequestStatus>()
    val getClubsNamesStatus : LiveData<RequestStatus>
        get() =_getClubsNamesStatus

    // Create coroutine job and scope
    private var viewModelJob = Job()

    // Uses main thread coz retrofit works itself on background threads
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getPossibleClubsNames()
        getPossibleClubs()
    }

    private fun getPossibleClubs() {

        _getClubsStatus.value = RequestStatus.LOADING
        coroutineScope.launch {

            try {

                _clubs.value = DatabaseApi.retrofitService.getPlayerClubs(player.id!!)
                _getClubsStatus.value = RequestStatus.DONE

            } catch(t: Throwable) {

                Timber.i(t.message + " / getPossibleClubs()")
                _getClubsStatus.value = RequestStatus.ERROR

            }
        }

    }

    private fun getPossibleClubsNames() {

        _getClubsNamesStatus.value = RequestStatus.LOADING
        coroutineScope.launch {

            try {

                _clubsNames.value = DatabaseApi.retrofitService.getPlayerClubs(player.id!!).map { club -> club.name }
                _getClubsNamesStatus.value = RequestStatus.DONE

            } catch(t: Throwable) {

                Timber.i(t.message + " / getPossibleClubsNames()")
                _getClubsNamesStatus.value = RequestStatus.ERROR

            }
        }

    }

    //TODO precise date and deletion date ?
    fun processMatchCreation(startHour: String, endHour: String, minSize: Int, maxSize: Int, location: String, repeatDays: List<Boolean>) {

        checkSlots(startHour, endHour, repeatDays)
        createMatch(location, minSize, maxSize)
        sendInvitations()

    }

    private fun checkSlots(startHour: String, endHour: String, repeatDays: List<Boolean>) {

        coroutineScope.launch {

            try {


            } catch (t: Throwable) {


            }
        }

    }

    private fun createMatch(location: String, minSize: Int, maxSize: Int) {

        coroutineScope.launch {

            try {


            } catch (t: Throwable) {


            }
        }
    }

    private fun sendInvitations() {
        
        coroutineScope.launch {

            try {


            } catch (t: Throwable) {


            }
        }

    }


}
