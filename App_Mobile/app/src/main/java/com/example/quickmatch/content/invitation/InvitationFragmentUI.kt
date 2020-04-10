package com.example.quickmatch.content.invitation

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.example.quickmatch.BaseFragment

import com.example.quickmatch.R
import com.example.quickmatch.databinding.FragmentInvitationBinding

class InvitationFragmentUI : BaseFragment() {

    private lateinit var viewModel: InvitationFragmentViewModel
    private lateinit var viewPager: ViewPager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding : FragmentInvitationBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_invitation, container, false)
        viewModel = ViewModelProviders.of(this).get(InvitationFragmentViewModel::class.java)

        binding.viewModel = viewModel

        viewPager = binding.pager
        viewPager.adapter = InvitationPagerAdapter(this.childFragmentManager)

        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getActionBar()?.title = "Invitations"
    }
}
