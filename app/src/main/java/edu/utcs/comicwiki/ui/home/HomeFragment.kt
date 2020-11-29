package edu.utcs.comicwiki.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.utcs.comicwiki.R

class HomeFragment : Fragment() {
    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    private lateinit var teamsAdapter: TeamsAdapter
    private lateinit var charactersAdapter: CharactersAdapter
    private lateinit var objectsAdapter: ObjectsAdapter
    private lateinit var locationsAdapter: LocationsAdapter
    private lateinit var powersAdapter: PowersAdapter
    private lateinit var conceptsAdapter: ConceptsAdapter
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
            teamsAdapter.notifyDataSetChanged()
        })
        homeViewModel.observeCharacterList().observe(viewLifecycleOwner, Observer {
            charactersAdapter.notifyDataSetChanged()
        })
        homeViewModel.observeCharacterList().observe(viewLifecycleOwner, Observer {
            objectsAdapter.notifyDataSetChanged()
        })
        homeViewModel.observeCharacterList().observe(viewLifecycleOwner, Observer {
            locationsAdapter.notifyDataSetChanged()
        })
        homeViewModel.observeCharacterList().observe(viewLifecycleOwner, Observer {
            conceptsAdapter.notifyDataSetChanged()
        })
    }

    private fun initView(root: View) {
        // characterList
        val characterList_rv = root.findViewById<RecyclerView>(R.id.rv_characters)
        charactersAdapter = CharactersAdapter(homeViewModel)
        characterList_rv.adapter = charactersAdapter
        characterList_rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val concept_rv = root.findViewById<RecyclerView>(R.id.rv_concepts)
        conceptsAdapter = ConceptsAdapter(homeViewModel)
        concept_rv.adapter = conceptsAdapter
        concept_rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val teamList_rv = root.findViewById<RecyclerView>(R.id.rv_teams)
        teamsAdapter = TeamsAdapter(homeViewModel)
        teamList_rv.adapter = teamsAdapter
        teamList_rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val locations_rv = root.findViewById<RecyclerView>(R.id.rv_locations)
        locationsAdapter = LocationsAdapter(homeViewModel)
        locations_rv.adapter = locationsAdapter
        locations_rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val objects_rv = root.findViewById<RecyclerView>(R.id.rv_objects)
        objectsAdapter = ObjectsAdapter(homeViewModel)
        objects_rv.adapter = objectsAdapter
        objects_rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

//        val power_rv = root.findViewById<RecyclerView>(R.id.rv_powers)
//        powersAdapter = PowersAdapter(homeViewModel)
//        power_rv.adapter = powersAdapter
//        power_rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)


    }
}