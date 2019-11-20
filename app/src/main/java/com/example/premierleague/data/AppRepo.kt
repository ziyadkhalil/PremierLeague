package com.example.premierleague.data

import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import androidx.paging.RxPagedListBuilder
import com.example.premierleague.data.datasource.TeamDataSourceFactory
import com.example.premierleague.data.model.Team
import io.reactivex.Observable

/**
 * Created by Ziyad on Nov, 2019
 */
class AppRepo(private val dataSourceFactory: TeamDataSourceFactory): Repo {

    override fun getPagedTeams(): Observable<PagedList<Team>> {
       return RxPagedListBuilder(dataSourceFactory,2)
           .buildObservable()
    }
}