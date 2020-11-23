package edu.utcs.comicwiki.model

data class ComicNode(
    var fromNode: ComicNode? = null,
    var toNode: ComicNode? = null,
    var name: String? = null,
    val image: Image? = null,
    val deck: String? = null,
    val apiDetailURL: String? = null
)