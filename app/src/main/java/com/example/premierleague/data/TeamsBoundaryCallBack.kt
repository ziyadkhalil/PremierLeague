package com.example.premierleague.data

import androidx.paging.PagedList
import com.example.premierleague.data.local.DatabaseHandler
import com.example.premierleague.data.model.Team
import com.example.premierleague.data.remote.ApiService
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * Created by Ziyad on Nov, 2019
 */
class TeamsBoundaryCallBack(val api: ApiService, val db: DatabaseHandler) :PagedList.BoundaryCallback<Team>() {
    override fun onZeroItemsLoaded() {
        api.fetchTeams()
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onNext = {
                    it.teamsList.forEach { it.pendingPlayers = true }
                    saveItemsToDb(it.teamsList)
                },
                onError = {
                    throw it
                }
            )
    }

    private fun saveItemsToDb(teams: List<Team>) {
        db.saveTeams(*teams.toTypedArray())
            .subscribeOn(Schedulers.io())
            .subscribe()
    }
}