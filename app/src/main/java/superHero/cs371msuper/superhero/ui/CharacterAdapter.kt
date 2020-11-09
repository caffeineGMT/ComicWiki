package superHero.cs371msuper.superhero.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import superHero.cs371msuper.superhero.R
import superHero.cs371msuper.superhero.api.Character
import superHero.cs371msuper.superhero.glide.Glide

class CharacterAdapter(private val viewModel: PanelViewModel) :
    RecyclerView.Adapter<CharacterAdapter.VH>() {

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var characterImage = itemView.findViewById<ImageView>(R.id.characterImage)
        private var deck = itemView.findViewById<TextView>(R.id.deck)

        init {
        }

        fun bind(item: Character?) {
            deck.text = item?.deck
            Glide.fetch(item?.image!!.imageURL,item?.image.imageURL, characterImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row_character, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
       holder.bind(viewModel.getCharacterAt(position))
    }

    override fun getItemCount(): Int {
        return viewModel.getCharactersCount()
    }

}