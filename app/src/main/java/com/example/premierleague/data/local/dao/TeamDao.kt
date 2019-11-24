package com.example.premierleague.data.local.dao

import androidx.paging.DataSource
import androidx.room.*
import com.example.premierleague.data.model.Team
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Ziyad on Nov, 2019
 */

@Dao
interface TeamDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTeams(vararg teams: Team): Completable

    @Update
    fun updateTeams(team: Team): Completable

    @Query("SELECT * FROM Team")
    fun getTeams(): DataSource.Factory<Int, Team>

    @Query("SELECT * FROM TEAM WHERE favourite == 1")
    fun getLikedTeams(): DataSource.Factory<Int, Team>

    @Query("UPDATE Team SET favourite = 1 WHERE id == :teamId")
    fun likeTeam(teamId: Int): Completable

    @Query("UPDATE Team SET favourite = 0 WHERE id == :teamId")
    fun unlikeTeam(teamId: Int): Completable

    @Query("SELECT * FROM TEAM WHERE id == :teamId")
    fun getTeam(teamId: Int): Single<Team>
}