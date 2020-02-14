package com.example.quickmatch.network

import com.squareup.moshi.Json

enum class STATUS { TRUE, FALSE, NULL }

/* Club data class */
data class InvitationObject(
    val id : Int,
    @Json(name = "slot") val slot : Int,
    @Json(name = "player") val player : Int,
    @Json(name = "meet") val meet : Int,
    @Json(name = "status") val status : Boolean?,
    @Json(name = "event_type") val event_type : String
)