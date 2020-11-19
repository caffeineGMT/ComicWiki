package edu.utcs.comicWiki.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private lateinit var teamMemberAdapter: TeamMemberAdapter
    private lateinit var friendsAdapter: TeamMemberAdapter

    //TODO: data observer
    private fun initObservers() {
        viewModel.observeTeam().observe(viewLifecycleOwner, Observer {
            viewModel.netFetch_teamMembers()
        })
        viewModel.observeTeamMembers().observe(viewLifecycleOwner, Observer {
            teamMemberAdapter.notifyDataSetChanged()
        })

    }

    // TODO: set up adapter
    private fun initView(root: View) {
        // TODO: teamMembers
        val teamMembers_rv = root.findViewById<RecyclerView>(R.id.teamMembers_rv)
        teamMemberAdapter = TeamMemberAdapter(viewModel)
        teamMembers_rv.adapter = teamMemberAdapter
        teamMembers_rv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)


        // TODO: action bar
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.team_fragment, container, false)

        initView(root)
        initObservers()

        return root
    }
}