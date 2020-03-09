package com.example.quickmatch.access

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.quickmatch.R
import com.example.quickmatch.access.signin.SigninMailStatus
import com.example.quickmatch.access.signin.SigninPseudoStatus
import timber.log.Timber

@BindingAdapter("signinMailStatus")
fun bindStatusMail(imgView: ImageView, status: SigninMailStatus?) {
    Timber.i("Get in adapter")
    Timber.i(status.toString())
    when(status) {
        SigninMailStatus.MAIL_VALID -> {
            imgView.visibility = View.VISIBLE
            imgView.setImageResource(R.drawable.ic_check_circle_white_24dp)
        }
        SigninMailStatus.LOADING -> {
            imgView.visibility = View.VISIBLE
            imgView.setImageResource(R.drawable.status_loading_animation)
        }
        null -> {
            imgView.visibility = View.VISIBLE
            imgView.setImageResource(R.drawable.ic_sync_orange_24dp)
        }
        else -> {
            imgView.visibility = View.VISIBLE
            imgView.setImageResource(R.drawable.ic_remove_circle_outline_red_24dp)
        }
    }
}

@BindingAdapter("signinPseudoStatus")
fun bindStatusPseudo(imgView: ImageView, status: SigninPseudoStatus?) {
    Timber.i("Get in adapter")
    Timber.i(status.toString())
    when(status) {
        SigninPseudoStatus.PSEUDO_VALID -> {
            imgView.visibility = View.VISIBLE
            imgView.setImageResource(R.drawable.ic_check_circle_white_24dp)
        }
        SigninPseudoStatus.LOADING -> {
            imgView.visibility = View.VISIBLE
            imgView.setImageResource(R.drawable.status_loading_animation)
        }
        null -> imgView.visibility = View.GONE
        else -> {
            imgView.visibility = View.VISIBLE
            imgView.setImageResource(R.drawable.ic_remove_circle_outline_red_24dp)
        }
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

