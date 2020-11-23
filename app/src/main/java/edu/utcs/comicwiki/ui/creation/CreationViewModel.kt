package edu.utcs.comicwiki.ui.creation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.utcs.comicwiki.model.ComicNode

class CreationViewModel : ViewModel() {
    private val nodes = mutableListOf<ComicNode>()

    fun addComicNode(comicNode: ComicNode) {



        nodes.add(comicNode)
        Log.d(javaClass.simpleName, nodes[0].name)
    }

    fun debug() {
//        println(nodes.value?.get(0)?.name + nodes.value?.get(0)?.fromNode?.name + nodes.value?.get(0)?.toNode?.name)
    }

}