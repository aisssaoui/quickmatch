package com.example.quickmatch.network

import com.squareup.moshi.Json
import java.util.*

/* TODO: vérifier pour le type DATE sil est conforme à ce que la requete renvoit (DATE ou DATETEXT ????)*/

/* Club data class */
data class SlotObject(
    val id : Int,
    @Json(name = "start_hour") val start : Date,
    @Json(name = "end_hour") val end : Date,
    @Json(name = "repeat_day") val day : String
)