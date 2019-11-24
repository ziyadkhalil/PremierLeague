package com.example.premierleague.features.teamsdetails

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.example.premierleague.R.layout.activity_team_details
import com.example.premierleague.base.BaseActivity
import com.example.premierleague.data.model.Team
import com.example.premierleague.util.Constants
import com.example.premierleague.base.Action
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_team_details.*
import kotlinx.android.synthetic.main.fragment_teams_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class TeamDetailsActivity : BaseActivity() {

    private var teamId: Int? = null
    private val viewModel: TeamDetailsViewModel by viewModel()
    lateinit var team: Team
    private lateinit var playersAdapter: PlayersListAdapter

    override fun observe() {
        compositeDisposable.addAll(
            viewModel.teamObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.e(":'","Recieved")
                    this.team = it; bindViews()
                },

            viewModel.errorsObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::handleAction)
            )
    }

    override fun handleAction(action: Action) {
        when(action) {
            Action.NETWORK_ERROR -> {
                progressBar.visibility = View.GONE
                Snackbar.make(constraintContainer, "Check your connection", BaseTransientBottomBar.LENGTH_LONG )
                    .setAction("Retry") {
                        viewModel.getTeam(teamId!!)
                    }
                    .show()
            }
            else -> {}
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        observe()
        super.onCreate(savedInstanceState)
        setContentView(activity_team_details)
        initAdapter()
        teamId = intent.extras!!.getInt(Constants.TEAM_ID_KEY)
        viewModel.getTeam(teamId!!)
    }

    private fun initAdapter() {
        playersAdapter = PlayersListAdapter()
        playersRv.adapter = playersAdapter
        playersRv.layoutManager = LinearLayoutManager(applicationContext)
        playersRv.addItemDecoration(DividerItemDecoration(applicationContext, OrientationHelper.VERTICAL))
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
        playersAdapter.players = team.players
        hideGroup.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    /**Data being bound using this method can be null from the API,
    thus hiding  them in case of null**/
    private fun setSafely(tv: TextView, value: String?){
        if(value != null)
            tv.text = value
        else
            tv.visibility = View.GONE
    }
}
