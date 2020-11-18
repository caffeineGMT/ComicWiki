/**
 * key tasks
 * 1. webview
 * 2. navigation
 * 3. page
 * 4. dynamic layout, animation, spring animation
 */
package edu.utcs.comicWiki.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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
    private lateinit var searchAdapter: SearchAdapter

    //TODO: data observer
    private fun initObservers() {
        viewModel.observeTeamList().observe(viewLifecycleOwner, Observer {
            teamListAdapter.notifyDataSetChanged()
        })

        viewModel.observeCharacterList().observe(viewLifecycleOwner, Observer {
            characterAdapter.notifyDataSetChanged()
        })

        viewModel.observeSearchResult().observe(viewLifecycleOwner, Observer {
            searchAdapter.notifyDataSetChanged()
        })
    }

    // TODO: set up adapter
    private fun initView(root: View) {
        // TODO: search
        val search_rv = root.findViewById<RecyclerView>(R.id.search_rv)
        searchAdapter = SearchAdapter(viewModel)
        search_rv.adapter = searchAdapter
        search_rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

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

        val search = root.findViewById<EditText>(R.id.actionSearch)
        search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                return
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                return
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                viewModel.netFetch_SearchCharacter(s.toString())
            }
        })

        return root
    }
}