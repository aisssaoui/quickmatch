package com.example.quickmatch.content.invitation

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.example.quickmatch.R
import com.example.quickmatch.databinding.FragmentInvitationBinding

class InvitationFragmentUI : Fragment() {

    private lateinit var viewModel: InvitationFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding : FragmentInvitationBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_match_creation, container, false)
        viewModel = ViewModelProviders.of(this).get(InvitationFragmentViewModel::class.java)

        binding.viewModel = viewModel

        return binding.root

    }
}
