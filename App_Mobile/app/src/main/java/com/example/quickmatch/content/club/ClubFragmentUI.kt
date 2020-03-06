package com.example.quickmatch.content.club


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.quickmatch.BaseFragment
import com.example.quickmatch.R
import com.example.quickmatch.databinding.FragmentClubBinding
import com.example.quickmatch.network.PlayerObject

/**
 * A simple [Fragment] subclass.
 */
class ClubFragmentUI : BaseFragment() {

    private lateinit var player: PlayerObject

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentClubBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_club, container, false)

        player = this.activity!!.intent.getParcelableExtra("player")

        binding.searchButton.setOnClickListener {
            findNavController().navigate(ClubFragmentUIDirections.actionClubFragmentUIToClubListFragmentUI())
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getActionBar()?.title = "Clubs"
    }

}
