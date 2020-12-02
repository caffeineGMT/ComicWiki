package edu.utcs.comicwiki.ui.creation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import edu.utcs.comicwiki.R
import edu.utcs.comicwiki.glide.Glide
import edu.utcs.comicwiki.model.ComicNode
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.apiDetailURLKey
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.deckKey
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.largeImageURLKey
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.nameKey
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.smallImageURLKey
import kotlinx.android.synthetic.main.fragment_test.*


class CreationFragmentII : Fragment(R.layout.fragment_test) {
    companion object {
        const val CENTER_NODE_RC = 1
        const val RELATED_NODE_RC = 2
        fun newInstance(): CreationFragmentII {
            return CreationFragmentII()
        }
    }

    private val viewModel: CreationViewModel by activityViewModels()
    private var curNode = ComicNode()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerView()
        initializeActions()
        initializeObservers()
    }

    private fun initializeRecyclerView() {
        rv.adapter = RecyclerViewAdapter(viewModel)
        rv.addItemDecoration(RecyclerItemDecoration())
    }

    private fun initializeObservers() {
        viewModel.observeRelatedNodes().observe(viewLifecycleOwner, Observer {
            rv.adapter?.notifyDataSetChanged()
        })
    }

    private fun initializeActions() {
        addRelatedNode.setOnClickListener {
            injectComicNode(requireContext(), RELATED_NODE_RC)
        }
        addCenterNode.setOnClickListener {
            injectComicNode(requireContext(), CENTER_NODE_RC)
        }
        centerNodeImage.setOnClickListener {
        }
        clearAll.setOnClickListener {
            viewModel.deleteAllRelatedNodes()
            viewModel.deleteCenterNode()
        }
        save.setOnClickListener {

        }
    }

    private fun injectComicNode(context: Context, requestCode: Int) {
        val intent = Intent(context, ComicNodeSearchActivity::class.java)
        startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CENTER_NODE_RC -> {
                data?.extras?.apply {
                    val name = getString(nameKey)
                    val deck = getString(deckKey)
                    val smallImageURL = getString(smallImageURLKey)
                    val largeImageURL = getString(largeImageURLKey)
                    val apiDetailURL = getString(apiDetailURLKey)

                    curNode.apply {
                        this.name = name
                        this.deck = deck
                        this.smallImageURL = smallImageURL
                        this.largeImageURL = largeImageURL
                        this.apiDetailURL = apiDetailURL
                    }

                    // handle UI
                    centerNodeImage.background = null
                    Glide.fetch(largeImageURL, largeImageURL, centerNodeImage)
                    centerNodeImage.clipToOutline = true
                    centerNodeImage.imageAlpha = 100
                    centerNodeName.text = name
                    centerNodeDeck.text = deck
                    Toast.makeText(context, "Center node created", Toast.LENGTH_SHORT).show()
                }
            }
            RELATED_NODE_RC -> {
                data?.extras?.apply {
                    val name = getString(nameKey)
                    val deck = getString(deckKey)
                    val smallImageURL = getString(smallImageURLKey)
                    val largeImageURL = getString(largeImageURLKey)
                    val apiDetailURL = getString(apiDetailURLKey)

                    val tempNode = ComicNode()
                    tempNode.apply {
                        this.name = name
                        this.deck = deck
                        this.smallImageURL = smallImageURL
                        this.largeImageURL = largeImageURL
                        this.apiDetailURL = apiDetailURL
                    }
                    viewModel.addRelatedNode(tempNode)

                    Toast.makeText(context, "Related node created", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }





    //    private fun initView(root: View) {
//        val clear = root.findViewById<Button>(R.id.clear)
//        val save = root.findViewById<Button>(R.id.save)
//        val customizedContent = root.findViewById<EditText>(R.id.customizedContent)
//
//        addNode.setOnClickListener {
//            injectComicNode(requireContext(), ADD_NODE_RC)
//        }
//        centerNode.setOnClickListener {
//            injectComicNode(requireContext(), CENTER_NODE_RC)
//        }
//        clear.setOnClickListener {
//            centerNode.setImageBitmap(null)
//            curNode = ComicNode()
//
//            viewModel.deleteAllRelatedNodes()
//        }
//        save.setOnClickListener {
//            if (FirebaseAuth.getInstance().currentUser == null) {
//                val text = "You have to log in first before saving any content."
//                Toast.makeText(context, text, Toast.LENGTH_LONG).show()
//            } else {
//                curNode.userDescription = customizedContent.text.toString()
////                curNode.relatedNodes = viewModel.getAllRelatedNodes()
//                viewModel.saveComicNode(curNode)
//                viewModel.getUserComicNodes()
//                viewModel.getGlobalComicNodes()
//                val text = "Successfully saved."
//                Toast.makeText(context, text, Toast.LENGTH_LONG).show()
//            }
//        }
//    }
}