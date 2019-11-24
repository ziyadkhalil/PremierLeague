package com.example.premierleague.base

import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Ziyad on Nov, 2019
 */

abstract class BaseActivity : AppCompatActivity(), Observer {
    protected val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}