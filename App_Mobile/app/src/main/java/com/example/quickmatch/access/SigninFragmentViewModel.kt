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

/* sizes to respect to store in database */
const val MAIL_SIZE = 50
const val BASIC_SIZE = 20
const val MIN_PASSWORD_SIZE = 8

/* enum classes for status check on requests */
enum class SigninMailStatus { ACCOUNT_EXISTS, MAIL_VALID, LOADING }
enum class SigninPseudoStatus { PSEUDO_EXISTS, PSEUDO_VALID, WRONG_FORMAT, LOADING }
enum class SigninPhoneNumberStatus { WRONG_FORMAT, PHONE_NUMBER_EXISTS, PHONE_NUMBER_VALID }
enum class SigninStatus { NETWORK_ERROR, DONE }

class SigninFragmentViewModel : ViewModel() {

    /* Live datas of the viewModel */

    private val _pseudoFormat = MutableLiveData<Boolean>()
    val pseudoFormat : LiveData<Boolean>
    get() = _pseudoFormat

    private val _mailFormat = MutableLiveData<Boolean>()
    val mailFormat : LiveData<Boolean>
        get() = _mailFormat

    private val _passwordFormat = MutableLiveData<Boolean>()
    val passwordFormat : LiveData<Boolean>
        get() = _passwordFormat

    private val _passwordCheckFormat = MutableLiveData<Boolean>()
    val passwordCheckFormat : LiveData<Boolean>
        get() = _passwordCheckFormat

    private val _nameFormat = MutableLiveData<Boolean>()
    val nameFormat : LiveData<Boolean>
        get() = _nameFormat

    private val _firstNameFormat = MutableLiveData<Boolean>()
    val firstNameFormat : LiveData<Boolean>
        get() = _firstNameFormat

    private val _phoneNumberFormat = MutableLiveData<Boolean>()
    val phoneNumberFormat : LiveData<Boolean>
        get() = _phoneNumberFormat

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

    /* Check format for pseudo */
    fun checkFormatPseudo(pseudo: String) {
        val l = pseudo.length
        _pseudoFormat.value = l in 1..BASIC_SIZE
    }

    /* Check format for pseudo */
    fun checkFormatMail(mailAddress: String) {
        val l = mailAddress.length
        val mailPattern = Regex("[a-zA-Z0-9._-]+@[a-z0-9-]+\\.[a-z]+")
        _mailFormat.value = l in 1..MAIL_SIZE && mailAddress.matches(mailPattern)
    }

    /* Check format for password */
    fun checkFormatPassword(password: String) {
        val passwordPattern = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{$MIN_PASSWORD_SIZE,$BASIC_SIZE}$")
        _passwordFormat.value = password.matches(passwordPattern)
    }

    /* Check passwords equality */
    fun checkFormatPasswordCheck(password: String, passwordCheck: String) {
        val passwordPattern = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{$MIN_PASSWORD_SIZE,$BASIC_SIZE}$")
        _passwordCheckFormat.value = password == passwordCheck && passwordCheck.matches(passwordPattern)
    }

    /* Check phone number format (french) */
    fun checkPhoneNumberFormat(phoneNumber: String) {
        val phoneNumberPattern = Regex("^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}\$")
        _phoneNumberFormat.value = phoneNumber.matches(phoneNumberPattern)
    }

    /* Check format for name */
    fun checkFormatName(name: String) {
        val l = name.length
        _nameFormat.value = l in 1..BASIC_SIZE
    }

    /* Check format for 1st name */
    fun checkFormatFirstName(firstName: String) {
        val l = firstName.length
        _firstNameFormat.value = l in 1..BASIC_SIZE
    }

    /* request to check if the mail typed already exists */
    private fun checkMailAddress(mailAddress: String) {

        _mailStatus.value = SigninMailStatus.LOADING

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

        _pseudoStatus.value = SigninPseudoStatus.LOADING

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

    /* Parse phone number to keek the difits only */
    private fun parsePhoneNumber(phoneNumber: String) : String? {
        if(phoneNumber != "") {
            var parsedPhoneNumber = phoneNumber.replace("+33", "0")
            Timber.i(phoneNumber)
            return parsedPhoneNumber.filterNot { it in ".- " }
        }
        return null
    }


    /* function checking the validity of unique informations and then posting the new player to the remote server */
    fun tryToSignIn(name : String, firstName : String, pseudo : String, mailAddress : String,
                    password : String, passwordCheck: String, phoneNumber : String) {

        /* Check unicity for unique fields */
        checkMailAddress(mailAddress)
        checkPseudo(pseudo)
        //checkPhoneNumber(phoneNumber)

        /* Parse phone number */
        val finalPhoneNumber = parsePhoneNumber(phoneNumber)
        Timber.i(finalPhoneNumber)

        /* hash password */
        val hashedPassword = Utils.sha512(password)
        //Timber.i(hashedPassword)

        /* Player that will be posted to the database */
        var newPlayerObject = PlayerObject(null, name, firstName, pseudo, hashedPassword, mailAddress, finalPhoneNumber,
                0, 0, 0, 0,
                null, null )

        //Timber.i(newPlayerObject.toString())

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
