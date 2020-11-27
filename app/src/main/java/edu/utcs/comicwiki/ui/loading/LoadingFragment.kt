package edu.utcs.comicwiki.ui.loading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import edu.utcs.comicwiki.R

class LoadingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_loading, container, false)

        initView(root)
        initObservers()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)?.visibility = View.INVISIBLE
        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav_view)?.visibility = View.INVISIBLE
    }

    private fun initObservers() {

    }

    private fun initView(root: View) {
        val loadingGIF = root.findViewById<WebView>(R.id.loadingGIF)
//        val url = "https://i2.wp.com/boingboing.net/wp-content/uploads/2015/10/pJReN4H1.gif?w=970"
//        Glide.fetch(url, url, test)
        val url = "https://i2.wp.com/boingboing.net/wp-content/uploads/2015/10/pJReN4H1.gif?w=970"
        loadingGIF.loadUrl(url)
    }
}