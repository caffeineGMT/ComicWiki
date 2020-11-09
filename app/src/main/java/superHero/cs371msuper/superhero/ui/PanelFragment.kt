package superHero.cs371msuper.superhero.ui

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.panel_fragment.*
import superHero.cs371msuper.superhero.R

class PanelFragment :
    Fragment(R.layout.panel_fragment) {

    companion object {
        fun newInstance() = PanelFragment()
    }
    // Per-fragment viewModel, not shared
    //SSS
    private val viewModel: PanelViewModel by viewModels()
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
        viewModel.observeCatFact().observe(viewLifecycleOwner, Observer {
            tv.text = it
        })
        // When clicked, refresh
        tv.setOnLongClickListener{
            viewModel.netFetchCatFact()
            // also works: return@setOnLongClickListener true
            true
        }
        ib.setOnClickListener {
            viewModel.netFetchImage(ib)
        }
        // Fetch our initial contents
        viewModel.netFetchCatFact()
        viewModel.netFetchImage(ib)
        //EEE // XXX Write me
    }
}
