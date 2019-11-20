package com.example.premierleague.features.teamslistfeature

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.example.premierleague.R
import com.example.premierleague.data.model.Team
import com.example.premierleague.features.teamdetailsfeature.TeamDetails
import com.example.premierleague.util.Constants
import com.example.premierleague.viewmodel.AppViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.view.recyclerView
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Created by Ziyad on Nov, 2019
 */

class TeamsFragment: Fragment(), TeamsView {

    lateinit var teamsListAdapter: TeamsListAdapter
    val appViewModel: AppViewModel by sharedViewModel()
    val compositeDisposable = CompositeDisposable()

    override fun openTeamDetails(team: Team) {
        compositeDisposable.dispose()
        val intent = Intent(activity, TeamDetails::class.java)
        intent.putExtra(Constants.TEAM_ID_KEY, team.id)
        startActivity(intent)
    }

    override fun likeTeam(team: Team) {
        compositeDisposable.add(appViewModel.likeTeam(team)
            .subscribeOn(Schedulers.io())
            .subscribe({
                team.liked = true
                appViewModel.invalidateLikedTeams()
            }, {
                teamsListAdapter.notifyDataSetChanged()
            })
        )
    }

    override fun unlikeTeam(team: Team) {
        compositeDisposable.add(appViewModel.unlikeTeam(team)
            .subscribeOn(Schedulers.io())
            .subscribe({
                team.liked = false
                appViewModel.invalidateLikedTeams()
            }, {
                teamsListAdapter.notifyDataSetChanged()
            })
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_teams_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        teamsListAdapter = TeamsListAdapter(likedTeamsFragment = false, teamsView = this)
        with(view.recyclerView) {
            this.adapter = teamsListAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, OrientationHelper.VERTICAL))
        }
        compositeDisposable.add(appViewModel.getPagedTeams()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (
                {
                    teamsListAdapter.submitList(it)
                } ,
                {  e->
                    Log.e(":(", e.message, e)
                }
            )
        )
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }


}