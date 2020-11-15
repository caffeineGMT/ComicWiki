package edu.utcs.comicWiki.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import edu.utcs.comicWiki.R

class CharacterFragment : Fragment() {
    // TODO: static var/method
    companion object {
        fun newInstance(): CharacterFragment {
            return CharacterFragment()
        }
    }

    // TODO: references
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: CharacterAdapter

    //TODO: data observer
    private fun initObservers() {
        viewModel.observeCharacterDeck().observe(viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()
        })

    }

    // TODO: set up adapter
    private fun initView(root: View) {
        // TODO: adpater
        val rv = root.findViewById<RecyclerView>(R.id.recyclerView)
        adapter = CharacterAdapter(viewModel)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(context)

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
        val root = inflater.inflate(R.layout.character_fragment, container, false)

        viewModel.netFetchCharacters()

        initView(root)
        initObservers()

        return root
    }
}