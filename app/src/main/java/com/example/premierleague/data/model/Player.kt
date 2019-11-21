package com.example.premierleague.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by Ziyad on Nov, 2019
 */

@Entity
data class Player(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,

    @ColumnInfo(name = "teamId")
    var teamId: Int,

    @SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String,

    @SerializedName("position")
    @ColumnInfo(name = "position")
    val position: String?,

    @SerializedName("role")
    @ColumnInfo(name = "role")
    val role: String,

    @SerializedName("dateOfBirth")
    @ColumnInfo(name = "dob")
    val dob: String?,

    @SerializedName("nationality")
    @ColumnInfo(name = "nationality")
    val nationality: String
    )