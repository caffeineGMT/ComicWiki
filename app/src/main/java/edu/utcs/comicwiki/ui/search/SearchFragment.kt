package edu.utcs.comicwiki.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.utcs.comicwiki.MainActivity
import edu.utcs.comicwiki.R

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        searchViewModel = SearchViewModel()
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
                if(s.isEmpty())
                    (activity as MainActivity).hideKeyboard()
                searchViewModel.netFetch_SearchCharacter(s.toString())
            }
        })
    }

    private fun initObservers() {
        searchViewModel.observeSearchResult().observe(viewLifecycleOwner, Observer {
            searchAdapter.notifyDataSetChanged()
        })
    }

    private fun initView(root: View) {
        val rv_search = root.findViewById<RecyclerView>(R.id.rv_search)
        searchAdapter = SearchAdapter(searchViewModel)
        rv_search.adapter = searchAdapter
        rv_search.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}