package com.example.premierleague.data.datasource

import android.util.Log
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PositionalDataSource
import com.example.premierleague.data.local.DatabaseHandler
import com.example.premierleague.data.model.Team
import com.example.premierleague.data.remote.ApiService
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Ziyad on Nov, 2019
 */
class TeamsDataSource(private val database: DatabaseHandler, private val api: ApiService, private val disposables: CompositeDisposable): PositionalDataSource<Team>() {
    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Team>) {
        disposables.add(database.getTeams()
            .subscribe { dpTeams ->
                if (dpTeams.isEmpty()) {
                    api.fetchTeams()
                        .map { it.teamsList }
                        .subscribe( { apiTeams ->
                            apiTeams.forEach { team ->
                                team.pendingPlayers = true
                            }
                            database.saveTeams(*apiTeams.toTypedArray()).subscribe()
                            callback.onResult(apiTeams.subList(params.startPosition, params.startPosition + params.loadSize))
                            disposables.clear()
                        } , { e ->
                            Log.e(":(", e.message, e)
                        })
                } else {
                    callback.onResult(dpTeams.subList(params.startPosition, params.startPosition + params.loadSize))
                    disposables.clear()
                }
            }
        )
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Team>) {
        Log.e(":(","ANGRY")
        disposables.add(database.getTeams()
            .subscribeOn(Schedulers.io()).subscribe( { dpTeams ->
            if (dpTeams.isEmpty()) {
                api.fetchTeams()
                    .map { it.teamsList }
                    .subscribe( { apiTeams ->
                        apiTeams.forEach { team ->
                            team.pendingPlayers = true
                        }
                        val totalCount = apiTeams.size
                        val position = computeInitialLoadPosition(params, totalCount)
                        val loadSize = computeInitialLoadSize(params, position, totalCount)
                        database.saveTeams(*apiTeams.toTypedArray()).subscribe()
                        callback.onResult(apiTeams.subList(position, position + loadSize), position, totalCount)
                        disposables.clear()
                    } , { e ->
                        Log.e(":(", e.message, e)
                    })
            } else {
                val totalCount = dpTeams.size
                val position = computeInitialLoadPosition(params, totalCount)
                val loadSize = computeInitialLoadSize(params, position, totalCount)
                callback.onResult(dpTeams.subList(position, position + loadSize), position, totalCount)
                disposables.clear()
            }
        }, { e ->
                Log.e(":(", e.message, e)
        })
        )
    }

}