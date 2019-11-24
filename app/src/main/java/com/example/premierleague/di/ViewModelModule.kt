package com.example.premierleague.di

import com.example.premierleague.features.teamsdetails.TeamDetailsViewModel
import com.example.premierleague.features.teamslist.TeamsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Ziyad on Nov, 2019
 */
val viewModelModule =  module {
    viewModel { TeamsViewModel( get() )}
    viewModel { TeamDetailsViewModel( get() )}
}