package com.example.premierleague.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.RxPagedListBuilder
import com.example.premierleague.data.local.DatabaseHandler
import com.example.premierleague.data.model.Team
import com.example.premierleague.data.remote.ApiService
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Ziyad on Nov, 2019
 */
class LikedTeamsDataSourceFactory(private val database: DatabaseHandler, private val api: ApiService): DataSource.Factory<Int, Team>() {
    private val sourceObservableData = MutableLiveData<LikedTeamsDataSource>()
    private lateinit var latestSource: LikedTeamsDataSource
    private var compositeDisposable = CompositeDisposable()
    override fun create(): DataSource<Int, Team> {
        compositeDisposable.dispose()
        compositeDisposable = CompositeDisposable()
        latestSource = LikedTeamsDataSource(database, api, compositeDisposable)
        sourceObservableData.postValue(latestSource)
        return latestSource
    }

    fun invalidate() {
        latestSource.invalidate()
    }

}