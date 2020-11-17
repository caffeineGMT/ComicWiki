package edu.utcs.comicWiki.ui

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.panel_fragment.*
import edu.utcs.comicWiki.R
import edu.utcs.comicWiki.glide.Glide

class PanelFragment :
    Fragment(R.layout.panel_fragment) {

    companion object {
        fun newInstance() = PanelFragment()
    }
    // Per-fragment viewModel, not shared
    //SSS
    private val viewModel: MainViewModel by viewModels()
    //RRR
    // // XXX Initialize me
    // private val viewModel: PanelViewModel
    //EEE


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tv.movementMethod = ScrollingMovementMethod()
        // Runtime figures out how big our imagebutton is at runtime.
        // Tell the viewmodel so we fetch the right sized images
        ib.addOnLayoutChangeListener { _, left, top, right, bottom, _, _, _, _ ->
            val width = right - left
            val height = bottom - top
            //Log.d(javaClass.simpleName, "ib layout width $width height $height")
            viewModel.width = width
            viewModel.height = height
        }
        //SSS
        viewModel.observeCharacterList().observe(viewLifecycleOwner, Observer {
            tv.text = it[0].deck
            Log.d(javaClass.simpleName, it[0].image.imageURL)
            Glide.fetch(it[0].image.imageURL,it[0].image.imageURL,ib)
        })
        // When clicked, refresh
        tv.setOnLongClickListener{
            viewModel.netFetchCharacterList()
            // also works: return@setOnLongClickListener true
            true
        }
        ib.setOnClickListener {
            viewModel.netFetchImage(ib)
        }
        // Fetch our initial contents
        viewModel.netFetchCharacterList()
        viewModel.netFetchImage(ib)
        //EEE // XXX Write me
    }
}
