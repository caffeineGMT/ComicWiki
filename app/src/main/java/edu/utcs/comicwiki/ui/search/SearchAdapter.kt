package edu.utcs.comicwiki.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.utcs.comicwiki.R
import edu.utcs.comicwiki.model.Character
import edu.utcs.comicwiki.glide.Glide

class SearchAdapter(private val searchViewModel: SearchViewModel) :
    RecyclerView.Adapter<SearchAdapter.VH>() {

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var searchImage = itemView.findViewById<ImageView>(R.id.searchImage)
        private var deck = itemView.findViewById<TextView>(R.id.deck)

        init {
            itemView.setOnClickListener {

            }
        }

        fun bind(item: Character?) {
            deck.text = item?.deck
            Glide.fetch(item?.image!!.iconURL, item.image.smallURL, searchImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row_search, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(searchViewModel.getSearchResultsAt(position))
    }

    override fun getItemCount(): Int {
        return searchViewModel.getSearchResultsCount()
    }
}