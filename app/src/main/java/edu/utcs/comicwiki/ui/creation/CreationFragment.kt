package edu.utcs.comicwiki.ui.creation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.firebase.auth.FirebaseAuth
import edu.utcs.comicwiki.R
import edu.utcs.comicwiki.glide.Glide
import edu.utcs.comicwiki.model.ComicNode
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.apiDetailURLKey
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.deckKey
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.largeImageURLKey
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.nameKey
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.smallImageURLKey
import edu.utcs.comicwiki.ui.posts.PostsFragment
import kotlinx.android.synthetic.main.fragment_creation.*

class CreationFragment : Fragment() {
    companion object {
        const val CENTER_RC = 1
        fun newInstance(): CreationFragment {
            return CreationFragment()
        }
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
        val centerNode = root.findViewById<ImageButton>(R.id.centerNodeImage)
        val clear = root.findViewById<Button>(R.id.clear)
        val save = root.findViewById<Button>(R.id.save)

        centerNode.setOnClickListener {
            injectComicNode(requireContext(), CENTER_RC)
        }
        clear.setOnClickListener {
            centerNode.setImageBitmap(null)
            curNode = ComicNode()
        }
        save.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser == null) {
                val text = "You have to log in first before saving any content."
                Toast.makeText(context, text, Toast.LENGTH_LONG).show()
            }
            else {
                creationViewModel.saveComicNode(curNode)
                creationViewModel.getUserComicNodes()
                creationViewModel.getGlobalComicNodes()
                val text = "Successfully saved."
                Toast.makeText(context, text, Toast.LENGTH_LONG).show()
            }
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

                    Glide.fetch(largeImageURL, largeImageURL, centerNodeImage)
                    centerNodeName.text = name
                    centerNodeDeck.text = deck
                    curNode.apply {
                        this.name = name
                        this.deck = deck
                        this.smallImageURL = smallImageURL
                        this.largeImageURL = largeImageURL
                        this.apiDetailURL = apiDetailURL
                    }
                }
            }
        }
    }
}