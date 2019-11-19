package com.example.premierleague.data.datasource

import com.example.premierleague.data.local.DatabaseHandler
import com.example.premierleague.data.model.Team
import com.example.premierleague.data.remote.ApiService
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * Created by Ziyad on Nov, 2019
 */
class AppDataManager(private val database: DatabaseHandler, private val api: ApiService): DataManager {
    override fun getTeams(from: Int, to: Int): List<Team> {
        if (teams.isEmpty()) {
            fetchTeamsFromDb()
        }
        return teams.subList(from, to)
    }

    private var teamsObservable: Observable<List<Team>> = this.database.getTeams()
    private var teams: List<Team> = listOf()
    private var disposables = mutableListOf<Disposable>()

    init {
        fetchTeamsFromDb()
    }

    private fun fetchTeamsFromApi() {
         disposables.add(api.fetchTeams()
            .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.computation())
            .map { it.teamsList }
            .subscribeBy {
                database.saveTeams(*it.toTypedArray()).subscribe()
                this.teams = it
            }
         )
    }

    private fun fetchTeamsFromDb() {
        disposables.add(teamsObservable.subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
                if (it.isEmpty()) {
                    fetchTeamsFromApi()
                }
            }
        )
    }

    private fun dispose(){
        disposables.forEach {
            it.dispose()
        }
    }

}