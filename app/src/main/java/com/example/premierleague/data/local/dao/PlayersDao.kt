package com.example.premierleague.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.premierleague.data.model.Player
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface PlayersDao {
    @Query("SELECT * FROM player WHERE teamId == :id ")
    fun getPlayers(id: Int): Single<List<Player>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePlayers(players: List<Player>): Completable
}
