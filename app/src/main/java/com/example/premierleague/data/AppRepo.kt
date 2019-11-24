package com.example.premierleague.data

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.example.premierleague.data.local.DatabaseHandler
import com.example.premierleague.data.model.Team
import com.example.premierleague.data.remote.ApiService
import io.reactivex.*
import io.reactivex.rxkotlin.zipWith
import io.reactivex.schedulers.Schedulers

/**
 * Created by Ziyad on Nov, 2019
 */
class AppRepo(
    private val db: DatabaseHandler,
    private val api: ApiService
) : Repo {

    private lateinit var zeroTeamsOnDbEmitter: ObservableEmitter<Single<List<Team>>>
    private val zeroTeamsOnDbObservable: Observable<Single<List<Team>>> = Observable.create { zeroTeamsOnDbEmitter = it }
    override fun getDatabaseEmptyObservable(): Observable<Single<List<Team>>> = zeroTeamsOnDbObservable

    override fun getPagedTeams(): Observable<PagedList<Team>> {
        return RxPagedListBuilder(db.getTeams(), 5)
            .setBoundaryCallback(object: PagedList.BoundaryCallback<Team>() { override fun onZeroItemsLoaded() = zeroTeamsOnDb() })
            .buildObservable()
    }

    override fun getPagedLikedTeams(): Observable<PagedList<Team>> {
        return RxPagedListBuilder(db.getLikedTeams(), 2)
            .buildObservable()
    }

    override fun likeTeam(team: Team): Completable = db.likeTeam(team)

    override fun unlikeTeam(team: Team): Completable = db.unlikeTeam(team)

    @Suppress("NAME_SHADOWING")
    override fun getTeam(teamId: Int): Single<Team> {
        val team = db.getTeam(teamId).subscribeOn(Schedulers.io()).blockingGet()
        if (team.pendingPlayers) {
            return Single.just(team)
                .zipWith( api.fetchTeamPlayer(team.id).map {it.players}) { team, players ->
                    team.players = players.filter {it.role == "PLAYER"}
                    team.players.forEach { it.teamId = team.id }
                    team.pendingPlayers = false

                    db.savePlayers(players)
                        .subscribeOn(Schedulers.io())
                        .doOnError { team.pendingPlayers = true }
                        .subscribe()

                    db.updateTeam(team)
                        .subscribeOn(Schedulers.io())
                        .doOnError { team.pendingPlayers = true }
                        .subscribe()

                    return@zipWith team
                }
        } else {
            return Single.just(team).zipWith(db.getPlayers(team.id)) { team, players ->
                team.players = players
                return@zipWith  team
            }
        }
    }

    private fun zeroTeamsOnDb() {
        zeroTeamsOnDbEmitter.onNext(
            api.fetchTeams()
                .map { res -> res.teamsList }
                .subscribeOn(Schedulers.io())
                .doOnSuccess {
                    it.forEach { team -> team.pendingPlayers = true }
                    db.saveTeams(*it.toTypedArray()).subscribeOn(Schedulers.io()).subscribe()
                }
        )
    }
}