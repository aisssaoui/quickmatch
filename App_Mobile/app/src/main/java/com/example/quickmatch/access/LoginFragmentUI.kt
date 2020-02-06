package com.example.quickmatch.access


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
import com.example.quickmatch.databinding.FragmentLoginBinding

/**
 * A simple [Fragment] subclass.
 */
class LoginFragmentUI : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        val viewModel = ViewModelProviders.of(this).get(LoginFragmentViewModel::class.java)

        binding.viewModel = viewModel

        binding.buttonLoginSignin.setOnClickListener {
            findNavController().navigate(LoginFragmentUIDirections.actionLoginFragmentUIToSigninFragmentUI())
        }

        binding.buttonLoginLogin.setOnClickListener {
            viewModel.tryToLogin(binding.inputLoginMail.text.toString(), binding.inputLoginPassword.text.toString())
        }

        viewModel.loginStatus.observe(this, Observer {

            when (it) {
                LoginStatus.SUCCESS -> findNavController().navigate(LoginFragmentUIDirections.actionLoginFragmentUIToHomeFragmentUI())
                LoginStatus.WRONG_PWD -> binding.textLoginStatus.text  = "Mot de passe invalide - Réessayez"
                LoginStatus.GOOGLE -> binding.textLoginStatus.text  = "Connectez vous avec Google"
                LoginStatus.NETWORK_ERROR -> binding.textLoginStatus.text = "Erreur réseau..."
                LoginStatus.UNKNOWN -> binding.textLoginStatus.text  = "Utilisateur inconnu - Inscrivez vous ou réessayez"
            }
        })

        return binding.root
    }


}
