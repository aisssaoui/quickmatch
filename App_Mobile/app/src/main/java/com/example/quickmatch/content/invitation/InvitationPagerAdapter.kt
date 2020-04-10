package com.example.quickmatch.content.invitation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class InvitationPagerAdapter (fm :FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when(position) {
            1 -> InvitationTreatedFragment()
            else -> InvitationNewFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {

        return when(position) {
            1 -> "Invitations traitées"
            else -> "Nouvelles invitations"
        }
    }
}