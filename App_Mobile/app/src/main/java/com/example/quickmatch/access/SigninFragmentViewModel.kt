package com.example.quickmatch.access

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quickmatch.network.DatabaseApi
import com.example.quickmatch.network.PlayerObject
import kotlinx.coroutines.*

enum class SigninMailStatus { ACCOUNT_EXISTS, MAIL_VALID }
enum class SigninPseudoStatus { PSEUDO_EXISTS, PSEUDO_VALID }
enum class SigninStatus { PASSWORD_NOT_MATCHING, NETWORK_ERROR, MISSING_FIELD, WRONG_PASSWORD_SIZE, DONE }

class SigninFragmentViewModel : ViewModel() {

    // Result of the signin query
    private val _signinStatus = MutableLiveData<SigninStatus>()
    val signinStatus : LiveData<SigninStatus>
        get() = _signinStatus

    // Result of the mailCheck query
    private val _mailStatus = MutableLiveData<SigninMailStatus>()
    val mailStatus : LiveData<SigninMailStatus>
        get() = _mailStatus

    // Result of the pseudoCheck query
    private val _pseudoStatus = MutableLiveData<SigninPseudoStatus>()
    val pseudoStatus : LiveData<SigninPseudoStatus>
        get() = _pseudoStatus

    // Create coroutine job and scope
    private var viewModelJob = Job()

    // Uses main thread coz retrofit works itself on background threads
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private fun checkMailAddress(mailAddress: String) {

        //TODO check mail address typo (vsfbs@sdsbbs.qvsdv)
        coroutineScope.launch {

            var resultDeferred : Deferred<PlayerObject> = DatabaseApi.retrofitService.getPlayerByMail(mailAddress)

            try { // Case request gives an existing player
                var result = resultDeferred.await()
                _mailStatus.value  = SigninMailStatus.ACCOUNT_EXISTS
            } catch (t : Throwable) { // Case we get an error
                Log.i("SigninFragmentViewModel", t.message + "mail")
                _mailStatus.value = SigninMailStatus.MAIL_VALID
            }
        }
    }

    //TODO finish when backend func is available
    private fun checkPseudo(mailAddress: String) {

        /*
        coroutineScope.launch {

            var resultDeferred : Deferred<PlayerObject> = DatabaseApi.retrofitService.getPlayerByMail(mailAddress)

            try { // Case request gives an existing player
                var result = resultDeferred.await()
                _pseudoStatus.value  = SigninPseudoStatus.PSEUDO_EXISTS
            } catch (t : Throwable) { // Case we get an error
                Log.i("SigninFragmentViewModel", t.message)
                _pseudoStatus.value = SigninPseudoStatus.PSEUDO_VALID
            }
        }*/
    }

    fun tryToSignIn(name : String, firstName : String, pseudo : String, mailAddress : String,
                    password : String, passwordCheck: String, phoneNumber : String) {

        Log.i("SigninFragmentViewModel", name)
        //TODO keep in memory which field is empty to help user

        //TODO fix an accepted password size
        if (password == "")
            _signinStatus.value = SigninStatus.WRONG_PASSWORD_SIZE

        else if (name == "")
            _signinStatus.value = SigninStatus.MISSING_FIELD

        else if (firstName == "")
            _signinStatus.value = SigninStatus.MISSING_FIELD

        else if (password != passwordCheck)
            _signinStatus.value = SigninStatus.PASSWORD_NOT_MATCHING

        else {

            val newPlayerObject = PlayerObject(null, name, firstName, pseudo, password, mailAddress, phoneNumber,
                    0, 0, 0, 0,
                    null, null )

            Log.i("SigninFragmentViewModel", newPlayerObject.toString())

            coroutineScope.launch {

                var resultDeferred = DatabaseApi.retrofitService.addPlayer(newPlayerObject)

                try {
                    var result = resultDeferred.await()
                    _signinStatus.value = SigninStatus.DONE

                } catch (t: Throwable) {
                    Log.i("SigninFragmentViewModel", t.message + "post")
                    _signinStatus.value = SigninStatus.NETWORK_ERROR
                }
            }
        }




    }

}
