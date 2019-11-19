package com.example.premierleague.di

import com.example.premierleague.data.AppRepo
import com.example.premierleague.data.Repo
import com.example.premierleague.data.datasource.AppDataManager
import com.example.premierleague.data.datasource.DataManager
import com.example.premierleague.data.datasource.TeamDataSourceFactory
import org.koin.dsl.module

/**
 * Created by Ziyad on Nov, 2019
 */

var repoModule = module {
    single<Repo> {AppRepo(get())}
    single<DataManager> { AppDataManager(get(), get()) }
    single {TeamDataSourceFactory(get())}
}