package com.example.premierleague.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.example.premierleague.data.model.Team
import io.reactivex.Completable

/**
 * Created by Ziyad on Nov, 2019
 */

@Dao
interface TeamDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTeams(vararg teams: Team): Completable

    @Update
    fun updateTeams(vararg teams: Team): Completable

}