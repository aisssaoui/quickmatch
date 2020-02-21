package com.example.quickmatch.access

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quickmatch.network.DatabaseApi
import com.example.quickmatch.network.PlayerObject
import com.example.quickmatch.utils.Utils
import kotlinx.coroutines.*
import timber.log.Timber
import java.security.MessageDigest

enum class SigninMailStatus { ACCOUNT_EXISTS, MAIL_VALID, WRONG_FORMAT }
enum class SigninPseudoStatus { PSEUDO_EXISTS, PSEUDO_VALID, WRONG_FORMAT }
enum class SigninPhoneNumberStatus { PHONE_NUMBER_EXISTS, PHONE_NUMBER_VALID, WRONG_FORMAT }
enum class SigninStatus { PASSWORD_NOT_MATCHING, NETWORK_ERROR, MISSING_FIELD_NAME, MISSING_FIELD_FIRST_NAME, WRONG_PASSWORD_SIZE, DONE }

class SigninFragmentViewModel : ViewModel() {

    /* Live datas of the viewModel */

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

    // Result of the phoneCheck query
    private val _phoneNumberStatus = MutableLiveData<SigninPhoneNumberStatus>()
    val phoneNumberStatus : LiveData<SigninPhoneNumberStatus>
        get() = _phoneNumberStatus

    /* Coroutine needed instances */

    // Create coroutine job and scope
    private var viewModelJob = Job()

    // Uses main thread coz retrofit works itself on background threads
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /* Methods */

    /* request to check if the mail typed already exists */
    private fun checkMailAddress(mailAddress: String) {

        // Check mail format
        val mailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+")

        if (!mailAddress.matches(mailPattern)) {
            _mailStatus.value = SigninMailStatus.WRONG_FORMAT
            return
        }

        coroutineScope.launch {

            try { // Case request gives an existing player

                var result = DatabaseApi.retrofitService.getPlayerByMail(mailAddress)
                _mailStatus.value  = SigninMailStatus.ACCOUNT_EXISTS

            } catch (t : Throwable) { // Case we get an error

                Timber.i( t.message + " mail")
                _mailStatus.value = SigninMailStatus.MAIL_VALID

            }
        }
    }

    /* request to check if the pseudo typed already exists */
    private fun checkPseudo(pseudo: String) {

        // Check pseudo format
        if ((pseudo.length < 2) or (pseudo.length > 20)) {
            _pseudoStatus.value = SigninPseudoStatus.WRONG_FORMAT
            return
        }

        coroutineScope.launch {

            try { // Case request gives an existing player

                var result = DatabaseApi.retrofitService.getPlayerByPseudo(pseudo)
                _pseudoStatus.value  = SigninPseudoStatus.PSEUDO_EXISTS

            } catch (t : Throwable) { // Case we get an error

                Timber.i(t.message + " pseudo")
                _pseudoStatus.value = SigninPseudoStatus.PSEUDO_VALID

            }
        }
    }

    //TODO uncomment when route is available
    /*
    /* request to check if the pseudo typed already exists */
    private fun checkPhoneNumber(phoneNumber: String?) {

        // Check phone number format
        if(phoneNumber.isNullOrEmpty()) {
            _phoneNumberStatus.value = SigninPhoneNumberStatus.WRONG_FORMAT
            return
        }

        coroutineScope.launch {

            try { // Case request gives an existing player

                var result = DatabaseApi.retrofitService.getPlayerByPhoneNumber(phoneNumber)
                _phoneNumberStatus.value  = SigninPhoneNumberStatus.PHONE_NUMBER_EXISTS

            } catch (t : Throwable) { // Case we get an error

                Timber.i(t.message + " phone")
                _phoneNumberStatus.value = SigninPhoneNumberStatus.PHONE_NUMBER_VALID

            }
        }
    }*/

    /* function checking the validity of unique informations and then posting the new player to the remote server */
    fun tryToSignIn(name : String, firstName : String, pseudo : String, mailAddress : String,
                    password : String, passwordCheck: String, phoneNumber : String?) {

        //TODO put the following calls in edits onChange methods

        //checkMailAddress(mailAddress)
        //checkPseudo(pseudo)
        //checkPhoneNumber(phoneNumber)

        //TODO fix limits for each field according to database varchar size
        //TODO fix an accepted password size
        if (password == "")
            _signinStatus.value = SigninStatus.WRONG_PASSWORD_SIZE

        else if (name == "")
            _signinStatus.value = SigninStatus.MISSING_FIELD_NAME

        else if (firstName == "")
            _signinStatus.value = SigninStatus.MISSING_FIELD_FIRST_NAME

        else if (password != passwordCheck)
            _signinStatus.value = SigninStatus.PASSWORD_NOT_MATCHING

        else {

            /* Give a value for phone number to actually have something in the player object */
            var finalPhoneNumber: String? = null
            if (phoneNumber == "") finalPhoneNumber else finalPhoneNumber = phoneNumber


            /* hash password */
            val hashedPassword = Utils.sha512(password)
            Timber.i(hashedPassword)

            /* Player that will be posted to the database */
            var newPlayerObject = PlayerObject(null, name, firstName, pseudo, hashedPassword, mailAddress, finalPhoneNumber,
                    0, 0, 0, 0,
                    null, null )

            Timber.i(newPlayerObject.toString())

            coroutineScope.launch {

                var firstResult : PlayerObject? = null

                try {

                    firstResult = DatabaseApi.retrofitService.addPlayer(newPlayerObject)

                    Timber.i(firstResult.toString())

                } catch (t: Throwable) {

                    Timber.i(t.message + " post1")
                    _signinStatus.value = SigninStatus.NETWORK_ERROR
                }

                if (firstResult == null) return@launch

                try { /* update salted password */

                    var finalPlayerObject = firstResult!!
                    finalPlayerObject.password = Utils.sha512(password + firstResult.id.toString())

                    Timber.i(finalPlayerObject.password)

                    var result = DatabaseApi.retrofitService.updatePlayerById(finalPlayerObject.id!!, finalPlayerObject)

                    _signinStatus.value = SigninStatus.DONE
                    Timber.i(result.toString())

                } catch (t: Throwable) {

                    Timber.i(t.message + " post2")
                    _signinStatus.value = SigninStatus.NETWORK_ERROR

                }
            }
        }


    }

}
