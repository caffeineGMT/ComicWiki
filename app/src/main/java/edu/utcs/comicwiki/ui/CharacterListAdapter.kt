package edu.utcs.comicwiki.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.utcs.comicwiki.R
import edu.utcs.comicwiki.model.Character
import edu.utcs.comicwiki.glide.Glide

class CharacterListAdapter(private val viewModel: MainViewModel) :
    RecyclerView.Adapter<CharacterListAdapter.VH>() {

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var characterImage = itemView.findViewById<ImageView>(R.id.characterImage)
        private var deck = itemView.findViewById<TextView>(R.id.name)

        init {
        }

        fun bind(item: Character?) {
            deck.text = item?.deck
            Glide.fetch(item?.image!!.screenURL,item?.image.screenURL, characterImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row_character, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
       holder.bind(viewModel.getCharacterListAt(position))
    }

    override fun getItemCount(): Int {
        return viewModel.getCharacterListCount()
    }

}