package edu.utcs.comicwiki.ui.creation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.utcs.comicwiki.model.ComicNode

class CreationViewModel : ViewModel() {
    private val nodes = MutableLiveData<List<ComicNode>>().apply {
        value = mutableListOf()
    }

    fun addComicNode(comicNode: ComicNode) {
        val local = nodes.value?.toMutableList()
        local?.let {
            local.add(comicNode)
            nodes.value = it
        }
        Log.d(javaClass.simpleName, nodes.value?.size.toString())
    }

}