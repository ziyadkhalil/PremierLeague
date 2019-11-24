package com.example.premierleague

import android.app.Application
import com.example.premierleague.di.repoModule
import com.example.premierleague.di.retrofitModule
import com.example.premierleague.di.roomModule
import com.example.premierleague.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by Ziyad on Nov, 2019
 */
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(retrofitModule, roomModule, repoModule, viewModelModule))
        }
    }
}