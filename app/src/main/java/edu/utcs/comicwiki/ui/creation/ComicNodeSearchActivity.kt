package edu.utcs.comicwiki.ui.creation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.utcs.comicwiki.R
import edu.utcs.comicwiki.ui.search.SearchViewModel

class ComicNodeSearchActivity : AppCompatActivity() {
    companion object {
        const val nameKey = "nameKey"
        const val deckKey = "largeImageURLKey"
        const val smallImageURLKey = "smallImageURLKey"
        const val largeImageURLKey = "largeImageURLKey"
        const val apiDetailURLKey = "apiDetailURLKey"
    }

    private val searchViewModel = SearchViewModel()
    private lateinit var searchAdapter: ComicNodeSearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initView()
        initObservers()
        initActionSearch()
    }

    private fun initActionSearch() {
        val search = findViewById<EditText>(R.id.action_search)
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
                searchViewModel.netFetch_SearchCharacter(s.toString())
            }
        })
    }

    private fun initObservers() {
        searchViewModel.observeSearchResult().observe(this, Observer {
            searchAdapter.notifyDataSetChanged()
        })
    }

    private fun initView() {
        val rv_search = findViewById<RecyclerView>(R.id.rv_connection)
        searchAdapter = ComicNodeSearchAdapter(searchViewModel, this)
        rv_search.adapter = searchAdapter
        rv_search.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}