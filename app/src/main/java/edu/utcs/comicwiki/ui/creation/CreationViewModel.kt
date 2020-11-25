package edu.utcs.comicwiki.ui.creation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import edu.utcs.comicwiki.model.ComicNode

class CreationViewModel : ViewModel() {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val comicNodes = MutableLiveData<List<ComicNode>>()

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

    fun saveComicNode(comicNode: ComicNode) {
        comicNode.selfID = db.collection("globalComicNodes").document().id
        db.collection("globalComicNodes")
            .document(comicNode.selfID)
            .set(comicNode)
            .addOnSuccessListener {
            }
            .addOnFailureListener { e ->
            }
    }

    fun deleteComicNode(comicNode: ComicNode) {
        db.collection("globalComicNodes")
            .document(comicNode.selfID)
            .delete()
            .addOnSuccessListener {
            }
            .addOnFailureListener { e ->
            }
    }

    fun getComicNodes() {
//        if (FirebaseAuth.getInstance().currentUser == null) {
//            Log.d(javaClass.simpleName, "Can't get chat, no one is logged in")
//            comicNodes.value = listOf()
//            return
//        }
        db.collection("globalComicNodes")
            .orderBy("timeStamp")
            .limit(100)
            .addSnapshotListener { querySnapshot, ex ->
                if (ex != null) {
                    return@addSnapshotListener
                }
                comicNodes.value = querySnapshot?.documents?.mapNotNull {
                    it.toObject(ComicNode::class.java)
                }
            }
    }

    fun moveComicNode(from: Int, to: Int) {
        if (from == to) return
        val local = comicNodes.value?.toMutableList()
        local?.let {
            val fromComicNode = it[from]
            it.removeAt(from)
            if (to < from) {
                it.add(to, fromComicNode)
            } else {
                it.add(to - 1, fromComicNode)
            }
        }
    }
}