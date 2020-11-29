package edu.utcs.comicwiki.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.utcs.comicwiki.R
import edu.utcs.comicwiki.glide.Glide
import edu.utcs.comicwiki.model.Concept
import edu.utcs.comicwiki.model.Location
import edu.utcs.comicwiki.model.Object
import edu.utcs.comicwiki.model.Power

class PowersAdapter(private val viewModel: HomeViewModel) :
    RecyclerView.Adapter<PowersAdapter.VH>() {

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var mainImage = itemView.findViewById<ImageView>(R.id.mainImage)
        private var deck = itemView.findViewById<TextView>(R.id.deck)

        init {
        }

        fun bind(item: Power?) {
//            deck.text = item?.description
            Glide.fetch(item?.image!!.screenURL, item?.image.screenURL, mainImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row_generic_item, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(viewModel.getPowersAt(position))
    }

    override fun getItemCount(): Int {
        return viewModel.getPowersCount()
    }

}