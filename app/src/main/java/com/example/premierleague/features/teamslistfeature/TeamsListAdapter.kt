package com.example.premierleague.features.teamslistfeature

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.premierleague.R
import com.example.premierleague.data.model.Team
import kotlinx.android.synthetic.main.team_list_item.view.*

/**
 * Created by Ziyad on Nov, 2019
 */
class TeamsListAdapter: PagedListAdapter<Team, TeamsListAdapter.TeamViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.team_list_item, parent, false)
        return TeamViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val team = getItem(position)
        if (team != null) {
            holder.bindTeam(team)
        }
    }

    class TeamViewHolder(val viewHolder: View): RecyclerView.ViewHolder(viewHolder) {
        fun bindTeam(team: Team) {
            viewHolder.teamName.text = team.name
        }
    }

    companion object {
        private val DIFF_CALLBACK  =  object: DiffUtil.ItemCallback<Team>() {
            override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean =
                oldItem == newItem

        }
    }
}