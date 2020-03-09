package com.example.quickmatch.content.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.quickmatch.BaseFragment

import com.example.quickmatch.R
import com.example.quickmatch.content.ContentActivity
import com.example.quickmatch.databinding.FragmentProfileBinding
import com.example.quickmatch.network.PlayerObject

class ProfileFragmentUI : BaseFragment() {

    private lateinit var viewModel: ProfileFragmentViewModel
    lateinit var player: PlayerObject

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding : FragmentProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        /* Cast activity into a Content activity to get access to the logged player */
        val contentActivity = (this.activity as ContentActivity)
        player = contentActivity.intent.getParcelableExtra<PlayerObject>("player")

        val viewModel = binding.viewModel

        binding.bioText.text = player.bio
        binding.mailText.text = player.mailAddress
        binding.phoneText.text = player.phoneNumber ?: ""
        binding.namePseudoText.text = "${player.surname} ${player.firstName} (${player.pseudo})"

        binding.editModeButton.setOnClickListener { findNavController().navigate(ProfileFragmentUIDirections.actionProfileFragmentUIToProfileEditFragmentUI()) }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getActionBar()?.title = "Mon profil"
    }
}
