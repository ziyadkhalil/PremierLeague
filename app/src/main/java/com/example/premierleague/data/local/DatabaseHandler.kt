package com.example.premierleague.data.local

import androidx.paging.DataSource
import com.example.premierleague.data.model.Player
import com.example.premierleague.data.model.Team
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Ziyad on Nov, 2019
 */
interface DatabaseHandler {
    fun updateTeam(team: Team): Completable
    fun saveTeams(vararg teams: Team): Completable
    fun getTeams(): DataSource.Factory<Int, Team>
    fun getLikedTeams(): DataSource.Factory<Int, Team>
    fun likeTeam(team: Team): Completable
    fun unlikeTeam(team: Team): Completable
    fun getPlayers(teamId: Int): Single<List<Player>>
    fun savePlayers(players: List<Player>): Completable
    fun getTeam(teamId: Int): Single<Team>
}