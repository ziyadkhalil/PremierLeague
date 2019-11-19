package com.example.premierleague.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.RxPagedListBuilder
import com.example.premierleague.data.model.Team
import io.reactivex.Observable

/**
 * Created by Ziyad on Nov, 2019
 */
class TeamDataSourceFactory(private val dataManager: DataManager): DataSource.Factory<Int, Team>() {
    private val sourceObservableData = MutableLiveData<TeamsDataSource>()
    private lateinit var latestSource: TeamsDataSource
    override fun create(): DataSource<Int, Team> {
        latestSource = TeamsDataSource(dataManager)
        sourceObservableData.postValue(latestSource)
        return latestSource
    }
}