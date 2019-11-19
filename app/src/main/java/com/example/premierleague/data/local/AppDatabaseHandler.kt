package com.example.premierleague.data.local
import com.example.premierleague.data.model.Team
import io.reactivex.Completable
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Created by Ziyad on Nov, 2019
 */
class AppDatabaseHandler: DatabaseHandler, KoinComponent {
    val appDatabase: AppDatabase by inject()

    override fun saveTeams(vararg teams: Team): Completable {
        return appDatabase.teamDao().insertTeams(*teams)
    }

    override fun updateTeams(vararg teams: Team): Completable {
        return appDatabase.teamDao().updateTeams(*teams)
    }


}