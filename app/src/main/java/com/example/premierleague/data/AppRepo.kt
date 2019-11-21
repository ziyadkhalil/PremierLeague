package com.example.premierleague.data

import android.util.Log
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.example.premierleague.data.local.DatabaseHandler
import com.example.premierleague.data.model.Team
import com.example.premierleague.data.remote.ApiService
import com.example.premierleague.data.remote.PlayersResponseWrapper
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.zipWith
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by Ziyad on Nov, 2019
 */
class AppRepo(
    private val databaseHandler: DatabaseHandler,
    private val api: ApiService
) : Repo {

    override fun getPagedTeams(): Observable<PagedList<Team>> {
        return RxPagedListBuilder(databaseHandler.getTeams(), 5)
            .setBoundaryCallback(TeamsBoundaryCallBack(api, databaseHandler))
            .buildObservable()
    }

    override fun getPagedLikedTeams(): Observable<PagedList<Team>> {
        return RxPagedListBuilder(databaseHandler.getLikedTeams(), 2)
            .buildObservable()
    }

    override fun likeTeam(team: Team): Completable {
        return databaseHandler.likeTeam(team)
    }

    override fun unlikeTeam(team: Team): Completable {
        return databaseHandler.unlikeTeam(team)
    }

    override fun getTeam(teamId: Int): Single<Team> {
        val team = databaseHandler.getTeam(teamId).subscribeOn(Schedulers.io()).blockingGet()
        if (team.pendingPlayers) {
            return Single.just(team)
                .zipWith( api.fetchTeamPlayer(team.id).map {it.players}) { team, players ->
                    team.players = players.filter {it.role == "PLAYER"}
                    team.players.forEach { it.teamId = team.id }
                    team.pendingPlayers = false
                    databaseHandler.savePlayers(players)
                        .subscribeOn(Schedulers.io())
                        .subscribe()

                    databaseHandler.updateTeam(team)
                        .subscribeOn(Schedulers.io())
                        .doOnError { team.pendingPlayers = true }
                        .subscribe()
                    return@zipWith team
                }
        } else {
            return Single.just(team).zipWith(databaseHandler.getPlayers(team.id)) { team, players ->
                team.players = players
                return@zipWith  team
            }

        }
    }
}