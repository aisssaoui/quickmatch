package com.example.quickmatch.access


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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

        binding.buttonLoginSignin.setOnClickListener {
            findNavController().navigate(LoginFragmentUIDirections.actionLoginFragmentUIToSigninFragmentUI())
        }

        return binding.root
    }


}
