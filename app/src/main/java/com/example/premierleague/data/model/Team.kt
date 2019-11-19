package com.example.premierleague.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by Ziyad on Nov, 2019
 */

@Entity
data class Team (
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "website")
    val website: String,

    @ColumnInfo(name = "clubColors")
    val clubColors: String,

    @ColumnInfo(name = "venue")
    val venue: String,

    @ColumnInfo(name = "lastUpdated")
    val lastUpdated: Date,

    @ColumnInfo(name = "isUpdated")
    var isUpdated: Boolean,

    @ColumnInfo(name  = "pendingPlayers")
    val pendingPlayers: Boolean,

    @ColumnInfo(name = "players")
    val players: List<String>
    )