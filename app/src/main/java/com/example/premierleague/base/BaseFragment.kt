package com.example.premierleague.base

import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Ziyad on Nov, 2019
 */
abstract class BaseFragment: Fragment(), Observer {
    protected val compositeDisposable = CompositeDisposable()

    override fun onDestroyView() {
        compositeDisposable.dispose()
        super.onDestroyView()
    }
}