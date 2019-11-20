package com.example.premierleague.di

import com.example.premierleague.viewmodel.AppViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Ziyad on Nov, 2019
 */
val viewModelModule =  module {
    viewModel {AppViewModel(get())}
}