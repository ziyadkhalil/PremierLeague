package com.example.premierleague.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.premierleague.data.model.Player
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface PlayersDao {
    @Query("SELECT * FROM player WHERE teamId == :id ")
    fun getPlayers(id: Int): Observable<List<Player>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePlayers(players: List<Player>): Completable
}
