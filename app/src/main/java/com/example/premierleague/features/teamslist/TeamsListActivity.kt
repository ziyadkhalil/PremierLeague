package com.example.premierleague.features.teamslist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.premierleague.R
import com.example.premierleague.features.teamslist.adapters.TeamsPagerAdapter
import kotlinx.android.synthetic.main.activity_teams_list.*

class TeamsListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teams_list)
        pager.adapter = TeamsPagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(pager)
    }
}
