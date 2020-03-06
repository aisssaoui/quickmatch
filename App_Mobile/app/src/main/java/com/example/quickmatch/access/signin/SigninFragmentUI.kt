package com.example.quickmatch.access.signin


import android.content.Intent
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
import com.example.quickmatch.content.ContentActivity
import com.example.quickmatch.databinding.FragmentSigninBinding
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
                SigninStatus.NETWORK_ERROR -> binding.textSigninStatus.text = "Erreur d'inscription (Bad request)"
                SigninStatus.DONE -> {
                    val intentContent = Intent(this.activity, ContentActivity::class.java)
                    startActivity(intentContent)
                    this.activity!!.finish()
                }
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
