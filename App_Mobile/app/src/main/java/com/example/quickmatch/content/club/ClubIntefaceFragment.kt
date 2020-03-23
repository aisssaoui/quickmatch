package com.example.quickmatch.content.club

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.quickmatch.BaseFragment

import com.example.quickmatch.R
import com.example.quickmatch.access.AccessActivity
import com.example.quickmatch.databinding.FragmentClubIntefaceBinding
import timber.log.Timber

class ClubIntefaceFragment : BaseFragment() {

    private lateinit var viewModelFactory: ClubInterfaceFragmentViewModelFactory
    private lateinit var viewModel: ClubIntefaceFragmentViewModel

    private val args: ClubIntefaceFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentClubIntefaceBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_club_inteface, container, false)

        viewModelFactory = ClubInterfaceFragmentViewModelFactory(args.clubId)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ClubIntefaceFragmentViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = PlayerAdapter(PlayerClickListener {
            Toast.makeText(this.context, "Player clicked", Toast.LENGTH_SHORT).show()
        })

        binding.listPlayers.adapter = adapter

        viewModel.players.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.buttonLeaveClub.setOnClickListener {
            val leaveAlertDialog = android.app.AlertDialog.Builder(this.context)

            leaveAlertDialog.setTitle("Quitter")
            leaveAlertDialog.setMessage("Etes-vous sûr de vouloir quitter le club ?")

            /* buttons YES/NO actions */

            leaveAlertDialog.setPositiveButton("Oui") { dialog, _ ->
                viewModel.leaveClub()
                dialog.cancel()
            }

            leaveAlertDialog.setNegativeButton("Non") { dialog, _ ->
                dialog.cancel()
            }

            leaveAlertDialog.show()
        }

        viewModel.leaveClubStatus.observe(this, Observer {
            when (it) {
                RequestStatus.ERROR -> Timber.i("error leaving club")
                RequestStatus.DONE -> {
                    viewModel.onClubLeft()
                    findNavController().navigate(ClubIntefaceFragmentDirections.actionClubIntefaceFragmentToClubFragmentUI())
                }
            }
        })


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getActionBar()?.title = "Détails du club"
    }
}
