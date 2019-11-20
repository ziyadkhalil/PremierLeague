package com.example.premierleague.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.premierleague.data.Repo
import com.example.premierleague.data.model.Player
import com.example.premierleague.data.model.Team
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

/**
 * Created by Ziyad on Nov, 2019
 */
class AppViewModel(private val repo: Repo): ViewModel(){

    fun getPagedLikedTeams(): Observable<PagedList<Team>> = repo.getPagedLikedTeams()
    fun getPagedTeams(): Observable<PagedList<Team>> = repo.getPagedTeams()
    fun likeTeam(team: Team): Completable = repo.likeTeam(team)
    fun unlikeTeam(team: Team): Completable = repo.unlikeTeam(team)
    fun invalidateLikedTeams() = repo.invalidateLikedTeams()
    fun getPlayersForTeam(teamId: Int, pendingPlayers: Boolean): Observable<List<Player>> = repo.getPlayersForTeam(teamId, pendingPlayers)
    fun getTeam(teamId: Int): Team = repo.getTeam(teamId).subscribeOn(Schedulers.io()).blockingGet()
    fun updateTeams(vararg team: Team): Completable = repo.updateTeams(*team)

    override fun onCleared() {
        super.onCleared()
    }

}