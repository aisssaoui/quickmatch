package com.example.quickmatch.utils

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import java.security.MessageDigest

/**
 * Hashing Utils
 * @author Sam Clarke <www.samclarke.com>
 * @license MIT
 */

object HashUtils {

    fun sha512(input: String) = hashString("SHA-512", input)

    private fun hashString(type: String, input: String): String {
        val HEX_CHARS = "0123456789ABCDEF"
        val bytes = MessageDigest
                .getInstance(type)
                .digest(input.toByteArray())

        /* Convert hash into string value */

        val result = StringBuilder(bytes.size * 2)

        bytes.forEach {
            val i = it.toInt()
            result.append(HEX_CHARS[i shr 4 and 0x0f])
            result.append(HEX_CHARS[i and 0x0f])
        }

        return result.toString()
    }

}

object FormatUtils {

    /* sizes to respect to store in database */
    const val MAIL_SIZE = 50
    const val BASIC_SIZE = 20
    const val MIN_PASSWORD_SIZE = 8
    const val BIO_SIZE = 80
    const val AVATAR_SIZE = 60
    const val CLUB_NAME_SIZE = 40

    /* Regexs */
    val mailPattern = Regex("[a-zA-Z0-9._-]+@[a-z0-9-]+\\.[a-z]+")
    val passwordPattern = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{$MIN_PASSWORD_SIZE,$BASIC_SIZE}$")
    val phoneNumberPattern = Regex("^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}\$")

    /* Parse phone number to keek the difits only */
    fun parsePhoneNumber(phoneNumber: String) : String? {
        if(phoneNumber != "") {
            var parsedPhoneNumber = phoneNumber.replace("+33", "0")
            Timber.i(phoneNumber)
            return parsedPhoneNumber.filterNot { it in ".- " }
        }
        return null
    }

    /* parse a date string from database */
    fun parseDateToJJMMAAAA(date: String) : String {
        val splitDate = date.split("-", "T")
        val day = splitDate[2]
        val month = splitDate[1]
        val year = splitDate[0]
        return "$day / $month / $year"
    }
}



