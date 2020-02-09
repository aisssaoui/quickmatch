package com.example.quickmatch.network

import com.squareup.moshi.Json
import java.util.*

/* TODO: implémenter les énum pour l'état d'une invitation acceptée, refusée, null*/
enum class STATUS;

/* Club data class */
data class InvitationObject(
    val id : Int,
    @Json(name = "slot") val slot : Int,
    @Json(name = "player") val player : Int,
    @Json(name = "meet") val meet : Int,
    @Json(name = "status") val status : STATUS,
    @Json(name = "event_type") val event_type : String
)