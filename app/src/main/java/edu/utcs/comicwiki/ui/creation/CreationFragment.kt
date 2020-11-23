package edu.utcs.comicwiki.ui.creation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import edu.utcs.comicwiki.R
import edu.utcs.comicwiki.R.color.design_default_color_primary_dark
import edu.utcs.comicwiki.glide.Glide
import edu.utcs.comicwiki.model.ComicNode
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.imageURLKey
import kotlinx.android.synthetic.main.fragment_creation.*

class CreationFragment : Fragment() {

    companion object {
        const val CENTER_RC = 0
        const val FROM_RC = 1
        const val TO_RC = 2
    }

    private val creationViewModel: CreationViewModel by activityViewModels()
    private var tempNode = ComicNode()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_creation, container, false)

        initView(root)

        return root
    }

    private fun initView(root: View) {
        val centerNode = root.findViewById<ImageButton>(R.id.centerNode)
        val fromNode1 = root.findViewById<ImageButton>(R.id.fromNode1)
        val toNode1 = root.findViewById<ImageButton>(R.id.toNode1)
        val clear = root.findViewById<Button>(R.id.clear)
        val save = root.findViewById<Button>(R.id.save)

        centerNode.setOnClickListener {
            injectComicNode(this.requireContext(), CENTER_RC)
        }
        fromNode1.setOnClickListener {
            tempNode.fromNode = ComicNode()
            injectComicNode(this.requireContext(), FROM_RC)
        }
        toNode1.setOnClickListener {
            tempNode.toNode = ComicNode()
            injectComicNode(this.requireContext(), TO_RC)
        }

        clear.setOnClickListener {
            centerNode.setImageBitmap(null)
            fromNode1.setImageBitmap(null)
            toNode1.setImageBitmap(null)
        }
        save.setOnClickListener {
            creationViewModel.addComicNode(tempNode)
        }

    }

    fun injectComicNode(context: Context, requestCode: Int) {
        val intent = Intent(context, ComicNodeSearchActivity::class.java)
        startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CENTER_RC -> data?.extras?.apply {
                val url = this.getString(imageURLKey)
                Glide.fetch(url, url, centerNode)
            }
            FROM_RC -> data?.extras?.apply {
                val url = this.getString(imageURLKey)
                Glide.fetch(url, url, fromNode1)
            }
            TO_RC -> data?.extras?.apply {
                val url = this.getString(imageURLKey)
                Glide.fetch(url, url, toNode1)
            }
        }
    }
}