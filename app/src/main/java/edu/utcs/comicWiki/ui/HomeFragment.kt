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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import edu.utcs.comicWiki.R

class HomeFragment : Fragment() {
    // TODO: static var/method
    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    // TODO: references
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var teamListAdapter: TeamListAdapter
    private lateinit var characterAdapter: CharacterListAdapter

    //TODO: data observer
    private fun initObservers() {
        viewModel.observeTeamList().observe(viewLifecycleOwner, Observer {
            teamListAdapter.notifyDataSetChanged()
        })

        viewModel.observeCharacterList().observe(viewLifecycleOwner, Observer {
            characterAdapter.notifyDataSetChanged()
        })
    }

    // TODO: set up adapter
    private fun initView(root: View) {
        // TODO: characterList
        val characterList_rv = root.findViewById<RecyclerView>(R.id.characterList_rv)
        characterAdapter = CharacterListAdapter(viewModel)
        characterList_rv.adapter = characterAdapter
        characterList_rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // TODO: teamList
        val teamList_rv = root.findViewById<RecyclerView>(R.id.teamList_rv)
        teamListAdapter = TeamListAdapter(viewModel)
        teamList_rv.adapter = teamListAdapter
        teamList_rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

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
        val root = inflater.inflate(R.layout.home_fragment, container, false)

        viewModel.netFetchTeamList()
        viewModel.netFetchCharacterList()

        initView(root)
        initObservers()

        return root
    }
}