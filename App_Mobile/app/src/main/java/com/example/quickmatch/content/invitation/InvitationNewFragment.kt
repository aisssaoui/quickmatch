package com.example.quickmatch.content.invitation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.quickmatch.R
import com.example.quickmatch.databinding.FragmentInvitationNewBinding

class InvitationNewFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding : FragmentInvitationNewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_invitation_new, container, false)
        val viewModel = ViewModelProviders.of(this).get(InvitationFragmentViewModel::class.java)

        val adapter = InvitationNewAdapter(InvitationNewClickListener {
            Toast.makeText(context, "Invitation $it clicked", Toast.LENGTH_SHORT).show()
        })

        binding.recyclerView.adapter = adapter
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.newInvitations.observe(this, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }
}