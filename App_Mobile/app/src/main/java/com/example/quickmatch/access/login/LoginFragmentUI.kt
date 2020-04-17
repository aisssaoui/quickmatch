package com.example.quickmatch.access.login


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

import com.example.quickmatch.R
import com.example.quickmatch.content.ContentActivity
import com.example.quickmatch.content.club.RequestStatus
import com.example.quickmatch.databinding.FragmentLoginBinding
import com.example.quickmatch.splash.SplashAccessToContent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */

const val RC_SIGN_IN = 1

class LoginFragmentUI : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        val viewModel = ViewModelProvider(this).get(LoginFragmentViewModel::class.java)

        binding.viewModel = viewModel

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this.context!!, gso)

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        val account = GoogleSignIn.getLastSignedInAccount(this.activity)
        if(account != null) {
            viewModel.getPlayerByMail(account.email!!)
        }

        val alertGoogleAccountFound = AlertDialog.Builder(this.context)
        alertGoogleAccountFound.setTitle("Compte Google")
        alertGoogleAccountFound.setMessage("Un compte Google a été trouvé : ${account?.displayName} (${account?.email})")
        alertGoogleAccountFound.setNegativeButton("Se connecter avec un autre compte") { dialog, _ ->
            dialog.cancel()
        }
        alertGoogleAccountFound.setPositiveButton("Se connecter avec ce compte") { _, _ ->
            val contentIntent = Intent(this.activity, SplashAccessToContent::class.java)
            contentIntent.putExtra("player", viewModel.playerGoogle.value)

            /* disable keyboard when navigating to the app */
            val inputMethodManager = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(activity!!.currentFocus!!.applicationWindowToken, 0)

            this.activity!!.finish() // Finish current activity
            startActivity(contentIntent) // Move to content activity (splash between)
        }

        viewModel.getGooglePlayer.observe(viewLifecycleOwner, Observer {
            when(it) {
                RequestStatus.DONE -> alertGoogleAccountFound.show()
                RequestStatus.ERROR -> Timber.i("Couldn't retrieve account from google mail address")
                else -> Timber.i("Retrieving Google account from database...")
            }
        })

        binding.buttonLoginSignin.setOnClickListener {
            findNavController().navigate(LoginFragmentUIDirections.actionLoginFragmentUIToSigninFragmentUI())
        }

        binding.buttonLoginLogin.setOnClickListener {
            viewModel.tryToLogin(binding.inputLoginMail.text.toString(), binding.inputLoginPassword.text.toString())
        }

        viewModel.loginStatus.observe(viewLifecycleOwner, Observer {

            when (it) {
                LoginStatus.SUCCESS -> {
                    val contentIntent = Intent(this.activity, SplashAccessToContent::class.java)
                    contentIntent.putExtra("player", viewModel.player)

                    /* disable keyboard when navigating to the app */
                    val inputMethodManager = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(activity!!.currentFocus!!.applicationWindowToken, 0)

                    this.activity!!.finish() // Finish current activity
                    startActivity(contentIntent) // Move to content activity (splash between)

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Get google sign in activity result
        Timber.i("request code: $requestCode")
        if (requestCode == RC_SIGN_IN) {
            Timber.i("Made it to task creation")
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)
            Timber.i("account: $account")
            //TODO create account or get existing account in DB with account.mail
            //TODO go to content activity with the playerObject in the intent

        } catch (e: ApiException) {
            Timber.i(e.message + " / handleSigninResult()")
        }
    }
}
