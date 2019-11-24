package com.example.premierleague.base

import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Ziyad on Nov, 2019
 */
abstract class BaseViewModel : ViewModel() {
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected lateinit var errorsEmitter: ObservableEmitter<Action>

    val errorsObservable: Observable<Action> =
        Observable.create { errorsEmitter = it }



    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}