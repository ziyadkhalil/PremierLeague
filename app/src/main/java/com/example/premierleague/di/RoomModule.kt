package com.example.premierleague.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.premierleague.data.local.AppDatabase
import com.example.premierleague.data.local.AppDatabaseHandler
import com.example.premierleague.data.local.DatabaseHandler
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Created by Ziyad on Nov, 2019
 */
var  roomModule = module {
    single { Room.databaseBuilder(
        androidApplication(), AppDatabase::class.java, "football-db")
        .build()
    }

    single<DatabaseHandler> {AppDatabaseHandler()}
}