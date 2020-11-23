package edu.utcs.comicwiki.ui.creation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.utcs.comicwiki.R
import edu.utcs.comicwiki.model.Character
import edu.utcs.comicwiki.glide.Glide
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.imageURLKey
import edu.utcs.comicwiki.ui.search.SearchViewModel

class ComicNodeSearchAdapter(
    private val comicNodeSearchViewModel: SearchViewModel,
    activity: Activity
) :
    RecyclerView.Adapter<ComicNodeSearchAdapter.VH>() {

    private val activity = activity

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var searchImage = itemView.findViewById<ImageView>(R.id.searchImage)
        private var deck = itemView.findViewById<TextView>(R.id.deck)

        init {
            itemView.setOnClickListener {
                val returnIntent = Intent().apply {
                    val result = comicNodeSearchViewModel.getSearchResultsAt(adapterPosition)
//                    result.name
//                    result.deck
                    val thumbURL = result?.image?.mediumURL
//                    result.apiDetailURL
                    this.putExtra(imageURLKey, thumbURL)
                }
                activity.setResult(0, returnIntent)
                activity.finish()
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
        holder.bind(comicNodeSearchViewModel.getSearchResultsAt(position))
    }

    override fun getItemCount(): Int {
        return comicNodeSearchViewModel.getSearchResultsCount()
    }
}