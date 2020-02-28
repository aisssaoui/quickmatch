package com.example.quickmatch.access


import android.graphics.Color
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

class SigninFragmentUI : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentSigninBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_signin, container, false)
        val viewModel = ViewModelProviders.of(this).get(SigninFragmentViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        /* Navigation to login fragment */
        binding.buttonSigninLogin.setOnClickListener {
            findNavController().navigate(SigninFragmentUIDirections.actionSigninFragmentUIToLoginFragmentUI())
        }

        /* Launch signin request */
        binding.buttonSigninSignin.setOnClickListener {
            Timber.i("Button clicked")
            viewModel.tryToSignIn(binding.inputSigninName.text.toString(),
                    binding.inputSigninFirstname.text.toString(),
                    binding.inputSigninPseudo.text.toString(),
                    binding.inputSigninMail.text.toString(),
                    binding.inputSigninPwd.text.toString(),
                    binding.inputSigninConfirmPwd.text.toString(),
                    binding.inputSigninPhone.text.toString())
        }

        /* Observer for the signin status in the viewModel */
        viewModel.signinStatus.observe(this, Observer {
            when (it) {
                SigninStatus.PASSWORD_NOT_MATCHING -> binding.textSigninStatus.text = "Incohérence de mot de passe"
                SigninStatus.NETWORK_ERROR -> binding.textSigninStatus.text = "Erreur d'inscription (Bad request)"
                SigninStatus.MISSING_FIELD_NAME -> binding.textSigninStatus.text = "Champ obligatoire manquant : Nom"
                SigninStatus.MISSING_FIELD_FIRST_NAME -> binding.textSigninStatus.text = "Champ obligatoire manquant : Prénom"
                SigninStatus.WRONG_PASSWORD_SIZE -> binding.textSigninStatus.text = "Format de mot de passe incorrect (8 caractères minimum)"
                SigninStatus.DONE -> findNavController().navigate(SigninFragmentUIDirections.actionSigninFragmentUIToHomeFragmentUI())
            }
        })

        /* Focus observers to check fields format */

        binding.inputSigninPseudo.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                viewModel.checkFormatPseudo(binding.inputSigninPseudo.text.toString())
            }
        }

        binding.inputSigninName.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                viewModel.checkFormatName(binding.inputSigninName.text.toString())
            }
        }

        binding.inputSigninFirstname.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                viewModel.checkFormatFirstName(binding.inputSigninFirstname.text.toString())
            }
        }

        binding.inputSigninMail.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                viewModel.checkFormatMail(binding.inputSigninMail.text.toString())
            }
        }

        binding.inputSigninPwd.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                viewModel.checkFormatPassword(binding.inputSigninPwd.text.toString())
            }
        }

        binding.inputSigninConfirmPwd.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                viewModel.checkFormatPasswordCheck(binding.inputSigninPwd.text.toString(), binding.inputSigninConfirmPwd.text.toString())
            }
        }

        binding.inputSigninPhone.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus && binding.inputSigninPhone.text.toString() != "") {
                viewModel.checkPhoneNumberFormat(binding.inputSigninPhone.text.toString())
            }
        }




        return binding.root
    }


}
