package edu.utcs.comicwiki.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import edu.utcs.comicwiki.R
import edu.utcs.comicwiki.ui.creation.CreationViewModel

class CollectionFragment : Fragment() {

    private val postsViewModel: CreationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_collection, container, false)

        initView(root)
        initObservers()

        return root
    }

    private fun initObservers() {

    }

    private fun initView(root: View) {
        val test = root.findViewById<WebView>(R.id.test)
//        val url = "https://i2.wp.com/boingboing.net/wp-content/uploads/2015/10/pJReN4H1.gif?w=970"
//        Glide.fetch(url, url, test)
        val a = "https://i2.wp.com/boingboing.net/wp-content/uploads/2015/10/pJReN4H1.gif?w=970"
        test.loadUrl(a)
    }
}