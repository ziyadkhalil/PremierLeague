package com.example.premierleague.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.premierleague.data.model.Player
import com.example.premierleague.data.model.Team
import org.koin.core.KoinComponent

/**
 * Created by Ziyad on Nov, 2019
 */
@Database(entities = [Team::class, Player::class], version = 1)
abstract class AppDatabase: RoomDatabase(), KoinComponent{
    abstract fun teamDao(): TeamDao
    abstract fun playersDao(): PlayersDao
}