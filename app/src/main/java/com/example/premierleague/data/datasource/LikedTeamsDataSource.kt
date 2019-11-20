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
class LikedTeamsDataSource(private val database: DatabaseHandler, private val api: ApiService, private val disposables: CompositeDisposable): PositionalDataSource<Team>() {
    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Team>) {
        disposables.add(database.getLikedTeams()
            .subscribe { dpTeams ->
                    if(dpTeams.isNotEmpty())
                        callback.onResult(dpTeams.subList(params.startPosition, params.startPosition + params.loadSize))
            }
        )
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Team>) {
        disposables.add(database.getLikedTeams()
            .subscribe { dpTeams ->
                if(dpTeams.isNotEmpty()) {
                    val totalCount = dpTeams.size
                    val position = computeInitialLoadPosition(params, totalCount)
                    val loadSize = computeInitialLoadSize(params, position, totalCount)
                    callback.onResult(
                        dpTeams.subList(position, position + loadSize),
                        position,
                        totalCount
                    )
                }
                disposables.clear()
            }
        )


    }

}