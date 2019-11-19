package com.example.premierleague.data.datasource

import androidx.paging.ItemKeyedDataSource
import com.example.premierleague.data.local.DatabaseHandler
import com.example.premierleague.data.model.Team

/**
 * Created by Ziyad on Nov, 2019
 */
class TeamsDataSource(private val dataManager: DataManager): ItemKeyedDataSource<Int, Team>() {
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Team>) {
        dataManager.getTeams(params.requestedInitialKey!!, params.requestedLoadSize )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Team>) {
        dataManager.getTeams(params.key+1, params.key+1+params.requestedLoadSize )

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Team>) {
        dataManager.getTeams(params.key-1, params.key-1-params.requestedLoadSize )
    }

    override fun getKey(item: Team): Int {
        return item.id
    }
}