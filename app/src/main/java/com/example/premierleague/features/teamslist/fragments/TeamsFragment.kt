package com.example.premierleague.features.teamslist.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.example.premierleague.R
import com.example.premierleague.base.BaseFragment
import com.example.premierleague.data.model.Team
import com.example.premierleague.features.teamsdetails.TeamDetailsActivity
import com.example.premierleague.features.teamslist.TeamsViewModel
import com.example.premierleague.features.teamslist.adapters.TeamsListAdapter
import com.example.premierleague.features.teamslist.TeamsView
import com.example.premierleague.util.Constants
import com.example.premierleague.base.Action
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_teams_list.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Created by Ziyad on Nov, 2019
 */

class TeamsFragment: BaseFragment(), TeamsView {

    private val teamsViewModel: TeamsViewModel by sharedViewModel()
    private lateinit var teamsListAdapter: TeamsListAdapter

    override fun observe() {
        compositeDisposable.addAll(
            teamsViewModel.likeFailureObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { teamsListAdapter.notifyDataSetChanged() }
            ,
            teamsViewModel.getPagedTeams()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { teamsListAdapter.submitList(it) }
            ,
            teamsViewModel.errorsObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::handleAction)
        )
    }

    override fun handleAction(action: Action) {
        when(action){
            Action.NETWORK_ERROR -> {
                Snackbar.make(view!!, "Check your connection", BaseTransientBottomBar.LENGTH_LONG )
                    .setAction("Retry") {
                        teamsListAdapter.currentList!!.dataSource.invalidate()
                    }
                    .show()
            }
            else -> {}
        }
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_teams_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        observe()
    }

    private fun initAdapter() {
        teamsListAdapter = TeamsListAdapter(false, this )
        with(recyclerView) {
            adapter = teamsListAdapter
            layoutManager =  LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, OrientationHelper.VERTICAL))
        }
    }

    override fun likeTeam(team: Team) {
        teamsViewModel.likeTeam(team)
    }

    override fun unlikeTeam(team: Team) {
        teamsViewModel.unlikeTeam(team)
    }

    override fun openTeamDetails(team: Team) {
        val intent = Intent(context, TeamDetailsActivity::class.java)
        intent.putExtra(Constants.TEAM_ID_KEY, team.id)
        startActivity(intent)
    }



}