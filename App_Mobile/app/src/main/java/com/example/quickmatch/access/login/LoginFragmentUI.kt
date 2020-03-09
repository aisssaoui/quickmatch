package com.example.quickmatch.access.login


import android.content.Intent
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
import com.example.quickmatch.content.ContentActivity
import com.example.quickmatch.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

/**
 * A simple [Fragment] subclass.
 */

const val RC_SIGN_IN = 1

class LoginFragmentUI : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        val viewModel = ViewModelProviders.of(this).get(LoginFragmentViewModel::class.java)

        binding.viewModel = viewModel

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this.context!!, gso)

        binding.buttonLoginSignin.setOnClickListener {
            findNavController().navigate(LoginFragmentUIDirections.actionLoginFragmentUIToSigninFragmentUI())
        }

        binding.buttonLoginLogin.setOnClickListener {
            viewModel.tryToLogin(binding.inputLoginMail.text.toString(), binding.inputLoginPassword.text.toString())
        }

        viewModel.loginStatus.observe(this, Observer {

            when (it) {
                LoginStatus.SUCCESS -> {
                    val contentIntent = Intent(this.activity, ContentActivity::class.java)
                    contentIntent.putExtra("player", viewModel.player)
                    startActivity(contentIntent) // Move to content activity
                    this.activity!!.finish() // Finish current activity
                }
                LoginStatus.WRONG_PWD -> binding.textLoginStatus.text = "Mot de passe invalide - Réessayez"
                LoginStatus.GOOGLE -> binding.textLoginStatus.text = "Connectez vous avec Google"
                LoginStatus.NETWORK_ERROR -> binding.textLoginStatus.text = "Erreur d'inscription (Bad request)"
                LoginStatus.UNKNOWN -> binding.textLoginStatus.text = "Utilisateur inconnu - Inscrivez vous ou réessayez"
            }
        })

        binding.buttonLoginGoogle.setOnClickListener {
            // Start google sign in activity and wait for result
            startActivityForResult(mGoogleSignInClient.signInIntent, RC_SIGN_IN)
        }

        return binding.root
    }

    /*
    private fun updateUI(account: GoogleSignInAccount?) {
        if (account != null) findNavController().navigate(LoginFragmentUIDirections.actionLoginFragmentUIToHomeFragmentUI())
    }*/

    override fun onStart() {
        super.onStart()

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        val account = GoogleSignIn.getLastSignedInAccount(this.activity)
        //updateUI(account)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Get google sign in activity result
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account!!.idToken
            //TODO Send token to the backend to check its validity
            //updateUI(account)
        } catch (e: ApiException) {
            Log.i("LoginFragmentUI", "SignIn failure: " + e.statusCode)
            //updateUI(null)
        }
    }
}
