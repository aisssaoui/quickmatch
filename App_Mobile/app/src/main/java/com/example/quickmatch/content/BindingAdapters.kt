package com.example.quickmatch.content

import android.graphics.Typeface
import android.provider.Settings.Global.getString
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.quickmatch.R
import com.example.quickmatch.content.club.ClubCreationStatus
import com.example.quickmatch.network.ClubAndPlayerBelongClubObject
import com.example.quickmatch.network.ClubObject
import com.example.quickmatch.network.PlayerAndPlayerBelongClubObject
import com.example.quickmatch.network.PlayerObject
import com.example.quickmatch.utils.FormatUtils
import timber.log.Timber

/* Binding adapters for live datas */

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    if (imgUrl == null)
        imgView.setImageResource(R.drawable.ic_broken_image)

    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
                .load(imgUri)
                .apply(RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image)
                )
                .into(imgView)
    }
}

@BindingAdapter("inputBasicFormat")
fun bindFormat(imgView: ImageView, status : Boolean?) {
    when(status) {
        true -> {
            imgView.visibility = View.VISIBLE
            imgView.setImageResource(R.drawable.ic_check_circle_white_24dp)
        }
        false -> {
            imgView.visibility = View.VISIBLE
            imgView.setImageResource(R.drawable.ic_remove_circle_outline_red_24dp)
        }
        null -> imgView.visibility = View.GONE
    }
}

@BindingAdapter("clubCreationStatus")
fun bindStatusMail(imgView: ImageView, status: ClubCreationStatus?) {
    Timber.i("Get in adapter")
    Timber.i(status.toString())
    when(status) {
        ClubCreationStatus.DONE -> {
            imgView.visibility = View.VISIBLE
            imgView.setImageResource(R.drawable.ic_check_circle_white_24dp)
        }
        ClubCreationStatus.LOADING -> {
            imgView.visibility = View.VISIBLE
            imgView.setImageResource(R.drawable.status_loading_animation)
        }
        null -> {
            imgView.visibility = View.GONE
        }
        else -> {
            imgView.visibility = View.VISIBLE
            imgView.setImageResource(R.drawable.ic_remove_circle_outline_red_24dp)
        }
    }
}

/* Adapter for club fields in the search new club recycler view */

@BindingAdapter("clubCreationFormatted")
fun TextView.setClubCreationDate(club: ClubObject?) {

    club?.let {
        text = "Créé le " + FormatUtils.parseDateToJJMMAAAA(it.creationDate!!)
    }
}

@BindingAdapter("clubName")
fun TextView.setClubName(club: ClubObject?) {

    club?.let {
        text = it.name
    }
}

@BindingAdapter("clubPrivacyFormatted")
fun TextView.setClubPrivacy(club: ClubObject?) {

    club?.let {
        text = if(it.private) "Privé" else "Public"
    }
}

@BindingAdapter("clubJoinIcon")
fun ImageButton.setClubJoinIcon(club: ClubObject?) {

    club?.let {
        setImageResource(R.drawable.join_button_background)
    }
}

/* Player clubs recyclerView binding adapter */

@BindingAdapter("clubAndPlayerInscriptionFormatted")
fun TextView.setClubInscriptionDate(clubAndPlayer: ClubAndPlayerBelongClubObject?) {

    clubAndPlayer?.let {
        text = "Membre depuis le : " + FormatUtils.parseDateToJJMMAAAA(it.inscriptionDate!!)
    }
}

@BindingAdapter("clubAndPlayerName")
fun TextView.setClubAndPlayerName(clubAndPlayer: ClubAndPlayerBelongClubObject?) {

    clubAndPlayer?.let {
        text = it.name
    }
}

@BindingAdapter("clubAndPlayerPrivacyFormatted")
fun TextView.setClubPrivacy(clubAndPlayer: ClubAndPlayerBelongClubObject?) {

    clubAndPlayer?.let {
        text = if(it.private) "Privé" else "Public"
    }
}

@BindingAdapter("clubAndPlayerMembership")
fun ImageView.setClubMembershipIcon(clubAndPlayer: ClubAndPlayerBelongClubObject?) {

    clubAndPlayer?.let {
        if (it.admin) setImageResource(R.drawable.ic_star_yellow_48dp)
        else setImageResource(R.drawable.ic_card_membership_black_48dp)
    }
}

@BindingAdapter("playerNameAndPseudoFormatted")
fun TextView.setPlayerNameAndPseudo(player: PlayerAndPlayerBelongClubObject?) {

    player?.let {
        text = "${player.surname} ${player.firstName} (${player.pseudo})"
    }
}

@BindingAdapter("playerLoggedTypeface")
fun TextView.setLoggedPlayerTypeface(isLogged: Boolean?) {
    isLogged?.let {
        typeface = if (it) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
    }

}

@BindingAdapter("clubNumberOfMembers")
fun TextView.setMembersCount(count: Int?) {

    count?.let {
        text = if (it > 1) "$count membres" else "$count membre"
    }
}