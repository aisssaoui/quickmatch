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
import com.example.quickmatch.databinding.FragmentInvitationTreatedBinding

class InvitationTreatedFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding : FragmentInvitationTreatedBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_invitation_treated, container, false)
        val viewModel = ViewModelProviders.of(this).get(InvitationFragmentViewModel::class.java)

        val adapter = InvitationTreatedAdapter(InvitationTreatedClickListener {
            Toast.makeText(context, "Invitation $it clicked", Toast.LENGTH_SHORT).show()
        })

        binding.recylerView.adapter = adapter
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.treatedInvitations.observe(this, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }
}