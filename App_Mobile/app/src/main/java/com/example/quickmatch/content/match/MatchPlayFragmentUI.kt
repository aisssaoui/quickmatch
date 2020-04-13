package com.example.quickmatch.content.match


import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.example.quickmatch.BaseFragment

import com.example.quickmatch.R
import com.example.quickmatch.databinding.FragmentMatchPlayBinding
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass.
 */
class MatchPlayFragmentUI : BaseFragment() {

    private lateinit var viewModelFactory: MatchPlayFragmentViewModelFactory
    private lateinit var viewModel: MatchPlayFragmentViewModel

    private val FORMAT = "%02d:%02d:%02d"
    private val args: MatchPlayFragmentUIArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding : FragmentMatchPlayBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_match_play, container, false)
        viewModelFactory = MatchPlayFragmentViewModelFactory(args.matchId)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MatchPlayFragmentViewModel::class.java)

        val timer = object : CountDownTimer(60000, 1000) {
            override fun onFinish() {

            }

            override fun onTick(millisUntilFinished: Long) {
                binding.textTime.text = String.format(FORMAT,
                    TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))
            }
        }

        binding.buttonPause.setOnClickListener {
            timer.cancel()
            it.isEnabled = false
            binding.buttonStart.isEnabled = true
        }

        binding.buttonStart.setOnClickListener {
            timer.start()
            it.isEnabled = false
            binding.buttonPause.isEnabled = true
        }
/*
        binding.buttonReset.setOnClickListener {
            timer.
        }*/

        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getActionBar()?.title = "Jouer un match"
    }
}
