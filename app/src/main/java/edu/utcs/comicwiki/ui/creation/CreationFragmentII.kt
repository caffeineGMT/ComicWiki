package edu.utcs.comicwiki.ui.creation

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityManager
import android.widget.*
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.kapil.circularlayoutmanager.*
import edu.utcs.comicwiki.R
import edu.utcs.comicwiki.glide.Glide
import edu.utcs.comicwiki.model.ComicNode
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.apiDetailURLKey
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.deckKey
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.largeImageURLKey
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.nameKey
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.smallImageURLKey
import kotlinx.android.synthetic.main.fragment_creation.*
import kotlinx.android.synthetic.main.fragment_test.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*


class CreationFragmentII : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_test, container, false)


        initializeRecyclerView(root)
        initializeScrollWheel(root)


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addItemButton.setOnClickListener { addItemToList() }
        scrollWheelToggleButton.setOnClickListener { toggleScrollWheel() }
        smoothScrollTestBtn.setOnClickListener {
            if (positionInput.text.isNullOrBlank())
                showMessage("Enter an index")
            else
                recyclerView.smoothScrollToPosition(positionInput.text.toString().toInt())
        }
        scrollTestBtn.setOnClickListener {
            if (positionInput.text.isNullOrBlank())
                showMessage("Enter an index")
            else
                recyclerView.scrollToPosition(positionInput.text.toString().toInt())
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

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        when (requestCode) {
//            CENTER_NODE_RC -> {
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
//            }
//            ADD_NODE_RC -> {
//                data?.extras?.apply {
//                    val name = getString(nameKey)
//                    val deck = getString(deckKey)
//                    val smallImageURL = getString(smallImageURLKey)
//                    val largeImageURL = getString(largeImageURLKey)
//                    val apiDetailURL = getString(apiDetailURLKey)
//
//                    val tempNode = ComicNode()
//                    tempNode.apply {
//                        this.name = name
//                        this.deck = deck
//                        this.smallImageURL = smallImageURL
//                        this.largeImageURL = largeImageURL
//                        this.apiDetailURL = apiDetailURL
//                    }
//                    viewModel.setRelatedNodes(tempNode)
//                }
//            }
//        }
//    }
    private fun initializeRecyclerView(root: View) {
        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = RecyclerViewAdapter().apply {
            submitList(getInitialList())
            onItemClickListener = { showMessage(event) }
        }
        recyclerView.addItemDecoration(RecyclerItemDecoration())

//        recyclerView.layoutManager = CircularLayoutManager(
//            resources.getDimension(R.dimen.circular_list_radius),
//            resources.getDimension(R.dimen.circular_list_center_x)
//        ).apply {
//            scalingFactor = 1f
//            shouldIgnoreHeaderAndFooterMargins = true
//            shouldCenterIfProgrammaticallyScrolled = true
//            isAutoStabilizationEnabled = true
//        }
    }

    private fun initializeScrollWheel(root: View) {
        val scrollWheel = root.findViewById<ScrollWheel>(R.id.scrollWheel)
        scrollWheel.isEnabled = false
        scrollWheel.isHighlightTouchAreaEnabled = false
//        scrollWheel.isHandleClicksEnabled = false
        scrollWheel.onItemClickListener = { x, y ->
            val index = recyclerView.getChildAdapterPosition(x, y)
            if (index != INVALID_INDEX) showMessage("onClick " + getList()[index].event)
        }
        scrollWheel.onItemLongClickListener = { x, y ->
            val index = recyclerView.getChildAdapterPosition(x, y)
            if (index != INVALID_INDEX) showMessage("onLongClick " + getList()[index].event)
        }
        scrollWheel.onScrollListener = { recyclerView.scrollBy(0, it.toInt()) }
        scrollWheel.onFlingListener = {
            // Since onTouchReleasedListener would be called before onFlingListener, we'll first
            // have to stop the ongoing stabilization before we could initiate the fling.
            recyclerView.stopScroll()
            recyclerView.fling(0, it.toInt())
        }
        scrollWheel.onTouchReleasedListener = {
            // Calling stabilize externally as scrolling and flinging is controlled by the
            // ScrollWheel. If RecyclerView.scrollBy method was not used, the external call to
            // stabilize is not necessary.
            recyclerView.circularLayoutManager!!.stabilize()
        }
    }

    private fun addItemToList() {
        (recyclerView.adapter as RecyclerViewAdapter).apply {
            submitList(currentList.toMutableList().apply {
                add(Model(size + 1, "Event ${size + 1}", "12:00am - 12:00pm"))
            }) { recyclerView.invalidateItemDecorations() }
        }
    }
    private fun toggleScrollWheel() {
        scrollWheel.isEnabled = !scrollWheel.isEnabled
        scrollWheel.isHighlightTouchAreaEnabled = !scrollWheel.isHighlightTouchAreaEnabled
    }

    private fun getInitialList() = (1..10).map { Model(it, "Event $it", "12:00am - 12:00pm") }

    private fun getList() = (recyclerView.adapter as RecyclerViewAdapter).currentList

    private fun showMessage(msg: String) =
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}