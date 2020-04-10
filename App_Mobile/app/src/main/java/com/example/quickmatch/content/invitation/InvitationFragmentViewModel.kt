package com.example.quickmatch.content.invitation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quickmatch.content.club.RequestStatus
import com.example.quickmatch.content.player
import com.example.quickmatch.network.DatabaseApi
import com.example.quickmatch.network.PlayerMeetObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class InvitationFragmentViewModel : ViewModel() {

    private val _getNewInvitationsStatus = MutableLiveData<RequestStatus>()
    val getNewInvitationsStatus : LiveData<RequestStatus>
        get() = _getNewInvitationsStatus

    private val _getTreatedInvitationsStatus = MutableLiveData<RequestStatus>()
    val getTreatedInvitationsStatus : LiveData<RequestStatus>
        get() = _getTreatedInvitationsStatus

    private val _treatedInvitations = MutableLiveData<List<PlayerMeetObject>>()
    val treatedInvitations : LiveData<List<PlayerMeetObject>>
        get() = _treatedInvitations

    private val _newInvitations = MutableLiveData<List<PlayerMeetObject>>()
    val newInvitations : LiveData<List<PlayerMeetObject>>
        get() = _newInvitations

    // Create coroutine job and scope
    private var viewModelJob = Job()

    // Uses main thread coz retrofit works itself on background threads
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getNewInvitations()
        getTreatedInvitations()
    }

    fun getNewInvitations() {

        _getNewInvitationsStatus.value = RequestStatus.LOADING

        coroutineScope.launch {

            try {

                _newInvitations.value = DatabaseApi.retrofitService.getPlayerInvitations(player.id!!).filter { invitation -> invitation.status == null }
                _getNewInvitationsStatus.value = RequestStatus.DONE

            } catch (t: Throwable) {

                Timber.i("%s / getNewInvitations()", t.message)
                _getNewInvitationsStatus.value = RequestStatus.ERROR

            }
        }
    }

    fun getTreatedInvitations() {

        _getTreatedInvitationsStatus.value = RequestStatus.LOADING

        coroutineScope.launch {

            try {

                _treatedInvitations.value = DatabaseApi.retrofitService.getPlayerInvitations(player.id!!).filter { invitation -> invitation.status != null }
                _getTreatedInvitationsStatus.value = RequestStatus.DONE

            } catch (t: Throwable) {

                Timber.i("%s / getTreatedInvitations()", t.message)
                _getTreatedInvitationsStatus.value = RequestStatus.ERROR

            }
        }
    }

}
