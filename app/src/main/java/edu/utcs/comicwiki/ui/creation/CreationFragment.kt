package edu.utcs.comicwiki.ui.creation

import android.annotation.SuppressLint
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
import edu.utcs.comicwiki.model.ComicNode

class CreationFragment : Fragment() {

    private val creationViewModel: CreationViewModel by activityViewModels()
    private var tempNode = ComicNode(null, null, null, null, null, null)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_creation, container, false)

        initView(root)

        return root
    }

    @SuppressLint("ResourceAsColor")
    private fun initView(root: View) {
        val centerNode = root.findViewById<ImageButton>(R.id.centerNode)
        val fromNode1 = root.findViewById<ImageButton>(R.id.fromNode1)
        val toNode1 = root.findViewById<ImageButton>(R.id.toNode1)
        val clear = root.findViewById<Button>(R.id.clear)
        val save = root.findViewById<Button>(R.id.save)

        centerNode.setOnClickListener {
            // TODO: setting a intent here
            centerNode.setBackgroundColor(design_default_color_primary_dark)
            tempNode.name = "this is center node name"
//            println(tempNode.name)
            //
        }
        fromNode1.setOnClickListener {
            tempNode.fromNode = ComicNode(null, null, "this if from Node name", null, null, null)
//            println(tempNode.name + tempNode.fromNode!!.name)
        }
        toNode1.setOnClickListener {
            tempNode.toNode = ComicNode(null, null, "this if to Node name", null, null, null)
//            println(tempNode.name + tempNode.fromNode!!.name + tempNode.toNode!!.name)
        }

        clear.setOnClickListener {
            creationViewModel.debug()
        }
        save.setOnClickListener {
//            println(tempNode.name + tempNode.fromNode!!.name + tempNode.toNode!!.name)
            creationViewModel.addComicNode(tempNode)
        }

    }
}