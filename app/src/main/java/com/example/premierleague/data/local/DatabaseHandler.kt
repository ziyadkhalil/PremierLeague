package com.example.premierleague.data.local

import com.example.premierleague.data.model.Player
import com.example.premierleague.data.model.Team
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Ziyad on Nov, 2019
 */
interface DatabaseHandler {
    fun  saveTeams(vararg teams: Team): Completable
    fun  updateTeams(vararg teams: Team): Completable
    fun  getTeams(): Observable<List<Team>>
    fun getLikedTeams(): Observable<List<Team>>
    fun likeTeam(team: Team): Completable
    fun unlikeTeam(team: Team): Completable
    fun getPlayers(teamId: Int): Observable<List<Player>>
    fun savePlayers(players: List<Player>): Completable
    fun getTeam(teamId: Int): Single<Team>
}