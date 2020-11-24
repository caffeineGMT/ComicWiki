package edu.utcs.comicwiki.ui.creation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.utcs.comicwiki.model.Character
import edu.utcs.comicwiki.model.ComicNode

class CreationViewModel : ViewModel() {
    private val comicNodes = MutableLiveData<List<ComicNode>>().apply {
        value = mutableListOf()
    }

    fun addComicNode(comicNode: ComicNode) {
        val local = comicNodes.value?.toMutableList()
        local?.let {
            local.add(comicNode)
            comicNodes.value = it
        }
        Log.d(javaClass.simpleName, comicNodes.value?.size.toString())
    }

    fun observeComicNodes(): LiveData<List<ComicNode>> {
        return comicNodes
    }

    fun getComicNodesAt(position: Int): ComicNode? {
        val local = comicNodes.value?.toList()
        local?.let {
            if (position >= it.size)
                return null
            return it[position]
        }
        return null
    }

    fun getComicNodesCount(): Int {
        return comicNodes.value?.size ?: 0
    }
}