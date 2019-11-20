package com.example.premierleague.data

import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import androidx.paging.RxPagedListBuilder
import com.example.premierleague.data.datasource.LikedTeamsDataSourceFactory
import com.example.premierleague.data.datasource.TeamDataSourceFactory
import com.example.premierleague.data.local.DatabaseHandler
import com.example.premierleague.data.model.Team
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Created by Ziyad on Nov, 2019
 */
class AppRepo(private val dataSourceFactory: TeamDataSourceFactory,
              private val likedDataSourceFactory: LikedTeamsDataSourceFactory,
              private val databaseHandler: DatabaseHandler): Repo {

    override fun getPagedTeams(): Observable<PagedList<Team>> {
       return RxPagedListBuilder(dataSourceFactory,2)
           .buildObservable()
    }

    override fun getPagedLikedTeams(): Observable<PagedList<Team>> {
        return RxPagedListBuilder(likedDataSourceFactory, 2)
            .buildObservable()
    }

    override fun likeTeam(team: Team): Completable {
        return databaseHandler.likeTeam(team)
    }

    override fun unlikeTeam(team: Team): Completable {
        return databaseHandler.unlikeTeam(team)
    }

    override fun invalidateLikedTeams() {
        likedDataSourceFactory.invalidate()
    }
}