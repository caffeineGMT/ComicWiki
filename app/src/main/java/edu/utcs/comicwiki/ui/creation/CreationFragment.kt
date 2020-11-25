package edu.utcs.comicwiki.ui.creation

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
import edu.utcs.comicwiki.glide.Glide
import edu.utcs.comicwiki.model.ComicNode
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.apiDetailURLKey
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.deckKey
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.largeImageURLKey
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.nameKey
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.smallImageURLKey
import kotlinx.android.synthetic.main.fragment_creation.*

class CreationFragment : Fragment() {

    companion object {
        const val CENTER_RC = 0
        const val FROM_RC = 1
        const val TO_RC = 2
    }

    private val creationViewModel: CreationViewModel by activityViewModels()
    private var curNode = ComicNode()

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
            injectComicNode(this.requireContext(), FROM_RC)
        }
        toNode1.setOnClickListener {
            injectComicNode(this.requireContext(), TO_RC)
        }

        clear.setOnClickListener {
            centerNode.setImageBitmap(null)
            fromNode1.setImageBitmap(null)
            toNode1.setImageBitmap(null)

            curNode = ComicNode()
        }
        save.setOnClickListener {
            creationViewModel.saveComicNode(curNode)
        }

    }

    private fun injectComicNode(context: Context, requestCode: Int) {
        val intent = Intent(context, ComicNodeSearchActivity::class.java)
        startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CENTER_RC -> {
                data?.extras?.apply {
                    val name = getString(nameKey)
                    val deck = getString(deckKey)
                    val smallImageURL = getString(smallImageURLKey)
                    val largeImageURL = getString(largeImageURLKey)
                    val apiDetailURL = getString(apiDetailURLKey)

                    Glide.fetch(largeImageURL, largeImageURL, centerNode)
                    curNode.apply {
                        this.name = name
                        this.deck = deck
                        this.smallImageURL = smallImageURL
                        this.largeImageURL = largeImageURL
                        this.apiDetailURL = apiDetailURL
                    }
                }
            }
            FROM_RC -> data?.extras?.apply {
                val name = getString(nameKey)
                val deck = getString(deckKey)
                val smallImageURL = getString(smallImageURLKey)
                val largeImageURL = getString(largeImageURLKey)
                val apiDetailURL = getString(apiDetailURLKey)

                Glide.fetch(smallImageURL, smallImageURL, fromNode1)
                curNode.fromNode =
                    ComicNode()
            }
            TO_RC -> data?.extras?.apply {
                val name = getString(nameKey)
                val deck = getString(deckKey)
                val smallImageURL = getString(smallImageURLKey)
                val largeImageURL = getString(largeImageURLKey)
                val apiDetailURL = getString(apiDetailURLKey)

                Glide.fetch(smallImageURL, smallImageURL, toNode1)
                curNode.toNode =
                    ComicNode()
            }
        }
    }
}