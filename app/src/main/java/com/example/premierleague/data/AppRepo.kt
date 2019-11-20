package com.example.premierleague.data

import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import androidx.paging.RxPagedListBuilder
import com.example.premierleague.data.datasource.LikedTeamsDataSourceFactory
import com.example.premierleague.data.datasource.TeamDataSourceFactory
import com.example.premierleague.data.local.DatabaseHandler
import com.example.premierleague.data.model.Player
import com.example.premierleague.data.model.Team
import com.example.premierleague.data.remote.ApiService
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Ziyad on Nov, 2019
 */
class AppRepo(private val dataSourceFactory: TeamDataSourceFactory,
              private val likedDataSourceFactory: LikedTeamsDataSourceFactory,
              private val databaseHandler: DatabaseHandler,
              private val api: ApiService): Repo {

    override fun getPagedTeams(): Observable<PagedList<Team>> {
       return RxPagedListBuilder(dataSourceFactory,2)
           .buildObservable()
    }

    override fun getPagedLikedTeams(): Observable<PagedList<Team>> {
        return RxPagedListBuilder(likedDataSourceFactory, 2)
            .buildObservable()
    }

    override fun likeTeam(team: Team): Completable {
        return databaseHandler.likeTeam(team)
    }

    override fun unlikeTeam(team: Team): Completable {
        return databaseHandler.unlikeTeam(team)
    }

    override fun invalidateLikedTeams() {
        likedDataSourceFactory.invalidate()
    }

    override fun getPlayersForTeam(teamId: Int, pendingPlayers: Boolean): Observable<List<Player>> {
        return if (pendingPlayers) api.fetchTeamPlayer(teamId).map { it.players } else databaseHandler.getPlayers(teamId)
    }

    override fun getTeam(teamId: Int): Single<Team> {
        return databaseHandler.getTeam(teamId)
    }

    override fun updateTeams(vararg team: Team): Completable {
        return databaseHandler.updateTeams(*team)
    }
}