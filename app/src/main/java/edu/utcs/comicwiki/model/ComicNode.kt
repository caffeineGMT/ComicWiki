package edu.utcs.comicwiki.model

data class ComicNode(
    var fromNode: ComicNode?,
    var toNode: ComicNode?,
    var name: String?,
    val image: Image?,
    val deck: String?,
    val apiDetailURL: String?
)