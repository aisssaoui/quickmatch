package com.example.quickmatch.access


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

import com.example.quickmatch.R
import com.example.quickmatch.databinding.FragmentSigninBinding
import com.example.quickmatch.title.TitleFragmentViewModel
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */

//TODO add * for mandatory fields in layout
class SigninFragmentUI : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentSigninBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_signin, container, false)
        val viewModel = ViewModelProviders.of(this).get(SigninFragmentViewModel::class.java)

        binding.viewModel = viewModel

        binding.buttonSigninLogin.setOnClickListener {
            findNavController().navigate(SigninFragmentUIDirections.actionSigninFragmentUIToLoginFragmentUI())
        }

        binding.buttonSigninSignin.setOnClickListener {
            viewModel.tryToSignIn(binding.inputSigninName.text.toString(),
                    binding.inputSigninFirstname.text.toString(),
                    binding.inputSigninPseudo.text.toString(),
                    binding.inputSigninMail.text.toString(),
                    binding.inputSigninPwd.text.toString(),
                    binding.inputSigninConfirmPwd.text.toString(),
                    binding.inputSigninPhone.text.toString())

            Timber.i(binding.inputSigninName.text.toString())
            Timber.i(binding.inputSigninFirstname.text.toString())
            Timber.i(binding.inputSigninPseudo.text.toString())
            Timber.i(binding.inputSigninMail.text.toString())
            Timber.i(binding.inputSigninPwd.text.toString())
            Timber.i(binding.inputSigninConfirmPwd.text.toString())
            Timber.i(binding.inputSigninPhone.text.toString() + "p")
        }

        viewModel.signinStatus.observe(this, Observer {
            when (it) {
                SigninStatus.PASSWORD_NOT_MATCHING -> binding.textSigninStatus.text = "Incohérence de mot de passe"
                SigninStatus.NETWORK_ERROR -> binding.textSigninStatus.text = "Erreur réseau..."
                SigninStatus.MISSING_FIELD -> binding.textSigninStatus.text = "Champ obligatoire manquant"
                SigninStatus.WRONG_PASSWORD_SIZE -> binding.textSigninStatus.text = "Format de mot de passe incorrect"
                SigninStatus.DONE -> findNavController().navigate(SigninFragmentUIDirections.actionSigninFragmentUIToHomeFragmentUI())
            }
        })

        return binding.root
    }


}
