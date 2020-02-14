package com.example.quickmatch.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
/* HTTP requests*/
/* TODO: changer les requêtes en HTTPS (afin de fit avec la version web */
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.DELETE


import retrofit2.http.Path
import retrofit2.http.Query

/* URL of the private database where data is retrieved */
private const val BASE_URL = "https://dbcontrol.quickmatch.fr/"

/* Create Moshi object which will parse the responses */
private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

/* Retrofit builder with converter for response and bes url */
private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()

/* TODO: trouver comment faire pour POST ie. passer un club par exemple dans les params d'une requete */

/* API to communicate with the backend */
interface DatabaseApiService {
    @GET("dbcontrol")
    fun testConnection() : Deferred<TestObject>

    /* PLAYERS */
    /* GET REQUESTS */
    @GET("dbcontrol/api/v1/PlayersRows")
    fun getAllPlayers() : Deferred<List<PlayerObject>>
    @GET("dbcontrol/api/v1/Players/ma{mail_address}")
    fun getPlayerByMail(@Path("mail_address") mailAddress : String) : Deferred<PlayerObject>
    @GET("dbcontrol/api/v1/Players/id{id}")
    fun getPlayerById(@Path("id") id : Int) : Deferred<PlayerObject>
    @GET("dbcontrol/api/v1/Players/stat{id}")
    fun getPlayerMeetSheetsById(@Path("id") id : Int) : Deferred<List<MeetsSheetObject>>

    /* POST REQUESTS */
    @POST("dbcontrol/api/v1/Players")
    fun addPlayer(player : PlayerObject) : Deferred<PlayerObject>

    /* PUT REQUESTS */
    @PUT("dbcontrol/api/v1/Players/id{id}")
    /* TODO: mettre tous les paramètres d'un player pour l'update 1) soit on met tout à la main 2) metre un objet player */
    fun updatePlayerById(@Path("id") id : Int, player: PlayerObject)

    /* DELETE REQUESTS */
    @DELETE("dbcontrol/api/v1/Players/{mail_adress}")
    fun deletePlayerByMail(@Path("mail_adress") mailAdress : String)

    /* CLUBS */
    /* GET REQUESTS */
    @GET("dbcontrol/api/v1/ClubsRows")
    fun getAllClubs() : Deferred<List<ClubObject>>
    @GET("dbcontrol/api/v1/Clubs/{id}")
    fun getClubById(@Path("id") id : Int) : Deferred<ClubObject>

    /* DELETE REQUESTS */
    @DELETE("dbcontrol/api/v1/Clubs/{id}")
    fun deleteClubById(@Path("id") id : Int)

    /* POST REQUESTS */
    @POST("dbcontrol/api/v1/Clubs")
    fun addClub(clubObject: ClubObject)

    /* INVITATIONS */
    /* GET REQUESTS */
    @GET("dbcontrol/api/v1/InvitationsRows")
    fun getAllInvitations() : Deferred<List<InvitationObject>>
    @GET("dbcontrol/api/v1/Invitations/{id}")
    fun getInvitationById(@Path("id") id : Int) : Deferred<InvitationObject>

    /* POST REQUESTS */
    @POST("dbcontrol/api/v1/Invitations")
    fun addInvitation(invitationObject: InvitationObject)

    /* DELETE REQUESTS */
    @DELETE("dbcontrol/api/v1/Invitations/{id}")
    fun deleteInvitationById(@Path("id") id : Int)

    /* SLOTS */
    /* GET REQUESTS */
    @GET("dbcontrol/api/v1/SlotsRows")
    fun getAllSlots() : Deferred<List<SlotObject>>
    @GET("dbcontrol/api/v1/Slots/{id}")
    fun getSlotById(@Path("id") id : Int) : Deferred<SlotObject>

    /* POST REQUESTS */
    @POST("dbcontrol/api/v1/Slots")
    fun addSlot(slotObject : SlotObject)

    /* DELETE REQUESTS */
    @DELETE("dbcontrol/api/v1/Slots/{id}")
    fun deleteSlotById(@Path("id") id : Int)

    /* TODO: faire pour les tables : meetsheet, meet, player belong club*/

    /* MEET */
    /* GET REQUESTS */
    @GET("dbcontrol/api/v1/MeetsRows")
    fun getAllMeets() : Deferred<List<MeetObject>>
    @GET("dbcontrol/api/v1/Meets/{id}")
    fun getMeetById(@Path("id") id : Int) : Deferred<MeetObject>

    /* POST REQUESTS */
    @POST("dbcontrol/api/v1/Meets")
    fun addMeet(meetObject : MeetObject)

    /* DELETE REQUESTS */
    @DELETE("dbcontrol/api/v1/Meets/{id}")
    fun deleteMeetById(@Path("id") id : Int)

    /* MEETSHEETS */
    /* GET REQUESTS */
    /* TODO: verifier les types pour les lists de meetsheets */
    @GET("dbcontrol/api/v1/MeetsSheetRows")
    fun getAllMeetsSheets() : Deferred<List<List<MeetsSheetObject>>>
    /* TODO: verifier le back pour cette route (ne marche pas atm) */
    @GET("dbcontrol/api/v1/MeetsSheet/{mail}")
    fun getMeetsSheetByMail(@Path("id") mail : String) : Deferred<List<MeetsSheetObject>>

    /* POST REQUESTS */
    @POST("dbcontrol/api/v1/MeetsSheet")
    fun addMeetsSheet(meetsSheetObject: MeetsSheetObject)

    /* DELETE REQUESTS */
    @DELETE("dbcontrol/api/v1/MeetsSheet/{mail}")
    fun deleteMeetsSheetByMail(@Path("mail") mail : String)

    /* PLAYERBELONGCLUB */
    /* GET REQUESTS */
    @GET("dbcontrol/api/v1/PlayerClubsRows")
    fun getPlayerClubs() : Deferred<List<PlayerClubsObject>>
    /* TODO : rajouter les rows pour chacune des fonctions suivantes (demander à faiz) */
    @GET("dbcontrol/api/v1/PlayerClubs/paid{id}")
    fun getClubsByIdAdmin(@Path("id") id : Int) : Deferred<List<PlayerClubsObject>>
    @GET("dbcontrol/api/v1/PlayerClubs/pid{id}")
    fun getClubsByIdPlayer(@Path("id") id : Int) : Deferred<List<PlayerClubsObject>>


}

object DatabaseApi {

    val retrofitService: DatabaseApiService by lazy {
        retrofit.create(DatabaseApiService::class.java)
    }
}