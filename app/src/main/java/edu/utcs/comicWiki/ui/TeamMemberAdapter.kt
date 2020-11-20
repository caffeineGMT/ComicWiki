package edu.utcs.comicWiki.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.utcs.comicWiki.R
import edu.utcs.comicWiki.model.Character
import edu.utcs.comicWiki.glide.Glide

class TeamMemberAdapter(private val viewModel: MainViewModel) :
    RecyclerView.Adapter<TeamMemberAdapter.VH>() {

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var teamImage = itemView.findViewById<ImageView>(R.id.memberImage)
        private var deck = itemView.findViewById<TextView>(R.id.deck)

        init {
            itemView.setOnClickListener {

            }
        }

        fun bind(item: Character?) {
            if (item != null) {
                deck.text = item?.name
                Glide.fetch(item?.image!!.smallURL, item?.image.thumbURL, teamImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row_team_member, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(viewModel.getTeamMemberAt(position))
    }

    override fun getItemCount(): Int {
        return viewModel.getTeamMemberCount()
    }

}