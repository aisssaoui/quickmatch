package com.example.quickmatch.title

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

import com.example.quickmatch.R
import com.example.quickmatch.databinding.FragmentTitleBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_title.*

class TitleFragmentUI : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentTitleBinding  = DataBindingUtil.inflate(inflater, R.layout.fragment_title, container, false)
        val viewModel = ViewModelProviders.of(this).get(TitleFragmentViewModel::class.java)

        binding.viewModel = viewModel

        binding.buttonTitleLogin.setOnClickListener {
            findNavController().navigate(TitleFragmentUIDirections.actionTitleFragmentToLoginFragmentUI())
        }

        binding.buttonTitleSignin.setOnClickListener {
            findNavController().navigate(TitleFragmentUIDirections.actionTitleFragmentToSigninFragmentUI())
        }

        viewModel.connectionEstablished.observe(this, Observer {
            when(it) {
                ConnectionStatus.LOADING -> Snackbar.make(
                        activity!!.findViewById(android.R.id.content),
                        "Connexion avec le serveur en cours",
                        Snackbar.LENGTH_SHORT
                ).show()

                ConnectionStatus.ERROR -> Snackbar.make(
                        activity!!.findViewById(android.R.id.content),
                        "La connexion avec le serveur a échoué",
                        Snackbar.LENGTH_SHORT
                ).show()

                ConnectionStatus.DONE -> Snackbar.make(
                        activity!!.findViewById(android.R.id.content),
                        "Connexion avec le serveur établie",
                        Snackbar.LENGTH_SHORT
                ).show()

                else -> Snackbar.make(
                        activity!!.findViewById(android.R.id.content),
                        "Echec du test",
                        Snackbar.LENGTH_SHORT
                ).show()
            }
            viewModel.doneTryingToConnect()

        })

        return binding.root
    }

}
