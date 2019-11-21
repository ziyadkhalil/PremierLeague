package com.example.premierleague.features.teamslist

import com.example.premierleague.data.model.Team

/**
 * Created by Ziyad on Nov, 2019
 */
interface TeamsView {
    fun likeTeam(team: Team)
    fun unlikeTeam(team: Team)
    fun openTeamDetails(team: Team)
}