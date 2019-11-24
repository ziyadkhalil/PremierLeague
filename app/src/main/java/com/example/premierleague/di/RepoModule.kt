package com.example.premierleague.di

import com.example.premierleague.data.AppRepo
import com.example.premierleague.data.Repo
import org.koin.dsl.module

/**
 * Created by Ziyad on Nov, 2019
 */

val repoModule = module {
    single<Repo> {AppRepo(get(),get())}
}