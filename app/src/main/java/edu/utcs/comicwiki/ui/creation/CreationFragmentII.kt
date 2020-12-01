package edu.utcs.comicwiki.ui.creation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import edu.utcs.comicwiki.R
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
        const val ADD_NODE_RC = 2
        fun newInstance(): CreationFragmentII {
            return CreationFragmentII()
        }
    }

    private val viewModel: CreationViewModel by activityViewModels()

    //    private lateinit var relatedNodesAdapter: RelatedNodesAdapter
    private var curNode = ComicNode()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerView()
        addItemButton.setOnClickListener {
            addItemToList()
        }
    }

//    private fun initView(root: View) {
//
//        val relatedNodes_rv = root.findViewById<RecyclerView>(R.id.rv_relatedNodes)
//        relatedNodesAdapter = RelatedNodesAdapter(viewModel)
//        relatedNodes_rv.adapter = relatedNodesAdapter
////        relatedNodes_rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//        relatedNodes_rv.layoutManager = CircularLayoutManager(10f,10f)
//
//        val addNode = root.findViewById<ImageButton>(R.id.addNode)
//        val centerNode = root.findViewById<ImageView>(R.id.centerNodeImage)
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

//    private fun injectComicNode(context: Context, requestCode: Int) {
//        val intent = Intent(context, ComicNodeSearchActivity::class.java)
//        startActivityForResult(intent, requestCode)
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CENTER_NODE_RC -> {
//                centerNodeImage.setBackgroundColor(Color.WHITE)
//                data?.extras?.apply {
//                    val name = getString(nameKey)
//                    val deck = getString(deckKey)
//                    val smallImageURL = getString(smallImageURLKey)
//                    val largeImageURL = getString(largeImageURLKey)
//                    val apiDetailURL = getString(apiDetailURLKey)
//
//                    Glide.fetch(largeImageURL, largeImageURL, centerNodeImage)
//                    centerNodeName.text = name
//                    centerNodeDeck.text = deck
//                    curNode.apply {
//                        this.name = name
//                        this.deck = deck
//                        this.smallImageURL = smallImageURL
//                        this.largeImageURL = largeImageURL
//                        this.apiDetailURL = apiDetailURL
//                    }
//                }
            }
            ADD_NODE_RC -> {
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
                    rv.adapter?.notifyDataSetChanged()
                }
            }
        }
    }
    private fun initializeRecyclerView() {
        rv.adapter = RecyclerViewAdapter(viewModel).apply {
//            submitList(getInitialList())
            onItemClickListener = {
                showMessage(event)
            }
        }
        rv.addItemDecoration(RecyclerItemDecoration())
    }

    private fun addItemToList() {
//        (rv.adapter as RecyclerViewAdapter).apply {
//            submitList(currentList.toMutableList().apply {
//                add(Model(size + 1, "Event ${size + 1}", "12:00am - 12:00pm"))
//            }) { rv.invalidateItemDecorations() }
//        }
        val intent = Intent(requireContext(), ComicNodeSearchActivity::class.java)
        startActivityForResult(intent, ADD_NODE_RC)
    }

    private fun showMessage(msg: String) =
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}