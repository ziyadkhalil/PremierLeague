package com.example.premierleague.features.teamslistfeature

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Created by Ziyad on Nov, 2019
 */
class TeamsPagerAdapter(private val fm: FragmentManager): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    override fun getItem(position: Int): Fragment {
        return if (position == 0)
            TeamsFragment()
        else
            LikedTeamsFragment()
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position == 0)
            "Teams"
        else
            "Favourite"
    }

}