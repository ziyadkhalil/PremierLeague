package com.example.premierleague.di

import com.example.premierleague.data.datasource.AppDataManager
import com.example.premierleague.data.datasource.DataManager
import com.example.premierleague.data.datasource.TeamDataSourceFactory
import org.koin.dsl.module

/**
 * Created by Ziyad on Nov, 2019
 */

var repoModule = module {
    single<DataManager> { AppDataManager(get(), get()) }
    single {TeamDataSourceFactory(get())}
}