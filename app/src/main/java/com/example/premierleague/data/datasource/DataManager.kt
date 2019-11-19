package com.example.premierleague.data.datasource

import com.example.premierleague.data.model.Team

/**
 * Created by Ziyad on Nov, 2019
 */
interface DataManager {
    fun getTeams(from: Int, to: Int): List<Team>
}