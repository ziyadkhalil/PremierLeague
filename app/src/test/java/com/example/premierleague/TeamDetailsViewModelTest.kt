package com.example.premierleague

import androidx.lifecycle.ViewModel
import com.example.premierleague.base.Action
import com.example.premierleague.data.AppRepo
import com.example.premierleague.data.Repo
import com.example.premierleague.data.local.DatabaseHandler
import com.example.premierleague.data.model.Player
import com.example.premierleague.data.model.Team
import com.example.premierleague.data.remote.ApiService
import com.example.premierleague.data.remote.PlayersResponseWrapper
import com.example.premierleague.features.teamsdetails.TeamDetailsViewModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.internal.MockitoCore
import org.mockito.internal.configuration.injection.MockInjection
import java.util.concurrent.TimeUnit

/**
 * Created by Ziyad on Nov, 2019
 */
@RunWith(JUnit4::class)
class TeamDetailsViewModelTest {

    lateinit var viewModel: TeamDetailsViewModel

    @Mock
    lateinit var api: ApiService

    @Mock
    lateinit var db: DatabaseHandler

    private lateinit var repo: Repo

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repo = AppRepo(db,api)
    }

    @Test
    fun getTeam_whenTeamandPlayersOnDb_returnTeam(){
        //Setup
        val viewModel = TeamDetailsViewModel(repo)
        val team = mock(Team::class.java)
        team.pendingPlayers = false
        val player = mock(Player::class.java)
        val players = listOf<Player>(player)
        Mockito.`when`(db.getPlayers(0)).thenReturn(Single.just(players))
        Mockito.`when`(db.getTeam(0)).thenReturn(Single.just(team))
        //Test
        viewModel.teamObservable.subscribe()
        val test = viewModel.teamObservable.test()
        viewModel.getTeam(0)
        Completable.complete().delay(20,TimeUnit.MILLISECONDS).blockingAwait()
        //Assert
        test.assertValue(team)
    }

    @Test
    fun getTeam_whenTeamOnlyOnDb_networkAvailable_getPlayersFromApiReturnTeam(){
        //Setup
        val player = mock(Player::class.java)
        val players = listOf<Player>(player)
        val playersResponse = PlayersResponseWrapper(players)
        val team = mock(Team::class.java)
        Mockito.`when`(team.pendingPlayers).thenReturn(true)
        Mockito.`when`(api.fetchTeamPlayer(0)).thenReturn(Single.just(playersResponse))
        Mockito.`when`(db.getTeam(0)).thenReturn(Single.just(team))
        Mockito.`when`(db.savePlayers(players)).thenReturn(Completable.complete())
        Mockito.`when`(db.updateTeam(team)).thenReturn(Completable.complete())
        val viewModel = TeamDetailsViewModel(repo)
        //Test
        viewModel.teamObservable.subscribe()
        val test = viewModel.teamObservable.test()
        viewModel.getTeam(0)
        Completable.complete().delay(20,TimeUnit.MILLISECONDS).blockingAwait()
        //Assert
        test.assertValue(team)
    }


    @Test
    fun getTeam_whenTeamOnlyOnDb_networkNotAvailable_returnNetworkError(){
        //Setup
        val viewModel = TeamDetailsViewModel(repo)
        val team = mock(Team::class.java)
        Mockito.`when`(team.pendingPlayers).thenReturn(true)
        Mockito.`when`(api.fetchTeamPlayer(0)).thenReturn(Single.error(Throwable()))
        Mockito.`when`(db.getTeam(0)).thenReturn(Single.just(team))
        //Test
        viewModel.errorsObservable.subscribe()
        val test = viewModel.errorsObservable.test()
        viewModel.getTeam(0)
        Completable.complete().delay(20,TimeUnit.MILLISECONDS).blockingAwait()
        //Assert
        test.assertValue(Action.NETWORK_ERROR)
    }


}