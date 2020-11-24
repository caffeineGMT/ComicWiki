package edu.utcs.comicwiki.ui.connection

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.utcs.comicwiki.MainActivity
import edu.utcs.comicwiki.R
import edu.utcs.comicwiki.ui.creation.CreationViewModel
import edu.utcs.comicwiki.ui.search.SearchAdapter
import edu.utcs.comicwiki.ui.search.SearchViewModel

class ConnectionFragment: Fragment() {

    private val creationViewModel: CreationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_connection, container, false)

        initView(root)
        initObservers()

        return root
    }

    private fun initObservers() {

    }

    private fun initView(root: View) {

    }
}