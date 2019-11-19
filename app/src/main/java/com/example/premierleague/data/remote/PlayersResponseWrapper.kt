package com.example.premierleague.data.remote

import com.example.premierleague.data.model.Player
import com.google.gson.annotations.SerializedName

data class PlayersResponseWrapper(
    @SerializedName("squad")
    val players: List<Player>
)
