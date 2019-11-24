package com.example.premierleague.data.remote

import com.example.premierleague.data.model.Team
import com.google.gson.annotations.SerializedName

data class TeamsResponseWrapper(
    @SerializedName("teams")
    var  teamsList: List<Team>
)
