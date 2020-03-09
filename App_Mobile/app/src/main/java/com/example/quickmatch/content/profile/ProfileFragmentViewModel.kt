package com.example.quickmatch.content.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quickmatch.network.DatabaseApi
import com.example.quickmatch.network.PlayerObject
import com.example.quickmatch.utils.FormatUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

enum class UniqueFieldStatus { LOADING, AVAILABLE, TAKEN }
enum class SaveStatus { LOADING, DONE, ERROR }

//TODO add language and notifications support here and in database
//TODO add phone checks when available in database

class ProfileFragmentViewModel : ViewModel() {

    /* Status for database requests */

    private val _mailStatus = MutableLiveData<UniqueFieldStatus>()
    val mailStatus : LiveData<UniqueFieldStatus>
        get() = _mailStatus

    private val _pseudoStatus = MutableLiveData<UniqueFieldStatus>()
    val pseudoStatus : LiveData<UniqueFieldStatus>
        get() = _pseudoStatus

    private val _phoneStatus = MutableLiveData<UniqueFieldStatus>()
    val phoneStatus : LiveData<UniqueFieldStatus>
        get() = _phoneStatus

    private val _saveStatus = MutableLiveData<SaveStatus>()
    val saveStatus : LiveData<SaveStatus>
        get() = _saveStatus

    /* Status for fields format */

    private val _pseudoFormat = MutableLiveData<Boolean>()
    val pseudoFormat : LiveData<Boolean>
        get() = _pseudoFormat

    private val _mailFormat = MutableLiveData<Boolean>()
    val mailFormat : LiveData<Boolean>
        get() = _mailFormat

    private val _nameFormat = MutableLiveData<Boolean>()
    val nameFormat : LiveData<Boolean>
        get() = _nameFormat

    private val _firstNameFormat = MutableLiveData<Boolean>()
    val firstNameFormat : LiveData<Boolean>
        get() = _firstNameFormat

    private val _phoneNumberFormat = MutableLiveData<Boolean>()
    val phoneNumberFormat : LiveData<Boolean>
        get() = _phoneNumberFormat

    private val _bioFormat = MutableLiveData<Boolean>()
    val bioFormat : LiveData<Boolean>
        get() = _bioFormat

    private val _avatarFormat = MutableLiveData<Boolean>()
    val avatarFormat : LiveData<Boolean>
        get() = _avatarFormat

    /* Coroutine needed instances */

    // Create coroutine job and scope
    private var viewModelJob = Job()

    // Uses main thread coz retrofit works itself on background threads
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /* Method checking unicity of certain fields before saving changes */

    fun checkMail(mailAddress : String) {

        _mailStatus.value = UniqueFieldStatus.LOADING

        coroutineScope.launch {

            try {

                var result = DatabaseApi.retrofitService.getPlayerByMail(mailAddress)
                _mailStatus.value = UniqueFieldStatus.TAKEN

            } catch (t: Throwable) {

                _mailStatus.value = UniqueFieldStatus.AVAILABLE
                Timber.i(t.message + " / checkMail()")

            }
        }
    }

    fun checkPseudo(pseudo : String) {

        _pseudoStatus.value = UniqueFieldStatus.LOADING

        coroutineScope.launch {

            try {

                var result = DatabaseApi.retrofitService.getPlayerByPseudo(pseudo)
                _pseudoStatus.value = UniqueFieldStatus.TAKEN

            } catch (t: Throwable) {

                _pseudoStatus.value = UniqueFieldStatus.AVAILABLE
                Timber.i(t.message + " / checkPseudo()")

            }
        }
    }

    fun checkPhone(phoneNumber : String) {

        _phoneStatus.value = UniqueFieldStatus.LOADING

        coroutineScope.launch {

            try {

                var result = DatabaseApi.retrofitService.getPlayerByMail(phoneNumber)
                _phoneStatus.value = UniqueFieldStatus.TAKEN

            } catch (t: Throwable) {

                _phoneStatus.value = UniqueFieldStatus.AVAILABLE
                Timber.i(t.message + " / checkPhone()")

            }
        }
    }

    /* Format check for each field */

    /* Check format for pseudo */
    fun checkFormatPseudo(pseudo: String) {
        val l = pseudo.length
        _pseudoFormat.value = l in 1..FormatUtils.BASIC_SIZE
    }

    /* Check format for mail address */
    fun checkFormatMail(mailAddress: String) {
        val l = mailAddress.length
        _mailFormat.value = l in 1..FormatUtils.MAIL_SIZE && mailAddress.matches(FormatUtils.mailPattern)
    }

    /* Check phone number format (french) */
    fun checkFormatPhoneNumber(phoneNumber: String) {
        _phoneNumberFormat.value = phoneNumber.matches(FormatUtils.phoneNumberPattern)
    }

    /* Check format for name */
    fun checkFormatName(name: String) {
        val l = name.length
        _nameFormat.value = l in 1..FormatUtils.BASIC_SIZE
    }

    /* Check format for 1st name */
    fun checkFormatFirstName(firstName: String) {
        val l = firstName.length
        _firstNameFormat.value = l in 1..FormatUtils.BASIC_SIZE
    }

    /* Check format for bio */
    fun checkFormatBio(bio: String) {
        val l = bio.length
        _bioFormat.value = l in 0..FormatUtils.BIO_SIZE
    }

    /* Check format for avatar's URL */
    fun checkFormatAvatar(avatarURL: String) {
        val l = avatarURL.length
        _avatarFormat.value = l in 0..FormatUtils.AVATAR_SIZE
    }

    /* Saves all changes done on the player profile */
    fun saveChanges(playerId: Int,
                    name: String, firstName: String, pseudo: String,
                    bio: String, mailAddress: String, phoneNumber: String, avatarURL: String) : Boolean {

        checkMail(mailAddress)
        //checkPhone(phoneNumber)
        checkPseudo(pseudo)

        val unicityList = listOf(_mailStatus.value, _pseudoStatus.value, _phoneStatus.value)
        Timber.i(unicityList.toString())

        if (UniqueFieldStatus.TAKEN in unicityList)
            return false
        return true

        //TODO change update in backend
        /*
        val updatedPlayer = PlayerObject(null, name, firstName, pseudo, , mailAddress, finalPhoneNumber,
                0, 0, 0, 0,
                null, null )


        coroutineScope.launch {

            _saveStatus.value = SaveStatus.LOADING

            try {

                var result = DatabaseApi.retrofitService.updatePlayerById(playerId, updatedPlayer)
                _saveStatus.value = SaveStatus.DONE

            } catch (t: Throwable) {

                Timber.i(t.message + " / SaveChanges()")
                _saveStatus.value = SaveStatus.ERROR

            }
        }*/

    }
}
