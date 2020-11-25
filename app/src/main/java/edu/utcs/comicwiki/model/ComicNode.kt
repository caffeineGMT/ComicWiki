package edu.utcs.comicwiki.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class ComicNode(
    var selfID: String = "",
    var ownerUID: String = "",
    @ServerTimestamp val timeStamp: Timestamp? = null,
    var fromNode: ComicNode? = null,
    var toNode: ComicNode? = null,
    var name: String = "",
    var deck: String? = null,
    var smallImageURL: String = "",
    var largeImageURL: String = "",
    var apiDetailURL: String = ""
)