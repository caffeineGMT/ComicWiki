package edu.utcs.comicwiki.ui.creation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.utcs.comicwiki.model.ComicNode

class CreationViewModel : ViewModel() {
    private val nodes = mutableListOf<ComicNode>()

    fun addComicNode(comicNode: ComicNode) {


        nodes.add(comicNode)
//        Log.d(
//            javaClass.simpleName,
//            nodes[0].name + nodes[0].fromNode?.name + nodes[0].toNode?.name
//        )
        Log.d(javaClass.simpleName, nodes.size.toString())
    }

}