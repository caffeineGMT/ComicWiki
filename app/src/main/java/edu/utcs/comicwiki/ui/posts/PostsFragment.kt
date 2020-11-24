package edu.utcs.comicwiki.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import edu.utcs.comicwiki.R
import edu.utcs.comicwiki.ui.creation.CreationViewModel

class PostsFragment : Fragment() {

    private val postsViewModel: CreationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_posts, container, false)

        initView(root)
        initObservers()

        return root
    }

    private fun initObservers() {

    }

    private fun initView(root: View) {

    }
}