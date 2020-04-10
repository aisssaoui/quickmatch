package com.example.quickmatch.content.profile


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.quickmatch.BaseFragment

import com.example.quickmatch.R
import com.example.quickmatch.databinding.FragmentProfileEditBinding
import com.example.quickmatch.network.PlayerObject

/**
 * A simple [Fragment] subclass.
 */


class ProfileEditFragmentUI : BaseFragment() {

    lateinit var player: PlayerObject

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding : FragmentProfileEditBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_edit, container, false)
        val viewModel = ViewModelProviders.of(this).get(ProfileFragmentViewModel::class.java)

        binding.viewModel = viewModel
        player = this.activity!!.intent.getParcelableExtra("player")

        /* Set every edit text value to the actual value in the player profile */

        binding.editFirstName.setText(player.firstName)
        binding.editName.setText(player.surname)
        binding.editPseudo.setText(player.pseudo)
        binding.editMail.setText(player.mailAddress)
        binding.editPhone.setText(player.phoneNumber)
        binding.editBio.setText(player.bio)
        binding.editAvatar.setText(player.avatar)


        /* navigate back to profile if cancel is clicked */

        binding.buttonCancel.setOnClickListener { findNavController().navigate(ProfileEditFragmentUIDirections.actionProfileEditFragmentUIToProfileFragmentUI()) }

        /* save changes and navigate back to profile if changes were successfully applied when save is clicked */

        binding.buttonSave.setOnClickListener {
            val saved = viewModel.saveChanges(player.id!!,
                            binding.editName.text.toString(), binding.editFirstName.text.toString(),
                            binding.editPseudo.text.toString(), binding.editBio.text.toString(),
                            binding.editMail.text.toString(), binding.editPhone.text.toString(), binding.editAvatar.text.toString())

            if (saved) findNavController().navigate(ProfileEditFragmentUIDirections.actionProfileEditFragmentUIToProfileFragmentUI())
        }

        /* set format check methods to each edit */

        binding.editName.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus)
                viewModel.checkFormatName(binding.editName.text.toString())
        }

        binding.editFirstName.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus)
                viewModel.checkFormatFirstName(binding.editFirstName.text.toString())
        }

        binding.editPseudo.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus)
                viewModel.checkFormatPseudo(binding.editPseudo.text.toString())
        }

        binding.editBio.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus)
                viewModel.checkFormatBio(binding.editBio.text.toString())
        }

        binding.editMail.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus)
                viewModel.checkFormatMail(binding.editMail.text.toString())
        }

        binding.editPhone.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus)
                viewModel.checkFormatPhoneNumber(binding.editPhone.text.toString())
        }

        binding.editAvatar.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus)
                viewModel.checkFormatAvatar(binding.editAvatar.text.toString())
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getActionBar()?.title = "Modifier mon profil"
    }
}
