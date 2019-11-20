package com.example.premierleague.features.teamslistfeature

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
import com.example.premierleague.viewmodel.AppViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class LikedTeamsFragment : Fragment() {

    private val appViewModel: AppViewModel by viewModel()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_liked_teams, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = TeamsListAdapter(true, null)
        with(view.recyclerView) {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, OrientationHelper.VERTICAL))
        }
        compositeDisposable.add(appViewModel.getPagedLikedTeams()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (
                {
                    adapter.submitList(it)
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
