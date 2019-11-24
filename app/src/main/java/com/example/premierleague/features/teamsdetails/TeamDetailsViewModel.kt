package com.example.premierleague.features.teamsdetails

import com.example.premierleague.base.Action
import com.example.premierleague.base.BaseViewModel
import com.example.premierleague.data.Repo
import com.example.premierleague.data.model.Team
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * Created by Ziyad on Nov, 2019
 */
class TeamDetailsViewModel(private val repo: Repo): BaseViewModel() {

    private var localTeam: Team? = null
    private lateinit var teamEmitter: ObservableEmitter<Team>
    val teamObservable: Observable<Team> =
        Observable.create { teamEmitter = it }

    fun getTeam(teamId: Int) {
        val localLocalTeam = localTeam
        if (localLocalTeam != null && localLocalTeam.id == teamId)
            teamEmitter.onNext(localLocalTeam)
        else compositeDisposable.add(repo.getTeam(teamId)
            .subscribeOn(Schedulers.computation())
            .subscribeBy(
                onSuccess = { team -> teamEmitter.onNext(team); localTeam = team },
                onError = { errorsEmitter.onNext(Action.NETWORK_ERROR) }
            )
        )
    }

}