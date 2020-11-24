package edu.utcs.comicwiki.ui.connection

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.utcs.comicwiki.R
import edu.utcs.comicwiki.model.Character
import edu.utcs.comicwiki.glide.Glide
import edu.utcs.comicwiki.model.ComicNode
import edu.utcs.comicwiki.ui.creation.CreationViewModel

class ConnectionAdapter(
    private val viewModel: CreationViewModel
) :
    RecyclerView.Adapter<ConnectionAdapter.VH>() {

    @SuppressLint("ClickableViewAccessibility")
    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var nodeImage = itemView.findViewById<ImageView>(R.id.nodeImage)
        private var dX = 0f
        private var dY = 0f

        init {
            itemView.setOnClickListener {
                nodeImage.tooltipText = "test"
            }

            nodeImage.setOnTouchListener { view, motionEvent ->
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {
                        dX = view.x - motionEvent.rawX
                        dY = view.y - motionEvent.rawY
                    }

                    MotionEvent.ACTION_MOVE -> {
                        val newX = motionEvent.rawX + dX
                        val newY = motionEvent.rawY + dY
                        view.animate().x(newX).y(newY).setDuration(0).start()
                    }
                }

                return@setOnTouchListener true
            }
        }

        fun bind(item: ComicNode?) {
            if (item != null) {
                Glide.fetch(item.smallImageURL!!, item.smallImageURL!!, nodeImage)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row_connection, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(viewModel.getComicNodesAt(position))
    }

    override fun getItemCount(): Int {
        return viewModel.getComicNodesCount()
    }
}