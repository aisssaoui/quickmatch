package com.example.quickmatch.network

import com.squareup.moshi.Json
import java.util.*

/* Meet created by parsing data (moshi) in the JSON response */
data class MeetSheetObject(
    /* TODO: rajouter les id dans le resultat de la requete du back ???  ca peut servir demander à Faiz*/
    /*
    val idPlayer : Int,
    val idMeet : Int,
    */
    @Json(name = "scored_goals") val scoredGoals : Int,
    @Json(name = "conceded_goals") val concededGoals : Int,
    @Json(name = "precise_date") val date : Date,
    @Json(name = "location") val location : String,
    @Json(name = "won") val victory : Boolean
)