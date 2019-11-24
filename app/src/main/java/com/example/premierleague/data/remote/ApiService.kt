package com.example.premierleague.data.remote

import com.example.premierleague.util.Constants
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Ziyad on Nov, 2019
 */
interface ApiService {
    @GET("competitions/${Constants.PREMIER_LEAGUE_CODE}/teams")
    fun fetchTeams(): Single<TeamsResponseWrapper>

    @GET("teams/{team_id}")
    fun fetchTeamPlayer(@Path(value = "team_id") id: Int): Single<PlayersResponseWrapper>
}