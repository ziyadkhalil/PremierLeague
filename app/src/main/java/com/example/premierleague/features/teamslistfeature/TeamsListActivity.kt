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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_teams_list.*
import org.koin.android.ext.android.get

class TeamsListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teams_list)
        pager.adapter = TeamsPagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(pager)
//        var adapter = TeamsListAdapter()
//        recyclerView.adapter = adapter
//        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
//        recyclerView.addItemDecoration(DividerItemDecoration(applicationContext, OrientationHelper.VERTICAL))
//        var repo: Repo = get()
//        repo.getPagedTeams().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    adapter.submitList(it)
//                    Log.e(":(", it.toString())
//
//                }, {
//                    Log.e(":(", it.message, it)
//                }
//            )

    }
}
