package edu.utcs.comicwiki.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.utcs.comicwiki.R

class HomeFragment : Fragment() {

    private lateinit var teamListAdapter: TeamListAdapter
    private lateinit var characterAdapter: CharacterListAdapter
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        initView(root)
        initObservers()

        return root
    }

    private fun initObservers() {
        homeViewModel.observeTeamList().observe(viewLifecycleOwner, Observer {
            teamListAdapter.notifyDataSetChanged()
        })

        homeViewModel.observeCharacterList().observe(viewLifecycleOwner, Observer {
            characterAdapter.notifyDataSetChanged()
        })
    }

    private fun initView(root: View) {
        // characterList
        val characterList_rv = root.findViewById<RecyclerView>(R.id.rv_characters)
        characterAdapter = CharacterListAdapter(homeViewModel)
        characterList_rv.adapter = characterAdapter
        characterList_rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // teamList
        val teamList_rv = root.findViewById<RecyclerView>(R.id.rv_teams)
        teamListAdapter = TeamListAdapter(homeViewModel)
        teamList_rv.adapter = teamListAdapter
        teamList_rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }
}