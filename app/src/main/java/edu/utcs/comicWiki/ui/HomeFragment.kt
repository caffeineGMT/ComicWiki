package edu.utcs.comicWiki.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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

    // TODO: private vars
    private lateinit var swipe: SwipeRefreshLayout

    // TODO: data observer

    // TODO: action bar interaction

    // TODO: adapter

    // TODO: swipe
    private fun initSwipeLayout(root: View) {
        swipe = root.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)
        swipe.setOnRefreshListener {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // TODO: fragment
        val root = inflater.inflate(R.layout.home_fragment, container, false)

        // TODO: viewModel observers

        // TODO: UI interactions

        return root
    }
}