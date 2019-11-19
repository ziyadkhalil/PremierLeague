package com.example.premierleague.data

import androidx.paging.PagedList
import com.example.premierleague.data.model.Team
import io.reactivex.Observable

/**
 * Created by Ziyad on Nov, 2019
 */
interface Repo {
    fun getPagedTeams(): Observable<PagedList<Team>>
}