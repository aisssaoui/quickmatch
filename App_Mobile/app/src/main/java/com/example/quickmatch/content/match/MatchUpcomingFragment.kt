package com.example.quickmatch.content.match


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.quickmatch.R
import com.example.quickmatch.databinding.FragmentMatchUpcomingBinding

/**
 * A simple [Fragment] subclass.
 */
class MatchUpcomingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding : FragmentMatchUpcomingBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_match_upcoming, container, false)
        val viewModel : MatchFragmentViewModel = ViewModelProviders.of(this).get(MatchFragmentViewModel::class.java)

        val adapter = MatchUpcomingAdapter(MatchUpcomingClickListener {
            Toast.makeText(context, "Match $it clicked", Toast.LENGTH_SHORT).show()
        })

        binding.listUpcomingMatches.adapter = adapter
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.upcomingMatches.observe(this, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }


}
