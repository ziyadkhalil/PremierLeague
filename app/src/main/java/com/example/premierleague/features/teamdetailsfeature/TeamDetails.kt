package com.example.premierleague.features.teamdetailsfeature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.premierleague.R
import com.example.premierleague.R.layout.activity_team_details
import com.example.premierleague.data.model.Team
import com.example.premierleague.util.Constants
import com.example.premierleague.viewmodel.AppViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_team_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TeamDetails : AppCompatActivity() {

    private val appViewModel: AppViewModel by viewModel()
    lateinit var team: Team
    private val compositeDisposables =  CompositeDisposable()
    private lateinit var playersAdapter: PlayersListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_team_details)
        val id = intent.extras!!.getInt(Constants.TEAM_ID_KEY)
        team = appViewModel.getTeam(id)
        compositeDisposables.add(appViewModel.getPlayersForTeam(team.id, team.pendingPlayers)
            .retryWhen { e ->
                Log.e(":(", "RETRY")
                e.retry()
            }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    team.players = it
                    team.pendingPlayers = false
                    appViewModel.updateTeams(team)
                        .subscribeOn(Schedulers.io())
                        .subscribe ({},{
                            team.pendingPlayers = true
                        })
                    progressBar.visibility = View.GONE
                    bindViews()
                    hideGroup.visibility = View.VISIBLE
                }
                ,
                {
                    //Unreachable
                }
            )
        )
    }

    private fun bindViews() {
        teamNameTv.text = team.name
        venueTv.text  = team.venue
        websiteTv.text =  team.website
        teamColorsTv.text = team.clubColors
        setSafely(emailTv, team.email)
        setSafely(phoneNoTv, team.phone)
        setSafely(addressTv, team.address)
        setSafely(foundedTv, team.founded)
        playersAdapter = PlayersListAdapter(team.players)
        playersRv.adapter = playersAdapter
        playersRv.layoutManager = LinearLayoutManager(applicationContext)
        playersRv.addItemDecoration(DividerItemDecoration(applicationContext, OrientationHelper.VERTICAL))
        playersAdapter.notifyDataSetChanged()
    }

    private fun setSafely(tv: TextView, value: String?){
        if(value != null)
            tv.text = value
        else
            tv.visibility = View.GONE
    }
}
