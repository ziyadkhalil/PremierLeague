package com.example.premierleague.base


/**
 * Created by Ziyad on Nov, 2019
 */
interface Observer {
    fun observe()
    fun handleAction(action: Action)
}