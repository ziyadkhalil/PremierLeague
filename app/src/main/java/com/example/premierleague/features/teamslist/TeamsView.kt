package com.example.premierleague.features.teamslist

import com.example.premierleague.data.model.Team

/**
 * Created by Ziyad on Nov, 2019
 */

//Interface to communicate between list adapters and their correspending fragments
interface TeamsView {
    fun likeTeam(team: Team)
    fun unlikeTeam(team: Team)
    fun openTeamDetails(team: Team)
}