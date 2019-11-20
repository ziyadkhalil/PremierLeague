package com.example.premierleague.features.teamslistfeature

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.premierleague.R
import com.example.premierleague.data.model.Team
import com.like.LikeButton
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
            viewHolder.teamNameTv.text = team.name
            viewHolder.websiteTv.text = team.website
            viewHolder.websiteTv.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(team.website)
                viewHolder.context.startActivity(intent)
            }
            viewHolder.venueTv.text = team.venue
            viewHolder.colorsTv.text = team.clubColors
            viewHolder.favBtn.isLiked = team.liked
            viewHolder.favBtn.setOnClickListener { btn ->
                if((btn as LikeButton).isLiked) {
                    team.liked = true
                    //viewmodel code to update db
                } else {
                    btn.isLiked = true
                }
            }
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