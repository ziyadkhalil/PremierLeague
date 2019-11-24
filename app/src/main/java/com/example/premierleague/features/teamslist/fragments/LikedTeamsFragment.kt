package com.example.premierleague.features.teamslist.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.example.premierleague.R
import com.example.premierleague.base.BaseFragment
import com.example.premierleague.data.model.Team
import com.example.premierleague.features.teamsdetails.TeamDetailsActivity
import com.example.premierleague.features.teamslist.TeamsView
import com.example.premierleague.features.teamslist.TeamsViewModel
import com.example.premierleague.features.teamslist.adapters.TeamsListAdapter
import com.example.premierleague.util.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_liked_teams.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LikedTeamsFragment : BaseFragment(), TeamsView {


    private val teamsViewModel: TeamsViewModel by sharedViewModel()
    private lateinit var teamsListAdapter: TeamsListAdapter

    override fun observe() {
            compositeDisposable.add(teamsViewModel.getPagedLikedTeams()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    teamsListAdapter.submitList(it)
                }
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_liked_teams, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initAdapter()
        observe()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initAdapter() {
        teamsListAdapter = TeamsListAdapter(true, this )
        with(recyclerView) {
            adapter = teamsListAdapter
            layoutManager =  LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, OrientationHelper.VERTICAL))
        }
    }

    override fun openTeamDetails(team: Team) {
        val intent = Intent(context, TeamDetailsActivity::class.java)
        intent.putExtra(Constants.TEAM_ID_KEY, team.id)
        startActivity(intent)    }

}
