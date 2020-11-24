package edu.utcs.comicwiki.ui.connection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.utcs.comicwiki.R
import edu.utcs.comicwiki.ui.creation.CreationViewModel

class ConnectionFragment : Fragment() {

    private val creationViewModel: CreationViewModel by activityViewModels()
    private lateinit var connectionAdapter: ConnectionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_connection, container, false)

        initView(root)
        initObservers()

        return root
    }

    private fun initObservers() {
        creationViewModel.observeComicNodes().observe(viewLifecycleOwner, Observer {
            connectionAdapter.notifyDataSetChanged()
        })
    }

    private fun initView(root: View) {
        val rv_connection = root.findViewById<RecyclerView>(R.id.rv_connection)
        connectionAdapter = ConnectionAdapter(creationViewModel)
        rv_connection.adapter = connectionAdapter
        rv_connection.layoutManager = GridLayoutManager(context, 5)
    }

//    private val itemTouchHelper by lazy {
//        val simpleItemTouchCallback =
//            object : ItemTouchHelper.SimpleCallback(
//                UP or
//                        DOWN or
//                        START or
//                        END, 0
//            ) {
//                override fun onMove(
//                    recyclerView: RecyclerView,
//                    viewHolder: RecyclerView.ViewHolder,
//                    target: RecyclerView.ViewHolder
//                ): Boolean {
//
//                    val adapter = recyclerView.adapter as MainRecyclerViewAdapter
//                    val from = viewHolder.adapterPosition
//                    val to = target.adapterPosition
//                    // 2. Update the backing model. Custom implementation in
//                    //    MainRecyclerViewAdapter. You need to implement
//                    //    reordering of the backing model inside the method.
//                    adapter.moveItem(from, to)
//                    // 3. Tell adapter to render the model update.
//                    adapter.notifyItemMoved(from, to)
//
//                    return true
//                }
//
//                override fun onSwiped(
//                    viewHolder: RecyclerView.ViewHolder,
//                    direction: Int
//                ) {
//                }
//            }
//        ItemTouchHelper(simpleItemTouchCallback)
//    }
}