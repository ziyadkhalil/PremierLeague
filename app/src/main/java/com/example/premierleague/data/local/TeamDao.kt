package com.example.premierleague.data.local

import androidx.room.*
import com.example.premierleague.data.model.Team
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Created by Ziyad on Nov, 2019
 */

@Dao
interface TeamDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTeams(vararg teams: Team): Completable

    @Update
    fun updateTeams(vararg teams: Team): Completable

    @Query("SELECT * FROM Team")
    fun getTeams(): Observable<List<Team>>

    @Query("SELECT * FROM TEAM WHERE favourite == 1")
    fun getLikedTeams(): Observable<List<Team>>

    @Query("UPDATE Team SET favourite = 1 WHERE id == :teamId")
    fun likeTeam(teamId: Int): Completable

    @Query("UPDATE Team SET favourite = 0 WHERE id == :teamId")
    fun unlikeTeam(teamId: Int): Completable
}