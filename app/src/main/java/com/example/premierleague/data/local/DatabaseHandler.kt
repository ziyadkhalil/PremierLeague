package com.example.premierleague.data.local

import com.example.premierleague.data.model.Team
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Created by Ziyad on Nov, 2019
 */
interface DatabaseHandler {
    fun  saveTeams(vararg teams: Team): Completable
    fun  updateTeams(vararg teams: Team): Completable
    fun  getTeams(): Observable<List<Team>>
}