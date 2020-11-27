package edu.utcs.comicwiki.ui.posts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import edu.utcs.comicwiki.R
import edu.utcs.comicwiki.ui.creation.CreationViewModel

class PostsFragment : Fragment() {

    private val creationViewModel: CreationViewModel by activityViewModels()
    private lateinit var connectionAdapter: ConnectionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_posts, container, false)

        initView(root)
        initObservers()

        return root
    }

    override fun onResume() {
        super.onResume()
        creationViewModel.getComicNodes()
    }

    private fun initObservers() {
        creationViewModel.observeComicNodes().observe(viewLifecycleOwner, Observer {
            Log.d(javaClass.simpleName,it.size.toString())
            connectionAdapter.notifyDataSetChanged()
        })
    }

    private fun initView(root: View) {
        val itemTouchHelper = initTouchHelper()
        connectionAdapter = ConnectionAdapter(creationViewModel, itemTouchHelper)
        val rv_myComicNodes = root.findViewById<RecyclerView>(R.id.rv_myComicNodes)

        rv_myComicNodes.adapter = connectionAdapter
        rv_myComicNodes.layoutManager = GridLayoutManager(context, 7)
        itemTouchHelper.attachToRecyclerView(rv_myComicNodes)
    }

    private fun initTouchHelper(): ItemTouchHelper {
        val simpleItemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(
                UP or DOWN or START or END,
                ItemTouchHelper.START
            ) {
                override fun onSelectedChanged(
                    viewHolder: RecyclerView.ViewHolder?,
                    actionState: Int
                ) {
                    super.onSelectedChanged(viewHolder, actionState)
                    if (actionState == ACTION_STATE_DRAG) {
                        viewHolder?.itemView?.alpha = 0.5f
                    }
                }

                override fun clearView(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder
                ) {
                    super.clearView(recyclerView, viewHolder)
                    viewHolder.itemView.alpha = 1.0f
                }

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    val from = viewHolder.adapterPosition
                    val to = target.adapterPosition

                    creationViewModel.moveComicNode(from, to)
                    connectionAdapter.notifyItemMoved(from, to)

                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                }
            }
        return ItemTouchHelper(simpleItemTouchCallback)
    }
}