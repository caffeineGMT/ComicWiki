package edu.utcs.comicWiki.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.utcs.comicWiki.R
import edu.utcs.comicWiki.api.Character
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
            deck.text = item?.name
//            if (item != null) {
//                deck.text = item?.name
////                Glide.fetch(item?.image!!.imageURL, item?.image.imageURL, teamImage)
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row_team_member, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
//        var list = viewModel.observeTeam().value?.characterList?.toList()
//        if (list != null) {
//            for (item in list) {
//                println(item.name)
//            }
//        }
        holder.bind(viewModel.getTeamMemberAt(position))
    }

    override fun getItemCount(): Int {
        return viewModel.getTeamMemberCount()
    }

}