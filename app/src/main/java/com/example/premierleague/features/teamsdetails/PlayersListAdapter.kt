package com.example.premierleague.features.teamsdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.premierleague.R
import com.example.premierleague.data.model.Player
import kotlinx.android.synthetic.main.player_vh.view.*

/**
 * Created by Ziyad on Nov, 2019
 */
class PlayersListAdapter: RecyclerView.Adapter<PlayersListAdapter.ViewHolder>() {

    var players: List<Player> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
         val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.player_vh, parent, false)
         return ViewHolder(view)
    }

    override fun getItemCount(): Int = players.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.nameTv.text = players[position].name
        holder.view.positionTv.text = players[position].position
    }

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view)
}