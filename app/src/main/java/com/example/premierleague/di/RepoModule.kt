package com.example.premierleague.di

import com.example.premierleague.data.AppRepo
import com.example.premierleague.data.Repo
import com.example.premierleague.data.datasource.TeamDataSourceFactory
import org.koin.dsl.module

/**
 * Created by Ziyad on Nov, 2019
 */

var repoModule = module {
    single {TeamDataSourceFactory(get(), get())}
    single<Repo> {AppRepo(get())}
}