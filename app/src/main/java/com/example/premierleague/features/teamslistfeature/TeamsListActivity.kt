package com.example.premierleague.features.teamslistfeature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.premierleague.R
import com.example.premierleague.data.Repo
import com.example.premierleague.viewmodel.AppViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_teams_list.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class TeamsListActivity : AppCompatActivity() {

    val appViewModel: AppViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teams_list)
        pager.adapter = TeamsPagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(pager)
//        var teamsListAdapter = TeamsListAdapter()
//        recyclerView.teamsListAdapter = teamsListAdapter
//        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
//        recyclerView.addItemDecoration(DividerItemDecoration(applicationContext, OrientationHelper.VERTICAL))
//        var repo: Repo = get()
//        repo.getPagedTeams().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    teamsListAdapter.submitList(it)
//                    Log.e(":(", it.toString())
//
//                }, {
//                    Log.e(":(", it.message, it)
//                }
//            )

    }
}
