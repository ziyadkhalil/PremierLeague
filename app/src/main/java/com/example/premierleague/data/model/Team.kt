package com.example.premierleague.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by Ziyad on Nov, 2019
 */

@Entity
data class Team (
    @SerializedName("id")
    @PrimaryKey
    val id: Int,

    @SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String,

    @SerializedName("website")
    @ColumnInfo(name = "website")
    val website: String,

    @SerializedName("clubColors")
    @ColumnInfo(name = "clubColors")
    val clubColors: String,

    @SerializedName("venue")
    @ColumnInfo(name = "venue")
    val venue: String,

    @SerializedName("lastUpdated")
    @ColumnInfo(name = "lastUpdated")
    val lastUpdated: Date,

    @ColumnInfo(name = "isUpdated")
    var isUpdated: Boolean,

    @ColumnInfo(name  = "pendingPlayers")
    val pendingPlayers: Boolean,

    @ColumnInfo(name = "players")
    val players: List<String>
    )