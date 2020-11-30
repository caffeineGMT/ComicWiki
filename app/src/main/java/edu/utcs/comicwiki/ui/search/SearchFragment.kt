package edu.utcs.comicwiki.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.utcs.comicwiki.MainActivity
import edu.utcs.comicwiki.R

class SearchFragment : Fragment() {
    companion object {
        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }

    private val viewModel: SearchViewModel by activityViewModels()
    private lateinit var characterAdapter: GenericItemSearchAdapter
    private lateinit var locationAdapter: GenericItemSearchAdapter
    private lateinit var storyArcAdapter: GenericItemSearchAdapter
    private val resourcesList = listOf("character", "location", "story_arc")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_search, container, false)

        initView(root)
        initObservers()
        initActionSearch(root)

        return root
    }

    private fun initActionSearch(root: View) {
        val search = root.findViewById<EditText>(R.id.action_search)
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
                // folding keyboard and clear views
                if (s.isEmpty()) {
                    (context as MainActivity).hideKeyboard()
                    viewModel.netFetch_Search("", "")
                }

                // searching on fly
                viewModel.netFetch_Search(s.toString(),"character")
                viewModel.netFetch_Search(s.toString(),"location")
                viewModel.netFetch_Search(s.toString(),"story_arc")
            }
        })
    }

    private fun initObservers() {

        viewModel.observeSearchResults("character").observe(viewLifecycleOwner, Observer {
            characterAdapter.notifyDataSetChanged()
        })

        viewModel.observeSearchResults("location").observe(viewLifecycleOwner, Observer {
            locationAdapter.notifyDataSetChanged()

        })

        viewModel.observeSearchResults("story_arc").observe(viewLifecycleOwner, Observer {
            storyArcAdapter.notifyDataSetChanged()
        })

    }

    private fun initView(root: View) {
        val rv_characters = root.findViewById<RecyclerView>(R.id.rv_characters)
        characterAdapter = GenericItemSearchAdapter(viewModel, resourcesList[0])
        rv_characters.adapter = characterAdapter
        rv_characters.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val rv_locations = root.findViewById<RecyclerView>(R.id.rv_locations)
        locationAdapter = GenericItemSearchAdapter(viewModel, "location")
        rv_locations.apply {
            adapter = locationAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        val rv_storyArcs = root.findViewById<RecyclerView>(R.id.rv_storyArcs)
        storyArcAdapter = GenericItemSearchAdapter(viewModel, "story_arc")
        rv_storyArcs.apply {
            adapter = storyArcAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}