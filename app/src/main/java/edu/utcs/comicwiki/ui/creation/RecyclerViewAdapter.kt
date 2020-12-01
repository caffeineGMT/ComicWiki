package edu.utcs.comicwiki.ui.creation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.utcs.comicwiki.R
import edu.utcs.comicwiki.glide.Glide
import edu.utcs.comicwiki.model.ComicNode

import kotlinx.android.synthetic.main.list_row.view.*

class RecyclerViewAdapter(private val viewModel: CreationViewModel) :
    RecyclerView.Adapter<RecyclerViewAdapter.VH>() {

    var onItemClickListener: (Model.() -> Unit)? = null

//    companion object {
//        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Model>() {
//            override fun areItemsTheSame(oldItem: Model, newItem: Model) = oldItem.id == newItem.id
//            override fun areContentsTheSame(oldItem: Model, newItem: Model) = oldItem == newItem
//        }
//    }

    @SuppressLint("ClickableViewAccessibility")
    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.name
        private val deck: TextView = itemView.timings
        private val image: ImageView = itemView.image

        fun bind(item: ComicNode?) {
            name.text = item?.name
            deck.text = item?.deck
            if (item != null) {
                Glide.fetch(item.smallImageURL, item.smallImageURL, image)
            }
//            itemView.setOnClickListener { onItemClickListener?.invoke(model) }
        }

//        init {
//            itemView.setOnLongClickListener {
//                viewModel.deleteRelatedNodesAt(adapterPosition)
//                true
//            }
//        }
//
//        fun bind(item: ComicNode?) {
//            if (item != null) {
//                Glide.fetch(item.smallImageURL!!, item.smallImageURL!!, nodeImage)
//            }
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.VH {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_row, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.VH, position: Int) {
        holder.bind(viewModel.getRelatedNodesAt(position))
    }

    override fun getItemCount(): Int {
        return viewModel.getRelatedNodesCount()
    }
}