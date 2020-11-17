package edu.utcs.comicWiki.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import edu.utcs.comicWiki.R

class TeamFragment : Fragment() {
    // TODO: static var/method
    companion object {
        fun newInstance(): TeamFragment {
            return TeamFragment()
        }
    }

    // TODO: references
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: TeamAdapter

    //TODO: data observer
    private fun initObservers() {
//        viewModel.observeTeam().observe(viewLifecycleOwner, Observer {
//            adapter.notifyDataSetChanged()
//        })
    }

    // TODO: set up adapter
    private fun initView(root: View) {
        // TODO: adpater
        val rv = root.findViewById<RecyclerView>(R.id.teamMembers_rv)
        adapter = TeamAdapter(viewModel)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // TODO: swipe
        val swipe = root.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)
        swipe.isEnabled = false

        // TODO: action bar
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.team_fragment, container, false)

        viewModel.netFetchTeam()

        initView(root)
        initObservers()

        return root
    }
}